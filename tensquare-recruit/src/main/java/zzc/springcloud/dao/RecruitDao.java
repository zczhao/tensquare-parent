package zzc.springcloud.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import zzc.springcloud.pojo.Recruit;

public interface RecruitDao extends JpaRepository<Recruit, String>, JpaSpecificationExecutor<Recruit> {

	// where state=? order by createtime limit 6
	public List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state);

	// where state!=? order by createtime limit 6
	public List<Recruit> findTop6StateNotOrderByCreatetimeDesc(String state);
}
