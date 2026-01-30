package com.realworld.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.realworld.api.mapper.UsersFavoriteArticlesMapper;
import com.realworld.api.model.entity.User;
import com.realworld.api.model.entity.association.UsersFavoriteArticles;
import com.realworld.api.service.UsersFavoriteArticlesService;
import com.realworld.api.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersFavoriteArticlesServiceImpl extends ServiceImpl<UsersFavoriteArticlesMapper, UsersFavoriteArticles> implements UsersFavoriteArticlesService {

	private final UserUtil userUtil;

	private final UsersFavoriteArticlesMapper usersFavoriteArticlesMapper;

	/**
	 * 判断当前登录用户是否已收藏该文章
	 *
	 * @param articleId 文章 ID
	 * @return true=已收藏，false=未收藏
	 */
	public Boolean isCurrentUserFavorited(Long articleId) {
		User currentUser = userUtil.getCurrentUserNullable();
		boolean favorited = false;

		// 如果有用户登录则查询当前用户是否收藏该文章
		if (currentUser != null) {
			List<Long> favoritedUserIdList = usersFavoriteArticlesMapper.findFavoritedUserIdsByArticleId(articleId);
			favorited = favoritedUserIdList.contains(currentUser.getId());
		}

		return favorited;
	}

	/**
	 * 统计该文章被多少人收藏
	 *
	 * @param articleId 文章 ID
	 * @return 收藏人数
	 */
	public Integer getFavoritesCountByArticleId(Long articleId) {
		LambdaQueryWrapper<UsersFavoriteArticles> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(UsersFavoriteArticles::getArticleId, articleId);
		Integer favoritesCount = list(queryWrapper).size();

		return favoritesCount;
	}
}
