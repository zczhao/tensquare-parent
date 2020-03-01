package zzc.springcloud.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import zzc.springcloud.entity.PageResult;
import zzc.springcloud.entity.Result;
import zzc.springcloud.entity.StatusCode;
import zzc.springcloud.pojo.User;
import zzc.springcloud.service.UserService;
import zzc.springcloud.util.JwtUtil;

/**
 * 控制器层
 * 
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private JwtUtil jwtUtil;
	

	/**
	 * 更新好友粉丝数和用户关注数
	 * @param userid
	 * @param friendid
	 * @param x
	 * @return
	 */
	@PutMapping("/{userid}/{friendid}/{x}")
	public Result updateFanscountAndFollowcount(@PathVariable String userid, @PathVariable String friendid, @PathVariable int x) {
		userService.updateFanscountAndFollowcount(x, userid, friendid);
		return new Result(true, StatusCode.OK, "操作成功");
	}
	
	@PostMapping("/login")
	public Result login(@RequestBody User user) {
		user = userService.login(user);
		if(user == null) {
			return new Result(false, StatusCode.LOGINERROR, "登录失败");
		}
		String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("roles", "user");
		return new Result(true, StatusCode.OK, "登录成功", map);
	}

	/**
	 * 发送验证码
	 * 
	 * @return
	 */
	@PostMapping("/sendsms/{mobile}")
	public Result sendSms(@PathVariable String mobile) {
		userService.sendSms(mobile);
		return new Result(true, StatusCode.OK, "发送成功");
	}

	/**
	 * 用户注册
	 * 
	 * @return
	 */
	@PostMapping("/register/{vcode}")
	public Result register(@PathVariable String vcode, @RequestBody User user) {
		// 得到缓存中的验证码
		String vcodeRedis = (String) redisTemplate.opsForValue().get("vcode_" + user.getMobile());
		if(StringUtils.isEmpty(vcodeRedis)) {
			return new Result(false, StatusCode.ERROR, "请先获取手机验证码");
		}
		if(!StringUtils.equals(vcode, vcodeRedis)) {
			return new Result(false, StatusCode.ERROR, "请输入正确验证码");
		}
		userService.add(user);
		return new Result(true, StatusCode.OK, "注册成功");
	}

	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
	}

	/**
	 * 根据ID查询
	 * 
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Result findById(@PathVariable String id) {
		return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
	}

	/**
	 * 分页+多条件查询
	 * 
	 * @param searchMap 查询条件封装
	 * @param page      页码
	 * @param size      页大小
	 * @return 分页结果
	 */
	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return new Result(true, StatusCode.OK, "查询成功",
				new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
	}

	/**
	 * 根据条件查询
	 * 
	 * @param searchMap
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap) {
		return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
	}

	/**
	 * 增加
	 * 
	 * @param user
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result add(@RequestBody User user) {
		userService.add(user);
		return new Result(true, StatusCode.OK, "增加成功");
	}

	/**
	 * 修改
	 * 
	 * @param user
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id) {
		user.setId(id);
		userService.update(user);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	/**
	 * 删除，必须有admin角色才能删除
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Result delete(@PathVariable String id) {
		/*
		String header = request.getHeader("Authorization");
		if(StringUtils.isEmpty(header)) {
			return new Result(false, StatusCode.LOGINERROR, "权限不足");
		}
		
		// 前后端约定：前端请求微服务时需要添加头信息Authorization，内容为Bearer+空格+token
		if(!header.startsWith("Bearer ")) {
			return new Result(false, StatusCode.LOGINERROR, "权限不足");
		}
		String token = header.substring(7);
		try {
			Claims claims = jwtUtil.parseJWT(token);
			System.out.println("用户id：" + claims.getId());
			System.out.println("用户名：" + claims.getSubject());
			String roles = (String) claims.get("roles");
			if(StringUtils.isEmpty(roles) || !StringUtils.equals("admin", roles)) {
				return new Result(false, StatusCode.LOGINERROR, "权限不足");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, StatusCode.LOGINERROR, "权限不足");
		}
		*/
		Claims claims = (Claims) request.getAttribute("claims_admin");
		if (Objects.isNull(claims)) {
			return new Result(false, StatusCode.LOGINERROR, "权限不足");
		}
		userService.deleteById(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}

}
