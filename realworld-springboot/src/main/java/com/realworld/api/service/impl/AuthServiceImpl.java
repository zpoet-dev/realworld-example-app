package com.realworld.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.realworld.api.enums.ErrorCode;
import com.realworld.api.mapper.AuthMapper;
import com.realworld.api.model.dto.user.LoginDTO;
import com.realworld.api.model.dto.user.UserRegisterDTO;
import com.realworld.api.model.entity.User;
import com.realworld.api.model.response.LoginResponse;
import com.realworld.api.model.response.UserRegisterResponse;
import com.realworld.api.model.vo.UserVO;
import com.realworld.api.service.AuthService;
import com.realworld.api.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends ServiceImpl<AuthMapper, User> implements AuthService {

	private final JwtUtil jwtUtil;

	private final PasswordEncoder passwordEncoder;

	/**
	 * 用户注册
	 *
	 * @param userRegisterDTO 注册信息
	 * @return 注册响应（包含用户信息和 Token）
	 * @throws RuntimeException 当邮箱或用户名已存在时
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserRegisterResponse userRegister(UserRegisterDTO userRegisterDTO) {

		// 校验邮箱是否已存在
		if (isEmailExists(userRegisterDTO.getEmail())) {
			throw new RuntimeException(ErrorCode.EMAIL_ALREADY_EXISTS.getMessage());
		}

		// 校验用户名是否已存在
		if (isUsernameExists(userRegisterDTO.getUsername())) {
			throw new RuntimeException(ErrorCode.USERNAME_ALREADY_EXISTS.getMessage());
		}

		// 创建用户实体
		User registerUser = User.builder()
				.username(userRegisterDTO.getUsername())
				.password(passwordEncoder.encode(userRegisterDTO.getPassword()))  // 密码加密
				.email(userRegisterDTO.getEmail())
				.bio("")  // 默认空白简介
				.image("")  // 默认空白头像
				.build();

		// 保存用户到数据库
		save(registerUser);

		// 生成 Token
		String token = jwtUtil.generateToken(registerUser.getId());

		// 构建响应 VO
		UserVO userVO = UserVO.builder()
				.username(registerUser.getUsername())
				.email(registerUser.getEmail())
				.bio(registerUser.getBio())
				.image(registerUser.getImage())
				.token(token)
				.build();

		return new UserRegisterResponse(userVO);
	}

	/**
	 * 登录
	 *
	 * @param loginDTO 登录信息
	 * @return 登录响应（包含用户信息和 Token）
	 * @throws RuntimeException 当邮箱或密码错误时
	 */
	public LoginResponse login(LoginDTO loginDTO) {
		// 根据 email 查询用户
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
		queryWrapper.eq(User::getEmail, loginDTO.getEmail());
		User exitedUser = getOne(queryWrapper);

		// 验证用户是否存在
		if (exitedUser == null) {
			throw new RuntimeException(ErrorCode.INVALID_CREDENTIALS.getMessage());
		}

		// 验证密码
		boolean isCorrectPassword = passwordEncoder.matches(loginDTO.getPassword(), exitedUser.getPassword());
		if (!isCorrectPassword) {
			throw new RuntimeException(ErrorCode.INVALID_CREDENTIALS.getMessage());
		}

		// 密码正确，生成 Token
		String token = jwtUtil.generateToken(exitedUser.getId());

		// 构建响应 VO
		UserVO userVO = UserVO.builder()
				.username(exitedUser.getUsername())
				.email(exitedUser.getEmail())
				.bio(exitedUser.getBio())
				.image(exitedUser.getImage())
				.token(token)
				.build();

		return new LoginResponse(userVO);
	}

	/**
	 * 检查邮箱是否已存在
	 *
	 * @param email 邮箱
	 * @return true=已存在，false=不存在
	 */
	private boolean isEmailExists(String email) {
		long count = count(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
		return count > 0;
	}

	/**
	 * 检查用户名是否已存在
	 *
	 * @param username 用户名
	 * @return true=已存在，false=不存在
	 */
	private boolean isUsernameExists(String username) {
		long count = count(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
		return count > 0;
	}
}
