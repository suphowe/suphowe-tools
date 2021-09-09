package com.soft.method.linux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Linux执行命令
 * @author suphowe
 */
public class LinuxUtil {

    private static final Logger logger = LoggerFactory.getLogger(LinuxUtil.class);

    /**
     * 执行Linux命令
     * @param shellOrder shell命令
     */
    public int executeLinuxSh(String shellOrder) {
        Runtime run = Runtime.getRuntime();
        Process process = null;
        int result = 0;
        try {
            process = run.exec(shellOrder);
            result = process.waitFor();
        } catch (Exception e) {
            logger.error("Exception:", e);
            return 0;
        }
        return result;
    }

    /**
     * 运行shell并获得结果，注意：如果sh中含有awk,一定要按new String[]{"/bin/sh","-c",shStr}写,才可以获得流
     *
     * @param shellOrder 需要执行的shell
     */
    public static List<String> runShell(String shellOrder) {
        List<String> strList = new ArrayList<>();
        InputStreamReader ir = null;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", shellOrder}, null, null);
            ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            process.waitFor();
            while ((line = input.readLine()) != null) {
                strList.add(line);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
        } finally {
            try {
                if (ir != null){
                    ir.close();
                }
            } catch (Exception e) {
                logger.error("Exception:", e);
            }
        }
        return strList;
    }

    /**
     * 运行shell并获得结果，注意：如果sh中含有awk,一定要按new String[]{"/bin/sh","-c",shStr}写,才可以获得流
     *
     * @param shellOrder 需要执行的shell
     * @return sh返回值
     */
    public static String runShellByReturnStr(String shellOrder) {
        StringBuilder result = new StringBuilder();
        InputStreamReader ir = null;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", shellOrder}, null, null);
            ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            process.waitFor();
            while ((line = input.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            logger.error("Exception:", e);
        } finally {
            try {
                if (ir != null){
                    ir.close();
                }
            } catch (Exception e) {
                logger.error("Exception:", e);
            }
        }
        return result.toString();
    }
}
