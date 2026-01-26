package com.realworld.api.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 错误响应包装类
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

	/**
	 * 错误信息集合
	 * key 为字段名，value 为错误信息列表
	 */
	@JsonProperty("errors")
	private Map<String, List<String>> errors;

	/**
	 * 创建单个错误信息的响应
	 *
	 * @param errorMessage 错误信息
	 * @return ErrorResponse 对象
	 */
	public static ErrorResponse of(String errorMessage) {
		Map<String, List<String>> errors = new HashMap<>();
		errors.put("body", Collections.singletonList(errorMessage));
		return new ErrorResponse(errors);
	}

	/**
	 * 创建多个错误信息的响应
	 *
	 * @param errorMessages 错误信息列表
	 * @return ErrorResponse 对象
	 */
	public static ErrorResponse of(List<String> errorMessages) {
		Map<String, List<String>> errors = new HashMap<>();
		errors.put("body", errorMessages);
		return new ErrorResponse(errors);
	}

	/**
	 * 创建字段级别的错误响应
	 *
	 * @param field        字段名
	 * @param errorMessage 错误信息
	 * @return ErrorResponse 对象
	 */
	public static ErrorResponse of(String field, String errorMessage) {
		Map<String, List<String>> errors = new HashMap<>();
		errors.put(field, Collections.singletonList(errorMessage));
		return new ErrorResponse(errors);
	}

	/**
	 * 创建多字段错误响应
	 *
	 * @param fieldErrors 字段错误映射
	 * @return ErrorResponse 对象
	 */
	public static ErrorResponse of(Map<String, List<String>> fieldErrors) {
		return new ErrorResponse(fieldErrors);
	}
}
