package com.realworld.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.realworld.api.model.entity.Tag;
import com.realworld.api.model.response.GetAllTagsResponse;

/**
 * 标签服务接口
 */
public interface TagService extends IService<Tag> {

	GetAllTagsResponse getAllTags();
}
