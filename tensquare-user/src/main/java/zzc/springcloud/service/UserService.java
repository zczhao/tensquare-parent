package zzc.springcloud.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import zzc.springcloud.dao.UserDao;
import zzc.springcloud.pojo.User;
import zzc.springcloud.util.IdWorker;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private IdWorker idWorker;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * 查询全部列表
	 * 
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	/**
	 * 条件查询+分页
	 * 
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest = PageRequest.of(page - 1, size);
		return userDao.findAll(specification, pageRequest);
	}

	/**
	 * 条件查询
	 * 
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * 
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 增加
	 * 
	 * @param user
	 */
	public void add(User user) {
		// 难验证码可在此验证
		// throw new RuntimeException("验证码输入不正确");
		user.setId(idWorker.nextId() + "");
		user.setFollowcount(0); // 关注数
		user.setFanscount(0); // 粉丝数
		user.setOnline(0L); // 在线时长
		Date date = new Date();
		user.setRegdate(date); // 注册日期
		user.setUpdatedate(date); // 更新日期
		user.setLastdate(date); // 最后登录日期
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // 加密
		userDao.save(user);
	}

	/**
	 * 修改
	 * 
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * 
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				// ID
				if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
					predicateList
							.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
				}
				// 手机号码
				if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
					predicateList.add(
							cb.like(root.get("mobile").as(String.class), "%" + (String) searchMap.get("mobile") + "%"));
				}
				// 密码
				if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
					predicateList.add(cb.like(root.get("password").as(String.class),
							"%" + (String) searchMap.get("password") + "%"));
				}
				// 昵称
				if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
					predicateList.add(cb.like(root.get("nickname").as(String.class),
							"%" + (String) searchMap.get("nickname") + "%"));
				}
				// 性别
				if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
					predicateList
							.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
				}
				// 头像
				if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
					predicateList.add(
							cb.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
				}
				// E-Mail
				if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
					predicateList.add(
							cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
				}
				// 兴趣
				if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
					predicateList.add(cb.like(root.get("interest").as(String.class),
							"%" + (String) searchMap.get("interest") + "%"));
				}
				// 个性
				if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
					predicateList.add(cb.like(root.get("personality").as(String.class),
							"%" + (String) searchMap.get("personality") + "%"));
				}

				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	public void sendSms(String mobile) {
		// 生成验证码
		String vcode = RandomStringUtils.randomNumeric(6);
		// 向缓存中放一份
		redisTemplate.opsForValue().set("vcode_" + mobile, vcode, 6, TimeUnit.HOURS);
		// 发送验证码
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		map.put("vcode", vcode);
		rabbitTemplate.convertAndSend("sms_vcode", map);
		// 在控制台显示
		System.out.println("验证码为：" + vcode);
	}

	public User login(User user) {
		User dbUser = userDao.findByMobile(user.getMobile());
		if (dbUser != null && bCryptPasswordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
			return dbUser;
		}
		return null;
	}

	@Transactional
	public void updateFanscountAndFollowcount(int x, String userid, String friendid) {
		userDao.updateFanscount(x, friendid);
		userDao.updateFollowcount(x, userid);
	}

}
