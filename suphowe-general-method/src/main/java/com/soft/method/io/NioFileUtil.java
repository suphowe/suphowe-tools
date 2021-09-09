package com.soft.method.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO文件操作
 *
 * @author suphowe
 */
public class NioFileUtil {

    /**
     * 通过MappedByteBuffer写文件
     *
     * @param fileName 文件名
     * @param info     写入信息
     */
    public static void mappedByteBufferFileWrite(String fileName, String info) {
        RandomAccessFile randomAccessFile = null;
        FileChannel fileChannel = null;
        try {
            randomAccessFile = new RandomAccessFile(fileName, "rw");
            fileChannel = randomAccessFile.getChannel();

            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, info.getBytes().length + 1);
            mappedByteBuffer.put(info.getBytes());

            mappedByteBuffer.flip();

            byte[] bytes = new byte[mappedByteBuffer.capacity()];
            while (mappedByteBuffer.hasRemaining()) {
                byte bufferCurrentPosition = mappedByteBuffer.get();
                bytes[mappedByteBuffer.position()] = bufferCurrentPosition;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileChannel.close();
                randomAccessFile.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 直接通过FileChannel读取
     * @param fileName 文件名
     * @return 文件数据
     */
    public static String fileChannelRead(String fileName) {
        try {
            FileChannel fileChannel = new FileInputStream(new File(fileName)).getChannel();
            long buffSize = (fileChannel.size()/4 + 1) * 4;
            ByteBuffer buffer = ByteBuffer.allocate((int) buffSize);
            fileChannel.read(buffer);
            return new String(buffer.array());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MappedByteBuffer文件拷贝
     * @param oldFile 旧文件
     * @param newFile 新文件
     * @param deleteOldFile 是否删除原文件
     */
    public void mappedByteBufferFileCopy(String oldFile, String newFile, boolean deleteOldFile) {
        File source = new File(oldFile);
        File target = new File(newFile);
        FileChannel in = null;
        FileChannel out = null;
        try {
            in = new FileInputStream(source).getChannel();
            out = new FileOutputStream(target).getChannel();
            long size = in.size();
            // 内存映射文件
            MappedByteBuffer mbbi = in.map(FileChannel.MapMode.READ_ONLY, 0, size);
            MappedByteBuffer mbbo = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            for (int i = 0; i < size; i++) {
                byte b = mbbi.get(i);
                mbbo.put(i, b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //文件复制完成后，删除源文件
        if (deleteOldFile) {
            source.delete();
        }
    }

}
