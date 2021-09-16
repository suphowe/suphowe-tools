package com.soft.dynamic.mapper;

import com.soft.dynamic.config.MyMapper;
import com.soft.dynamic.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper
 * @author suphowe
 */
@Mapper
public interface UserMapper extends MyMapper<User> {
}
