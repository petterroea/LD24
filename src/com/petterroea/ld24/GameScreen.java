package com.petterroea.ld24;

import java.awt.Color;
import java.awt.Graphics;


public class GameScreen extends Screen {
	Level level;
	public GameScreen(Game master) {
		super(master);
		// TODO Auto-generated constructor stub
		level = new Level(GameScreen.class.getResourceAsStream("level.png"));
	}
	@Override
	public void tick(int delta, Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, master.getScaledWidth(), master.getScaledHeight());
		level.render(0, 0, g);
	}

}
