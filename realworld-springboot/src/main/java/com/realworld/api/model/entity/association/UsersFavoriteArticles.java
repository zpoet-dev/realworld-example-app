package com.realworld.api.model.entity.association;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("users_favorite_articles")
public class UsersFavoriteArticles {

	// 主键
	@TableId
	private Long id;

	// 用户 Id
	private Long userId;

	// 文章 Id
	private Long articleId;

}
