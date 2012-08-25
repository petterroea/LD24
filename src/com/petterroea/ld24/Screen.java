package com.petterroea.ld24;

import java.awt.Color;
import java.awt.Graphics;


public class Screen 
{
	Game master;
	public Screen(Game master)
	{
		this.master = master;
	}
	public void tick(int delta, Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, master.getWidth(), master.getHeight());
	}
}
