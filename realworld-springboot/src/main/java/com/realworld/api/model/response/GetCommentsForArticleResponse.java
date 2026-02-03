package com.realworld.api.model.response;

import com.realworld.api.model.vo.CommentVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentsForArticleResponse {

	private List<CommentVO> comments;
}
