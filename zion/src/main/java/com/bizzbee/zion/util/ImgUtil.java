package com.bizzbee.zion.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImgUtil {
	
	// 获取classpath的绝对值路径
		private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		// 时间格式化的格式
		private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		// 随机数
		private static final Random r = new Random();

	
	/**
	 * 处理商铺缩略图
	 * 
	 * @param thumbnail  Spring自带的文件处理对象
	 * @param targetAddr 图片存储路径
	 * @return
	 */
	public static String generateThumbnail(MultipartFile thumbnail, String targetAddr) {
		// 获取随机文件名，防止文件重名
		String realFileName = getRandomFileName();
		// 获取文件扩展名
		String extension = getFileExtension(thumbnail);
		// 在文件夹不存在时创建
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File newPic = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getInputStream()).size(400, 400)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/pic/harden.jpg")), 0.5f)
					.outputQuality(0.8f).toFile(newPic);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}
	
	/**
	 * 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
	 */
	private static String getRandomFileName() {
		// 获取随机五位数
		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000;
		// 当前时间
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}

	/**
	 * 获取输入流的文件扩展名
	 * 
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(MultipartFile thumbnail) {
		String originalFileName = thumbnail.getOriginalFilename();// 返回客户端文件系统中的原始文件名
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}

	/**
	 * 创建目标路径上所涉及的目录
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		// 目录文件不存在
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	
	
	
	public static void main(String[] args) throws IOException {
		//获取classPath
		String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		Thumbnails.of(new File("D:\\zion.jpg"))
	    .size(400, 400)
	    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/pic/harden.jpg")),1f)
	    .outputQuality(0.8)
	    .toFile("D:/zion2.jpg");
		
	}
	

	/**
	 * 删除文件或目录下的文件
	 * 南通传智信息技术 --王逸嘉
	 * git 教研
	 * @param storePath：文件路径或者目录路径
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		// 存在
		if (fileOrPath.exists()) {
			// 如果是目录
			if (fileOrPath.isDirectory()) {
				File[] files = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			// 删除文件或文件夹
			fileOrPath.delete();
		}
	}

}
