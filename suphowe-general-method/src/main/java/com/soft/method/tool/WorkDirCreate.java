package com.soft.method.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 工作文件夹创建
 * @author suphowe
 */
public class WorkDirCreate {

    /**
     * 根目录
     */
    public static String rootFile = "C:\\suphowe\\公司文档\\HR 数字化\\";

    public static void createDirs(){
        File root = new File(rootFile);
        if (root.exists()) {
            System.out.println("根文件夹:    " + rootFile + "     已存在");
            return;
        }
        List<String> dirList = workDirs();
        for (String dir : dirList) {
            String dirFile = rootFile + dir;
            File file = new File(dirFile);
            if (!file.exists()) {
                file.mkdirs();
                System.out.println("文件夹:    " + dirFile + "     创建成功");
            }
        }
    }

    /**
     * 文件夹
     * @return 文件夹
     */
    public static List<String> workDirs(){
        List<String> dirList = new ArrayList<>();
        dirList.add("0.需求文档");
        dirList.add("1.开发文档");
        dirList.add("2.修订文档");
        dirList.add("3.测试文档");
        dirList.add("4.技术手册");
        dirList.add("5.会议纪要");
        dirList.add("6.资料收集");
        dirList.add("7.设计文档");
        dirList.add("8.操作手册");
        dirList.add("9.上线文档");
        dirList.add("10.节点记录");
        return dirList;
    }
    
    public static void main (String[] args) {
        WorkDirCreate.createDirs();
    }

}
