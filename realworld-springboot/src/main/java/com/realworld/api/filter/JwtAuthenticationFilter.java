package com.realworld.api.filter;

import com.realworld.api.config.JwtProperties;
import com.realworld.api.mapper.UserMapper;
import com.realworld.api.model.entity.User;
import com.realworld.api.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT 认证过滤器
 * 在每个请求处理前执行，验并证 JWT Token 设置 Security 上下文
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	private final JwtProperties jwtProperties;

	private final UserMapper userMapper;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {

		try {
			// 从请求头中获取 Token
			String token = extractTokenFromRequest(request);

			// Token为空，直接放行（由 Spring Security 处理未认证情况）
			if (!StringUtils.hasText(token)) {
				filterChain.doFilter(request, response);
				return;
			}

			// 验证 Token 有效性
			if (!jwtUtil.validateToken(token)) {
				log.warn("Invalid JWT token for request: {}", request.getRequestURI());
				filterChain.doFilter(request, response);
				return;
			}

			// 从 Token 中解析用户 ID
			Long userId = jwtUtil.getUserIdFromToken(token);
			if (userId == null) {
				log.warn("Failed to extract user ID from token");
				filterChain.doFilter(request, response);
				return;
			}

			// 加载用户信息
			User user = userMapper.selectById(userId);
			if (user == null) {
				log.warn("User not found with ID: {}", userId);
				filterChain.doFilter(request, response);
				return;
			}

			// 创建认证对象并设置到 Security 上下文
			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(
							user,  // principal 设置为 User 对象
							null,
							Collections.emptyList()  // 权限列表为空
					);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.debug("Set authentication for user: {} in SecurityContext", userId);

		} catch (Exception e) {
			log.error("Cannot set user authentication: {}", e.getMessage(), e);
		}

		// 继续过滤链
		filterChain.doFilter(request, response);
	}

	/**
	 * 从HTTP请求中提取JWT Token
	 *
	 * @param request HTTP 请求
	 * @return JWT Token字符串，如果不存在返回null
	 */
	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(jwtProperties.getHeaderName());

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperties.getTokenPrefix() + " ")) {
			// 移除前缀，返回纯Token
			return bearerToken.substring(jwtProperties.getTokenPrefix().length() + 1);
		}

		return null;
	}
}
