package com.realworld.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.realworld.api.model.entity.association.UsersFavoriteArticles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UsersFavoriteArticlesMapper extends BaseMapper<UsersFavoriteArticles> {

	List<Long> findFavoritedUserIdsByArticleId(@Param("articleId") Long articleId);
}
