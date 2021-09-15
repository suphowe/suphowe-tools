package com.soft.mysql.dao.cafmvs;

import com.soft.mysql.entity.TsUser;
import com.soft.mysql.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户表
 * @author suphowe
 */
@Mapper
@Component
public interface TsUserMapper {

    /**
     * 查询所有用户信息
     * @return 用户信息
     */
	List<TsUser> queryAllUser();
}