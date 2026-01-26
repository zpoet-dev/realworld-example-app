package com.realworld.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码枚举
 * 统一管理所有业务错误码
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

	// 通用错误
	SUCCESS(200, "Success"),
	INTERNAL_SERVER_ERROR(500, "Internal server error"),
	BAD_REQUEST(400, "Bad request"),
	UNAUTHORIZED(401, "Unauthorized"),
	FORBIDDEN(403, "Forbidden"),
	NOT_FOUND(404, "Resource not found"),

	// 用户相关错误 (2000-2999)
	USER_NOT_FOUND(2001, "User not found"),
	USER_ALREADY_EXISTS(2002, "User already exists"),
	EMAIL_ALREADY_EXISTS(2003, "Email is already taken"),
	USERNAME_ALREADY_EXISTS(2004, "Username is already taken"),
	INVALID_PASSWORD(2005, "Invalid password"),
	INVALID_CREDENTIALS(2006, "Invalid email or password"),

	// 认证相关错误 (3000-3999)
	TOKEN_INVALID(3001, "Invalid token"),
	TOKEN_EXPIRED(3002, "Token expired"),
	TOKEN_MISSING(3003, "Token is required"),

	// 参数校验错误 (4000-4999)
	VALIDATION_ERROR(4001, "Validation error"),
	MISSING_REQUIRED_FIELD(4002, "Missing required field");

	/**
	 * HTTP 状态码
	 */
	private final Integer httpStatus;

	/**
	 * 错误信息
	 */
	private final String message;
}
