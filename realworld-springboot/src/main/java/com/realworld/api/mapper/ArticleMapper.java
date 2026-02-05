package com.realworld.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.realworld.api.model.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

	List<Article> findArticlesByAuthor(@Param("userName") String userName);

	List<Article> findArticlesByTag(@Param("tagName") String tagName);

	List<Article> findArticlesByUserName(@Param("userName") String userName);

	List<Article> getAllFeedArticles(@Param("userId") Long userId);
}
