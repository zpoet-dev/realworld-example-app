package com.realworld.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.realworld.api.model.dto.UpdateUserDTO;
import com.realworld.api.model.entity.User;
import com.realworld.api.model.response.GetCurrentUserResponse;
import com.realworld.api.model.response.UpdateUserResponse;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

	GetCurrentUserResponse getCurrentUser();

	UpdateUserResponse updateCurrentUser(UpdateUserDTO updateCurrentUserDTO);
}
