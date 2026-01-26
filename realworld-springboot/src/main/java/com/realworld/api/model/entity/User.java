package com.realworld.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("users")
public class User {

	@TableId
	// 主键
	private Long id;

	// 用户名
	private String username;

	// 密码
	private String password;

	// 邮箱
	private String email;

	// 个人简介
	private String bio;

	// 头像
	private String image;

}
