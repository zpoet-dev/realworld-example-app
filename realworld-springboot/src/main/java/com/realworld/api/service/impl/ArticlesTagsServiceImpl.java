package com.realworld.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.realworld.api.mapper.ArticlesTagsMapper;
import com.realworld.api.model.entity.association.ArticlesTags;
import com.realworld.api.service.ArticlesTagsService;
import org.springframework.stereotype.Service;

@Service
public class ArticlesTagsServiceImpl extends ServiceImpl<ArticlesTagsMapper, ArticlesTags> implements ArticlesTagsService {

}
