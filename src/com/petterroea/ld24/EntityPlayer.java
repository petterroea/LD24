package com.petterroea.ld24;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class EntityPlayer extends Entity {
	public EntityPlayer(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	static BufferedImage[][] player;
	static BufferedImage[][] playerLeft;
	boolean moving = false;
	long runStart=0;
	public static boolean left = true;
	long lastFire = System.currentTimeMillis();
	@Override
	public void tick(int delta, Level level)
	{
		boolean keyDown=false;
		//Deal with keys
		if(Input.keys[KeyEvent.VK_A])
		{
			xspeed=-0.06;
			keyDown=true;
			left=true;
		}
		if(Input.keys[KeyEvent.VK_D])
		{
			xspeed=0.06;
			keyDown=true;
			left=false;
		}
		if(!keyDown)
		{
			moving=false;
			xspeed=0.0d;
			runStart=0;
		}
		else
		{
			if(!moving)
			{
				runStart=System.currentTimeMillis();
			}
			moving=true;
		}
		if(Input.keys[KeyEvent.VK_SPACE]&&canJump(level))
		{
			yspeed=-0.22;
		}
		if(Input.keys[KeyEvent.VK_ENTER]&&(System.currentTimeMillis()-lastFire>800))
		{
			lastFire=System.currentTimeMillis();
			if(left)
			{
				level.entities.add(new EntityBullet((int)x, (int)y+5, 5, 5, -0.1, 0.0));
			}
			else
			{
				level.entities.add(new EntityBullet((int)x+8, (int)y+5, 5, 5, 0.1, 0.0));
			}
		}
		super.tick(delta, level);
		level.playerx=(int)x;
		level.playery=(int)y;
	}
	private boolean canJump(Level level) {
		double usey=y+1.0;
		Rectangle xrect = new Rectangle((int)x, (int)usey, w, h);//Me
		//Loop through nearby tiles
		for(int loopx=(int)x/8; (loopx<(x/8)+(w/8)+1); loopx++)
		{
			for(int loopy=(int)y/8; (loopy<(y/8)+(h/8)+1); loopy++)
			{
				if(loopx>=0&&loopy>=0)
				{
					if(level.tiles[loopx][loopy]!=0)
					{
						Rectangle rect = new Rectangle(loopx*8, loopy*8, 8, 8);
						if(rect.intersects(xrect))
						{
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
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	@Override
	public void render(Graphics g, int xoff, int yoff, Level level)
	{
		if(player==null)
		{
			player=Util.loadSplit(12, 16, EntityPlayer.class.getResourceAsStream("char_anim.png"));
			playerLeft=Util.loadSplitMirror(12, 16, EntityPlayer.class.getResourceAsStream("char_anim.png"));
		}
		if(!moving)
		{
			if(left)
			{
				g.drawImage(playerLeft[0][0], (int)x-xoff, (int)y-yoff, null);
			}
			else
			{
				g.drawImage(player[0][0], (int)x-xoff, (int)y-yoff, null);
			}
		}
		else
		{
			if(left)
			{
				g.drawImage(playerLeft[(int) ((((System.currentTimeMillis()-runStart)/150)%6)+1)][0], (int)x-xoff, (int)y-yoff, null);
			}
			else
			{
				g.drawImage(player[(int) ((((System.currentTimeMillis()-runStart)/150)%6)+1)][0], (int)x-xoff, (int)y-yoff, null);
			}
		}
		//Computer activation and other derp.
				Rectangle xrect = new Rectangle((int)x, (int)y, w, h);//Me
				for(int loopx=(int)x/8; (loopx<(x/8)+(w/8)+1); loopx++)
				{
					for(int loopy=(int)y/8; (loopy<(y/8)+(h/8)+1); loopy++)
					{
						if(loopx>=0&&loopy>=0)
						{
							if(level.tiles[loopx][loopy]!=0)
							{
								Rectangle rect = new Rectangle(loopx*8, loopy*8, 8, 8);
								if(rect.intersects(xrect))
								{
									if(level.tiles[loopx][loopy]==6)
									{
										if(level.master.dialog.equals("")&&Input.focus)
										{
											Util.drawString("Press enter to open", (Game.getScaledWidth()/2)-(190/2),Game.getScaledHeight()-15, g);
											if(Input.keys[KeyEvent.VK_ENTER])
											{
												level.master.dialog=level.getDialog((int)loopx, (int)loopy);
												Input.keys[KeyEvent.VK_ENTER]=false;
												xspeed=0.0d;
												yspeed=0.0d;
												moving=false;
											}
										}
									}
								}
							}
						}
					}
				}
	}
}
