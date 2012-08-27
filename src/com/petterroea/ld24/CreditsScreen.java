package com.petterroea.ld24;

import java.awt.Color;
import java.awt.Graphics;

public class CreditsScreen extends Screen {

	public CreditsScreen(Game master) {
		super(master);
		// TODO Auto-generated constructor stub
		created = System.currentTimeMillis();
	}
	long created = 0;
	public void tick(int delta, Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, master.getScaledWidth(), master.getScaledHeight());
		Util.drawString("Credits:", 0, 0, g);
		Util.drawString("OPFR: Map design, code", 0, 20, g);
		Util.drawString("petterroea: code", 0, 32, g);
		if(System.currentTimeMillis()-created>10000)
		{
			master.currentScreen=new ScreenMainMenu(master);
		}
	}
}
