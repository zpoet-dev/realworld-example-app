package com.realworld.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.realworld.api.mapper.ArticlesUsersCommentsMapper;
import com.realworld.api.model.entity.association.ArticlesUsersComments;
import com.realworld.api.service.ArticlesUsersCommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 文章-用户-评论中间表服务实现类
 */
@Service
@RequiredArgsConstructor
public class ArticlesUsersCommentsServiceImpl extends ServiceImpl<ArticlesUsersCommentsMapper, ArticlesUsersComments> implements ArticlesUsersCommentsService {

}
