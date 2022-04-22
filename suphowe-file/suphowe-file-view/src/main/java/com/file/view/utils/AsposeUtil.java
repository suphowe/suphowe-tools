package com.file.view.utils;

import java.io.*;

import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.aspose.words.*;

/**
 * word excel pptx 转 pdf
 * @author suphowe
 */
public class AsposeUtil {



    public static void main(String[] args) throws Exception {
        AsposeUtil bean = new AsposeUtil();
        //bean.word2Pdf2("D:\\pdf\\12.docx","D:\\pdf\\12.pdf");
        //bean.excel2Pdf("C:\\test\\GFPS组织架构信息导入示例20220422092149.xlsx","C:\\test\\GFPS组织架构信息导入示例20220422092149.pdf");
        bean.word2Pdf("C:\\test\\GFPS工具 -需求分析说明书 20220415.docx","C:\\test\\GFPS工具 -需求分析说明书 20220415.pdf");
        //bean.ppt2Pdf("C:\\test\\长安福特_HR数字化建设项目 蓝图概览方案_Final.pptx","C:\\test\\长安福特_HR数字化建设项目 蓝图概览方案_Final.pdf");
    }

    /**
     * word转pdf
     * inPath: 输入word的路径
     * outPath: 输出pdf的路径
     */
    public void word2Pdf(String inPath, String outPath) throws Exception {
        if (!getLicense()) {
            System.out.println("非法------------");
            return;
        }
        long old = System.currentTimeMillis();
        File file = new File(outPath);
        FileOutputStream fileOutputStream = null;
        try {
            //解决乱码
            //如果是windows执行，不需要加这个
            //TODO 如果是linux执行，需要添加这个*****
            //FontSettings.setFontsFolder("/usr/share/fonts",true);

            Document doc = new Document(inPath);
            fileOutputStream = new FileOutputStream(file);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(fileOutputStream, SaveFormat.PDF);
            //计算时间
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * word转pdf
     * @param path      pdf输出路径
     * @param wordInput word输入流
     * @param wordName  word文档的名称
     */
    public void word2Pdf(String path, InputStream wordInput, String wordName) {
        if (!getLicense()) {
            System.out.println("非法");
            return;
        }

        //新建一个空白pdf文档
        long old = System.currentTimeMillis();
        File file = new File(path + wordName + ".pdf");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            //Address是将要被转化的word文档
            Document doc = null;
            try {
                doc = new Document(wordInput);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
                if (doc != null) {
                    doc.save(fileOutputStream, SaveFormat.PDF);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            long now = System.currentTimeMillis();
            //转化用时
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * excel 转 pdf
     * @param path excel 路径
     * @param outPath pdf输出路径
     */
    public void excel2Pdf(String path,String outPath) {
        if (!getLicenseExcel()) {
            System.out.println("非法------------");
            return;
        }
        File file = new File(outPath);
        FileOutputStream fileOutputStream = null;
        try {
            Workbook wb = new Workbook(path);
            fileOutputStream= new FileOutputStream(file);
            wb.save(fileOutputStream, com.aspose.cells.SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ppt 转 pdf
     * @param path ppt路径
     * @param outPath pdf输出路径
     */
    public void ppt2Pdf(String path,String outPath) {
        if (!getLicensePpt()) {
            System.out.println("非法------------");
            return;
        }
        File file = new File(outPath);
        FileOutputStream fileOutputStream = null;
        try {
            //输入pdf路径
            Presentation pres = new Presentation(path);
            fileOutputStream = new FileOutputStream(file);
            pres.save(fileOutputStream, com.aspose.slides.SaveFormat.Pdf);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 验证License 若不验证则转化出的pdf文档会有水印产生
     * @return 验证lisence结果
     */
    private boolean getLicense() {
        boolean result = false;
        try {
            //引入license.xml文件,去除水印
            InputStream is =this.getClass().getClassLoader().getResourceAsStream("license.xml");
            //注意此处为apose-slides的jar包
            License aposeLic = new License();
            aposeLic.setLicense(is);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 验证License 若不验证则转化出的pdf文档会有水印产生
     * @return 验证excel lisence结果
     */
    private boolean getLicenseExcel() {
        boolean result = false;
        try {

            InputStream is =this.getClass().getClassLoader().getResourceAsStream("license.xml");
            //注意此处为对应aspose-cells的jar包
            com.aspose.cells.License aposeLic = new com.aspose.cells.License();

            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 验证License 若不验证则转化出的pdf文档会有水印产生
     * @return pdf lisence结果
     */
    private boolean getLicensePpt(){
        boolean result = false;
        try {
            InputStream is =this.getClass().getClassLoader().getResourceAsStream("license.xml");
            //注意此处为对应aspose-slides的jar包
            com.aspose.slides.License aposeLic = new com.aspose.slides.License();

            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
