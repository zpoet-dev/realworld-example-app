package com.realworld.api.controller;

import com.realworld.api.model.request.CreateCommentRequest;
import com.realworld.api.model.response.CreateCommentResponse;
import com.realworld.api.model.response.GetCommentsForArticleResponse;
import com.realworld.api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/articles/{slug}/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	// 创建评论
	@PostMapping
	public CreateCommentResponse createCommentForArticle(@PathVariable String slug,
																											 @RequestBody CreateCommentRequest request) {
		return commentService.createCommentForArticle(slug, request.getComment());
	}

	// 获取文章全部评论
	@GetMapping
	public GetCommentsForArticleResponse getCommentsForArticle(@PathVariable String slug) {
		return commentService.getCommentsForArticle(slug);
	}

	// 删除评论
	@DeleteMapping("/{commentId}")
	public Map<String, Object> deleteCommentForArticle(@PathVariable String slug,
																										 @PathVariable Long commentId) {
		commentService.deleteCommentForArticle(commentId);

		return Collections.emptyMap();
	}
}
