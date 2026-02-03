package com.realworld.api.model.vo;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class CommentVO {

	private Long id;

	private String body;

	private OffsetDateTime createdAt;

	private OffsetDateTime updatedAt;

	private String author;
}
