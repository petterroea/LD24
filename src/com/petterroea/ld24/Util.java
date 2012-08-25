package com.petterroea.ld24;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Util {
	public static String[] chars = new String[]{"ABCDEFGHIJ",
		"KLMNOPQRST",
		"UVWXYZ.,?!",
		"0123456789"};
	private static BufferedImage[][] font;
	public static void drawString(String in, int x, int y, Graphics g)
	{
		if(font==null)
		{
			font=loadSplit(20, 20, Util.class.getResourceAsStream("font.png"));
		}
		in = in.toUpperCase();
		int yoff=0;
		int fontSize=20;
		int letterOff=0;
		for(int i = 0; i < in.length(); i++)
		{
			if(in.charAt(i)=='\n')
			{
				letterOff=0;
				yoff+=fontSize;
			}
			else
			{
				//Search the font list
				if(in.charAt(i)==' ')
				{
					letterOff++;
				}
				else
				{
					for(int a = 0; a < chars.length; a++)
					{
						for(int b = 0; b < chars[a].length(); b++)
						{
							if(chars[a].charAt(b)==in.charAt(i))
							{
								g.drawImage(font[b][a], x+(letterOff*fontSize), yoff+y, null);
								letterOff++;
							}
						}
					}
				}
			}
		}
	}
	public static BufferedImage[][] loadSplit(int w, int h, InputStream in)
	{
		BufferedImage src = null;
		try {
			src = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(src.getWidth()%w!=0||src.getHeight()%h!=0)
		{
			System.out.println("ERROR: Wrong dimensions for image");
		}
		BufferedImage[][] returnable = new BufferedImage[src.getWidth()/2][src.getHeight()/2];
		for(int x = 0; x < src.getWidth()/w; x++)
		{
			for(int y = 0; y < src.getHeight()/h; y++)
			{
				returnable[x][y]=src.getSubimage(x*w, y*h, w, h);
			}
		}
		return returnable;
	}

	public static BufferedImage[][] loadSplitMirror(int w, int h, InputStream in) {
		BufferedImage src = null;
		try {
			src = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(src.getWidth()%w!=0||src.getHeight()%h!=0)
		{
			System.out.println("ERROR: Wrong dimensions for image");
		}
		BufferedImage[][] returnable = new BufferedImage[src.getWidth()/2][src.getHeight()/2];
		for(int x = 0; x < src.getWidth()/w; x++)
		{
			for(int y = 0; y < src.getHeight()/h; y++)
			{
				returnable[x][y]=flipVertical(src.getSubimage(x*w, y*h, w, h));
				
			}
		}
		return returnable;
	}
	public static BufferedImage flipVertical(BufferedImage in)
	{
		BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), in.getColorModel().getTransparency());
		Graphics2D g = out.createGraphics();
		g.drawImage(in, 0, 0, in.getWidth(), in.getHeight(), in.getWidth(), 0, 0, in.getHeight(), null);
		g.dispose();
		return out;
	}
}
