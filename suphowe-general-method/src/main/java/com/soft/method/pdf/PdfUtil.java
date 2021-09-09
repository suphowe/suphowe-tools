package com.soft.method.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * PDF公共类
 *
 * @author suphowe
 */
public class PdfUtil {

    private static final Logger logger = LoggerFactory.getLogger(PdfUtil.class);

    /**
     * 设置页面大小和背景颜色
     */
    public Rectangle setPdfPage() {
        //页面大小
        Rectangle rect = new Rectangle(PageSize.B5.rotate());
        //页面背景色
        rect.setBackgroundColor(BaseColor.WHITE);
        return rect;
    }

    /**
     * 设置PDF的版本和输出文件
     *
     * @param document 文档
     * @param filePath 路径
     */
    public PdfWriter setPdfWriter(Document document, String filePath) {
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            //PDF版本(默认1.4)
            writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
            return writer;
        } catch (Exception e) {
            logger.error("Exception:", e);
            return null;
        }

    }

    /**
     * 设置PDF字体
     *
     * @param fontSize 字号
     */
    public Font setPdfFont(int fontSize) {
        try {
            BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            return new Font(bfChinese, fontSize, Font.NORMAL);
        } catch (Exception e) {
            logger.error("Exception:", e);
            return null;
        }
    }


    /**
     * 设置PDF文档属性
     *
     * @param document 文档
     */
    public void setPdfProperty(Document document) {
        //文档属性
        //添加题目
        document.addTitle("检测报告统计");
        //添加作者
        document.addAuthor("Author@rensanning");
        //添加主题
        document.addSubject("Subject@iText sample");
        //添加关键词
        document.addKeywords("Keywords@iText");
        //添加创作者
        document.addCreator("Creator@iText");
        //页边空白
        document.setMargins(10, 20, 30, 40);
    }

    /**
     * 向PDF中写入String
     *
     * @param document 文档
     * @param data 数据
     * @param type 是否设置居中
     */
    public void setPdfString(Document document, String data, Font font, int type) {
        try {
            Paragraph inputData = new Paragraph(data, font);
            if (type == 1) {
                inputData.setAlignment(1);
            }
            document.add(inputData);
        } catch (Exception e) {
            logger.error("Exception:", e);
        }
    }

    /**
     * 向PDF中写入Chunk组块内容
     *
     * @param document 文档
     */
    public void setPdfChunk(Document document, String data, Font font) {
        try {
            Chunk id = new Chunk(data, font);
            id.setBackground(BaseColor.BLACK, 1f, 0.5f, 1f, 1.5f);
            id.setTextRise(6);
            document.add(id);
            document.add(Chunk.NEWLINE);
        } catch (Exception e) {
            logger.error("Exception:", e);
        }
    }

    /**
     * 向PDF中写入Phrase对象(带下划线的短语)
     *
     * @param document 文档
     */
    public void setPdfPhrase(Document document, String data, Font font) {
        //Phrase对象: a List of Chunks with leading
        try {
            document.add(Chunk.NEWLINE);
            Phrase director = new Phrase();
            Chunk name = new Chunk(data, font);
            name.setUnderline(0.2f, -2f);
            director.add(name);
            director.setLeading(24);
            document.add(director);
        } catch (Exception e) {
            logger.error("Exception:", e);
        }
    }

    /**
     * 向PDF中写入Paragraph对象
     *
     * @param document 文档
     */
    public void setPdfParagraph(Document document, String data, Font font) {
        try {
            Paragraph info = new Paragraph(data, font);
            document.add(info);
        } catch (Exception e) {
            logger.error("Exception:", e);
        }
    }

    /**
     * 放入链接或锚点
     *
     * @param document PDF文件
     * @param name     语言 CN EN
     * @param url      文件url
     * @param font     文件字体
     */
    public void setPdfAnchor(Document document, String anchorName, String name, String url, Font font) {
        try {
            document.add(Chunk.NEWLINE);
            Anchor anchor = new Anchor(anchorName, font);
            if (name.trim().length() > 0) {
                anchor.setName(name);
            }
            if (url.trim().length() > 0) {
                anchor.setReference(url);
            }
            document.add(anchor);
        } catch (Exception e) {
            logger.error("Exception:", e);
        }
    }

    /**
     * 放入图片
     *
     * @param document  PDF文件
     * @param fileName  文件名
     * @param fitWidth  宽
     * @param fitHeight 高
     */
    public void setPdfImage(Document document, String fileName, int fitWidth, int fitHeight) {
        try {
            document.add(Chunk.NEWLINE);
            Image img = Image.getInstance(fileName);
            img.setAbsolutePosition(70, 50);
            img.setAlignment(Image.LEFT | Image.TEXTWRAP);
            img.setBorder(Image.BOX);
            img.setBorderWidth(10);
            img.setBorderColor(BaseColor.WHITE);
            //大小
            img.scaleToFit(fitWidth, fitHeight);
            //旋转
            img.setRotationDegrees(0);
            document.add(img);
        } catch (Exception e) {
            logger.error("Exception:", e);
        }
    }

    /**
     * 写入文件到TABLE
     *
     * @param document PDF文件
     * @param font     字体
     * @param list     数据
     */
    public void setPdfTable(Document document, Font font, List<HashMap<String, Object>> list) {
        try {
            document.add(Chunk.NEWLINE);
            int colNum = 0;
            if (list.size() > 0) {
                colNum = list.get(0).size();
            }
            PdfPTable table = new PdfPTable(colNum);
            for (int i = 0; i < list.size(); i++) {
                HashMap<String, Object> thisMap = list.get(i);
                for (String key : thisMap.keySet()) {
                    table.addCell(new Paragraph(thisMap.get(key).toString(), font));
                }
            }
            document.add(table);
        } catch (Exception e) {
            logger.error("Exception:", e);
        }
    }

    /**
     * 改变图片 像素
     *
     * @param fileName     图片文件
     * @param zipPicLength 压缩后图片大小(KB)
     * @param picType      图片类型(jpg,png)
     * @return 图片文件
     */
    public File compressPictureByQality(String fileName, float zipPicLength, String picType) {
        try {
            File file = new File(fileName);
            //需要压缩的大小
            float newLength = zipPicLength * 1000;
            //原图大小
            float oldLength = Float.parseFloat(Long.toString(file.length()));
            if (oldLength < newLength) {
                return null;
            }
            float qality = newLength / oldLength;
            BufferedImage src = null;
            FileOutputStream out = null;
            ImageWriter imgWrier;
            ImageWriteParam imgWriteParams;
            // 指定写图片的方式为 jpg
            imgWrier = ImageIO.getImageWritersByFormatName(picType).next();
            imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(
                    null);
            // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
            imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            // 这里指定压缩的程度，参数qality是取值0~1范围内，
            imgWriteParams.setCompressionQuality(qality);
            imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
            // 实例化一个对象 ColorModel.getRGBdefault();
            ColorModel colorModel = ImageIO.read(file).getColorModel();
            imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
                    colorModel, colorModel.createCompatibleSampleModel(32, 32)));
            if (!file.exists()) {
                throw new FileNotFoundException("Not Found Img File,文件不存在");
            } else {
                src = ImageIO.read(file);
                out = new FileOutputStream(file);
                imgWrier.reset();
                // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
                // OutputStream构造
                imgWrier.setOutput(ImageIO.createImageOutputStream(out));
                // 调用write方法，就可以向输入流写图片
                imgWrier.write(null, new IIOImage(src, null, null),
                        imgWriteParams);
                out.flush();
                out.close();
                return file;
            }
        } catch (Exception e) {
            logger.info("Exception:", e);
            return null;
        }
    }

//    /**
//     * 创建PDF文件
//     */
//    public void createPdfFile() {
//        Document document = new Document(setPdfPage());
//        Font font = setPdfFont(12);
//        setPdfWriter(document, "G:/test.pdf");
//        setPdfProperty(document);
//        document.open();
////        System.out.println(AppConstants.FONT_BODY);
////        System.out.println(AppConstants.FONT_TITLE);
////
////        setPdfString(document, "这是一个测试", font, 1);
////        setPdfChunk(document, "测试");
////        setPdfPhrase(document,"this is 又有");
////        setPdfParagraph(document,"this is a test");
////        test(document);
////        setPdfImage(document,"H:/大王临死前.jpg",800,600);
////        setPdfTable(document, font);
////        setPdfParagraph(document, ".相机名称:", font);
////        setPdfParagraph(document, "事件:", font);
////        setPdfParagraph(document, "触发时间:", font);
//        List this_data_list = new ArrayList();
//        LinkedHashMap<String, Object> data0 = new LinkedHashMap<String, Object>();
//        data0.put("col1","编号");
//        data0.put("col2", 1);
//        this_data_list.add(data0);
//
//        LinkedHashMap<String, Object> data1 = new LinkedHashMap<String, Object>();
//        data1.put("col1","相机名称");
//        data1.put("col2","123");
//        this_data_list.add(data1);
//
//        LinkedHashMap<String, Object> data2 = new LinkedHashMap<String, Object>();
//        data2.put("col1","事件");
//        data2.put("col2","345");
//        this_data_list.add(data2);
//
//        LinkedHashMap<String, Object> data3 = new LinkedHashMap<String, Object>();
//        data3.put("col1","触发时间");
//        data3.put("col2","456");
//        this_data_list.add(data3);
//
//        setPdfTable(document, font, this_data_list);
//        setPdfImage(document, "G:/2019-07-16_183108.png", 560,420);
//        document.close();
//    }
////
//    public static void main(String[] args) throws Exception {
//        PdfUtil pdfUtil = new PdfUtil();
//        pdfUtil.createPdfFile();
//    }
}
