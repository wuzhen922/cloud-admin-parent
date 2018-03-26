package com.eyelake.cloud.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 导出文件
 * 
 * @author XJJ
 * @create 2017-07-13-17:05
 */
public class ExportFileUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExportFileUtil.class);

	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param filePath
	 *            下载文件路径
	 * @param fileName
	 *            下载文件名
	 */
	public static void downloadFile(HttpServletResponse response, String filePath, String fileName) {

		FileInputStream in = null;
		OutputStream out = null;

		try {
			if (StringUtils.isBlank(fileName)) {
				logger.error("fileName not found.");
				return;
			}

			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

			String srcPath = filePath + "/" + fileName;

			// 设置文件ContentType类型，这样设置，会自动判断下载文件类型
			response.setContentType("multipart/form-data");
			// 设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + fileName);
			// 读取要下载的文件，保存到文件输入流
			in = new FileInputStream(srcPath);
			// 创建输出流
			out = response.getOutputStream();
			// 创建缓冲区
			byte buffer[] = new byte[1024];
			int len = 0;
			// 循环将输入流中的内容读取到缓冲区当中
			while ((len = in.read(buffer)) > 0) {
				// 输出缓冲区的内容到浏览器，实现文件下载
				out.write(buffer, 0, len);
			}
		} catch (IOException e) {
			logger.error("Export error :" + e);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 * @return
	 */
	public boolean fileDelete(File file) {
		if (file.isDirectory()) {
			String[] children = file.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = fileDelete(new File(file, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return file.delete();
	}

}
