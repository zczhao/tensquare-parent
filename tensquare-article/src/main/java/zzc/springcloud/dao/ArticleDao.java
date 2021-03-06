package zzc.springcloud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import zzc.springcloud.pojo.Article;

public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {

	@Modifying
	@Query(value = "UPDATE tb_article SET state=1 where id = ?1", nativeQuery = true)
	public void updateState(String id);
	
	
	@Modifying
	@Query(value = "UPDATE tb_article SET thumbup = thumbup + 1 where id = ?", nativeQuery = true)
	public void addThumbup(String id);

}
