package com.realworld.api.model.response;

import com.realworld.api.model.vo.ArticleVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleResponse {

	private ArticleVO article;
}
