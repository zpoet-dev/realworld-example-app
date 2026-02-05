package com.realworld.api.controller;

import com.realworld.api.model.response.FollowProfileResponse;
import com.realworld.api.model.response.GetProfileResponse;
import com.realworld.api.model.response.UnfollowProfileResponse;
import com.realworld.api.service.UsersFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class UsersFollowController {

	private final UsersFollowService usersFollowService;

	// 根据用户名获取 profile
	@GetMapping("/{userName}")
	public GetProfileResponse getProfileByUserName(@PathVariable String userName) {
		return usersFollowService.getProfileByUserName(userName);
	}

	// 关注某用户
	@PostMapping("/{userName}/follow")
	public FollowProfileResponse followProfileByUserName(@PathVariable String userName) {
		return usersFollowService.followProfileByUserName(userName);
	}

	// 取消关注某用户
	@DeleteMapping("/{userName}/follow")
	public UnfollowProfileResponse unfollowProfileByUserName(@PathVariable String userName) {
		return usersFollowService.unfollowProfileByUserName(userName);
	}
}
