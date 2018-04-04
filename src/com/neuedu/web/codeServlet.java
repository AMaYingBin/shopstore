package com.neuedu.web;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.prism.Graphics;

@WebServlet("/code")
public class codeServlet extends HttpServlet {

	private static final long serialVersionUID = 4962034736318961585L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//编写验证码逻辑
		System.out.println("验证码逻辑");
		//设置服务器返回文件的格式为jpg 格式图片
		response.setContentType("image/jpeg");
		//画图  bufferedImage：内存映像对象
		BufferedImage image=new BufferedImage(120, 30, BufferedImage.TYPE_INT_RGB);
		Random r=new Random();
		java.awt.Graphics g=image.getGraphics();
		//背景色
		g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		//背景颜色
		g.fillRect(0, 0, 60, 20);
		//前景色
		g.setColor(new Color(0, 0, 0));
		String number=String.valueOf(r.nextInt(99999));
		//画到图片上
		g.drawString(number, 5, 15);
		//获得字节输出流，因为输出的是图像压缩之后的字节数组，所以不能用printwrite
		OutputStream oStream=response.getOutputStream();
		//将图片压缩，输出
		JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(oStream);
		encoder.encode(image);
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
