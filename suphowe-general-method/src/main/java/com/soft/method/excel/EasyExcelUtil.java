package com.soft.method.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.soft.method.excel.innerclass.ExcelListener;
import com.soft.method.excel.innerclass.MultipleSheetProperty;
import com.soft.method.excel.innerclass.MergeStrategy;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    public static List<Object> readLessThan1000Rows(String filePath){
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
            return excelListener.getData();
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
     * @param multipleSheetProperties 数据集
     */
    public static void writeWithMultipleSheet(String filePath,List<MultipleSheetProperty> multipleSheetProperties){
        if(CollectionUtils.isEmpty(multipleSheetProperties)){
            return;
        }
        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            for (MultipleSheetProperty multipleSheetProperty : multipleSheetProperties) {
                Sheet sheet = multipleSheetProperty.getSheet() != null ? multipleSheetProperty.getSheet() : DEFAULT_SHEET;
                if(!CollectionUtils.isEmpty(multipleSheetProperty.getData())){
                    sheet.setClazz(multipleSheetProperty.getData().get(0).getClass());
                }
                writer.write(multipleSheetProperty.getData(), sheet);
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
     * 合并单元格写文件
     * @param filePath 导出文件
     * @param head 表头
     * @param sheetName sheet名称
     * @param cellRangeAddress 自定义需要合并单元格 CellRangeAddress(开始行,结束行,开始列,结束列)
     * @param data 主体数据
     */
    public static void writeMergeCell(String filePath, List<List<String>> head, String sheetName,
                                      List<CellRangeAddress> cellRangeAddress, List<List<Object>> data) {
        EasyExcel.write(filePath).head(head).sheet(sheetName)
                .registerWriteHandler(new MergeStrategy(cellRangeAddress))
                .doWrite(data);
    }

    /**
     * 模板数据写入
     * @param response 返回
     * @param excelTemplate excel模板
     * @param listData list数据
     * @param data 数据
     */
    public static void writeTemplateExcel(HttpServletResponse response, String excelTemplate, Object listData, Object data){
        /*
         * application/vnd.ms-excel;charset=UTF-8 会出现找不到转换器的错误 no converter
         * 需要实现WebMvcConfigurer,自定义转换器
         * 实现在 MyWebMvcConfigurer.java文件中
         */
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        // 在代理服务器端防止缓冲
        response.setDateHeader("Expires", 0);
        response.setHeader("Content-disposition", "attachment;filename=" + new Date() + ".xlsx");

        //获取模板
        ClassPathResource classPathResource = new ClassPathResource(excelTemplate);
        try (InputStream inputStream = classPathResource.getInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            //设置输出流和模板信息
            ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            if (data != null) {
                excelWriter.fill(data, writeSheet);
            }
            //开启自动换行,自动换行表示每次写入一条list数据是都会重新生成一行空行,此选项默认是关闭的,需要提前设置为true
            if (listData != null) {
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                excelWriter.fill(listData, fillConfig, writeSheet);
            }
            excelWriter.finish();
            outputStream.flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理空值
     * @param excelListener excel数据
     * @return 处理后结果
     */
    private static List<Object> emptyCellSet(ExcelListener excelListener) {
        List<Object> result = new ArrayList<>();

        List<Object> data = excelListener.getData();
        //获取表头的size
        int size = ((List<String>) data.get(0)).size();
        //解析内容
        for (Object object : data) {
            //声明一个数组, 是excel列, 要保持列数
            Object[] cells = new Object[size];
            List<String> strings = (List<String>) object;
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
