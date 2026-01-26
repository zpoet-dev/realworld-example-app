package com.realworld.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realworld.api.model.dto.user.LoginDTO;
import lombok.Data;

/**
 * 登录请求包装类
 */
@Data
public class LoginRequest {

	@JsonProperty("user")
	private LoginDTO user;
}
