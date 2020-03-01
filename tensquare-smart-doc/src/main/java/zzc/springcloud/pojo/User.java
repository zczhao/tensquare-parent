package zzc.springcloud.pojo;

import javax.validation.constraints.NotNull;

/**
 * 用户表
 *
 */
public class User {
	/**
	 * 姓名
	 */
	@NotNull // 有此注解生成文档require = true
	private String name;

	/**
	 * 电话
	 */
	private String mobile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
