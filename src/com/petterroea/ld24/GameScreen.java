package com.petterroea.ld24;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class GameScreen extends Screen {
	Level level;
	BufferedImage backdrop;
	public GameScreen(Game master) {
		super(master);
		try {
			backdrop = ImageIO.read(GameScreen.class.getResourceAsStream("backdrop.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
		level = new Level(GameScreen.class.getResourceAsStream("level.png"));
	}
	@Override
	public void tick(int delta, Graphics g)
	{
		double dropx = (level.xoff/(backdrop.getWidth()-master.getScaledWidth()))*backdrop.getWidth();
		double dropy = (level.yoff/(backdrop.getHeight()-master.getScaledHeight()))*backdrop.getHeight();
		BufferedImage sub = backdrop.getSubimage((int)dropx, (int)dropy, master.getScaledWidth(), master.getScaledHeight());
		g.drawImage(sub, 0, 0, null);
		if(Input.focus)
			level.tick(delta);
		level.render(g);
		Util.drawString("Testing 123", 0, 0, g);
	}

}
