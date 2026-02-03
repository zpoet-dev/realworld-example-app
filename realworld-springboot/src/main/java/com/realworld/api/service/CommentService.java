package com.realworld.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.realworld.api.model.dto.CreateCommentDTO;
import com.realworld.api.model.entity.Comment;
import com.realworld.api.model.response.CreateCommentResponse;
import com.realworld.api.model.response.GetCommentsForArticleResponse;

/**
 * 评论服务接口
 */
public interface CommentService extends IService<Comment> {

	CreateCommentResponse createCommentForArticle(String slug, CreateCommentDTO createCommentDTO);

	GetCommentsForArticleResponse getCommentsForArticle(String slug);

	void deleteCommentForArticle(Long commentId);
}
