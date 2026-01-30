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
@TableName("articles_tags")
public class ArticlesTags {

	// 主键
	@TableId
	private Long id;

	// 文章 Id
	private Long articleId;

	// 标签 Id
	private Long tagId;
}
