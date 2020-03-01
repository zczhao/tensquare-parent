package zzc.springcloud.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import zzc.springcloud.client.UserClient;
import zzc.springcloud.entity.Result;
import zzc.springcloud.entity.StatusCode;
import zzc.springcloud.service.FriendService;

@RestController
@RequestMapping("/friend")
public class FriendController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private FriendService friendService;
	
	@Autowired
	private UserClient userClient;

	/**
	 * 添加好友或者添加非好友
	 * 
	 * @param friendid
	 * @param type
	 * @return
	 */
	@PutMapping("/like/{friendid}/{type}")
	public Result addFriend(@PathVariable String friendid, @PathVariable String type) {
		// 验证是否登录，并且拿到当前登录的用户id
		Claims claims = (Claims) request.getAttribute("claims_user");
		if (Objects.isNull(claims)) {
			// 进入这，说明当前用户没有user角色
			return new Result(false, StatusCode.LOGINERROR, "权限不足");
		}

		// 得到当前登录的用户id
		String userId = claims.getId();
		// 判断是添加好友，还是添加非好友
		if (!StringUtils.isEmpty(type)) {
			if (StringUtils.equals("1", type)) {
				// 添加好友
				int flag = friendService.addFriend(userId, friendid);
				if (flag == 0) {
					return new Result(false, StatusCode.ERROR, "不能重复添加好友");
				} else if (flag == 1) {
					userClient.updateFanscountAndFollowcount(userId, friendid, 1);
					return new Result(true, StatusCode.OK, "添加成功");
				}
			} else if (StringUtils.equals("2", type)) {
				// 添加非好友
				int flag = friendService.addNoFriend(userId, friendid);
				if (flag == 0) {
					return new Result(false, StatusCode.ERROR, "不能重复添加非好友");
				} else if (flag == 1) {
					return new Result(true, StatusCode.OK, "添加成功");
				}
			}
			return new Result(false, StatusCode.ERROR, "参数异常");
		} else {
			return new Result(false, StatusCode.ERROR, "参数异常");
		}
	}
	
	/**
	 * 删除好友
	 * @param friendid
	 * @return
	 */
	@DeleteMapping("/{friendid}")
	public Result deleteFriend(@PathVariable String friendid) {
		// 验证是否登录，并且拿到当前登录的用户id
		Claims claims = (Claims) request.getAttribute("claims_user");
		if (Objects.isNull(claims)) {
			// 进入这，说明当前用户没有user角色
			return new Result(false, StatusCode.LOGINERROR, "权限不足");
		}
		// 得到当前登录的用户id
		String userId = claims.getId();
		friendService.deleteFriend(userId, friendid);
		userClient.updateFanscountAndFollowcount(userId, friendid, -1);
		return new Result(true, StatusCode.OK, "删除成功");
	}
}
