package com.soft.method.file;

import java.io.File;

/**
 * 文件操作
 * @author suphowe
 */
public class FileManagerUtil {

	/**
	 * 删除文件，可以是文件或文件夹
	 *
	 * @param fileName 要删除的文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			return false;
		} else {
			if (file.isFile()) {
				return deleteFile(fileName);
			} else {
				return deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 删除单个文件
	 *
	 * @param fileName 要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if(!file.exists()) {
			return false;
		}
		try {
			return file.delete();
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 删除单个文件
	 *
	 * @param fileName 要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean delFile(String fileName) {
		File file = new File(fileName);
		if(!file.exists()) {
			return false;
		}
		try {
			executeLinuxCmd("rm -f "+fileName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 *
	 * @param dir 要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (File file : files) {
			// 删除子文件
			if (file.isFile()) {
				flag = deleteFile(file.getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else if (file.isDirectory()) {
				flag = deleteDirectory(file.getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			return false;
		}
		// 删除当前目录
		return dirFile.delete();
	}

	/**
	 * 执行cmd命令
	 * @param cmd 命令
	 * @return 执行结果
	 */
	public int executeLinuxCmd(String cmd) {
	     Runtime run = Runtime.getRuntime();
	     Process process;
	     int result;
	     try {
	         process = run.exec(cmd);
	         result = process.waitFor();
	     } catch (Exception e) {
			return 0;
	     }
	     return result;
	 }
}
