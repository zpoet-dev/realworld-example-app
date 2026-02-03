package com.realworld.api.model.request;

import com.realworld.api.model.dto.CreateCommentDTO;
import lombok.Data;

/**
 * 创建评论请求包装类
 */
@Data
public class CreateCommentRequest {

	private CreateCommentDTO comment;
}
