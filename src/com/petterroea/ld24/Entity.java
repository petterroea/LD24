package com.petterroea.ld24;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Entity {
	double x=0.0d, y=0.0d;
	public static BufferedImage[][] particles;
	public static BufferedImage[][] enemies;
	int w=8, h=8;
	double xspeed=0.0d, yspeed=0.0d;
	double GRAVITY = 0.0011d;
	static final double TERMINAL_VELOCITY=0.30d;
	byte[] nonCollide = new byte[]{6, 13, 14};
	byte[] special = new byte[]{13, 14};
	boolean dead = false;
	public void doSpecial(byte theByte, Level level)
	{
		if(theByte==13)
		{
			
		}
	}
	public Entity(int x, int y, int w, int h)
	{
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		if(enemies==null)
			enemies=Util.loadSplit(16, 16, Entity.class.getResourceAsStream("enemies.png"));
		if(particles==null)
			particles = Util.loadSplit(5, 5, Entity.class.getResourceAsStream("particles.png"));
	}
	public void tick(int delta, Level level)
	{
		double oldx = x;
		double oldy = y;
		//Calculate gravity
		yspeed=yspeed+GRAVITY;
		if(yspeed>TERMINAL_VELOCITY)
		{
			yspeed=TERMINAL_VELOCITY;
		}
		//Move the player on x axis
		x+=(xspeed*delta);
		
		Rectangle xrect = new Rectangle((int)x, (int)y, w, h);//Me
		//Loop through nearby tiles
		boolean foundColl=false;
		for(int loopx=(int)x/8; (loopx<(x/8)+(w/8)+1)&&(!foundColl); loopx++)
		{
			for(int loopy=(int)y/8; (loopy<(y/8)+(h/8)+1)&&(!foundColl); loopy++)
			{
				if(loopx>=0&&loopy>=0)
				{
					if(level.tiles[loopx][loopy]!=0)
					{
						Rectangle rect = new Rectangle(loopx*8, loopy*8, 8, 8);
						if(rect.intersects(xrect))
						{
							for(int i = 0; i < special.length; i++)
							{
								if(level.tiles[loopx][loopy]==special[i])
								{
									doSpecial(level.tiles[loopx][loopy], level);
								}
							}
							boolean nonColl = false;
							for(int i = 0; i < nonCollide.length; i++)
							{
								if(level.tiles[loopx][loopy]==nonCollide[i])
								{
									nonColl=true;
								}
							}
							if(!nonColl)
							{
								x=oldx;
								xspeed=0.0d;
								foundColl=true;
							}
						}
					}
				}
			}
		}
		//Move the player on y axis
		y+=(yspeed*delta);
		xrect = new Rectangle((int)x, (int)y, w, h);//Me
		//Loop through nearby tiles
		foundColl=false;
		for(int loopx=(int)x/8; (loopx<(x/8)+(w/8)+1)&&(!foundColl); loopx++)
		{
			for(int loopy=(int)y/8; (loopy<(y/8)+(h/8)+1)&&(!foundColl); loopy++)
			{
				if(loopx>=0&&loopy>=0)
				{
					if(level.tiles[loopx][loopy]!=0)
					{
						Rectangle rect = new Rectangle(loopx*8, loopy*8, 8, 8);
						if(rect.intersects(xrect))
						{
							for(int i = 0; i < special.length; i++)
							{
								if(level.tiles[loopx][loopy]==special[i])
								{
									doSpecial(level.tiles[loopx][loopy], level);
								}
							}
							boolean nonColl = false;
							for(int i = 0; i < nonCollide.length; i++)
							{
								if(level.tiles[loopx][loopy]==nonCollide[i])
								{
									nonColl=true;
								}
							}
							if(!nonColl)
							{
								y=oldy;
								yspeed=0.0d;
								foundColl=true;
							}
						}
					}
				}
			}
		}
	}
	public void damage(Level level)
	{
		
	}
	public void render(Graphics g, int xoff, int yoff, Level level)
	{
		
	}
}
