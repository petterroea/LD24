package com.petterroea.ld24;

import java.awt.Color;
import java.awt.Graphics;

public class EntitySmall extends EntityHostile {

	public EntitySmall(int x, int y) {
		super(x, y, 6, 6);
		// TODO Auto-generated constructor stub
		lastFire=System.currentTimeMillis();
	}
	long lastFire = System.currentTimeMillis();
	double hp = 1;
	int maxhp = 1;
	@Override
	public void tick(int delta, Level level)
	{
		if(System.currentTimeMillis()-lastFire>1200)
		{
			lastFire=System.currentTimeMillis();
			double xp = x-level.playerx;
			double yp = y-level.playery;
			float radiansToPlayer=(float) Math.atan2(-xp, -yp);
			double xvelocity=Math.sin(radiansToPlayer)/8;
			double yvelocity=Math.cos(radiansToPlayer)/8;
			level.entities.add(new EntityGoo((int)x-3, (int)y-3, xvelocity, yvelocity));
		}
		//super.tick(delta, level);
	}
	@Override
	public void damage(Level level)
	{
		hp--;
		if(hp <= 0)
		{
			dead=true;
			level.doKill();
		}
	}
	@Override
	public void render(Graphics g, int xoff, int yoff, Level level)
	{
		g.drawImage(enemies[1][0], (int)x-xoff, (int)y-yoff, null);
		
	}

}
