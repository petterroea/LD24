package com.petterroea.ld24;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Level {
	byte[][] tiles;
	BufferedImage[][] tileRes;
	LinkedList<Entity> entities;
	double xoff=0, yoff=0;
	int w, h;
	GameScreen master;
	Random rand;
	boolean dead = false;
	int spawnx = -1;
	int spawny = -1;
	int lastScreenX=-1;
	int lastScreenY=-1;
	public Level(InputStream in, GameScreen master)
	{
		rand = new Random();
		this.master = master;
		long startTime = System.currentTimeMillis();
		entities = new LinkedList<Entity>();
		BufferedImage src = null;
		try {
			src = ImageIO.read(in);
		} catch (IOException e) {
			System.err.println("ERROR READING THE IMAGE, YOU IDIOT. NO TAMPERING WITH THE IMAGES.");
		}
		w=src.getWidth()*8;
		h=src.getHeight()*8;
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
				case 0x0026FF: //Rat
					entities.add(new EntitySlime(x*8, (y-1)*8));
					break;
				case 0xFFD800: //Vertical pipe
					tiles[x][y]=8;
					break;
				case 0x00FFFF: //Vertical pipe with right puncture
					tiles[x][y]=10;
					entities.add(new EntityEmitter((x*8)+4, y*8, 0.2, -0.1, 400, 3));
					break;
				case 0xFFF600: //Acid
					tiles[x][y]=13;
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
		if(Input.keys[KeyEvent.VK_ENTER])
		{
			if(dead)
			{
				Input.keys[KeyEvent.VK_ENTER]=false;
				master.level=new Level(Level.class.getResourceAsStream("level.png"), master);
				for(int i = 0; i < master.level.entities.size(); i++)
				{
					if(master.level.entities.get(i) instanceof EntityPlayer)
					{
						master.level.entities.get(i).x=spawnx;
						master.level.entities.get(i).y=spawny;
					}
				}
				master.level.xoff=xoff;
				master.level.yoff=yoff;
				return;
			}
		}
		for(int i = 0; i < entities.size(); i++)
		{
			entities.get(i).tick(delta, this);
			if(entities.get(i).dead)
			{
				entities.remove(i);
				i--;
			}
		}
		int levelX=playerx/Game.getScaledWidth();
		int levelY=playery/Game.getScaledHeight();
		if(levelX!=lastScreenX||levelY!=lastScreenY)
		{
			lastScreenX=levelX;
			lastScreenY=levelY;
			spawnx=playerx;
			spawny=playery;
			System.out.println("Set spawn");
		}
		if(xoff<levelX*Game.getScaledWidth())
		{
			xoff+=0.9*delta;
		}
		if(xoff>levelX*Game.getScaledWidth())
		{
			xoff-=0.9*delta;
		}
		if(yoff<levelY*Game.getScaledHeight())
		{
			yoff+=0.9*delta;
		}
		if(yoff>levelY*Game.getScaledHeight())
		{
			yoff-=0.9*delta;
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
			entities.get(i).render(g, (int)xoff, (int)yoff, this);
		}
		Util.drawString(kills + " of " + (weaponLevel*4) + " kills, Level " + weaponLevel, 0, Game.getScaledHeight()-10, g);
		if(dead)
		{
			Util.drawString("Press enter", (Game.getScaledWidth()/2)-(110/2), (Game.getScaledHeight()/2)-5, g);
		}
	}
	public void doKill()
	{
		kills++;
		if(kills>weaponLevel*4)
		{
			kills=0;
			weaponLevel++;
		}
	}
	int weaponLevel=1;
	int kills=0;
	public String getDialog(int loopx, int loopy) {
		// TODO Auto-generated method stub
		if(loopx==17&&loopy==30)
			return "Your weapon is weak\n\nKill and destroy to\nevolve it.\n\nEnter to fire.";
		if(loopx==9&&loopy==55)
			return "This is a slime.\nHe evolved from suage.\nHe spits radioactive goo.";
		return "Hi.";
	}
}
