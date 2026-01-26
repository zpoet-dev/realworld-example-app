package com.realworld.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.realworld.api.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper extends BaseMapper<User> {

}
