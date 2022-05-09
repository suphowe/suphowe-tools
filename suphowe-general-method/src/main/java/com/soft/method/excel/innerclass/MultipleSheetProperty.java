package com.soft.method.excel.innerclass;

import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;

import java.util.List;

/**
 * 数据集模型
 * @author suphowe
 */
public class MultipleSheetProperty {

    private List<? extends BaseRowModel> data;

    private Sheet sheet;

    public List<? extends BaseRowModel> getData() {
        return data;
    }

    public void setData(List<? extends BaseRowModel> data) {
        this.data = data;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }
}
