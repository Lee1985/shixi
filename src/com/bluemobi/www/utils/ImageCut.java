package com.bluemobi.www.utils;

import java.io.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class ImageCut {
	/**
	 * 图像切割（改） *
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param dirImageFile
	 *            新图像地址
	 * @param x
	 *            目标切片起点x坐标
	 * @param y
	 *            目标切片起点y坐标
	 * @param destWidth
	 *            目标切片宽度
	 * @param destHeight
	 *            目标切片高度
	 */
	public static void abscut(String srcImageFile, String dirImageFile, int x,
			int y, int destWidth, int destHeight) {
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度
			if (srcWidth >= destWidth && srcHeight >= destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				// 改进的想法:是否可用多线程加快切割速度
				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
				img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(destWidth, destHeight,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null); // 绘制缩小后的图
				g.dispose();
				// 输出为文件
				ImageIO.write(tag, "JPEG", new File(srcImageFile));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 缩放图像
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param scale
	 *            缩放比例
	 * @param flag
	 *            缩放选择:true 放大; false 缩小;
	 */
	public static void scale(String srcImageFile, String result, int scale,
			boolean flag) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长
			if (flag) {
				// 放大
				width = width * scale;
				height = height * scale;
			} else {
				// 缩小
				width = width / scale;
				height = height / scale;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重新生成按指定宽度和高度的图像
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            新的图像地址
	 * @param _width
	 *            设置新的图像宽度
	 * @param _height
	 *            设置新的图像高度
	 */
	public static void scale(String srcImageFile, String result, int _width,
			int _height) {
		scale(srcImageFile, result, _width, _height, 0, 0);
	}

	public static void scale(String srcImageFile, String result, int _width,
			int _height, int x, int y) {
		try {

			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件

			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长

			if (width > _width) {
				width = _width;
			}
			if (height > _height) {
				height = _height;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, x, y, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像类型转换 GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
	 */
	public static void convert(String source, String result) {
		try {
			File f = new File(source);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, "JPG", new File(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 彩色转为黑白
	 * 
	 * @param source
	 * @param result
	 */
	public static void gray(String source, String result) {
		try {
			BufferedImage src = ImageIO.read(new File(source));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "JPEG", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
public static InputStream rotateImg(BufferedImage image, int degree, Color bgcolor) throws IOException { 
        
        int iw = image.getWidth();//原始图象的宽度 
        int ih = image.getHeight();//原始图象的高度 
        int w = 0; 
        int h = 0; 
        int x = 0; 
        int y = 0; 
        degree = degree % 360; 
        if (degree < 0) 
            degree = 360 + degree;//将角度转换到0-360度之间 
        double ang = Math.toRadians(degree);//将角度转为弧度 
  
        /**
         *确定旋转后的图象的高度和宽度
         */ 
  
        if (degree == 180 || degree == 0 || degree == 360) { 
            w = iw; 
            h = ih; 
        } else if (degree == 90 || degree == 270) { 
            w = ih; 
            h = iw; 
        } else { 
            //int d = iw + ih; 
            //w = (int) (d * Math.abs(Math.cos(ang))); 
              //h = (int) (d * Math.abs(Math.sin(ang)));
            double cosVal = Math.abs(Math.cos(ang));
            double sinVal = Math.abs(Math.sin(ang));
            w = (int) (sinVal*ih) + (int) (cosVal*iw);
            h = (int) (sinVal*iw) + (int) (cosVal*ih);
        } 
  
        x = (w / 2) - (iw / 2);//确定原点坐标 
        y = (h / 2) - (ih / 2); 
        BufferedImage rotatedImage = new BufferedImage(w, h, image.getType()); 
        Graphics2D gs = (Graphics2D)rotatedImage.getGraphics(); 
        if(bgcolor==null){ 
            rotatedImage = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT); 
        }else{ 
            gs.setColor(bgcolor); 
            gs.fillRect(0, 0, w, h);//以给定颜色绘制旋转后图片的背景 
        } 
          
        AffineTransform at = new AffineTransform(); 
        at.rotate(ang, w / 2, h / 2);//旋转图象 
        at.translate(x, y); 
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC); 
        op.filter(image, rotatedImage); 
        image = rotatedImage; 
          
        ByteArrayOutputStream byteOut= new ByteArrayOutputStream(); 
        ImageOutputStream iamgeOut = ImageIO.createImageOutputStream(byteOut); 
          
        ImageIO.write(image, "png", iamgeOut); 
        InputStream inputStream = new ByteArrayInputStream(byteOut.toByteArray()); 
          
        return inputStream; 
    } 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 晕。。。搞成多个了...
		// cut("c:/images/ipomoea.jpg", "c:/images/t/ipomoea.jpg", 200, 150);
		// ok
		// gray("c:/images/ipomoea.jpg", "c:/images/t/ipomoea.jpg");
		// convert("c:/images/ipomoea.jpg", "c:/images/t/ipomoea.gif");
		// scale("c:/images/5105049910001020110718648723.jpg",
		// "c:/images/t/5105049910001020110718648725.jpg",154,166,157,208);
		// scale("c:/images/rose1.jpg",
		// "c:/images/t/rose1.jpg",154,166,157,208);
		scale("c:/images/rose1.jpg", "c:/images/t/rose2.jpg", 154, 166, 10, 10);

	}
}
