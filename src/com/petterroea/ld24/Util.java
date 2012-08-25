package com.petterroea.ld24;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Util {
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
}
