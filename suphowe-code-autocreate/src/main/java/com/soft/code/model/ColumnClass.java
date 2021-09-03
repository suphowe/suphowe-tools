package com.soft.code.model;

import lombok.Data;
import lombok.ToString;

/**
 * 字段实体
 * @author suphowe
 */
@Data
@ToString
public class ColumnClass {

    private String propertyName;
    private String columnName;
    private String type;
    private String remark;
    private Boolean primary;
}
