package com.soft.code.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 表
 * @author suphowe
 */
@Data
@ToString
public class TableClass {

    private String tableName;
    private String modelName;
    private String serviceName;
    private String mapperName;
    private String controllerName;
    private String packageName;
    private List<ColumnClass> columns;
}
