package com.bluemobi.www.utils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

public class Grabber {
	/**
	 * 获取指定视频的帧并保存为图片至指定目录
	 * 
	 * @param videofile
	 *            源视频文件路径
	 * @param framefile
	 *            截取帧的图片存放路径
	 * @throws Exception
	 */
	public static boolean fetchFrame(String videofile, String framefile)
			throws Exception {		
		boolean flag = false;
		FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
		ff.start();
		int lenght = ff.getLengthInFrames();//总共多少帧
		int i = 0;
		Frame f = null;
		while (i < lenght) {
			// 过滤前100帧
			f = ff.grabImage();
			int skipFrame=100;
			if(lenght<=100){
				skipFrame = lenght/2;
			}
			if ((i > skipFrame) && (f.image != null)) {
				break;
			}
			i++;
		}
		Java2DFrameConverter paintConverter = new Java2DFrameConverter();
		BufferedImage bi = paintConverter.getBufferedImage(f);
		bi.getGraphics().drawImage(bi, 0, 0, null);
		bi.getGraphics().drawImage(paintConverter.getBufferedImage(f), 0, 0, null);
		File targetFile = new File(framefile);
		flag = ImageIO.write(bi, "jpg", targetFile);
		ff.stop();
		ff.release();
		return flag;
	}

	public static void main(String[] args) {
		try {
			Grabber.fetchFrame(
					"D:\\20160317150830600724461.mp4",
					"D:\\20160317150830600724461.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
