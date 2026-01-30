package com.realworld.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realworld.api.model.dto.CreateArticleDTO;
import lombok.Data;

/**
 * 创建文章请求包装类
 */
@Data
public class CreateArticleRequest {

	@JsonProperty("article")
	private CreateArticleDTO article;
}
