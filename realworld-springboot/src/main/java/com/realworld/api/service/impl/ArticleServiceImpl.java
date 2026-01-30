package com.realworld.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.realworld.api.enums.ErrorCode;
import com.realworld.api.mapper.ArticleMapper;
import com.realworld.api.mapper.TagMapper;
import com.realworld.api.model.dto.CreateArticleDTO;
import com.realworld.api.model.dto.UpdateArticleBySlugDTO;
import com.realworld.api.model.entity.Article;
import com.realworld.api.model.entity.Tag;
import com.realworld.api.model.entity.User;
import com.realworld.api.model.entity.association.ArticlesTags;
import com.realworld.api.model.response.CreateArticleResponse;
import com.realworld.api.model.response.GetArticleListResponse;
import com.realworld.api.model.response.GetArticleResponse;
import com.realworld.api.model.response.UpdateArticleBySlugResponse;
import com.realworld.api.model.vo.ArticleVO;
import com.realworld.api.service.*;
import com.realworld.api.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * 标签服务实现类
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

	private final UserUtil userUtil;

	private final TagService tagService;

	private final ArticlesTagsService articlesTagsService;

	private final TagMapper tagMapper;

	private final UserService userService;

	private final UsersFavoriteArticlesService usersFavoriteArticlesService;

	private final ArticleMapper articleMapper;

	/**
	 * 新建文章
	 *
	 * @param createArticleDTO 文章信息
	 * @return 新建文章响应
	 */
	@Transactional(rollbackFor = Exception.class)
	public CreateArticleResponse createArticle(CreateArticleDTO createArticleDTO) {
		User currentUser = userUtil.getCurrentUser();
		Long currentUserId = currentUser.getId();
		String slug = createArticleDTO.getTitle().replaceAll("\\s+", "-");
		OffsetDateTime now = OffsetDateTime.now();
		List<String> tagNameList = createArticleDTO.getTagList();

		// 文章存入数据库
		Article article = Article.builder()
				.createdBy(currentUserId)  // 创建人为当前登录用户
				.title(createArticleDTO.getTitle())
				.slug(slug)
				.body(createArticleDTO.getBody())
				.createdAt(now)
				.updatedAt(now)
				.description(createArticleDTO.getDescription())
				.build();
		save(article);

		// 如果 tagList 不为空
		if (!tagNameList.isEmpty()) {
			for (String tagNameItem : tagNameList) {
				LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.eq(Tag::getTagName, tagNameItem);
				Tag tagNameInDataBase = tagService.getOne(queryWrapper);

				// 如果当前没有该标签
				if (tagNameInDataBase == null) {
					// 标签存入数据库
					Tag tag = Tag.builder()
							.tagName(tagNameItem)
							.build();
					tagService.save(tag);

					// 存入中间表
					ArticlesTags articlesTags = ArticlesTags.builder()
							.articleId(article.getId())
							.tagId(tag.getId())
							.build();
					articlesTagsService.save(articlesTags);
				}
			}
		}

		// 构建响应 VO
		ArticleVO articleVO = ArticleVO.builder()
				.title(createArticleDTO.getTitle())
				.slug(slug)
				.body(createArticleDTO.getBody())
				.createdAt(now)
				.updatedAt(now)
				.description(createArticleDTO.getDescription())
				.tagList(createArticleDTO.getTagList())
				.author(currentUser)
				.favorited(false)  // 默认当前登录用户没有收藏
				.favoritesCount(0)  // 默认收藏数为 0
				.build();

		return new CreateArticleResponse(articleVO);
	}

	/**
	 * 获取文章列表
	 *
	 * @param userName 用户名
	 * @param tagName  标签名
	 * @return 文章列表响应，包含文章列表及文章总数
	 */
	public GetArticleListResponse getArticleList(String userName, String tagName) {
		List<Article> allArticlesInDataBase;

		// 形参含有用户名
		if (userName != null) {
			allArticlesInDataBase = articleMapper.findArticlesByAuthor(userName);
		}

		// 形参含有标签名
		else if (tagName != null) {
			allArticlesInDataBase = articleMapper.findArticlesByTag(tagName);
		}

		// 无形参
		else {
			// 获取当前登录用户
			User currentUser = userUtil.getCurrentUserNullable();

			if (currentUser == null) {  // 用户未登录，获取全部文章
				allArticlesInDataBase = list();
			} else {  // 用户已登录，获取该作者的文章
				allArticlesInDataBase = list(
						new LambdaQueryWrapper<Article>()
								.eq(Article::getCreatedBy, currentUser.getId())
				);
			}
		}

		// 构建响应
		List<ArticleVO> allArticlesForResponse = allArticlesInDataBase.stream()
				.map(this::ArticleToArticleVO)
				.toList();

		return new GetArticleListResponse(allArticlesForResponse, allArticlesForResponse.size());
	}

	/**
	 * 更新文章
	 *
	 * @param slug
	 * @param updateArticleBySlugDTO 更新内容
	 * @return 更新响应
	 * @throws RuntimeException 当文章不是当前登录用户创建的时
	 */
	@Transactional(rollbackFor = Exception.class)
	public UpdateArticleBySlugResponse updateArticleBySlug(String slug, UpdateArticleBySlugDTO updateArticleBySlugDTO) {
		// 获取数据库中实体
		Article articleInDataBase = getOne(
				new LambdaQueryWrapper<Article>()
						.eq(Article::getSlug, slug)
		);

		// 判断该文章是否是当前用户创建的
		User currentUser = userUtil.getCurrentUser();
		boolean isCurrentCreated = currentUser.getId().equals(articleInDataBase.getCreatedBy());
		if (!isCurrentCreated) {
			throw new RuntimeException(ErrorCode.FORBIDDEN.getMessage());
		}

		// 更新数据库
		update(
				new LambdaUpdateWrapper<Article>()
						.eq(Article::getSlug, slug)
						.set(Article::getBody, updateArticleBySlugDTO.getBody())
						.set(Article::getUpdatedAt, OffsetDateTime.now())  // 更新时间改为当前
		);

		// 获取更新后的实体
		Article updatedArticle = getById(articleInDataBase.getId());

		// 获取标签列表
		List<String> tagList = tagMapper.findTagNamesByArticleId(updatedArticle.getId());

		// 获取文章作者
		User author = userService.getById(updatedArticle.getCreatedBy());

		// 获取文章收藏信息
		boolean favorited = usersFavoriteArticlesService.isCurrentUserFavorited(updatedArticle.getId());
		Integer favoritesCount = usersFavoriteArticlesService.getFavoritesCountByArticleId(updatedArticle.getId());

		ArticleVO articleVO = ArticleVO.builder()
				.title(updatedArticle.getTitle())
				.slug(slug)
				.body(updatedArticle.getBody())
				.createdAt(updatedArticle.getCreatedAt())
				.updatedAt(updatedArticle.getUpdatedAt())
				.description(updatedArticle.getDescription())
				.tagList(tagList)
				.author(author)
				.favorited(favorited)
				.favoritesCount(favoritesCount)
				.build();

		return new UpdateArticleBySlugResponse(articleVO);
	}

	/**
	 * 根据 slug 删除文章
	 *
	 * @param slug
	 * @throws RuntimeException 当文章不是当前登录用户创建的时
	 */
	public void deleteArticleBySlug(String slug) {
		// 获取数据库中实体
		Article articleInDataBase = getOne(
				new LambdaQueryWrapper<Article>()
						.eq(Article::getSlug, slug)
		);

		// 判断该文章是否是当前用户创建的
		User currentUser = userUtil.getCurrentUser();
		boolean isCurrentCreated = currentUser.getId().equals(articleInDataBase.getCreatedBy());
		if (!isCurrentCreated) {
			throw new RuntimeException(ErrorCode.FORBIDDEN.getMessage());
		}

		// 删除文章
		removeById(articleInDataBase.getId());
	}

	/**
	 * 根据 slug 获取文章详情
	 *
	 * @param slug
	 * @return 文章响应
	 */
	public GetArticleResponse getArticleBySlug(String slug) {
		// 获取实体
		Article articleInDataBase = getOne(
				new LambdaQueryWrapper<Article>()
						.eq(Article::getSlug, slug)
		);

		// 获取标签列表
		List<String> tagList = tagMapper.findTagNamesByArticleId(articleInDataBase.getId());

		// 获取文章作者
		User author = userService.getById(articleInDataBase.getCreatedBy());

		// 获取文章收藏信息
		boolean favorited = usersFavoriteArticlesService.isCurrentUserFavorited(articleInDataBase.getId());
		Integer favoritesCount = usersFavoriteArticlesService.getFavoritesCountByArticleId(articleInDataBase.getId());

		// 构建响应
		ArticleVO articleVO = ArticleVO.builder()
				.title(articleInDataBase.getTitle())
				.slug(articleInDataBase.getSlug())
				.body(articleInDataBase.getBody())
				.createdAt(articleInDataBase.getCreatedAt())
				.updatedAt(articleInDataBase.getUpdatedAt())
				.description(articleInDataBase.getDescription())
				.tagList(tagList)
				.author(author)
				.favorited(favorited)
				.favoritesCount(favoritesCount)
				.build();

		return new GetArticleResponse(articleVO);
	}

	/**
	 * 将 Article 实体转换为 ArticleVO
	 *
	 * @param article 文章实体对象
	 * @return 文章视图对象 ArticleVO
	 */
	private ArticleVO ArticleToArticleVO(Article article) {
		// 获取标签列表
		List<String> tagList = tagMapper.findTagNamesByArticleId(article.getId());

		// 获取文章作者
		User author = userService.getById(article.getCreatedBy());

		// 获取文章收藏信息
		boolean favorited = usersFavoriteArticlesService.isCurrentUserFavorited(article.getId());
		Integer favoritesCount = usersFavoriteArticlesService.getFavoritesCountByArticleId(article.getId());

		return ArticleVO.builder()
				.title(article.getTitle())
				.slug(article.getSlug())
				.body(article.getBody())
				.createdAt(article.getCreatedAt())
				.updatedAt(article.getUpdatedAt())
				.description(article.getDescription())
				.tagList(tagList)
				.author(author)
				.favorited(favorited)
				.favoritesCount(favoritesCount)
				.build();
	}
}
