package com.realworld.api.controller;

import com.realworld.api.model.request.LoginRequest;
import com.realworld.api.model.request.UserRegisterRequest;
import com.realworld.api.model.response.LoginResponse;
import com.realworld.api.model.response.UserRegisterResponse;
import com.realworld.api.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

	private final AuthServiceImpl authService;

	// 用户注册
	@PostMapping
	public UserRegisterResponse userRegister(@RequestBody UserRegisterRequest request) {
		return authService.userRegister(request.getUser());
	}

	// 登录
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {
		return authService.login(request.getUser());
	}
}
