package com.soft.clickhouse.service;

import com.soft.clickhouse.dao.DmHrEmployeeInfoDetailfMapper;
import com.soft.clickhouse.entity.DmHrEmployeeInfoDetailf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 业务处理
 * @author suphowe
 */
@Service
public class DmHrEmployeeInfoDetailfService {

    @Autowired
    private DmHrEmployeeInfoDetailfMapper dmHrEmployeeInfoDetailfMapper;

    public List<DmHrEmployeeInfoDetailf> queryFirst100() {
        return dmHrEmployeeInfoDetailfMapper.queryFirst100();
    }
}
