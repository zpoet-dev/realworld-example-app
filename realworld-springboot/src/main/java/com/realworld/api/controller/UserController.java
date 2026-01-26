package com.realworld.api.controller;

import com.realworld.api.model.request.UpdateUserRequest;
import com.realworld.api.model.response.GetCurrentUserResponse;
import com.realworld.api.model.response.UpdateUserResponse;
import com.realworld.api.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserServiceImpl userService;

	// 获取当前登录用户
	@GetMapping
	public GetCurrentUserResponse getCurrentUser() {
		return userService.getCurrentUser();
	}

	// 更新当前登录用户
	@PutMapping
	public UpdateUserResponse updateCurrentUser(@RequestBody UpdateUserRequest request) {
		return userService.updateCurrentUser(request.getUser());
	}
}
