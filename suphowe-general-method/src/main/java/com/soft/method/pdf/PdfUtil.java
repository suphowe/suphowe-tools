package com.soft.method.pdf;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * PDF公共类
 * @author suphowe
 */
public class PdfUtil {

    /**
     * 设置页面大小和背景颜色
     */
    public static Rectangle setPdfPage() {
        //页面大小
        Rectangle rect = new Rectangle(PageSize.A4.rotate());
        //页面背景色
        rect.setBackgroundColor(BaseColor.WHITE);
        return rect;
    }

    /**
     * 设置PDF的版本
     * @param document 文档
     * @param outputStream 输出流
     */
    public static PdfWriter setPdfWriter(Document document, OutputStream outputStream) {
        try {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            //PDF版本(默认1.4)
            writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
            return writer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 设置PDF字体
     * @param fontSize 字号
     */
    public static Font setPdfFont(int fontSize) {
        try {
            BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            return new Font(bfChinese, fontSize, Font.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置PDF文档属性
     * @param document 文档
     * @param title 题目
     * @param author 作者
     * @param subject 主题
     * @param keywords 关键词
     * @param creator 创作者
     * @param marginLeft 左边距
     * @param marginRight 右边距
     * @param marginTop 顶编剧
     * @param marginBottom 下边距
     */
    public static Document setPdfProperty(Document document, String title, String author, String subject,
                               String keywords, String creator, int marginLeft, int marginRight,
                               int marginTop, int marginBottom) {
        if (title.trim().length() > 0) {
            document.addTitle(title);
        }
        if (author.trim().length() > 0) {
            document.addAuthor(author);
        }
        if (subject.trim().length() > 0) {
            document.addSubject(subject);
        }
        if (keywords.trim().length() > 0) {
            document.addSubject(keywords);
        }
        if (creator.trim().length() > 0) {
            document.addCreator(creator);
        }
        //设置创建日期
        document.addCreationDate();
        document.setMargins(marginLeft, marginRight, marginTop, marginBottom);
        return document;
    }

    /**
     * 向PDF中写入String
     * @param document 文档
     * @param message 数据
     * @param middleType 是否设置居中
     */
    public static void addPdfString(Document document, String message, Font font, boolean middleType) {
        try {
            Paragraph inputData = new Paragraph(message, font);
            if (middleType) {
                inputData.setAlignment(1);
            }
            document.add(inputData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加空行
     * @param document 文档
     */
    public static void addSpaceRow(Document document) {
        try {
            Font font = setPdfFont(18);
            Paragraph inputData = new Paragraph(" ", font);
            document.add(inputData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 向PDF中写入Chunk组块内容
     * @param document 文档
     * @param message 数据
     * @param font 字体
     * @param extraLeft 左侧额外
     * @param extraBottom 下侧额外
     * @param extraRight 右侧额外
     * @param extraTop 上侧额外
     */
    public static void addPdfChunk(Document document, String message, Font font,
                            float extraLeft, float extraBottom, float extraRight, float extraTop) {
        try {
            Chunk id = new Chunk(message, font);
            id.setBackground(BaseColor.BLACK, extraLeft, extraBottom, extraRight, extraTop);
            id.setTextRise(6);
            document.add(id);
            document.add(Chunk.NEWLINE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向PDF中写入Phrase对象(带下划线的短语)
     * @param document 文档
     * @param message 数据
     * @param font 字体
     * @param thickness 宽度
     * @param yPosition y位置
     * @param fixedLeading 头位置
     */
    public static void addPdfPhrase(Document document, String message, Font font,
                             float thickness, float yPosition, float fixedLeading) {
        //Phrase对象: a List of Chunks with leading
        try {
            document.add(Chunk.NEWLINE);
            Phrase director = new Phrase();
            Chunk name = new Chunk(message, font);
            name.setUnderline(0.2f, -2f);
            director.add(name);
            director.setLeading(fixedLeading);
            document.add(director);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向PDF中写入Paragraph对象
     * @param document 文档
     * @param message 数据
     * @param font 字体
     */
    public static void addPdfParagraph(Document document, String message, Font font) {
        try {
            Paragraph info = new Paragraph(message, font);
            document.add(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 放入链接或锚点
     * @param document PDF文件
     * @param language 语言 CN EN
     * @param url 文件url
     * @param font 文件字体
     */
    public static void addPdfAnchor(Document document, String anchorName, String language, String url, Font font) {
        try {
            document.add(Chunk.NEWLINE);
            Anchor anchor = new Anchor(anchorName, font);
            if (!StrUtil.hasBlank(language)) {
                anchor.setName(language);
            }
            if (!StrUtil.hasBlank(url)) {
                anchor.setReference(url);
            }
            document.add(anchor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 放入图片
     * @param document  PDF文件
     * @param fileName  图片文件名
     * @param absoluteX  X绝对位置
     * @param absoluteY Y绝对位置
     * @param borderWidth 边框宽度
     * @param fitWidth  宽
     * @param fitHeight 高
     * @param deg 旋转角度
     */
    public static void addPdfImage(Document document, String fileName, float absoluteX, float absoluteY,
                            float borderWidth, float fitWidth, float fitHeight, float deg) {
        try {
            document.add(Chunk.NEWLINE);
            Image img = Image.getInstance(fileName);
            img.setAbsolutePosition(absoluteX, absoluteY);
            img.setAlignment(Image.LEFT | Image.TEXTWRAP);
            img.setBorder(Image.BOX);
            img.setBorderWidth(borderWidth);
            img.setBorderColor(BaseColor.WHITE);
            //大小
            img.scaleToFit(fitWidth, fitHeight);
            //旋转
            img.setRotationDegrees(deg);
            document.add(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入信息到TABLE
     * @param document 文档
     * @param font 字体
     * @param list 数据
     */
    public static void addPdfTable(Document document, Font font, List<HashMap<String, Object>> list) {
        try {
            document.add(Chunk.NEWLINE);
            int colNum = 0;
            if (list.size() > 0) {
                colNum = list.get(0).size();
            }
            PdfPTable table = new PdfPTable(colNum);
            for (HashMap<String, Object> thisMap : list) {
                for (String key : thisMap.keySet()) {
                    table.addCell(new Paragraph(thisMap.get(key).toString(), font));
                }
            }
            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 放入table
     * @param document 文档
     * @param fontSize 字号
     * @param miniHeight 行高度
     * @param columnAmount 每行数量
     * @param list 数据
     */
    public static void addPdfTable(Document document, int fontSize, int miniHeight, int columnAmount, List<String> list) {
        try {
            PdfPCell cell = new PdfPCell();
            PdfPTable table = new PdfPTable(columnAmount);
            for (String data : list) {
                cell.setPhrase(new Paragraph(PdfUtil.handleNullMessage(data), setPdfFont(fontSize)));
                // 居中设置
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                // 设置表格的高度
                cell.setMinimumHeight(miniHeight);
                table.addCell(cell);
            }
            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 放入table
     * @param document 文档
     * @param fontSize 字号
     * @param miniHeight 行高度
     * @param heads 表头
     * @param list 数据
     */
    public static void addPdfTable(Document document, int fontSize, int miniHeight,
                            List<String> heads, List<String> list) {
        try {
            PdfPCell cell = new PdfPCell();
            PdfPTable table = new PdfPTable(heads.size());
            // 放入head
            for (String head : heads) {
                cell.setPhrase(new Paragraph(PdfUtil.handleNullMessage(head), setPdfFont(fontSize)));
                // 表头背景色
                cell.setBackgroundColor(BaseColor.GRAY);
                // 居中设置
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                // 设置表格的高度
                cell.setMinimumHeight(miniHeight);
                table.addCell(cell);
            }
            // 放入数据
            for (String data : list) {
                cell.setPhrase(new Paragraph(PdfUtil.handleNullMessage(data), setPdfFont(fontSize)));
                // 居中设置
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                // 设置表格的高度
                cell.setMinimumHeight(miniHeight);
                table.addCell(cell);
            }
            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 放入竖表格(column设置颜色)
     * @param document 文档
     * @param fontSize 字号
     * @param miniHeight 行高度
     * @param columns 字段数据描述
     * @param list 数据
     * @param baseColor 背景色
     */
    public static void addPdfTable(Document document, int fontSize, int miniHeight,
                            List<String> columns, List<String> list, BaseColor baseColor) {
        try {
            PdfPCell cell = new PdfPCell();
            if (columns.size() != list.size()) {
                return;
            }
            PdfPTable table = new PdfPTable(4);
            for (int i = 0; i < columns.size(); i++) {
                String column = columns.get(i);
                String colMessage = list.get(i);
                cell.setPhrase(new Paragraph(PdfUtil.handleNullMessage(column), setPdfFont(fontSize)));
                // 表头背景色
                cell.setBackgroundColor(baseColor);
                // 居中设置
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                // 设置表格的高度
                cell.setMinimumHeight(miniHeight);
                table.addCell(cell);
                cell.setPhrase(new Paragraph(PdfUtil.handleNullMessage(colMessage), setPdfFont(fontSize)));
                cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setMinimumHeight(miniHeight);
                table.addCell(cell);
            }
            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加表格信息方法(可对表格进行行或列合并)
     * @param table 创建的表格
     * @param paragraphValue 填充表格的值信息
     * @param fontSize 字体的大小
     * @param minimumHeight 表格高度
     * @param colSpan 是否跨列
     * @param rowSpan 是否跨行
     * @param colSize 具体跨几列
     * @param rowSize 具体跨几行
     * @param baseColor cell背景色
     */
    public static void addTableCell(PdfPTable table, String paragraphValue, int fontSize, float minimumHeight,
                             boolean colSpan, boolean rowSpan, int colSize, int rowSize, BaseColor baseColor) {
        PdfPCell cell = new PdfPCell();
        cell.setPhrase(new Paragraph(paragraphValue, setPdfFont(fontSize)));
        if (baseColor != null) {
            cell.setBackgroundColor(baseColor);
        }
        // 居中设置
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置表格的高度
        cell.setMinimumHeight(minimumHeight);
        // 是否跨列
        if (colSpan) {
            cell.setColspan(colSize);
        }
        // 是否跨行
        if (rowSpan) {
            cell.setRowspan(rowSize);
        }
        // 具体的某个cell加入到表格
        table.addCell(cell);
    }

    /**
     * pdf添加水印(两行三列)
     */
    public static void addWaterMark(PdfWriter pdfWriter, Document document, float size, String waterMarkName) {
        try {
            //获取pdf内容正文页面宽度
            float pageWidth = document.right() + document.left();
            //获取pdf内容正文页面高度
            float pageHeight = document.top() + document.bottom();
            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            PdfContentByte waterMarkPdfContent = pdfWriter.getDirectContentUnder();
            Font waterMarkFont = new Font(base, size, Font.BOLD, BaseColor.LIGHT_GRAY);
            Phrase phrase = new Phrase(waterMarkName, waterMarkFont);
            //两行三列
            ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase,
                    pageWidth * 0.25f, pageHeight * 0.2f, 45);
            ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase,
                    pageWidth * 0.25f, pageHeight * 0.5f, 45);
            ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase,
                    pageWidth * 0.25f, pageHeight * 0.8f, 45);
            ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase,
                    pageWidth * 0.65f, pageHeight * 0.2f, 45);
            ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase,
                    pageWidth * 0.65f, pageHeight * 0.5f, 45);
            ColumnText.showTextAligned(waterMarkPdfContent, Element.ALIGN_CENTER, phrase,
                    pageWidth * 0.65f, pageHeight * 0.8f, 45);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理字符串空值
     * @param object Object字符串
     * @return 字符串
     */
    private static String handleNullMessage(Object object) {
        if (ObjectUtil.isNull(object)) {
            return "";
        }
        return object.toString();
    }
}
