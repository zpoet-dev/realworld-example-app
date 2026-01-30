package com.realworld.api.model.vo;

import com.realworld.api.model.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class ArticleVO {

	private String title;

	private String slug;

	private String body;

	private OffsetDateTime createdAt;

	private OffsetDateTime updatedAt;

	private String description;

	private List<String> tagList;

	private User author;

	private Boolean favorited;

	private Integer favoritesCount;
}
