package zzc.springcloud.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import zzc.springcloud.pojo.Problem;

public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

	@Query(value = "SELECT * FROM tb_problem p, tb_pl pl WHERE p.id = pl.problemid AND pl.labelid=? ORDER BY p.replytime DESC", nativeQuery = true)
	public Page<Problem> newlist(String labelid, Pageable pageable);

	@Query(value = "SELECT * FROM tb_problem p, tb_pl pl WHERE p.id = pl.problemid AND pl.labelid=? ORDER BY p.reply DESC", nativeQuery = true)
	public Page<Problem> hotlist(String labelid, Pageable pageable);

	@Query(value = "SELECT * FROM tb_problem p, tb_pl pl WHERE p.id = pl.problemid AND pl.labelid=? AND (p.reply IS NULL OR p.reply = 0) ORDER BY p.createtime DESC", nativeQuery = true)
	public Page<Problem> waitlist(String labelid, Pageable pageable);
}
