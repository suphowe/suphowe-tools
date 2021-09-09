package com.soft.method.io;

import java.io.*;

/**
 * IO字符流操作<br/>
 * 只能够对文本进行读取,对于非文本文件(视频、音频、图片)等文件，只能够使用字节流<br/>
 * 能够操作Unicode<br/>
 * 2 byte * 8 = 16位
 *
 * @author suphowe
 */
public class IoCharOperate {

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
    public IoCharOperate(String file, String data, boolean isCover, int bufferSize, String charset) {
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
     * @param charset   字符集
     */
    public IoCharOperate(String file, int readCount, String charset) {
        this.file = file;
        this.readCount = readCount;
        this.charset = charset;
    }

    private String targetFile;

    private String goalFile;

    /**
     * 字符流复制文件
     *
     * @param targetFile 目标文件(被复制)
     * @param goalFile   目的文件
     * @param readCount 每次读取大小
     * @param bufferSize  缓冲区大小
     */
    public IoCharOperate(String targetFile, String goalFile, int readCount, int bufferSize) {
        this.targetFile = targetFile;
        this.goalFile = goalFile;
        this.readCount = readCount;
        this.bufferSize = bufferSize;
    }

    /*----------------------写文件 输出流-----------------------------*/

    /**
     * 向文件中写入数据         <br/>
     * file 文件              <br/>
     * isCover 是否覆盖        <br/>
     * data 数据              <br/>
     */
    public void writeFileByFileWriter() {

        /*
         * 创建一个FileWriter对象。该对象一被初始化就必须要明确被操作的文件。
         * 而且该文件会被创建到指定目录下。如果该目录下已有同名文件，将被覆盖。
         * 其实该步就是在明确数据要存放的目的地。
         */
        FileWriter fw = null;

        try {
            fw = new FileWriter(file, isCover);
            /*
             * 将字符串写入到流中
             */
            fw.write(data);

            /*
             * 关闭流资源,关闭之前会刷新一次内部缓冲中的数据
             * 将数据刷到目的地中
             * 和flush区别：flush刷新后，流可以继续使用，close刷新后，会将流关闭
             */
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 向文件中写入数据,引入Buffer缓冲区 <br/>
     * file 文件              <br/>
     * isCover 是否覆盖        <br/>
     * data 数据              <br/>
     * bufferSize 缓冲区大小              <br/>
     */
    public void writeFileByBufferedWriter() {

        /*
         * 创建一个FileWriter对象。该对象一被初始化就必须要明确被操作的文件。
         * 而且该文件会被创建到指定目录下。如果该目录下已有同名文件，将被覆盖。
         * 其实该步就是在明确数据要存放的目的地。
         */
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file, isCover);
            bw = new BufferedWriter(fw, bufferSize);

            bw.write(data);

            /*
             * 刷新流对象中缓冲中的数据
             * 将数据刷到目的地中
             */
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /*
             * 关闭BufferedWriter流资源
             */
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * 向文件中写入数据,引入OutputStreamWriter<br/>
     * file 文件              <br/>
     * isCover 是否覆盖        <br/>
     * data 数据              <br/>
     * charset 字符集              <br/>
     */
    public void writeFileByOutputStreamWriter() {

        /*
         * OutputStream: 表示输出字节流所有类的超类
         * OutputStreamWriter: outputStreamWriter是从字符流到字节流的桥接，写入它的字符使用指定的字节编码为字节charset
         */
        OutputStream os = null;
        OutputStreamWriter osw = null;
        try {
            os = new FileOutputStream(file, isCover);
            osw = new OutputStreamWriter(os, charset);
            osw.write(data);
            osw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 清空文件内容<br/>
     * file 文件              <br/>
     */
    public void clearFileByFileWriter(){
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write("");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*----------------------读文件 输入流-----------------------------*/

    /**
     * 读取文件中的数据         <br/>
     * file 文件         <br/>
     * readCount 每次读取大小         <br/>
     *
     * @return 获取到的数据
     */
    public Object readByFileReader() {
        FileReader fr = null;
        String result = "";
        try {
            File f = new File(file);

            /*判断是否为文件*/
            if (!f.isFile()) {
                return null;
            }
            /*建立一个流对象，将已存在的一个文件加载进流*/
            fr = new FileReader(f);
            int len = 0;

            /*创建一个临时存放数据的数组*/
            char[] ch = new char[readCount];

            /*调用流对象的读取方法将流中的数据读入到数组中,没有读取到值时,长度为-1*/
            while ((len = fr.read(ch)) != -1) {
                result += new String(ch, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    /**
     * 读取文件中的数据(引入BufferedReader缓冲区)         <br/>
     * file 文件         <br/>
     * readCount 每次读取大小         <br/>
     *
     * @return 获取到的数据
     */
    public Object readFileByBufferedReader() {
        BufferedReader br = null;
        FileReader fr = null;
        String result = "";
        try {
            File f = new File(file);

            /*判断是否为文件*/
            if (!f.isFile()) {
                return null;
            }
            /*建立一个流对象，将已存在的一个文件加载进流*/
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            int len = 0;

            /*创建一个临时存放数据的数组*/
            char[] ch = new char[readCount];

            /*调用流对象的读取方法将流中的数据读入到数组中,没有读取到值时,长度为-1*/
            while ((len = br.read(ch)) != -1) {
                result += new String(ch, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 读取数据,引入InputStreamReader<br/>
     * file 文件         <br/>
     * charset 字符集         <br/>
     *
     * @return 获取到的数据
     */
    public String readFileByInputStreamReader() {
        /*
         * FileInputStream: 表示输入字节流所有类的超类
         * InputStreamReader： InputStreamReader 是从字节流到字符流的桥接器，它读取字节并使用指定字符将他们解码为字符charset
         */
        FileInputStream fis = null;
        InputStreamReader isr = null;
        String result = "";
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, charset);
            int i;
            while ((i = isr.read()) != -1) {
                result += (char) i;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (isr != null) {
                    isr.close();
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

    /*---------------------复制文件-------------------------*/

    /**
     * 复制文件
     */
    public void copyFileByIoChar() {
        //建立字符流输入（读）对象，并绑定数据源
        FileReader fReader = null;
        //建立字符流输出（写）对象，并绑定目的地
        FileWriter fWriter = null;
        try {
            fReader = new FileReader(targetFile);
            fWriter = new FileWriter(goalFile);
            int len = 0;
            char[] b = new char[readCount];
            //遍历出需复制的文件的字节，一个字符一个字符的写入复制过后的新文件中
            while ((len = fReader.read(b)) != -1) {
                fWriter.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fReader != null) {
                    fReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fWriter != null) {
                    fWriter.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 复制文件(引入缓冲区)
     */
    public void copyFileByIoCharBuffer() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader(targetFile));
            bw = new BufferedWriter(new FileWriter(goalFile));
            String len = "";
            char[] c = new char[bufferSize];
            //将读到的内容遍历出来，然后在通过字符写入
            while ((len = br.readLine()) != null) {
                bw.write(c, 0, len.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
