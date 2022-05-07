package com.soft.method.picture;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

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

    /**
     * 改变图片 像素
     * @param fileName 图片文件
     * @param zipPicLength 压缩后图片大小(KB)
     * @param picType 图片类型(jpg,png)
     * @return 图片文件
     */
    public static File revisePicturePixel(String fileName, float zipPicLength, String picType) {
        try {
            File file = new File(fileName);
            //需要压缩的大小
            float newLength = zipPicLength * 1000;
            //原图大小
            float oldLength = Float.parseFloat(Long.toString(file.length()));
            if (oldLength < newLength) {
                return null;
            }
            //压缩比
            float qality = newLength / oldLength;
            BufferedImage src;
            FileOutputStream out;
            ImageWriter imageWriter;
            ImageWriteParam imgWriteParams;
            // 指定写图片的方式为 jpg
            imageWriter = ImageIO.getImageWritersByFormatName(picType).next();
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
                imageWriter.reset();
                // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
                // OutputStream构造
                imageWriter.setOutput(ImageIO.createImageOutputStream(out));
                // 调用write方法，就可以向输入流写图片
                imageWriter.write(null, new IIOImage(src, null, null),
                        imgWriteParams);
                out.flush();
                out.close();
                return file;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getPictureAttributes(String pictureFile) {
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
    private static String getPictureType(String pictureFile){
        String[] pictureTypes = pictureFile.split("\\.");
        return pictureTypes[1].toLowerCase();
    }

    private static void getJpgPictureAttributes(String jpgFile) {
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

    private static void getOtherPictureAttributes(String pictureFileName){
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
        PictureUtil.getPictureAttributes(fileName);
    }

}
