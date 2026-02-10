package com.realworld.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.realworld.api.mapper.TagMapper;
import com.realworld.api.model.entity.Tag;
import com.realworld.api.model.entity.association.ArticlesTags;
import com.realworld.api.model.response.GetAllTagsResponse;
import com.realworld.api.model.vo.TagVO;
import com.realworld.api.service.ArticlesTagsService;
import com.realworld.api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签服务实现类
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

	private final ArticlesTagsService articlesTagsService;

	/**
	 * 获取全部标签
	 *
	 * @return 所有标签名列表
	 */
	public GetAllTagsResponse getAllTags() {
		// 获取 tag 列表
		LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.select(Tag::getTagName);
		List<Tag> tagList = list(
				new LambdaQueryWrapper<>()
		);

		// 构建响应
		List<TagVO> tagVOList = new ArrayList<>();
		for (Tag tag : tagList) {
			// 查询该标签有多少文章
			Long articlesCount = articlesTagsService.count(
					new LambdaQueryWrapper<ArticlesTags>()
							.eq(ArticlesTags::getTagId, tag.getId())
			);

			TagVO tagVO = TagVO.builder()
					.tagName(tag.getTagName())
					.articlesCount(articlesCount)
					.build();

			tagVOList.add(tagVO);
		}

		return new GetAllTagsResponse(tagVOList);
	}
}
