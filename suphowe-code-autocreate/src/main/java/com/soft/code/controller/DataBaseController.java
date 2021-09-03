package com.soft.code.controller;

import com.google.common.base.CaseFormat;
import com.soft.code.model.DataBase;
import com.soft.code.model.ResultBean;
import com.soft.code.model.TableClass;
import com.soft.code.utils.DataBaseUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库控制
 * @author suphowe
 */
@RestController
public class DataBaseController {

    @PostMapping("/connect")
    public ResultBean connect(@RequestBody DataBase dataBase) {
        Connection con = DataBaseUtils.initDb(dataBase);
        if (con != null) {
            return ResultBean.ok("数据库连接成功");
        }
        return ResultBean.error("数据库连接失败");
    }

    @PostMapping("/config")
    public ResultBean config(@RequestBody Map<String, String> map) {
        String packageName = map.get("packageName");
        try {
            Connection connection = DataBaseUtils.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, null);
            List<TableClass> tableClassList = new ArrayList<>();
            while (tables.next()) {
                TableClass tableClass = new TableClass();
                tableClass.setPackageName(packageName);
                String tableName = tables.getString("TABLE_NAME");
                String modelName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
                tableClass.setTableName(tableName);
                tableClass.setModelName(modelName);
                tableClass.setControllerName(modelName + "Controller");
                tableClass.setMapperName(modelName + "Mapper");
                tableClass.setServiceName(modelName+"Service");
                tableClassList.add(tableClass);
            }
            return ResultBean.ok("数据库信息读取成功", tableClassList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultBean.error("数据库信息读取失败");
    }
}
