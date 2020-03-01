package zzc.springcloud.service;

import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zzc.springcloud.dao.FriendDao;
import zzc.springcloud.dao.NoFriendDao;
import zzc.springcloud.pojo.Friend;
import zzc.springcloud.pojo.NoFriend;

@Service
@Transactional
public class FriendService {

	@Autowired
	private FriendDao friendDao;

	@Autowired
	private NoFriendDao noFriendDao;

	public int addFriend(String userid, String friendid) {
		// 先判断userid到friendid是否有数据，有就是重复添加好友，返回 0
		Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
		if (Objects.nonNull(friend)) {
			return 0;
		}

		// 直接添加好友，让好友表中userid到friendid方向的type为0
		friend = new Friend();
		friend.setUserid(userid);
		friend.setFriendid(friendid);
		friend.setIslike("0");
		friendDao.save(friend);

		// 判断从friendid到userid是否有数据，如果有，把双方的状态都改为1
		if (Objects.nonNull(friendDao.findByUseridAndFriendid(friendid, userid))) {
			// 把双方的islike都改为1
			friendDao.updateIslike("1", userid, friendid);
			friendDao.updateIslike("1", friendid, userid);
		}
		return 1;
	}

	public int addNoFriend(String userid, String friendid) {
		// 先判断是否为非好友，返回 0
		NoFriend friend = noFriendDao.findByUseridAndFriendid(userid, friendid);
		if (Objects.nonNull(friend)) {
			return 0;
		}
		friend = new NoFriend();
		friend.setUserid(userid);
		friend.setFriendid(friendid);
		noFriendDao.save(friend);
		return 1;
	}

	public void deleteFriend(String userid, String friendid) {
		// 删除好友表中userid到friendid这条数据
		friendDao.deleteFriend(userid, friendid);
		// 更新friendid到userid的islike为0
		friendDao.updateIslike("0", friendid, userid);
		// 非好友表中添加数据
		NoFriend friend = new NoFriend();
		friend.setUserid(userid);
		friend.setFriendid(friendid);
		noFriendDao.save(friend);
		
	}

}
