package com.soft.method.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Excel公共方法类
 * @author suphowe
 */
public class ExcelPoiUtil {

    //2003- 版本的excel
    private final static String EXCEL_2003L = ".xls";
    //2007+ 版本的excel
    private final static String EXCEL_2007U = ".xlsx";

    /**
     * 获取IO流中的数据，组装成List<List<Object>>对象
     * @param file Excel文件
     * @return List数据
     * @throws Exception 异常
     */
    public List<List<Object>> importExcel(File file) throws Exception {
        List<List<Object>> list = new ArrayList<>();

        //创建Excel工作薄
        Workbook work = this.getWorkbook(file);
        if (work == null) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        list = new ArrayList<List<Object>>();
        //遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            //遍历当前sheet中的所有行
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }

                //遍历所有的列
                List<Object> li = new ArrayList<Object>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(this.getCellValue(cell));
                }
                list.add(li);
            }
        }
        return list;
    }

    /**
     * 生成excel文件
     * @param filename 文件名
     * @param workbook 工作空间
     */
    public boolean buildExcelFile(String filename, HSSFWorkbook workbook){
        boolean result = false;
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            workbook.write(fos);
            fos.flush();
            fos.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 浏览器下载excel
     * @param filename 文件名
     * @param workbook 工作空间
     * @param response 返回
     */
    public boolean buildExcelDocument(String filename, HSSFWorkbook workbook, HttpServletResponse response){
        boolean result = false;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 生成并下载Excel
     * @param headerList 表头
     * @param keysList 取值键
     * @param isSerial 是否生成序号，序号从1开始
     * @param sheetName sheet名称
     * @param dataList 写入数据
     * @param fileName 文件名
     * @return 生成结果
     */
    public boolean buildAndDownloadExcel(HttpServletResponse response, List<String> headerList, List<String> keysList, boolean isSerial,
                                         String sheetName, List<HashMap<String, Object>> dataList, String fileName) {
        boolean result = false;
        HSSFWorkbook workbook = new HSSFWorkbook();
        result = createExcel(workbook, headerList, keysList, isSerial, sheetName, dataList);
        result = buildExcelFile(fileName, workbook);
        result = buildExcelDocument(fileName, workbook, response);
        return result;
    }

    /**
     * Excel内容设置
     * @param workbook 工作空间
     * @param headerList 表头
     * @param isSerial 是否生成序号，序号从1开始
     * @param sheetName sheet名称
     * @param dataList 写入数据
     */
    public boolean createExcel(HSSFWorkbook workbook, List<String> headerList, List<String> keysList, boolean isSerial, String sheetName, List<HashMap<String, Object>> dataList) {
        if (headerList.size() != keysList.size()){
            return false;
        }
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //列宽
        List<Integer> colWidth = new ArrayList<>(headerList.size());
        //只考虑一个sheet的情况
        HSSFRow row = sheet.createRow(0);

        //表头设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        HSSFCell cell;
        //生成表头
        int headRow=0;
        if (isSerial){
            //设置第一个列为序号列
            cell = row.createCell(0);
            cell.setCellValue("序号");
            cell.setCellStyle(style);
            colWidth.add(0, 15);
            headRow = 1;

        }
        for (String header : headerList) {
            cell = row.createCell(headRow);
            cell.setCellValue(header);
            cell.setCellStyle(style);
            colWidth.add(headRow, header.getBytes().length);
            headRow++;
        }

        int rowNum = 1;
        //需要生成序号
        if (isSerial){
            for (HashMap<String, Object> rowData: dataList){
                HSSFRow currRow = sheet.createRow(rowNum);
                int j = 0;
                for (String key: keysList){
                    currRow.createCell(0).setCellValue(rowNum);
                    currRow.createCell(j + 1).setCellValue((String) rowData.get(key));
                    colWidth.set(j + 1, ((String) rowData.get(key)).getBytes().length);
                    j++;
                }
                rowNum++;
            }
        //无需生成序号
        }else{
            for (HashMap<String, Object> rowData: dataList){
                HSSFRow currRow = sheet.createRow(rowNum);
                int j = 0;
                for (String key: keysList){
                    currRow.createCell(j).setCellValue((String) rowData.get(key));
                    colWidth.set(j, ((String) rowData.get(key)).getBytes().length);
                    j++;
                }
                rowNum++;
            }
        }
        adjustColWidth(colWidth, sheet);
        return true;
    }

    /**
     * 调整列宽
     */
    private void adjustColWidth(List<Integer> colWidth, Sheet sheet){
        for (int i = 0; i < colWidth.size(); i++) {
            sheet.setColumnWidth(i, (int) (colWidth.get(i)  * 1.2d * 256 > 12 * 256 ? colWidth.get(i) * 1.2d * 256 : 12 * 256));
        }
    }

    /**
     * 根据文件后缀，自适应文件的版本
     *
     * @param file Excel文件
     * @return excel工作表
     * @throws Exception 异常
     */
    private Workbook getWorkbook(File file) throws Exception {
        Workbook wb = null;
        String fileType = file.getName().substring(file.getName().lastIndexOf("."));
        if (EXCEL_2003L.equals(fileType)) {
            wb = new HSSFWorkbook(new FileInputStream(file));  //2003-
        } else if (EXCEL_2007U.equals(fileType)) {
            wb = new XSSFWorkbook(new FileInputStream(file));  //2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 对表格中数值进行格式化
     * @param cell 表格数据
     * @return 格式化数据
     */
    private Object getCellValue(Cell cell) {
        //用String接收所有返回的值
        String value = null;
        //格式化number String字符
        DecimalFormat df = new DecimalFormat("0");
        //日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        //格式化数字
        DecimalFormat df2 = new DecimalFormat("0.00");

        switch (cell.getCellType()) {
            //String类型的数据
            case STRING:
                value = cell.getStringCellValue();
                break;

            //数值类型(取值用cell.getNumericCellValue() 或cell.getDateCellValue())
            case NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;

            //Boolean类型
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;

            //表达式类型
            case FORMULA:
                value = String.valueOf(cell.getCellFormula());
                break;

            //异常类型 不知道何时算异常
            case ERROR:
                value = String.valueOf(cell.getErrorCellValue());
                break;

            //空，不知道何时算空
            case BLANK:
                break;

            default:
                value = "";
                break;
        }
        if ("".equals(value) || value == null) {
            value = "";
        }
        if (cell == null) {
            return "";
        }
        return value;
    }
}