package com.realworld.api.model.dto;

import lombok.Data;

/**
 * 登录请求 DTO
 */
@Data
public class LoginDTO {

	private String email;

	private String password;
}
