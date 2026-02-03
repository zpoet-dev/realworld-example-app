package com.realworld.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.realworld.api.mapper.CommentMapper;
import com.realworld.api.model.dto.CreateCommentDTO;
import com.realworld.api.model.entity.Article;
import com.realworld.api.model.entity.Comment;
import com.realworld.api.model.entity.User;
import com.realworld.api.model.entity.association.ArticlesUsersComments;
import com.realworld.api.model.response.CreateCommentResponse;
import com.realworld.api.model.response.GetCommentsForArticleResponse;
import com.realworld.api.model.vo.CommentVO;
import com.realworld.api.service.ArticleService;
import com.realworld.api.service.ArticlesUsersCommentsService;
import com.realworld.api.service.CommentService;
import com.realworld.api.service.UserService;
import com.realworld.api.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论服务实现类
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

	private final ArticleService articleService;

	private final UserUtil userUtil;

	private final ArticlesUsersCommentsService articlesUsersCommentsService;

	private final CommentMapper commentMapper;

	private final UserService userService;

	/**
	 * 创建评论
	 *
	 * @param slug
	 * @param createCommentDTO 评论信息
	 * @return 创建评论响应
	 */
	@Transactional(rollbackFor = Exception.class)
	public CreateCommentResponse createCommentForArticle(String slug, CreateCommentDTO createCommentDTO) {
		// 根据 slug 获取文章实体
		Article articleInDataBase = articleService.getOne(
				new LambdaQueryWrapper<Article>()
						.eq(Article::getSlug, slug)
		);

		// 保存评论记录
		User currentUser = userUtil.getCurrentUser();
		Comment comment = Comment.builder()
				.body(createCommentDTO.getBody())
				.createdAt(OffsetDateTime.now())
				.updatedAt(OffsetDateTime.now())
				.createdBy(currentUser.getId())
				.build();
		save(comment);

		// 保存中间表记录
		articlesUsersCommentsService.save(
				ArticlesUsersComments.builder()
						.articleId(articleInDataBase.getId())
						.articleSlug(slug)
						.UserId(currentUser.getId())
						.commentId(comment.getId())
						.build()
		);

		// 构建响应
		CommentVO commentVO = CommentVO.builder()
				.id(comment.getId())
				.body(comment.getBody())
				.createdAt(comment.getCreatedAt())
				.updatedAt(comment.getUpdatedAt())
				.author(currentUser.getUsername())
				.build();

		return new CreateCommentResponse(commentVO);
	}

	/**
	 * 获取文章全部评论评论
	 *
	 * @param slug
	 * @return 评论列表
	 */
	public GetCommentsForArticleResponse getCommentsForArticle(String slug) {
		// 从数据库中获取评论实例
		List<Comment> commentListInDataBase = commentMapper.findCommentsByArticle(slug);

		// 构建响应 VO
		List<CommentVO> commentList = new ArrayList<>();
		for (Comment commentInDataBase : commentListInDataBase) {
			CommentVO commentVO = CommentVO.builder()
					.id(commentInDataBase.getId())
					.body(commentInDataBase.getBody()).
					createdAt(commentInDataBase.getCreatedAt()).
					updatedAt(commentInDataBase.getUpdatedAt()).
					author(userService.UserIdToUserName(commentInDataBase.getCreatedBy())).
					build();

			commentList.add(commentVO);
		}

		return new GetCommentsForArticleResponse(commentList);

	}

	/**
	 * 删除评论
	 *
	 * @param commentId 评论 ID
	 */
	public void deleteCommentForArticle(Long commentId) {
		removeById(commentId);
	}
}
