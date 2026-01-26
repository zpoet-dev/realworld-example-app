package com.realworld.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realworld.api.model.dto.user.UpdateUserDTO;
import lombok.Data;

/**
 * 更新当前登录用户请求包装类
 */
@Data
public class UpdateUserRequest {

	@JsonProperty("user")
	private UpdateUserDTO user;
}
