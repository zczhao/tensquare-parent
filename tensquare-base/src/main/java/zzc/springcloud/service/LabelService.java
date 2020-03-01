package zzc.springcloud.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import zzc.springcloud.dao.LabelDao;
import zzc.springcloud.pojo.Label;
import zzc.springcloud.util.IdWorker;

@Service
@Transactional
public class LabelService {

	@Autowired
	private LabelDao labelDao;
	
	@Autowired
	private IdWorker idWorker;
	
	public List<Label> findAll() {
		return labelDao.findAll();
	}
	
	public Label findById(String id) {
		return labelDao.findById(id).get();
	}
	
	public void save(Label label) {
		label.setId(String.valueOf(idWorker.nextId()));
		labelDao.save(label);
	}
	
	public void update(Label label) {
		labelDao.save(label);
	}

	public void deleteById(String id) {
		labelDao.deleteById(id);
	}

	public List<Label> findSearch(Label label) {
		return labelDao.findAll(new Specification<Label>() {
			private static final long serialVersionUID = 1L;
		
			/**
			 *@param root 根对象，也就是要把条件封装到哪个对象中，where 类名=label.getXxx
			 *@param query 封装的都是查询关键字，比如group by、order by等
			 *@param criteriaBuilder 用来封装条件对象，如果直接返回null，表示需要任何条件
			 */
			@Override
			public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// new一个list集合，来存放所有的条件
				List<Predicate> list = new ArrayList<Predicate>();
				if(label.getLabelname() != null && !"".equals(label.getLabelname())) {
					// labelname like %xxx%
					Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
					list.add(predicate);
				}
				
				if(label.getState() != null && !"".equals(label.getState())) {
					// state = n
					Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
					list.add(predicate);
				}
				// new一个数组作为最终返回值的条件
				Predicate[] parr = new Predicate[list.size()];
				// 把list直接转成数组
				list.toArray(parr);
				// where labelname like %xxx% and state = n
				return criteriaBuilder.and(parr);
			}
		});
	}

	public Page<Label> pageQuery(Label label, int page, int size) {
		// 默认页码从0开始
		Pageable pageable = PageRequest.of(page - 1, size);
		return labelDao.findAll(new Specification<Label>() {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(label.getLabelname() != null && !"".equals(label.getLabelname())) {
					Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
					list.add(predicate);
				}
				
				if(label.getState() != null && !"".equals(label.getState())) {
					Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
					list.add(predicate);
				}
				Predicate[] parr = new Predicate[list.size()];
				list.toArray(parr);
				return criteriaBuilder.and(parr);
			}
		}, pageable);
	}
}
