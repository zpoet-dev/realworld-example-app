package com.realworld.api.model.response;

import com.realworld.api.model.vo.ArticleVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetArticleListResponse {

	private List<ArticleVO> articles;

	private Integer articlesCount;
}
