package zzc.springcloud.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import zzc.springcloud.pojo.NoFriend;

public interface NoFriendDao extends JpaRepository<NoFriend, String> {

	public NoFriend findByUseridAndFriendid(String userid, String friendid);

}
