package com.hadoop.hive.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 员工操作类型表
 * @author suphowe
 */
@Component
public class PsActionLngDao {

    @Autowired
    @Qualifier("hiveJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**
     * 查看所有数据
     * @return 所有操作数据
     */
    public List<Map<String, Object>> queryAll(){
        String sql = "select action,effdt,language_cd,action_descr,action_descrshort,integration_id,delete_flg,w_insert_dt from ods_hr.ps_action_lng";
        List<Map<String, Object>> datalist = jdbcTemplate.queryForList(sql);
        return datalist;
    };
}
