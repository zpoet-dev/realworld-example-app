package com.realworld.api.model.response;

import com.realworld.api.model.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCurrentUserResponse {

	private UserVO user;
}
