package com.realworld.api.utils;

import com.realworld.api.enums.ErrorCode;
import com.realworld.api.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 用户工具类
 */
@Component
@RequiredArgsConstructor
public class UserUtil {

	public User getCurrentUser() {
		// 从 SecurityContext 获取认证信息
		UsernamePasswordAuthenticationToken auth =
				(UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

		// 未认证或认证信息为空
		if (auth == null || auth.getPrincipal() == null) {
			throw new RuntimeException(ErrorCode.UNAUTHORIZED.getMessage());
		}

		// 从 principal 中获取用户信息（在 JwtAuthenticationFilter 中设置）,并返回
		return (User) auth.getPrincipal();
	}

	public User getCurrentUserNullable() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null || auth instanceof AnonymousAuthenticationToken) {
			return null;
		}

		return (User) auth.getPrincipal();
	}
}
