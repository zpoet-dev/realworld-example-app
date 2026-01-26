package com.realworld.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置属性类
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	/**
	 * JWT 密钥，用于签名和验证 Token
	 */
	private String secret;

	/**
	 * Token过期时间（毫秒）
	 */
	private Long expiration;

	/**
	 * Token 前缀
	 */
	private String tokenPrefix;

	/**
	 * HTTP Header 名称
	 */
	private String headerName;
}
