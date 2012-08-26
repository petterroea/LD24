package com.petterroea.ld24;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class EntityBullet extends Entity {
	Random tickRand;
	public EntityBullet(int x, int y, int w, int h, double xspeed, double yspeed) {
		super(x, y, w, h);
		this.xspeed=xspeed;
		this.yspeed=yspeed;
		GRAVITY=0.00003d;
		tickRand = new Random();
	}
	public void spawnDustThingie(Level level)
	{
		level.entities.add(new EntityParticle((int)x+2, (int)y+2, 3, 3, xspeed*level.rand.nextDouble(), yspeed*level.rand.nextDouble(), 1000, 1000, 1, GRAVITY));
	}
	@Override
	public void tick(int delta, Level level)
	{
		if(tickRand.nextInt(100/(delta+1))==0)
		{
			//level.entities.add(new EntityParticle((int)x+2, (int)y+2, 3, 3, xspeed*0.6, yspeed*0.6, 1000, 1000, 1+level.rand.nextInt(2), GRAVITY));
			spawnDustThingie(level);
		}
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
									doSpecial(level.tiles[loopx][loopy]);
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
								dead=true;
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
									doSpecial(level.tiles[loopx][loopy]);
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
								dead=true;
							}
						}
					}
				}
			}
		}
	}
	@Override
	public void render(Graphics g, int xoff, int yoff, Level level)
	{
		g.drawImage(particles[2][0], (int)x-xoff, (int)y-yoff, null);
	}

}
