package com.hadoop.hive.entity;

import lombok.Data;

/**
 * 员工操作类型表
 * @author suphowe
 */
@Data
public class PsActionLng {

    String action;
    String effdt;
    String languageCd;
    String actionDescr;
    String actionDescrshort;
    String integrationId;
    String deleteFlg;
    String wInsertDt;
}
