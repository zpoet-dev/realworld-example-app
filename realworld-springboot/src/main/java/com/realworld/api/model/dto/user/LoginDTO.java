package com.realworld.api.model.dto.user;

import lombok.Data;

/**
 * 登录请求 DTO
 */
@Data
public class LoginDTO {

	private String email;

	private String password;
}
