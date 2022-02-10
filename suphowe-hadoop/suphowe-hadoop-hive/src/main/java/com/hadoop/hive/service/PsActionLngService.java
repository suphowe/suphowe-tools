package com.hadoop.hive.service;

import com.hadoop.hive.dao.PsActionLngDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 员工操作类型业务处理
 * @author suphowe
 */
@Service
public class PsActionLngService {

    @Autowired
    private PsActionLngDao psActionLngDao;

    public List<Map<String, Object>> queryAll() {
        return psActionLngDao.queryAll();
    }
}
