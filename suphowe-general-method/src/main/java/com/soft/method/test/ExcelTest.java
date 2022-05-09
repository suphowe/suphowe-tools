package com.soft.method.test;

import com.alibaba.excel.EasyExcel;
import com.soft.method.excel.innerclass.MergeStrategy;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * excel操作测试
 * @author suphowe
 */
public class ExcelTest {

    public static void main(String[] args) throws Exception {
        String fileName = "C:/test/" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        CellRangeAddress item1 = new CellRangeAddress(1, 3, 2, 5);
        CellRangeAddress item2 = new CellRangeAddress(2, 2, 2, 5);
        CellRangeAddress item3 = new CellRangeAddress(3, 3, 0, 1);
        List<CellRangeAddress> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);

        EasyExcel.write(fileName).head(head()).sheet("模板")
                //自定义合并 单元格
                .registerWriteHandler(new MergeStrategy(list))
                .doWrite(dataList());
    }

    /**
     * 创建表头
     * @return
     */
    private static List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("字符串");
        List<String> head1 = new ArrayList<String>();
        head1.add("数字");
        List<String> head2 = new ArrayList<String>();
        head2.add("日期");
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    /**
     * 创建数据
     * @return
     */
    private static List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (int i = 0; i < 3; i++) {
            List<Object> data = new ArrayList<Object>();
            data.add("字符串" + i);
            data.add("数字" + i);
            data.add("时间" + i);
            list.add(data);
        }
        return list;
    }
}
