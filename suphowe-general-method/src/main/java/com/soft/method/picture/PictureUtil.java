package com.soft.method.picture;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
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
import java.io.IOException;
import java.util.Iterator;

/**
 * 图片处理公共方法
 *
 * @author suphowe
 */
public class PictureUtil {

    private static final Logger logger = LoggerFactory.getLogger(PictureUtil.class);

    /**
     * 改变图片 像素
     *
     * @param file     图片文件
     * @param zipPicLength 压缩后图片大小(KB)
     * @param imageType      图片类型(jpg,png)
     * @return 压缩后图片
     */
    public File compressPictureByQality(File file, float zipPicLength, String imageType) {
        BufferedImage src = null;
        FileOutputStream out = null;
        ImageWriter imgWrier;
        ImageWriteParam imgWriteParams;
        //需要压缩的大小
        float newLength = zipPicLength * 1000;
        //原图大小
        float oldLength = Float.parseFloat(Long.toString(file.length()));
        if (oldLength < newLength) {
            return null;
        }
        float qality = newLength / oldLength;
        try {
            logger.info("开始设置图片压缩参数 qality:{} , imageType:{}", qality, imageType);
            // 指定写图片的方式为 jpg
            imgWrier = ImageIO.getImageWritersByFormatName(imageType).next();
            imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(
                    null);
            // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
            imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            // 这里指定压缩的程度，参数qality是取值0~1范围内，
            imgWriteParams.setCompressionQuality(qality);
            imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
            /**ColorModel.getRGBdefault();*/
            ColorModel colorModel = ImageIO.read(file).getColorModel();
            imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
                    colorModel, colorModel.createCompatibleSampleModel(32, 32)));
            logger.info("结束设定压缩图片参数");
            if (!file.exists()) {
                logger.info("Not Found Img File,文件不存在");
                throw new FileNotFoundException("Not Found Img File,文件不存在");
            } else {
                logger.info("图片转换前大小" + file.length() + "字节");
                src = ImageIO.read(file);
                out = new FileOutputStream(file);
                imgWrier.reset();
                // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
                // OutputStream构造
                imgWrier.setOutput(ImageIO.createImageOutputStream(out));
                // 调用write方法，就可以向输入流写图片
                imgWrier.write(null, new IIOImage(src, null, null),
                        imgWriteParams);
                logger.info("图片转换后大小" + file.length() + "字节");
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            logger.error("压缩图片失败 FileName:{} , Qality:{} , imageType:{}", file.getName(), qality, imageType, e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                logger.error("压缩图片失败 FileName:{} , Qality:{} , imageType:{}", file.getName(), qality, imageType, e);
            }
        }
        return file;
    }

    public void getPictureAttributes(String pictureFile) {
        switch (getPictureType(pictureFile)){
            case "jpg":
                getJpgPictureAttributes(pictureFile);
                break;
            case "bmp":
                getOtherPictureAttributes(pictureFile);
                break;
            case "jpeg":
                getOtherPictureAttributes(pictureFile);
                break;
            case "gif":
                getOtherPictureAttributes(pictureFile);
                break;
            default :
                break;
        }
    }

    /**
     * 获取图片文件后缀名
     * @param pictureFile 图片文件
     * @return 文件格式
     */
    private String getPictureType(String pictureFile){
        String[] pictureTypes = pictureFile.split("\\.");
        return pictureTypes[1].toLowerCase();
    }

    private void getJpgPictureAttributes(String jpgFile) {
        File pictureFile = new File(jpgFile);
        if (!pictureFile.exists()){
            return;
        }
        Metadata metadata;
        try {
            metadata = JpegMetadataReader.readMetadata(pictureFile);
            Iterator<Directory> it = metadata.getDirectories().iterator();
            while (it.hasNext()) {
                Directory exif = it.next();
                Iterator<Tag> tags = exif.getTags().iterator();
                while (tags.hasNext()) {
                    Tag tag = (Tag) tags.next();
                    System.out.println(tag);
                }
            }
            System.out.println("图片信息读取完成！");
        } catch (JpegProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getOtherPictureAttributes(String pictureFileName){
        File file = new File(pictureFileName);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 像素 宽
        int width = bi.getWidth();
        // 像素 高
        int height = bi.getHeight();
        System.out.println(bi.getType());
        System.out.println(bi.getSources());
        System.out.println("width=" + width + ",height=" + height + ".");
    }


    public static void main (String[] args) {
        String fileName = "G:/ipc/2019_G50S视频/K171+0/2018-11-08_08-19-39/3fc5dcab-f768-4c3c-a76d-5b7af64c236a.bmp";
        PictureUtil pictureUtil = new PictureUtil();
        pictureUtil.getPictureAttributes(fileName);
    }

}
