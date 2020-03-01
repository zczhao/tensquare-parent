package zzc.springcloud.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzc.springcloud.dao.SpitDao;
import zzc.springcloud.pojo.Spit;
import zzc.springcloud.util.IdWorker;

@Service
@Transactional
public class SpitService {

	@Autowired
	private SpitDao spitDao;

	@Autowired
	private IdWorker idWorker;

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<Spit> findAll() {
		return spitDao.findAll();
	}

	public Spit findById(String id) {
		return spitDao.findById(id).get();
	}

	public void save(Spit spit) {
		spit.set_id(String.valueOf(idWorker.nextId()));
		spit.setPublishtime(new Date()); // 发布日期
		spit.setVisits(0); // 浏览量
		spit.setShare(0); // 分享数
		spit.setThumbup(0); // 点赞数
		spit.setComment(0); // 回复数
		spit.setState("1"); // 状态
		// 如果当前添加的吐槽，有父节点，那么父节点的吐槽回复数要 +1
		if (spit.getParentid() != null && !"".equals(spit.getParentid())) {
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
			Update update = new Update();
			update.inc("comment", 1);
			mongoTemplate.updateFirst(query, update, "spit");
		}
		spitDao.save(spit);
	}

	public void update(Spit spit) {
		spitDao.save(spit);
	}

	public void deleteById(String id) {
		spitDao.deleteById(id);
	}

	public Page<Spit> findByParentid(String parentid, int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		return spitDao.findByParentid(parentid, pageable);
	}

	public void thumbup(String spitid) {
		// 方式一：效率有问题
		/*
		 * Spit spit = spitDao.findById(spitid).get();
		 * spit.setThumbup((spit.getThumbup() == null ? 0 : spit.getThumbup()) + 1);
		 * spitDao.save(spit);
		 */
		// 方式二：使用原生mongo命令来操作 db.spit.update({_id: "1"},{$inc: {thumbup: NumberInt(1)}})
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(spitid));
		Update update = new Update();
		update.inc("thumbup", 1);
		mongoTemplate.updateFirst(query, update, "spit");
	}
}
