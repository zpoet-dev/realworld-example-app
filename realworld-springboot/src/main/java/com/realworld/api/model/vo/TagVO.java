package com.realworld.api.model.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagVO {

	private String tagName;

	private Long articlesCount;
}
