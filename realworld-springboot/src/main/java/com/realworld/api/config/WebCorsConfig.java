package com.realworld.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS 跨域配置
 */
@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")  // 允许所有路径
				.allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
				.allowedHeaders("*")  // 允许所有请求头
				.exposedHeaders("Authorization")  // 暴露 Authorization 头，让前端能读取 Token
				.allowCredentials(false)  // 不使用 Cookie
				.maxAge(3600);  // 预检请求缓存1小时
	}
}
