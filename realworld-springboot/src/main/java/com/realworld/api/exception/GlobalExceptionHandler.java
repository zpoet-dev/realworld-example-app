package com.realworld.api.exception;

import com.realworld.api.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 统一处理所有 Controller 抛出的异常，返回友好的错误信息给前端
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 处理RuntimeException
	 * 包括业务异常
	 */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
		log.error("RuntimeException: {}", ex.getMessage());
		ErrorResponse errorResponse = ErrorResponse.of(ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
	}

	/**
	 * 处理所有未捕获的异常
	 * 作为兜底异常处理，防止系统错误信息泄露
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		log.error("Unexpected exception: ", ex);
		ErrorResponse errorResponse = ErrorResponse.of("Internal server error");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
}
