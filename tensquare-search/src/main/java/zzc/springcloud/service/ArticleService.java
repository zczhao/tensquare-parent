package zzc.springcloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import zzc.springcloud.dao.ArticleDao;
import zzc.springcloud.pojo.Article;

@Service
public class ArticleService {

	@Autowired
	private ArticleDao articleDao;

	public void save(Article article) {
		articleDao.save(article);
	}

	public Page<Article> findByKeyword(String keyword, int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		return articleDao.findByTitleOrContentLike(keyword, keyword, pageable);
	}

}
