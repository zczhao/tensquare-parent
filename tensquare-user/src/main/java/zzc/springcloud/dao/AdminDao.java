package zzc.springcloud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import zzc.springcloud.pojo.Admin;


public interface AdminDao extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {

	public Admin findByLoginname(String loginname);
}
