package com.petterroea.ld24;

import java.awt.Graphics;

public class EntityGoo extends EntityBullet {

	public EntityGoo(int x, int y, double xspeed, double yspeed) {
		super(x, y, 5, 5, xspeed, yspeed);
		GRAVITY=0.00001d;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void render(Graphics g, int xoff, int yoff, Level level)
	{
		g.drawImage(particles[0][0], (int)x-xoff, (int)y-yoff, null);
	}
	@Override
	public void spawnDustThingie(Level level)
	{
		level.entities.add(new EntityParticle((int)x+2, (int)y+2, 3, 3, xspeed*level.rand.nextDouble(), yspeed*level.rand.nextDouble(), 1000, 1000, 3, GRAVITY));
	}

}
