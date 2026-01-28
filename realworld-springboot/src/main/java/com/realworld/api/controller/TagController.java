package com.realworld.api.controller;

import com.realworld.api.model.response.GetAllTagsResponse;
import com.realworld.api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

	private final TagService tagService;

	// 获取全部标签
	@GetMapping
	public GetAllTagsResponse getAllTags() {
		return tagService.getAllTags();
	}
}
