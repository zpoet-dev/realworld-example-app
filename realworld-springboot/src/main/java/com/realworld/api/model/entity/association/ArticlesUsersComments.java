package com.realworld.api.model.entity.association;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("articles_users_comments")
public class ArticlesUsersComments {

	// 主键
	private Long id;

	// 文章 ID
	private Long articleId;

	// 文章 slug
	private String articleSlug;

	// 用户 ID
	private Long UserId;

	// 评论 ID
	private Long commentId;
}
