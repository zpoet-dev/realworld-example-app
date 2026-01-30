package com.realworld.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.realworld.api.model.entity.association.ArticlesTags;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticlesTagsMapper extends BaseMapper<ArticlesTags> {
	
}
