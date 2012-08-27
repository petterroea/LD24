package com.petterroea.ld24;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class GameScreen extends Screen {
	Level level;
	public static Sound jump;
	public static Sound hit;
	public static Sound dead;
	public static Sound shoot;
	BufferedImage backdrop;
	long anim = System.currentTimeMillis();
	public GameScreen(Game master) {
		super(master);
		try {
			backdrop = ImageIO.read(GameScreen.class.getResourceAsStream("backdrop.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
		level = new Level(GameScreen.class.getResourceAsStream("level.png"), this);
		dialog="Press the button!\nthere isnt much time!";
		jump = new Sound(GameScreen.class.getResourceAsStream("jump.wav"));
		hit = new Sound(GameScreen.class.getResourceAsStream("hit.wav"));
		dead = new Sound(GameScreen.class.getResourceAsStream("dead.wav"));
		shoot = new Sound(GameScreen.class.getResourceAsStream("shoot.wav"));
	}
	String dialog = "";
	@Override
	public void tick(int delta, Graphics g)
	{
		double dropx = ((backdrop.getWidth()-master.getScaledWidth())/(level.w-master.getScaledWidth()))*level.xoff;
		double dropy = ((backdrop.getHeight()-master.getScaledHeight())/(level.h-master.getScaledHeight()))*level.yoff;
		BufferedImage sub = backdrop.getSubimage((int)dropx, (int)dropy, master.getScaledWidth(), master.getScaledHeight());
		g.drawImage(sub, 0, 0, null);
		if(Input.focus&&dialog.equals(""))
			level.tick(delta);
		level.render(g);
		if(!Input.focus||!dialog.equals(""))
		{
			g.setColor(new Color(20, 20, 20, 80));
			g.fillRect(0, 0, master.getScaledWidth(), master.getScaledHeight());
		}
		if(!Input.focus)
		{
			Util.drawString("Click to focus!", (master.getScaledWidth()/2)-(150/2), ((master.getScaledHeight()/2)-5)+(int)(Math.sin((double)(System.currentTimeMillis()-anim)/150)*10), g);
		}
		else if(!dialog.equals(""))
		{
			Util.drawString(dialog, 0, 0, g);
			Util.drawString("Press enter to continue", (master.getScaledWidth()/2)-(230/2), master.getScaledHeight()-15, g);
			if(Input.keys[KeyEvent.VK_ENTER])
			{
				dialog="";
				Input.keys[KeyEvent.VK_ENTER]=false;
			}
		}
	}

}
