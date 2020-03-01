package zzc.springcloud.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import zzc.springcloud.entity.Result;

@FeignClient("tensquare-user")
public interface UserClient {
	/**
	 * 更新好友粉丝数和用户关注数
	 * 
	 * @param userid
	 * @param friendid
	 * @param x
	 * @return
	 */
	@PutMapping("/user/{userid}/{friendid}/{x}")
	public Result updateFanscountAndFollowcount(@PathVariable("userid") String userid,
			@PathVariable("friendid") String friendid, @PathVariable("x") int x);
}
