package com.realworld.api.model.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileVO {

	private String username;

	private String bio;

	private String image;

	private Boolean following;
}
