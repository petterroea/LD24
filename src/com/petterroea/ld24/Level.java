package com.petterroea.ld24;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Level {
	byte[][] tiles;
	BufferedImage[][] tileRes;
	public Level(InputStream in)
	{
		long startTime = System.currentTimeMillis();
		BufferedImage src = null;
		try {
			src = ImageIO.read(in);
		} catch (IOException e) {
			System.err.println("ERROR READING THE IMAGE, YOU IDIOT. NO TAMPERING WITH THE IMAGES.");
		}
		tileRes=Util.loadSplit(8, 8, Level.class.getResourceAsStream("tiles.png"));
		tiles = new byte[src.getWidth()][src.getHeight()];
		for(int x = 0; x < src.getWidth(); x++)
		{
			for(int y = 0; y < src.getHeight(); y++)
			{
				int rgbThingie = src.getRGB(x, y) & 0xFFFFFF;
				switch(rgbThingie)
				{
				case 0xFFFFFF:
					tiles[x][y]=1;
					break;
				default:
					tiles[x][y]=0;
					break;
				}
			}
		}
		System.out.println("Loaded level in " + (System.currentTimeMillis()-startTime) + " ms");
	}
	public void render(int xoff, int yoff, Graphics g)
	{
		for(int x = xoff/16; x<(xoff/8)+Game.TILES_W; x++)
		{
			for(int y = yoff/16; y < (yoff/8)+Game.TILES_H; y++)
			{
				if(tiles[x][y]!=0)
				{
					int tilex = (tiles[x][y]-1)%8;
					int tiley = (tiles[x][y]-1)/8;
					g.drawImage(tileRes[tilex][tiley], (x*8)-xoff, (y*8)-yoff, null);
				}
			}
		}
	}
}
