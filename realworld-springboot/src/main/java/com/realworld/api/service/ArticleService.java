package com.realworld.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.realworld.api.model.dto.CreateArticleDTO;
import com.realworld.api.model.dto.UpdateArticleBySlugDTO;
import com.realworld.api.model.entity.Article;
import com.realworld.api.model.response.*;

/**
 * 文章服务接口
 */
public interface ArticleService extends IService<Article> {

	CreateArticleResponse createArticle(CreateArticleDTO createArticleDTO);

	GetArticleListResponse getArticleList(String userName, String tagName, String favorited);

	UpdateArticleBySlugResponse updateArticleBySlug(String slug, UpdateArticleBySlugDTO updateArticleBySlugDTO);

	void deleteArticleBySlug(String slug);

	GetArticleResponse getArticleBySlug(String slug);

	FavoriteArticleResponse favoriteArticle(String slug);

	UnfavoriteArticleResponse unfavoriteArticle(String slug);
}
