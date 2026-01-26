package com.realworld.api.utils;

import com.realworld.api.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

	private final JwtProperties jwtProperties;

	/**
	 * 生成JWT Token
	 *
	 * @param userId 用户 ID
	 * @return JWT Token 字符串
	 */
	public String generateToken(Long userId) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtProperties.getExpiration());

		String token = Jwts.builder()
				.subject(String.valueOf(userId))  // 将用户 ID 作为 subject
				.issuedAt(now)                    // 签发时间
				.expiration(expiryDate)           // 过期时间
				.signWith(getSigningKey())        // 签名密钥
				.compact();

		log.debug("Generated JWT token for user: {}", userId);
		return token;
	}

	/**
	 * 获取签名密钥
	 * 使用 HMAC-SHA 算法
	 *
	 * @return SecretKey 对象
	 */
	private SecretKey getSigningKey() {
		byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * 从Token 中解析用户 ID
	 *
	 * @param token JWT Token
	 * @return 用户ID，解析失败返回null
	 */
	public Long getUserIdFromToken(String token) {
		try {
			Claims claims = parseToken(token);
			String subject = claims.getSubject();
			return Long.valueOf(subject);
		} catch (Exception e) {
			log.error("Failed to get user ID from token: {}", e.getMessage());
			return null;
		}
	}

	/**
	 * 解析JWT Token获取 Claims
	 *
	 * @param token JWT Token
	 * @return Claims 对象
	 */
	private Claims parseToken(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	/**
	 * 验证 Token 是否有效
	 *
	 * @param token JWT Token
	 * @return true=有效，false=无效
	 */
	public boolean validateToken(String token) {
		try {
			parseToken(token);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

	/**
	 * 检查 Token 是否过期
	 *
	 * @param token JWT Token
	 * @return true=已过期，false=未过期
	 */
	public boolean isTokenExpired(String token) {
		try {
			Claims claims = parseToken(token);
			return claims.getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			return true;
		} catch (Exception e) {
			log.error("Error checking token expiration: {}", e.getMessage());
			return true;
		}
	}
}
