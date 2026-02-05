package com.realworld.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.realworld.api.model.entity.association.UsersFollow;
import com.realworld.api.model.response.FollowProfileResponse;
import com.realworld.api.model.response.GetProfileResponse;
import com.realworld.api.model.response.UnfollowProfileResponse;

/**
 * 关注中间表服务接口
 */
public interface UsersFollowService extends IService<UsersFollow> {

	GetProfileResponse getProfileByUserName(String userName);

	FollowProfileResponse followProfileByUserName(String userName);

	UnfollowProfileResponse unfollowProfileByUserName(String userName);
}
