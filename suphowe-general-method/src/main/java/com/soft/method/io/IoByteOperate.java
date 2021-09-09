package com.soft.method.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * IO字节流操作<br/>
 * 1 byte * 8 = 16位
 *
 * @author suphowe
 */
public class IoByteOperate {

    /**
     * 文件路径
     */
    private String file;

    /**
     * 数据
     */
    private String data;

    /**
     * 每次读取长度
     */
    private int readCount;

    /**
     * 是否覆盖 覆盖:false 追加:true
     */
    private boolean isCover;

    /**
     * 采用BufferedWriter时buffer大小
     */
    private int bufferSize;

    /**
     * 字符集
     */
    private String charset;

    /**
     * 字符流操作文件(写入)
     *
     * @param file       文件路径
     * @param data       数据
     * @param isCover    是否覆盖 覆盖:false 追加:true
     * @param bufferSize 缓冲区大小
     * @param charset    字符集
     */
    public IoByteOperate(String file, String data, boolean isCover, int bufferSize, String charset) {
        this.file = file;
        this.data = data;
        this.isCover = isCover;
        this.bufferSize = bufferSize;
        this.charset = charset;
    }


    /**
     * 字符流操作文件(读取)
     *
     * @param file      文件路径
     * @param readCount 每次读取大小
     */
    public IoByteOperate(String file, int readCount) {
        this.file = file;
        this.readCount = readCount;
    }

    private String targetFile;

    private String goalFile;

    /**
     * 字节流复制文件
     *
     * @param targetFile 目标文件(被复制)
     * @param goalFile   目的文件
     * @param readCount 每次读取大小
     * @param bufferSize  缓冲区大小
     */
    public IoByteOperate(String targetFile, String goalFile, int readCount, int bufferSize) {
        this.targetFile = targetFile;
        this.goalFile = goalFile;
        this.readCount = readCount;
        this.bufferSize = bufferSize;
    }

    //----------------------读取文件 字节输入流-----------------------------------
    /*
    InputStream 是字节输入流的抽象基类
    FileInputStream 主要用来操作文件输入流，它除了可以使用基类定义的函数外,它还实现了基类的read()函数（无参的）
    BufferedInputStream 缓冲区 BufferedInputStream不是InputStream的直接实现子类，是FilterInputStream的子类
    */

    /**
     * 读取文件中的数据                 <br/>
     * file 文件                      <br/>
     * readCount 每次读取大小         <br/>
     *
     * @return 获取到的数据
     */
    public String readFileByFileInputStream() {
        FileInputStream fis = null;
        String result = "";
        try {
            fis = new FileInputStream(file);
            byte[] buf = new byte[readCount];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                result += new String(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 读取文件中的数据(引入BufferedInputStream缓冲区)         <br/>
     * file 文件                                              <br/>
     * readCount 每次读取大小                                 <br/>
     *
     * @return 获取到的数据
     */
    public Object readFileByBufferedReader() {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        String result = "";
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);

            byte[] buf = new byte[readCount];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                result += new String(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    //----------------------写入文件 字节输出流-----------------------------------
    /*
    OutputStream 是字节输出流的基类
    FileOutputStream 是用于写文件的输出流
    BufferedOutputStream 缓冲区
     */

    /**
     * 字节流写文件
     * file 文件              <br/>
     * isCover 是否覆盖        <br/>
     * data 数据              <br/>
     */
    public void writeFileByFileOutputStream() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, isCover);
            fos.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 字节流写文件(引入BufferedOutputStream缓冲区) <br/>
     * file 文件              <br/>
     * isCover 是否覆盖        <br/>
     * data 数据              <br/>
     */
    public void writeFileByBufferedOutputStream() {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            fos = new FileOutputStream(file, isCover);
            bos = new BufferedOutputStream(fos);
            bos.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*---------------------复制文件-------------------------*/

    /**
     * 复制文件
     */
    public void copyFileByIoByte() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(targetFile);
            fos = new FileOutputStream(goalFile);
            int len;
            byte[] b = new byte[readCount];
            //遍历出需复制的文件的字节，一个字符一个字符的写入复制过后的新文件中
            while ((len = fis.read(b)) != -1) {
                fos.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 复制文件(引入缓冲区)
     */
    public void copyFileByIoByteBuffer() {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(targetFile));
            bos = new BufferedOutputStream(new FileOutputStream(goalFile));
            int len;
            byte[] b = new byte[bufferSize];
            // 遍历出需复制的文件的字节，一个字符一个字符的写入复制过后的新文件中
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
