package com.realworld.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.realworld.api.mapper.TagMapper;
import com.realworld.api.model.entity.Tag;
import com.realworld.api.model.response.GetAllTagsResponse;
import com.realworld.api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签服务实现类
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

	/**
	 * 获取全部标签
	 *
	 * @return 所有标签名列表
	 */
	public GetAllTagsResponse getAllTags() {
		// 获取 tagNames 列表
		LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.select(Tag::getTagName);
		List<String> tagNamesList = list(queryWrapper).stream().map(Tag::getTagName).toList();

		return new GetAllTagsResponse(tagNamesList);
	}
}
