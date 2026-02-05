package com.realworld.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.realworld.api.mapper.UsersFollowMapper;
import com.realworld.api.model.entity.User;
import com.realworld.api.model.entity.association.UsersFollow;
import com.realworld.api.model.response.FollowProfileResponse;
import com.realworld.api.model.response.GetProfileResponse;
import com.realworld.api.model.response.UnfollowProfileResponse;
import com.realworld.api.model.vo.ProfileVO;
import com.realworld.api.service.UserService;
import com.realworld.api.service.UsersFollowService;
import com.realworld.api.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 关注服务实现类
 */
@Service
@RequiredArgsConstructor
public class UsersFollowServiceImpl extends ServiceImpl<UsersFollowMapper, UsersFollow> implements UsersFollowService {

	private final UserService userService;

	private final UserUtil userUtil;

	/**
	 * 根据用户名获取 profile
	 *
	 * @param userName 用户名
	 * @return 获取到的 profile
	 */
	public GetProfileResponse getProfileByUserName(String userName) {
		// 从数据库中获取用户实体
		User userInDataBase = userService.getOne(
				new LambdaQueryWrapper<User>()
						.eq(User::getUsername, userName)
		);

		// 判断当前登录用户是否关注了该用户
		User currentUser = userUtil.getCurrentUser();
		boolean isFollowing = isFollowing(currentUser.getId(), userInDataBase.getId());

		// 构建响应
		ProfileVO profileVO = ProfileVO.builder()
				.username(userInDataBase.getUsername())
				.bio(userInDataBase.getBio())
				.image(userInDataBase.getImage())
				.following(isFollowing)
				.build();

		return new GetProfileResponse(profileVO);
	}

	/**
	 * 关注某用户
	 *
	 * @param userName 用户名
	 * @return 被关注用户的 profile
	 */
	public FollowProfileResponse followProfileByUserName(String userName) {
		// 从数据库中获取用户实体
		User userInDataBase = userService.getOne(
				new LambdaQueryWrapper<User>()
						.eq(User::getUsername, userName)
		);

		// 获取当前登录用户
		User currentUser = userUtil.getCurrentUser();

		// 插入记录进入中间包
		UsersFollow usersFollow = UsersFollow.builder()
				.followUserId(currentUser.getId())
				.followedUserId(userInDataBase.getId())
				.build();
		save(usersFollow);

		// 构建响应
		ProfileVO profileVO = ProfileVO.builder()
				.username(userInDataBase.getUsername())
				.bio(userInDataBase.getBio())
				.image(userInDataBase.getImage())
				.following(true)
				.build();

		return new FollowProfileResponse(profileVO);
	}

	/**
	 * 取消关注某用户
	 *
	 * @param userName 用户名
	 * @return 被取消关注用户的 profile
	 */
	public UnfollowProfileResponse unfollowProfileByUserName(String userName) {
		// 从数据库中获取要取消关注用户的 Id
		User userInDataBase = userService.getOne(
				new LambdaQueryWrapper<User>()
						.eq(User::getUsername, userName)
		);

		// 删除中间表中的记录
		remove(
				new LambdaQueryWrapper<UsersFollow>()
						.eq(UsersFollow::getFollowedUserId, userInDataBase.getId())
		);

		// 构建响应
		ProfileVO profileVO = ProfileVO.builder()
				.username(userInDataBase.getUsername())
				.bio(userInDataBase.getBio())
				.image(userInDataBase.getImage())
				.following(false)
				.build();

		return new UnfollowProfileResponse(profileVO);
	}

	/**
	 * 判断两用户之间是否存在关注关系
	 *
	 * @param followUserId   关注者 Id
	 * @param followedUserId 被关注者 Id
	 * @return true=已关注，false=未关注
	 */
	private Boolean isFollowing(Long followUserId, Long followedUserId) {
		// 获取所有关注了被关注者的用户 Id
		List<Long> followerIdList = list(
				new LambdaQueryWrapper<UsersFollow>()
						.select(UsersFollow::getFollowUserId)
						.eq(UsersFollow::getFollowedUserId, followedUserId)
		)
				.stream()
				.map(UsersFollow::getFollowUserId)
				.toList();

		return followerIdList.contains(followUserId);
	}
}
