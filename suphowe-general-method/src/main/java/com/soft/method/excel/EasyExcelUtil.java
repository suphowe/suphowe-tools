package com.soft.method.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.soft.method.excel.innerclass.ExcelListener;
import com.soft.method.excel.innerclass.MultipleSheelPropety;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * EasyExcel使用公共类
 * @author suphowe
 */
public class EasyExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(EasyExcelUtil.class);

    private static final Sheet DEFAULT_SHEET;

    static {
        DEFAULT_SHEET = new Sheet(1, 0);
        DEFAULT_SHEET.setSheetName("sheet");
        //设置自适应宽度
        DEFAULT_SHEET.setAutoWidth(Boolean.TRUE);
    }

    /**
     * 读取小于1000行的Excel数据(读取全部)
     * @param filePath 文件绝对路径
     * @return 读取数据
     */
    public static List<Object> readLessthan1000Rows(String filePath){
        return readLessThan1000RowBySheet(filePath, 1, 0);
    }

    /**
     * 读小于1000行数据,带样式
     * @param filePath 文件绝对路径
     * @param sheetNum sheet编号,从1开始
     * @param rowNum 行号,从0开始
     * @return 读取数据
     */
    public static List<Object> readLessThan1000RowBySheet(String filePath, int sheetNum, int rowNum){
        if(!StringUtils.hasText(filePath)){
            return null;
        }
        Sheet sheet = new Sheet(sheetNum, rowNum);
        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(filePath);
            return EasyExcelFactory.read(fileStream, sheet);
        } catch (FileNotFoundException e) {
            logger.info("找不到文件或文件路径错误, 文件：{}", filePath);
        }finally {
            try {
                if(fileStream != null){
                    fileStream.close();
                }
            } catch (IOException e) {
                logger.info("excel文件读取失败, 失败原因：", e);
            }
        }
        return null;
    }

    /**
     * 读大于1000行数据(读取全部)
     * @param filePath 文件绝对路径
     * @param cellEmpty 处理空单元格,true时空单元格放空值
     * @return 读取数据
     */
    public static List<Object> readMoreThan1000Row(String filePath, boolean cellEmpty){
        return readMoreThan1000RowBySheet(filePath, 1, 0, cellEmpty);
    }

    /**
     * 读大于1000行数据, 带样式
     * @param filePath 文件绝对路径
     * @param sheetNum sheet编号,从1开始
     * @param rowNum 行号,从0开始
     * @param cellEmpty 处理空单元格,true时空单元格放空值
     * @return 读取数据
     */
    public static List<Object> readMoreThan1000RowBySheet(String filePath, int sheetNum, int rowNum, boolean cellEmpty){
        if(!StringUtils.hasText(filePath)){
            return null;
        }
        Sheet sheet = new Sheet(sheetNum, rowNum);
        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(filePath);
            ExcelListener excelListener = new ExcelListener();
            EasyExcelFactory.readBySax(fileStream, sheet, excelListener);
            if (cellEmpty){
                return emptyCellSet(excelListener);
            }
            return excelListener.getDatas();
        } catch (FileNotFoundException e) {
            logger.error("找不到文件或文件路径错误, 文件：{}", filePath);
        }finally {
            try {
                if(fileStream != null){
                    fileStream.close();
                }
            } catch (IOException e) {
                logger.error("excel文件读取失败, 失败原因：", e);
            }
        }
        return null;
    }

    /**
     * 生成excle
     * @param filePath 文件绝对路径
     * @param data 数据源
     * @param head 表头
     */
    public static void writeBySimple(String filePath, List<List<Object>> data, List<String> head){
        writeSimpleBySheet(filePath, data, head, null, "sheet");
    }

    /**
     * 生成excle
     * @param filePath 文件绝对路径
     * @param data 数据源
     * @param head 表头
     * @param sheet excle页面样式
     * @param sheetName sheet名称
     */
    public static void writeSimpleBySheet(String filePath, List<List<Object>> data, List<String> head, Sheet sheet, String sheetName){
        sheet = (sheet != null) ? sheet : DEFAULT_SHEET;
        sheet.setSheetName(sheetName);
        if(head != null){
            List<List<String>> list = new ArrayList<>();
            head.forEach(h -> list.add(Collections.singletonList(h)));
            sheet.setHead(list);
        }
        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            writer.write1(data,sheet);
        } catch (FileNotFoundException e) {
            logger.error("找不到文件或文件路径错误, 文件：{}", filePath);
        }finally {
            try {
                if(writer != null){
                    writer.finish();
                }
                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                logger.error("excel文件导出失败, 失败原因：", e);
            }
        }
    }

    /**
     * 生成excle
     * @param filePath 文件绝对路径
     * @param data 数据源
     */
    public static void writeWithTemplate(String filePath, List<? extends BaseRowModel> data){
        writeWithTemplateAndSheet(filePath, data, null, "sheet");
    }

    /**
     * 生成excle
     * @param filePath 文件绝对路径
     * @param data 数据源
     * @param sheet excle页面样式
     * @param sheetName sheet名称
     */
    public static void writeWithTemplateAndSheet(String filePath, List<? extends BaseRowModel> data, Sheet sheet, String sheetName){
        if(CollectionUtils.isEmpty(data)){
            return;
        }
        sheet = (sheet != null) ? sheet : DEFAULT_SHEET;
        sheet.setSheetName(sheetName);
        sheet.setClazz(data.get(0).getClass());

        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            writer.write(data,sheet);
        } catch (FileNotFoundException e) {
            logger.error("找不到文件或文件路径错误, 文件：{}", filePath);
        }finally {
            try {
                if(writer != null){
                    writer.finish();
                }

                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                logger.error("excel文件导出失败, 失败原因：", e);
            }
        }

    }

    /**
     * 生成多Sheet的excle
     * @param filePath 文件绝对路径
     * @param multipleSheelPropetys 数据集
     */
    public static void writeWithMultipleSheel(String filePath,List<MultipleSheelPropety> multipleSheelPropetys){
        if(CollectionUtils.isEmpty(multipleSheelPropetys)){
            return;
        }
        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            for (MultipleSheelPropety multipleSheelPropety : multipleSheelPropetys) {
                Sheet sheet = multipleSheelPropety.getSheet() != null ? multipleSheelPropety.getSheet() : DEFAULT_SHEET;
                if(!CollectionUtils.isEmpty(multipleSheelPropety.getData())){
                    sheet.setClazz(multipleSheelPropety.getData().get(0).getClass());
                }
                writer.write(multipleSheelPropety.getData(), sheet);
            }
        } catch (FileNotFoundException e) {
            logger.error("找不到文件或文件路径错误, 文件：{}", filePath);
        }finally {
            try {
                if(writer != null){
                    writer.finish();
                }
                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                logger.error("excel文件导出失败, 失败原因：", e);
            }
        }

    }

    /**
     * 处理空值
     * @param excelListener excel数据
     * @return 处理后结果
     */
    private static List<Object> emptyCellSet(ExcelListener excelListener) {
        List<Object> result = new ArrayList<>();

        List<Object> datas = excelListener.getDatas();
        //获取表头的size
        int size = ((List<String>) datas.get(0)).size();
        //解析内容
        for (Object data : datas) {
            //声明一个数组, 是excel列, 要保持列数
            Object[] cells = new Object[size];
            List<String> strings = (List<String>) data;
            if (strings.size() != size) {
                System.arraycopy(strings.toArray(), 0, cells, 0, strings.toArray().length);
            } else {
                cells = strings.toArray();
            }

            List<Object> rowResult = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                rowResult.add(j, cells[j]);
            }
            result.add(rowResult);
        }
        return result;
    }

}
