package com.realworld.api.model.dto;

import lombok.Data;

/**
 * 用户注册请求 DTO
 */
@Data
public class UserRegisterDTO {

	private String username;

	private String password;

	private String email;
}
