package com.realworld.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realworld.api.model.dto.UpdateArticleBySlugDTO;
import lombok.Data;

/**
 * 更新文章请求包装类
 */
@Data
public class UpdateArticleBySlugRequest {

	@JsonProperty("article")
	private UpdateArticleBySlugDTO article;
}
