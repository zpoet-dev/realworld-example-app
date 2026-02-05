package com.realworld.api.model.response;

import com.realworld.api.model.vo.ProfileVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProfileResponse {

	private ProfileVO profile;
}
