package com.realworld.api.controller;

import com.realworld.api.model.request.CreateArticleRequest;
import com.realworld.api.model.request.UpdateArticleBySlugRequest;
import com.realworld.api.model.response.*;
import com.realworld.api.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	// 创建文章
	@PostMapping
	public CreateArticleResponse createArticle(@RequestBody CreateArticleRequest request) {
		return articleService.createArticle(request.getArticle());
	}

	// 条件查询获取文章列表
	@GetMapping
	public GetArticleListResponse getArticleList(
			@RequestParam(required = false) String author,
			@RequestParam(required = false) String tag,
			@RequestParam(required = false) String favorited
	) {
		return articleService.getArticleList(author, tag, favorited);
	}

	// 根据 slug 获取文章详情
	@GetMapping("/{slug}")
	public GetArticleResponse getArticleBySlug(@PathVariable String slug) {
		return articleService.getArticleBySlug(slug);
	}

	// 更新文章
	@PutMapping("/{slug}")
	public UpdateArticleBySlugResponse updateArticleBySlug(@PathVariable String slug, @RequestBody UpdateArticleBySlugRequest request) {
		return articleService.updateArticleBySlug(slug, request.getArticle());
	}

	// 删除文章
	@DeleteMapping("/{slug}")
	public Map<String, Object> deleteArticleBySlug(@PathVariable String slug) {
		articleService.deleteArticleBySlug(slug);

		return Collections.emptyMap();
	}

	// 收藏文章
	@PostMapping("/{slug}/favorite")
	public FavoriteArticleResponse favoriteArticle(@PathVariable String slug) {
		return articleService.favoriteArticle(slug);
	}

	// 取消收藏
	@DeleteMapping("/{slug}/favorite")
	public UnfavoriteArticleResponse unfavoriteArticle(@PathVariable String slug) {
		return articleService.unfavoriteArticle(slug);
	}

	// 获取当前登录用户关注的所有用户的文章
	@GetMapping("/feed")
	public GetArticleListResponse getAllFeedArticles() {
		return articleService.getAllFeedArticles();
	}
}
