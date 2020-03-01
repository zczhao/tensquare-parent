package zzc.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zzc.springcloud.entity.PageResult;
import zzc.springcloud.entity.Result;
import zzc.springcloud.entity.StatusCode;
import zzc.springcloud.pojo.Spit;
import zzc.springcloud.service.SpitService;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

	@Autowired
	private SpitService spitService;

	@Autowired
	private RedisTemplate redisTemplate;

	@GetMapping
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
	}

	@GetMapping("/{id}")
	public Result findById(@PathVariable String id) {
		return new Result(true, StatusCode.OK, "查询成功", spitService.findById(id));
	}

	@PostMapping
	public Result save(@RequestBody Spit spit) {
		spitService.save(spit);
		return new Result(true, StatusCode.OK, "增加成功");
	}

	@PutMapping("/{id}")
	public Result update(@RequestBody Spit spit, @PathVariable String id) {
		spit.set_id(id);
		spitService.update(spit);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	@DeleteMapping("/{id}")
	public Result delete(@PathVariable String id) {
		spitService.deleteById(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}

	@GetMapping("/comment/{parentid}/{page}/{size}")
	public Result findByParentid(@PathVariable String parentid, @PathVariable int page, @PathVariable int size) {
		Page<Spit> pages = spitService.findByParentid(parentid, page, size);
		return new Result(true, StatusCode.OK, "查询成功",
				new PageResult<Spit>(pages.getTotalElements(), pages.getContent()));
	}

	@PutMapping("/thumbup/{spitid}")
	public Result thumbup(@PathVariable String spitid) {
		// 判断当前用户是否已经点赞，但是现在还没有做认证，暂时先把userId固定
		String userId = "1001";
		String redisKey = "thumbup_" + userId + "_" + spitid;
		Object object = redisTemplate.opsForValue().get(redisKey);
		if (object == null) {
			spitService.thumbup(spitid);
			redisTemplate.opsForValue().set(redisKey, 1);
			return new Result(true, StatusCode.OK, "点赞成功");
		} else {
			return new Result(true, StatusCode.REPERROR, "不能重复点赞");
		}
	}
}
