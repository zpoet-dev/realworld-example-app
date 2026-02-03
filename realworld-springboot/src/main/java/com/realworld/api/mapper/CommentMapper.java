package com.realworld.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.realworld.api.model.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

	List<Comment> findCommentsByArticle(@Param("slug") String slug);
}
