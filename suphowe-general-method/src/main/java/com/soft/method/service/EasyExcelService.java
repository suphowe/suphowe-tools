package com.soft.method.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EasyExcel 测试
 * @author suphowe
 */
@Service
public class EasyExcelService {

    public HashMap<String, Object> importDatabase(List<Object> list){
        Map<String,Object>[] importList = new HashMap[list.size()-1];
        for(int i = 1; i<list.size(); i++){
            String[] data = list.get(i).toString().trim().substring(1).replaceAll("]","").split(",");
            HashMap<String, Object> importParams = new HashMap<>(16);
            importParams.put("ID", data[0]);
            importParams.put("A", data[1]);
            importParams.put("B", data[2]);
            importParams.put("C", data[3]);
            importParams.put("D", data[4]);
            importParams.put("E", data[5]);
            importParams.put("F", data[6]);
            importParams.put("G", data[7]);
            importParams.put("H", data[8]);
            importParams.put("I", data[9]);
            importList[i-1] = (importParams);
        }
        return null;
    }
}
