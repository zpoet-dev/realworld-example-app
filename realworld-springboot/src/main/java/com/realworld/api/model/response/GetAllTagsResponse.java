package com.realworld.api.model.response;

import com.realworld.api.model.vo.TagVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllTagsResponse {

	private List<TagVO> tags;
}
