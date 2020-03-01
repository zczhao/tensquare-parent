package zzc.springcloud.pojo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "User",description = "用户实体类")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "用户ID")
	private Integer userId;
	@ApiModelProperty(notes = "用户名")
	private String userName;

	@ApiModelProperty(hidden = true)
	private String password;
	@ApiModelProperty(notes = "手机号码")
	private String phone;

	public User() {
	}

	public User(String userName, String password, String phone) {
		this.userName = userName;
		this.password = password;
		this.phone = phone;
	}

	public User(Integer userId, String userName, String password, String phone) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.phone = phone;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
