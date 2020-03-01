package zzc.springcloud.controller;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import zzc.springcloud.client.BaseClient;
import zzc.springcloud.entity.PageResult;
import zzc.springcloud.entity.Result;
import zzc.springcloud.entity.StatusCode;
import zzc.springcloud.pojo.Problem;
import zzc.springcloud.service.ProblemService;

@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private BaseClient baseClient;

	/**
	 * fegin调用
	 * @param labelId
	 * @return
	 */
	@GetMapping("/label/{labelId}")
	public Result findByLabelId(@PathVariable String labelId) {
		return baseClient.findById(labelId);
	}

	@GetMapping("/newlist/{labelid}/{page}/{size}")
	public Result newlist(@PathVariable String labelid, @PathVariable Integer page, @PathVariable Integer size) {
		Page<Problem> pages = problemService.newlist(labelid, page, size);
		return new Result(true, StatusCode.OK, "查询成功",
				new PageResult<Problem>(pages.getTotalElements(), pages.getContent()));
	}

	@GetMapping("/hotlist/{labelid}/{page}/{size}")
	public Result hotlist(@PathVariable String labelid, @PathVariable Integer page, @PathVariable Integer size) {
		Page<Problem> pages = problemService.hotlist(labelid, page, size);
		return new Result(true, StatusCode.OK, "查询成功",
				new PageResult<Problem>(pages.getTotalElements(), pages.getContent()));
	}

	@GetMapping("/waitlist/{labelid}/{page}/{size}")
	public Result waitlist(@PathVariable String labelid, @PathVariable Integer page, @PathVariable Integer size) {
		Page<Problem> pages = problemService.waitlist(labelid, page, size);
		return new Result(true, StatusCode.OK, "查询成功",
				new PageResult<Problem>(pages.getTotalElements(), pages.getContent()));
	}

	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
	}

	/**
	 * 根据ID查询
	 * 
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Result findById(@PathVariable String id) {
		return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
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
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return new Result(true, StatusCode.OK, "查询成功",
				new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
	}

	/**
	 * 根据条件查询
	 * 
	 * @param searchMap
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap) {
		return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap));
	}

	/**
	 * 增加
	 * 
	 * @param problem
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result add(@RequestBody Problem problem) {
		Claims claims = (Claims) request.getAttribute("claims_user");
		if (Objects.isNull(claims)) {
			return new Result(false, StatusCode.ACCESSERROR, "权限不足");
		}
		problemService.add(problem);
		return new Result(true, StatusCode.OK, "增加成功");
	}

	/**
	 * 修改
	 * 
	 * @param problem
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id) {
		problem.setId(id);
		problemService.update(problem);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Result delete(@PathVariable String id) {
		problemService.deleteById(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}

}
