package com.realworld.api.controller;

import com.realworld.api.model.request.CreateArticleRequest;
import com.realworld.api.model.request.UpdateArticleBySlugRequest;
import com.realworld.api.model.response.CreateArticleResponse;
import com.realworld.api.model.response.GetArticleListResponse;
import com.realworld.api.model.response.GetArticleResponse;
import com.realworld.api.model.response.UpdateArticleBySlugResponse;
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
			@RequestParam(required = false) String tag
	) {
		return articleService.getArticleList(author, tag);
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
}
