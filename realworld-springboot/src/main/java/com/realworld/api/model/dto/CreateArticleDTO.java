package com.realworld.api.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 创建文章请求 DTO
 */
@Data
public class CreateArticleDTO {

	private String title;

	private String description;

	private String body;

	private List<String> tagList;
}
