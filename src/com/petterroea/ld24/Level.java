package com.petterroea.ld24;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Level {
	byte[][] tiles;
	BufferedImage[][] tileRes;
	LinkedList<Entity> entities;
	double xoff=0, yoff=0;
	public Level(InputStream in)
	{
		long startTime = System.currentTimeMillis();
		entities = new LinkedList<Entity>();
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
				case 0xFFFFFF: //Wall
					tiles[x][y]=1;
					break;
				case 0x00FF21: //Player
					entities.add(new EntityPlayer(x*8, (y-1)*8, 12, 16));
					break;
				case 0xA0A0A0: //Floor
					tiles[x][y]=2;
					break;
				case 0x303030: //Ceiling
					tiles[x][y]=3;
					break;
				case 0x606060: //Danger-thing
					tiles[x][y]=4;
					break;
				case 0xC0C0C0: //Metal plate
					tiles[x][y]=5;
					break;
				case 0xFF0000: //Pc
					tiles[x][y]=6;
					break;
				default:
					tiles[x][y]=0;
					break;
				}
			}
		}
		System.out.println("Loaded level in " + (System.currentTimeMillis()-startTime) + " ms");
	}
	int playerx=0;
	int playery=0;
	public void tick(int delta)
	{
		for(int i = 0; i < entities.size(); i++)
		{
			entities.get(i).tick(delta, this);
		}
		int levelX=playerx/Game.getScaledWidth();
		int levelY=playery/Game.getScaledHeight();
		if(xoff<levelX*Game.getScaledWidth())
		{
			xoff+=0.8*delta;
		}
		if(xoff>levelX*Game.getScaledWidth())
		{
			xoff-=0.8*delta;
		}
		if(yoff<levelY*Game.getScaledHeight())
		{
			yoff+=0.8*delta;
		}
		if(yoff>levelY*Game.getScaledHeight())
		{
			yoff-=0.8*delta;
		}
	}
	public void render(Graphics g)
	{
		for(int x = (int)xoff/16; x<((int)xoff/8)+Game.TILES_W; x++)
		{
			for(int y = (int)yoff/16; y < ((int)yoff/8)+Game.TILES_H; y++)
			{
				if(tiles[x][y]!=0)
				{
					int tilex = (tiles[x][y]-1)%8;
					int tiley = (tiles[x][y]-1)/8;
					g.drawImage(tileRes[tilex][tiley], (x*8)-(int)xoff, (y*8)-(int)yoff, null);
				}
			}
		}
		for(int i = 0; i < entities.size(); i++)
		{
			entities.get(i).render(g, (int)xoff, (int)yoff);
		}
	}
}
