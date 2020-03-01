package zzc.springcloud.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import java.io.Serializable;


@Entity
@Table(name = "tb_admin")
@Data
public class Admin implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;// ID

	private String loginname;// 登陆名称
	private String password;// 密码
	private String state;// 状态

}
