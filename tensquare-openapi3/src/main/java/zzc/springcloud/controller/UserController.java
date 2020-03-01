package zzc.springcloud.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zzc.springcloud.pojo.User;

@RestController
@RequestMapping("/user")
public class UserController {
	/**
	 *    访问默认路径，查看文档：http://localhost:8080/v3/api-docs/
	 *    	自定义路径application.properties在文档中指定：springdoc.api-docs.path=/api-docs，
	 *   文档的访问路径就变成：http://localhost:8080/api-docs/
	 *OpenAPI 默认定义为JSON 格式。对于 yaml 格式，可以访问下面的路径获取 ：http://localhost:8080/api-docs.yaml    	
	 * @param userId
	 * @return
	 */
	@PostMapping(value = "/{userId}")
	public User getUser(@PathVariable int userId) {
		return new User("zyc", "123123", "1761110");
	}
}
