package com.realworld.api.controller;

import com.realworld.api.model.request.CreateArticleRequest;
import com.realworld.api.model.request.UpdateArticleBySlugRequest;
import com.realworld.api.model.response.CreateArticleResponse;
import com.realworld.api.model.response.GetAllArticlesResponse;
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

	// 获取全部文章
	@GetMapping
	public GetAllArticlesResponse getAllArticles() {
		return articleService.getAllArticles();
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
