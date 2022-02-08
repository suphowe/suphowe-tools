package com.hadoop.hive.dao.impl;

import com.hadoop.hive.dao.HiveJdbcBaseDao;

/**
 * Hive
 * @author suphowe
 */
public class HiveDao extends HiveJdbcBaseDao {

    /**
     * 测试获取hive数据库数据信息
     */
    public String test() {
        String sql = "SELECT name from sixmonth limit 1";
        return this.getJdbcTemplate().queryForObject(sql,String.class);
    }
}
