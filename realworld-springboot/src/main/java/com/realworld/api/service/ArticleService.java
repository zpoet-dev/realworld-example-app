package com.realworld.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.realworld.api.model.dto.CreateArticleDTO;
import com.realworld.api.model.dto.UpdateArticleBySlugDTO;
import com.realworld.api.model.entity.Article;
import com.realworld.api.model.response.CreateArticleResponse;
import com.realworld.api.model.response.GetArticleListResponse;
import com.realworld.api.model.response.GetArticleResponse;
import com.realworld.api.model.response.UpdateArticleBySlugResponse;

/**
 * 文章服务接口
 */
public interface ArticleService extends IService<Article> {

	CreateArticleResponse createArticle(CreateArticleDTO createArticleDTO);

	GetArticleListResponse getArticleList(String userName, String tagName);

	UpdateArticleBySlugResponse updateArticleBySlug(String slug, UpdateArticleBySlugDTO updateArticleBySlugDTO);

	void deleteArticleBySlug(String slug);

	GetArticleResponse getArticleBySlug(String slug);
}
