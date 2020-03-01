package zzc.springcloud.pojo;

import java.io.Serializable;


public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private String userName;

	private String password;
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
