package com.petterroea.ld24;

import java.awt.Graphics;

public class EntitySlime extends Entity {

	public EntitySlime(int x, int y) {
		super(x, y, 16, 16);
		// TODO Auto-generated constructor stub
	}
	long lastFire = System.currentTimeMillis();
	@Override
	public void tick(int delta, Level level)
	{
		if(System.currentTimeMillis()-lastFire>1000)
		{
			lastFire=System.currentTimeMillis();
			double xp = x-level.playerx;
			double yp = y-level.playery;
			float radiansToPlayer=(float) Math.atan2(-xp, -yp);
			double xvelocity=Math.sin(radiansToPlayer)/8;
			double yvelocity=Math.cos(radiansToPlayer)/8;
			level.entities.add(new EntityGoo((int)x+8, (int)y+8, xvelocity, yvelocity));
		}
		super.tick(delta, level);
	}
	@Override
	public void render(Graphics g, int xoff, int yoff, Level level)
	{
		g.drawImage(enemies[0][0], (int)x-xoff, (int)y-yoff, null);
	}

}
