package zzc.springcloud.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
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
import zzc.springcloud.pojo.Label;
import zzc.springcloud.service.LabelService;

@RestController
@CrossOrigin
@RequestMapping("label")
@RefreshScope // 通过bus-refresh配置文件自动刷新
public class LabelController {

	@Autowired
	private LabelService labelService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Value("${ip}")
	private String ip;

	@GetMapping
	public Result findAll() {
		System.out.println("ip: " + ip);
		// 获取头信息
		String header = request.getHeader("Authorization");
		if (StringUtils.isEmpty(header)) {
			// 获取通过网关转发过来的请求头信息(*必须小写，不能用Authorization单词)
			header = request.getHeader("authorization-gateway");
		}
		System.out.println(header);
		return new Result(true, StatusCode.OK, "查询成功", labelService.findAll());
	}

	@GetMapping("/{labelId}")
	public Result findById(@PathVariable String labelId) {
		return new Result(true, StatusCode.OK, "查询成功", labelService.findById(labelId));
	}

	@PostMapping
	public Result save(@RequestBody Label label) {
		labelService.save(label);
		return new Result(true, StatusCode.OK, "添加成功");
	}

	@PutMapping("/{labelId}")
	public Result update(@PathVariable String labelId, @RequestBody Label label) {
		label.setId(labelId);
		labelService.update(label);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	@DeleteMapping("/{labelId}")
	public Result deleteById(@PathVariable String labelId) {
		labelService.deleteById(labelId);
		return new Result(true, StatusCode.OK, "删除成功");
	}

	/**
	 * 注释@RequestBody可把参数转换为Map
	 * 
	 * @param label
	 * @return
	 */
	@PostMapping("/search")
	public Result findSearch(@RequestBody Label label) {
		List<Label> list = labelService.findSearch(label);
		return new Result(true, StatusCode.OK, "查询成功", list);
	}

	@PostMapping("/search/{page}/{size}")
	public Result pageQuery(@RequestBody Label label, @PathVariable int page,
			@PathVariable int size) {
		Page<Label> pageData = labelService.pageQuery(label, page, size);
		return new Result(true, StatusCode.OK, "查询成功",
				new PageResult<Label>(pageData.getTotalElements(), pageData.getContent()));
	}
}
