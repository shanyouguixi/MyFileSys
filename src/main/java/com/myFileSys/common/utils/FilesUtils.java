package com.myFileSys.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;

public class FilesUtils {
	public static final String SAVEFILEPATH = "";

	/**
	 * 根据系统当前时间，返回时间层次的文件夹结果，如：upload/2015/01/18/0.jpg
	 * 
	 * @return
	 */
	public static String getTimeFilePath() {
		return new SimpleDateFormat("/yyyyMMdd").format(new Date());
	}

	/**
	 * 复制文件
	 * 
	 * @param oldFilePath
	 *            源文件路径
	 * @param newFilePath
	 *            目标文件毒经
	 * @return 是否成功
	 */
	public static boolean copyFile(String oldFilePath, String newFilePath) {
		try {
			int byteRead = 0;
			File oldFile = new File(oldFilePath);
			if (oldFile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldFilePath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newFilePath);
				byte[] buffer = new byte[1444];
				while ((byteRead = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteRead);
				}
				inStream.close();
				fs.close();
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错 ");
			e.printStackTrace();
			return false;
		}
	}

	public static String getFileType(String fileUri) {
		File file = new File(fileUri);
		String fileName = file.getName();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		return fileType;
	}

	/**
	 * @param file
	 * @param user_id
	 * @return url
	 */
	public static final Map<String, String> saveFile(UploadFile uploadFile, String id) {
		File file = uploadFile.getFile();
		PropKit.use("a_little_config.txt");
		String web_root_path = PropKit.get("WEB_ROOT_PATH");
		String saveFilePath = web_root_path;
		String timeFilePath = getTimeFilePath();
		String fileName = file.getName();
		String fileType = fileType(fileName);
		String urlPath = "/"+fileType +"/" + id + "/source" + timeFilePath;
		saveFilePath += urlPath + "/";
		File saveFileDir = new File(saveFilePath);
		if (!saveFileDir.exists()) {
			saveFileDir.mkdirs();
		}
		File f = new File(saveFilePath + fileName);
		if(f.exists()){
			fileName ="1"+fileName; 
		}
		// 保存 文件
		if (copyFile(file.getAbsolutePath(), saveFilePath + fileName)) {
			// 删掉临时文件
			file.delete();
			System.out.println(urlPath + fileName);
			Map<String, String> map = new HashMap<String, String>();
			map.put("path", urlPath+"/" + fileName);
			map.put("fileName", fileName);
			return map;
		} else {
			return null;
		}
	}

	public static String fileType(String fileName) {
		if (fileName == null) {
			fileName = "文件名为空！";
			return fileName;

		} else {
			// 获取文件后缀名并转化为写，用于后续比较
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
			// 创建图片类型数组
			String img[] = { "bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd",
					"cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf" };
			for (int i = 0; i < img.length; i++) {
				if (img[i].equals(fileType)) {
					return "picture";
				}
			}

			// 创建文档类型数组
			String document[] = { "txt", "doc", "docx", "xls", "htm", "html", "jsp", "rtf", "wpd", "pdf", "ppt" };
			for (int i = 0; i < document.length; i++) {
				if (document[i].equals(fileType)) {
					return "document";
				}
			}
			// 创建视频类型数组
			String video[] = { "mp4", "avi", "mov", "wmv", "asf", "navi", "3gp", "mkv", "f4v", "rmvb", "webm" };
			for (int i = 0; i < video.length; i++) {
				if (video[i].equals(fileType)) {
					return "video";
				}
			}
			// 创建音乐类型数组
			String music[] = { "mp3", "wma", "wav", "mod", "ra", "cd", "md", "asf", "aac", "vqf", "ape", "mid", "ogg",
					"m4a", "vqf" };
			for (int i = 0; i < music.length; i++) {
				if (music[i].equals(fileType)) {
					return "music";
				}
			}

		}
		return "other";
	}
	public static void deleteFile(String path){
		String web_root_path = PropKit.get("WEB_ROOT_PATH");
		File file = new File(web_root_path+path);
		if ( file.exists() ) {
			file.delete();
		}
	}
}
