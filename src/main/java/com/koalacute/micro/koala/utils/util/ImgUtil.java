package com.koalacute.micro.koala.utils.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class ImgUtil {
	
	 /**
     * 按自定义文字画一张图
     * @param str
     * @param font
     * @param out
     * @param width
     * @param height
     * @throws Exception
     */
    public static byte[] createImage(String str, Font font, ByteArrayOutputStream out, 
    		Integer width, Integer height) throws Exception { 
    	// 创建图片 
    	BufferedImage image = new BufferedImage(width, height, 
    			BufferedImage.TYPE_INT_BGR);
    	Graphics2D g = image.createGraphics(); 
    	g.setClip(0, 0, width, height); 
    	g.setBackground(null);
    	g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景 
    	g.setColor(Color.black);// 在换成黑色 
    	g.setFont(font);// 设置画笔字体 
    	/** 用于获得垂直居中y */
    	Rectangle clip = g.getClipBounds(); 
    	FontMetrics fm = g.getFontMetrics(font); 
    	int ascent = fm.getAscent(); 
    	int descent = fm.getDescent(); 
    	int y = (clip.height - (ascent + descent)) / 2 + ascent; 
		g.drawString(str, 280, y);// 画出字符串 
    	//释放对象
    	g.dispose(); 
    	ImageIO.write(image, "jpg", out);// 输出png图片 
    	return out.toByteArray();
    }

    
    /**
     * 生成签字图片（不是客户手动写的）
     * @param name
     * @return
     * @throws Exception
     */
    public static byte[] generateSign(String name) throws Exception {
    	return createImage(name, new Font("宋体", Font.PLAIN, 200), new ByteArrayOutputStream(), 1260, 420);
    }
    
//    public static void main(String[] args) throws Exception {
//    	byte[] generateSign = ImgUtil.generateSign("张小明");
//		String a = Base64Utils.encode(generateSign);
//		List<String> lines = new ArrayList<String>();
//		lines.add(a);
//		FileUtils.writeLines(new File("d:/aaa.txt"), lines);
//	}
}
