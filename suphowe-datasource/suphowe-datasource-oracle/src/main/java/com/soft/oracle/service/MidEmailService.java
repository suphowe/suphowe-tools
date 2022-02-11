package com.soft.oracle.service;

import com.soft.oracle.dao.MidEmailMapper;
import com.soft.oracle.entity.MidEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MID_EMAIL表操作业务类
 * @author suphowe
 */
@Service
public class MidEmailService {

    @Autowired
    private MidEmailMapper MidEmailMapper;

    /**
     * 读取前100 行数据
     * 事务控制
     */
    @Transactional(readOnly = true)
    public List<MidEmail> queryFirst100(){
        return MidEmailMapper.queryFirst100();
    }
}
