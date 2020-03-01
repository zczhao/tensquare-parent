package zzc.springcloud.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zzc.springcloud.pojo.User;

/**
 * hello
 *
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
	/**
	 * 创建
	 *
	 * @param user
	 * @return
	 */
	@PostMapping
	public User create(User user) {
		return user;
	}

	/**
	 * 查询
	 *
	 * @param name 用户名
	 * @return
	 */
	@GetMapping
	public User getName(String name) {
		return new User();
	}

	/**
	 * 修改
	 *
	 * @param name
	 * @return
	 */
	@PutMapping
	public String update(String name) {
		return name;
	}

	/**
	 * 删除
	 *
	 * @param name
	 * @return
	 */
	@DeleteMapping
	public String delete(String name) {
		return name;
	}
}
