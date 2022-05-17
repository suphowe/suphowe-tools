package com.soft.method.printer;

import cn.hutool.core.util.StrUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 打印机相关工具类
 * @author suphowe
 */
@Slf4j
public class PrinterUtil {

    /**
     * 寻找指定的打印机
     * @param printerName 打印机名称
     * @return 打印机服务
     */
    public static PrintService lookupPrinter(String printerName) {
        PrintService service = null;
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        if (printServices == null || printServices.length == 0) {
            throw new RuntimeException("Cannot found Print Service");
        }
        // 可选打印机
        List<String> printerNames = Arrays.stream(printServices).map(PrintService::getName).collect(Collectors.toList());
        log.info("Optional Printer:{}", printerNames);
        for (int i = 0; i < printServices.length; i++) {
            String name = printServices[i].getName().toLowerCase();
            log.info("printName{}:{}", i, name);
            if (name.contains(printerName.toLowerCase())) {
                service = printServices[i];
                break;
            }
        }
        if (service == null) {
            throw new RuntimeException("Cannot found Print:" + printerName + ", Optional Printer:" + printerNames);
        }
        return service;
    }

    /**
     * 打印数据
     * @param filePath PDF文件路径
     * @param printerName 打印机名称
     * @param printRequestAttributeSet 打印机请求公共接口(打印属性设置)
     * @throws Exception 异常
     */
    public static void print(String filePath, String printerName, PrintRequestAttributeSet printRequestAttributeSet) throws Exception {
        print(new File(filePath), printerName, printRequestAttributeSet);
    }


    /**
     * 打印指定文件
     * @param file 文件
     * @param printerName 打印机
     * @param printRequestAttributeSet 打印机请求公共接口(打印属性设置)
     * @throws Exception 异常
     */
    public static void print(File file, String printerName, PrintRequestAttributeSet printRequestAttributeSet) throws Exception {
        if (file == null) {
            log.error("Cannot found file");
            throw new Exception("传入的文件为空");
        }
        if (!file.exists()) {
            log.error("Cannot found file:" + file.getAbsolutePath());
            throw new Exception("文件不存在:" + file.getAbsolutePath());
        }
        PrintService printService = lookupPrinter(printerName);
        if (null == printRequestAttributeSet) {
            // 获取打印参数
            printRequestAttributeSet = getPrintRequestAttributeSet();
        }
        PDDocument document = PDDocument.load(file);
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setJobName(file.getName());
        // 选择打印机
        printJob.setPrintService(printService);

        //设置纸张及缩放
        PDFPrintable pdfPrintable = new PDFPrintable(document, Scaling.ACTUAL_SIZE);
        //设置多页打印
        Book book = new Book();
        PageFormat pageFormat = new PageFormat();
        //设置打印方向
        //纵向
        pageFormat.setOrientation(PageFormat.PORTRAIT);
        //设置纸张
        pageFormat.setPaper(getPaper());
        book.append(pdfPrintable, pageFormat, document.getNumberOfPages());
        printJob.setPageable(book);

        try {
            printJob.print(printRequestAttributeSet);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Print Exception:{}", e.getMessage());
            throw new Exception("打印机打印异常:" + e.getMessage());
        } finally {
            IOUtils.closeQuietly(document);
        }
    }

    /**
     * 获取默认打印机属性
     * @return 打印机属性
     */
    private static PrintRequestAttributeSet getPrintRequestAttributeSet() {
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        //份数
        printRequestAttributeSet.add(new Copies(1));
        //纸张
        printRequestAttributeSet.add(MediaSizeName.ISO_A4);
        //装订
        // printRequestAttributeSet.add(Finishings.STAPLE);
        //单双面
        printRequestAttributeSet.add(Sides.ONE_SIDED);
        return printRequestAttributeSet;
    }

    /**
     * 设置打印份数
     * @param copy 打印份数
     * @return 打印属性
     */
    public static PrintRequestAttributeSet getPrintRequestAttributeSet(int copy) {
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        //份数
        printRequestAttributeSet.add(new Copies(copy));
        //纸张
        printRequestAttributeSet.add(MediaSizeName.ISO_A4);
        //装订
        // printRequestAttributeSet.add(Finishings.STAPLE);
        //单双面
        printRequestAttributeSet.add(Sides.ONE_SIDED);
        return printRequestAttributeSet;
    }


    /**
     * 将图片转换成pdf
     * @param images 图片
     * @return 二进制PDF
     * @throws Exception 异常
     */
    public static byte[] imgConvertPdf(List<byte[]> images) throws Exception {
        //普通a4
        Document doc = new Document(PageSize.A4, 0, 0, 36.0F, 36.0F);
        ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
        PdfWriter.getInstance(doc, pdfOut);
        doc.open();
        for (byte[] image : images) {
            com.itextpdf.text.Image pic = com.itextpdf.text.Image.getInstance(image);
            pic.setScaleToFitLineWhenOverflow(true);
            doc.add(pic);
        }

        doc.close();
        byte[] pdf = pdfOut.toByteArray();
        IOUtils.closeQuietly(pdfOut);
        return pdf;
    }

    /**
     * 图片缩放成A4尺寸,转换为pdf文件
     * @param imagePath 图片地址
     * @param descFolder 存放文件夹
     * @return PDF地址
     */
    public static String imgConvertPdf(String imagePath, String descFolder, String pdfName) {
        return imgConvertPdf(Collections.singletonList(imagePath), descFolder, pdfName);
    }

    /**
     * 图片缩放成A4尺寸,转换为pdf文件
     * @param imgPaths 图片地址
     * @param descFolder 存放文件夹
     * @return PDF地址
     */
    public static String imgConvertPdf(List<String> imgPaths, String descFolder) {
        String pdfName = System.currentTimeMillis() + ".pdf";
        return imgConvertPdf(imgPaths, descFolder, pdfName);
    }

    /**
     * 图片缩放成A4尺寸,转换为pdf文件
     * @param imgPaths 图片地址
     * @param descFolder 存放文件夹
     * @param pdfName pdf名称
     * @return PDF地址
     */
    public static String imgConvertPdf(List<String> imgPaths, String descFolder, String pdfName) {
        pdfName = StrUtil.hasBlank(pdfName) ? System.currentTimeMillis() + ".pdf" : pdfName;
        String pdfPath;
        FileOutputStream fos = null;
        try {
            File file = new File(descFolder);
            if (!file.exists()) {
                file.mkdirs();
            }

            pdfPath = descFolder + "/" + pdfName;
            Document doc = new Document(PageSize.A4, 0, 0, 0, 0);

            fos = new FileOutputStream(pdfPath);
            PdfWriter.getInstance(doc, fos);
            doc.open();
            for (String imagePath : imgPaths) {
                Image image = Image.getInstance(imagePath);
                image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());
                doc.add(image);
            }
            doc.close();
            log.info("Success Create Pdf File:{}", pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Create Pdf File Exception:" + e.getMessage());
            throw new RuntimeException("生成pdf异常:" + e.getMessage());
        } finally {
            IOUtils.closeQuietly(fos);
        }
        return pdfPath;
    }

    /**
     * 设置纸张
     * @return 纸张
     */
    private static Paper getPaper() {
        Paper paper = new Paper();
        // 默认为A4纸张，对应像素宽和高分别为 595, 842
        int width = 595;
        int height = 842;
        // 设置边距，单位是像素，10mm边距，对应 28px
        int marginLeft = 10;
        int marginRight = 0;
        int marginTop = 10;
        int marginBottom = 0;
        paper.setSize(width, height);
        // 下面一行代码，解决了打印内容为空的问题
        paper.setImageableArea(marginLeft, marginRight, width - (marginLeft + marginRight), height - (marginTop + marginBottom));
        return paper;
    }



    public static void main(String[] args) throws Exception {
        File file = new File("c:\\test\\test.pdf");
        print(file, "\\\\ccq00002.chongqing.ford.com\\PR08639", getPrintRequestAttributeSet(1));
//        PDDocument document =  PDDocument.load(file);
//        PrinterJob printJob = PrinterJob.getPrinterJob();
//        printJob.setJobName(file.getName());
//        PrintService printService = lookupPrinter("HP Las");
//        printJob.setPrintService(printService);
//
//        //设置纸张及缩放
//        PDFPrintable pdfPrintable = new PDFPrintable(document, Scaling.ACTUAL_SIZE);
//        //设置多页打印
//        Book book = new Book();
//        PageFormat pageFormat = new PageFormat();
//        //设置打印方向
//        pageFormat.setOrientation(PageFormat.PORTRAIT);//纵向
//        pageFormat.setPaper(getPaper());//设置纸张
//        book.append(pdfPrintable, pageFormat, document.getNumberOfPages());
//        printJob.setPageable(book);
//        printJob.setCopies(1);//设置打印份数
//
//        //添加打印属性
//        HashPrintRequestAttributeSet pars = new HashPrintRequestAttributeSet();
//        pars.add(Sides.ONE_SIDED); //设置单双页
//        printJob.print(pars);
    }
}
