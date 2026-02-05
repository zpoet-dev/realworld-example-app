package com.realworld.api.model.entity.association;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("users_follow")
public class UsersFollow {

	// 主键
	private Long id;

	// 关注人 Id
	private Long followUserId;

	// 被关注人 Id
	private Long followedUserId;
}
