package com.hadoop.hdfs.config;

import com.hadoop.hdfs.utils.SysUtils;

/**
 * hdfs 静态常量
 * @author suphowe
 */
public class HdfsConstants {

    public static final String HDFS_PATH = SysUtils.getProperties("properties/hdfs", "hdfs.path");

    public static final String HDFS_NAME = SysUtils.getProperties("properties/hdfs", "hdfs.name");

    public static final int HDFS_BUFFER_SIZE = 1024 * 1024 * Integer.parseInt(SysUtils.getProperties("properties/hdfs", "hdfs.bufferSize"));
}
