package com.realworld.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("articles")
public class Article {

	// 主键
	@TableId
	private Long id;

	// 创建人 Id
	private Long createdBy;

	// 标题
	private String title;

	// slug
	private String slug;

	// 内容
	private String body;

	// 创建时间
	private OffsetDateTime createdAt;

	// 更新时间
	private OffsetDateTime updatedAt;

	// 描述
	private String description;
}
