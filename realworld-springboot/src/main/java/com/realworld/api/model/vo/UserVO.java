package com.realworld.api.model.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserVO {

	private String username;

	private String email;

	private String bio;

	private String image;

	private String token;
}
