package com.realworld.api.config;

import com.realworld.api.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security安全配置类
 * 配置认证、授权、JWT过滤器等安全相关内容
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	/**
	 * 配置安全过滤链
	 * 这是 Spring Security 的核心配置
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// 禁用 CSRF 保护（RESTful API 不需要 CSRF 保护）
				.csrf(AbstractHttpConfigurer::disable)

				// 配置会话管理：无状态（使用 JWT，不使用 Session）
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)

				// 配置授权规则
				.authorizeHttpRequests(auth -> auth
						// 允许所有 OPTIONS 请求（CORS 预检请求）
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						// 白名单：允许未认证访问的接口
						.requestMatchers(HttpMethod.POST, "/users").permitAll()            // 注册接口
						.requestMatchers(HttpMethod.POST, "/users/login").permitAll()      // 登录接口
						.requestMatchers(HttpMethod.GET, "/tags").permitAll()              // 获取全部标签接口
						// 其他所有接口都需要认证
						.anyRequest().authenticated()
				)

				// 添加JWT认证过滤器（在 UsernamePasswordAuthenticationFilter 之前执行）
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

				// 禁用默认的登录页面
				.formLogin(AbstractHttpConfigurer::disable)

				// 禁用默认的登出功能
				.logout(AbstractHttpConfigurer::disable);

		return http.build();
	}

	/**
	 * 密码加密器
	 * 使用 BCrypt 算法进行密码加密
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 认证管理器
	 * 用于在登录时进行认证
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
