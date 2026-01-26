package com.realworld.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.realworld.api.model.dto.user.LoginDTO;
import com.realworld.api.model.dto.user.UserRegisterDTO;
import com.realworld.api.model.entity.User;
import com.realworld.api.model.response.LoginResponse;
import com.realworld.api.model.response.UserRegisterResponse;

/**
 * 用户服务接口
 */
public interface AuthService extends IService<User> {

	UserRegisterResponse userRegister(UserRegisterDTO userRegisterDTO);

	LoginResponse login(LoginDTO loginDTO);
}
