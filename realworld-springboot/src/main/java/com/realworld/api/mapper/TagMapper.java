package com.realworld.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.realworld.api.model.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

	List<String> findTagNamesByArticleId(@Param("articleId") Long articleId);
}
