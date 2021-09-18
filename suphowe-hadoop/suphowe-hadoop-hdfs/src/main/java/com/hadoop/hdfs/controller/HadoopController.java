package com.hadoop.hdfs.controller;

import com.alibaba.fastjson.JSONObject;
import com.hadoop.hdfs.utils.HdfsUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 已经全部测试均可用
 * @author suphowe
 */
@RestController
@RequestMapping("/hadoop")
public class HadoopController {

    private static final Logger log = LoggerFactory.getLogger(HadoopController.class);

    /**
     * 创建文件夹
     */

    @RequestMapping(value = "mkdir", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject mkdir(@RequestParam("path") String path) throws Exception {
        JSONObject object = new  JSONObject( );
        if (StringUtils.isEmpty(path)) {
            object.put("message", "请求参数为空");
            log.debug("请求参数为空");
            return object;
        }
        // 创建空文件夹
        if (HdfsUtils.mkdir(path)) {
            log.debug("文件夹创建成功");
            object.put("message", "文件夹创建成功");
            return object;
        }
        log.debug("文件夹创建失败");
        object.put("message", "文件夹创建成功");
        return object;
    }

    /**
     * 读取HDFS目录信息
     */

    @PostMapping("/readPathInfo")
    public JSONObject  readPathInfo(@RequestParam("path") String path) throws Exception {
        List<Map<String, Object>> list = HdfsUtils.readPathInfo(path);
        JSONObject object = new  JSONObject( );
        object.put( "读取HDFS目录信息成功", list);
        return object;
    }



    /**
     * 读取HDFS文件内容
     */
    @PostMapping("/readFile")
    public JSONObject readFile(@RequestParam("path") String path) throws Exception {
        JSONObject object = new  JSONObject( );
        String targetPath = HdfsUtils.readFile(path);
        object.put("message", "读取HDFS文件内容success");
        object.put("data",targetPath);
        return object;

    }


    /**
     * 读取文件列表
     */
    @PostMapping("/listFile")
    public JSONObject listFile(@RequestParam("path") String path) throws Exception {
        JSONObject object = new  JSONObject( );
        if (StringUtils.isEmpty(path)) {
            object.put("message","请求参数为空");
            return object;
        }
        List<Map<String, String>> returnList = HdfsUtils.listFile(path);
        object.put("message","读取文件列表成功");
        object.put("data",returnList);
        return object;
    }


    /**
     * 重命名文件
     */
    @PostMapping("/renameFile")
    public JSONObject renameFile(@RequestParam("oldName") String oldName, @RequestParam("newName") String newName)
            throws Exception {
        JSONObject object = new  JSONObject( );
        if (StringUtils.isEmpty(oldName) || StringUtils.isEmpty(newName)) {
            object.put("message","请求参数为空");
            return object;
        }
        if (HdfsUtils.renameFile(oldName, newName)) {
            object.put("message", "文件重命名成功");
            return object;
        }
        object.put("message", "文件重命名失败");
        return object;
    }

    /**
     * 删除文件
     */
    @PostMapping("/deleteFile")
    public JSONObject deleteFile(@RequestParam("path") String path) throws Exception {
        JSONObject object = new  JSONObject( );
        if (HdfsUtils.deleteFile(path)) {
            object.put("message", "delete file success");
            return object;
        }
        object.put("message", "delete file fail");
        return object;
    }



    /**
     * 上传文件
     */
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("path") String path, @RequestParam("uploadPath") String uploadPath)
            throws Exception {
        HdfsUtils.uploadFile(path, uploadPath);
        return    "upload file success" ;
    }

    /**
     * 下载文件
     */
    @PostMapping("/downloadFile")
    public String downloadFile(@RequestParam("path") String path, @RequestParam("downloadPath") String downloadPath)
            throws Exception {
        HdfsUtils.downloadFile(path, downloadPath);
        return  "download file success" ;
    }

    /**
     * HDFS文件复制
     * @param sourcePath 源文件路径 例如:/user/Administrator/001/11.jpg
     * @param targetPath 目标路径 例如:/user/Administrator/111/11.jpg
     */
    @PostMapping("/copyFile")
    public String copyFile(@RequestParam("sourcePath") String sourcePath, @RequestParam("targetPath") String targetPath)
            throws Exception {
        HdfsUtils.copyFile(sourcePath, targetPath);
        return  "copy file success" ;
    }

    /**
     * 查看文件是否已存在
     */
    @PostMapping("/existFile")
    public String existFile(@RequestParam("path") String path) throws Exception {
        boolean isExist = HdfsUtils.existFile(path);
        return   "file isExist: " + isExist ;
    }
}
