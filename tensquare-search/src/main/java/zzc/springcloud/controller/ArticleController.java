package zzc.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zzc.springcloud.entity.PageResult;
import zzc.springcloud.entity.Result;
import zzc.springcloud.entity.StatusCode;
import zzc.springcloud.pojo.Article;
import zzc.springcloud.service.ArticleService;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@PostMapping
	public Result save(@RequestBody Article article) {
		articleService.save(article);
		return new Result(true, StatusCode.OK, "增加成功");
	}
	
	@GetMapping("/search/{keyword}/{page}/{size}")
	public Result findByKeyword(@PathVariable String keyword,@PathVariable int page, @PathVariable int size) {
		Page<Article> pages = articleService.findByKeyword(keyword, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<Article>(pages.getTotalElements(),pages.getContent()));
	}
}
