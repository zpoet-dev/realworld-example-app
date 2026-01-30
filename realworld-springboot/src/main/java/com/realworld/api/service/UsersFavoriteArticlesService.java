package com.realworld.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.realworld.api.model.entity.association.UsersFavoriteArticles;

/**
 * users-articles 中间表服务接口
 */
public interface UsersFavoriteArticlesService extends IService<UsersFavoriteArticles> {

	Boolean isCurrentUserFavorited(Long articleId);

	Integer getFavoritesCountByArticleId(Long articleId);
}
