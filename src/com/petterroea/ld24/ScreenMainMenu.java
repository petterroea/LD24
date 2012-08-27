package com.petterroea.ld24;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class ScreenMainMenu extends Screen {

	public ScreenMainMenu(Game master) {
		super(master);
		// TODO Auto-generated constructor stub
		created = System.currentTimeMillis();
	}
	long created = 0;
	public void tick(int delta, Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, master.getScaledWidth(), master.getScaledHeight());
		Util.drawString("NAME", (master.getScaledWidth()/2)-20, (master.getScaledHeight()/2)-40, g);
		Util.drawString("Enter to start", (master.getScaledWidth()/2)-(140/2), (master.getScaledHeight()/2)-10, g);
		if(Input.keys[KeyEvent.VK_ENTER])
		{
			master.currentScreen=new GameScreen(master);
			Input.keys[KeyEvent.VK_ENTER]=false;
		}
		if(System.currentTimeMillis()-created>10000)
		{
			master.currentScreen=new CreditsScreen(master);
		}
	}

}
