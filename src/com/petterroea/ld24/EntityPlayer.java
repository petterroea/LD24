package com.petterroea.ld24;

import java.awt.Color;
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
	int health = 10;
	public static boolean left = true;
	long lastFire = System.currentTimeMillis();
	@Override
	public void doSpecial(byte theByte, Level level)
	{
		if(theByte==13)
		{
			this.yspeed=-0.5;
			this.health=this.health-5;
			if(this.health<=0)
			{
				health=0;
				damage(level);
			}
		}
	}
	@Override
	public void damage(Level level)
	{
		health--;
		if(health<=0)
		{
			level.dead=true;
			dead = true;
			for(int i = 0; i < 300; i++)
			{
				level.entities.add(new EntityParticle((int)x+level.rand.nextInt(8), (int)y+level.rand.nextInt(16), 3, 3, (level.rand.nextDouble()-0.5)/2, (level.rand.nextDouble()-0.5)/2, 3000, 3000, 4+level.rand.nextInt(2), GRAVITY/3));
			}
			GameScreen.dead.play();
		}
		else
		{
			GameScreen.hit.play();
		}
	}
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
			GameScreen.jump.play();
		}
		int fireTime=800/level.weaponLevel;
		double speed = 0.1*level.weaponLevel;
		if(Input.keys[KeyEvent.VK_ENTER]&&(System.currentTimeMillis()-lastFire>fireTime))
		{
			lastFire=System.currentTimeMillis();
			GameScreen.shoot.play();
			if(left)
			{
				level.entities.add(new EntityBullet((int)x, (int)y+5, 5, 5, -speed, 0.0));
			}
			else
			{
				level.entities.add(new EntityBullet((int)x+8, (int)y+5, 5, 5, speed, 0.0));
			}
		}
		Rectangle me = new Rectangle((int)x, (int)y, w, h);
		for(int i = 0; i < level.entities.size(); i++)
		{
			if(level.entities.get(i) instanceof EntityHostile)
			{
				Rectangle temp = new Rectangle((int)level.entities.get(i).x, (int)level.entities.get(i).y, (int)level.entities.get(i).w, (int)level.entities.get(i).h);
				if(me.intersects(temp))
				{
					health=0;
					damage(level);
				}
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
		g.setColor(Color.red);
		g.fillRect(0, 0, Game.getScaledWidth(), 3);
		g.setColor(Color.green);
		double precentage = ((double)health/10.0)*Game.getScaledWidth();
		g.fillRect(0, 0, (int)precentage, 3);
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
									if(level.tiles[loopx][loopy]==14)
									{
										if(level.master.dialog.equals("")&&Input.focus)
										{
											Util.drawString("Press enter to finish", (Game.getScaledWidth()/2)-(210/2),Game.getScaledHeight()-15, g);
											if(Input.keys[KeyEvent.VK_ENTER])
											{
												level.master.master.currentScreen=new CreditsScreen(level.master.master);
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
