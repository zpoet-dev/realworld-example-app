package com.realworld.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realworld.api.model.dto.UserRegisterDTO;
import lombok.Data;

/**
 * 用户注册请求包装类
 */
@Data
public class UserRegisterRequest {

	@JsonProperty("user")
	private UserRegisterDTO user;
}
