package com.realworld.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.realworld.api.mapper.UserMapper;
import com.realworld.api.model.dto.UpdateUserDTO;
import com.realworld.api.model.entity.User;
import com.realworld.api.model.response.GetCurrentUserResponse;
import com.realworld.api.model.response.UpdateUserResponse;
import com.realworld.api.model.vo.UserVO;
import com.realworld.api.service.UserService;
import com.realworld.api.utils.JwtUtil;
import com.realworld.api.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	private final JwtUtil jwtUtil;

	private final UserUtil userUtil;

	/**
	 * 获取当前登录用户信息
	 *
	 * @return 当前用户响应（包含用户信息和 Token）
	 * @throws RuntimeException 当用户未认证时
	 */
	@Override
	public GetCurrentUserResponse getCurrentUser() {
		// 获取当前登录用户信息
		User currentUser = userUtil.getCurrentUser();

		// 生成新的 Token
		String token = jwtUtil.generateToken(currentUser.getId());

		// 构建响应 VO
		UserVO userVO = UserVO.builder()
				.username(currentUser.getUsername())
				.email(currentUser.getEmail())
				.bio(currentUser.getBio())
				.image(currentUser.getImage())
				.token(token)
				.build();

		return new GetCurrentUserResponse(userVO);
	}

	/**
	 * 更新当前登录用户信息
	 *
	 * @param updateCurrentUserDTO 需要更新的用户信息
	 * @return 更新后的用户响应(包括用户信息和 Token)
	 * @throws RuntimeException 当用户未认证时
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public UpdateUserResponse updateCurrentUser(UpdateUserDTO updateCurrentUserDTO) {
		// 获取当前登录用户信息
		User currentUser = userUtil.getCurrentUser();
		Long currentUserId = currentUser.getId();

		// 更新用户信息
		LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(User::getId, currentUserId)
				.set(User::getEmail, updateCurrentUserDTO.getEmail());
		update(updateWrapper);

		// 获取更新后的用户信息
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getId, currentUserId);
		User updatedUser = getOne(queryWrapper);

		// 生成新的 Token
		String token = jwtUtil.generateToken(currentUser.getId());

		// 构建响应 VO
		UserVO userVO = UserVO.builder()
				.username(updatedUser.getUsername())
				.email(updatedUser.getEmail())
				.bio(updatedUser.getBio())
				.image(updatedUser.getImage())
				.token(token)
				.build();

		return new UpdateUserResponse(userVO);
	}

	/**
	 * 根据用户 ID 获取用户名
	 *
	 * @param userId 用户 ID
	 * @return 用户名
	 */
	public String UserIdToUserName(Long userId) {

		return getOne(
				new LambdaQueryWrapper<User>()
						.select(User::getUsername)
						.eq(User::getId, userId)
		).getUsername();
	}
}
