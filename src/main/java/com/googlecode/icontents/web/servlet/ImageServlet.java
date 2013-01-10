package com.googlecode.icontents.web.servlet;

/**
 * <p>Title: ImageServlet.java</p>
 * <p>Description: Verify code image producer servlet</p>
 * <p>Copyright: Hongshee Software (c) 2007</p>
 * @author jackie du
 * @version 1.0
 */

import java.io.IOException;
import java.io.OutputStream;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ImageServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private Random rand = new Random();

    //Initialize global variables
    public void init() throws ServletException
    {
    	System.out.println("EasyJForum: Image servlet initialized.");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) 
                        throws ServletException, IOException
    {
        doGet(request, response); 
    }
    
    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
                        throws ServletException, IOException
    {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);       

        // 在内存中创建图象
        int width = 112, height = 42;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics g = image.getGraphics();

        // 设定背景色
        // g.setColor(getRandColor(200,250));
        g.setColor(new Color(255,255,255));
        g.fillRect(0, 0, width, height);

        //设定字体
        g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 48));

        //画边框
        //g.setColor(new Color());
        //g.drawRect(0,0,width-1,height-1);

        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        /*
        g.setColor(getRandColor(160,200));
        for (int i=0;i<155;i++) 
        {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x,y,x+xl,y+yl);
        }
        */
        
        // 取随机产生的认证码(4位数字)
        Color fontColor = getRandColor(0,220);
        StringBuilder vcode = new StringBuilder();
        String aCode = null;
        
        for (int i=0;i<4;i++)
        {
            aCode = String.valueOf(rand.nextInt(10));
            vcode.append(aCode);
            // 将认证码显示到图象中
            g.setColor(fontColor);
            // g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(aCode, 28*i, 38);
        }

        // 将认证码存入SESSION
        HttpSession session = request.getSession();
        session.setAttribute("vcode", vcode.toString());
        // 图象生效
        g.dispose();
        // 输出图象到页面
        OutputStream out = response.getOutputStream();
        try
        {
            ImageIO.write(image, "JPEG", out);
            out.flush();
        }
        finally
        {
            out.close();
        }
    }

    private Color getRandColor(int fcc, int bcc)
    {   //给定范围获得随机颜色
        int fc = fcc;
        int bc = bcc;
        if(fc > 255) fc = 255;
        if(bc > 255) bc = 255;
        int r = fc + rand.nextInt(bc-fc);
        int g = fc + rand.nextInt(bc-fc);
        int b = fc + rand.nextInt(bc-fc);
        return new Color(r,g,b);
    }
}