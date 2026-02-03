package com.realworld.api.model.entity;

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
@TableName("comments")
public class Comment {

	// 主键
	private Long id;

	// 内容
	private String body;

	// 创建时间
	private OffsetDateTime createdAt;

	// 更新时间
	private OffsetDateTime updatedAt;

	// 创建人
	private Long createdBy;
}
