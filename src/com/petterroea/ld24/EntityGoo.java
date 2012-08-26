package com.petterroea.ld24;

import java.awt.Graphics;
import java.awt.Rectangle;

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
	public void doDamageCheck(Level level)
	{
		Rectangle xrect = new Rectangle((int)x, (int)y, w, h);//Me
		for(int i = 0; i < level.entities.size(); i++)
		{
			Rectangle tempBox = new Rectangle((int)level.entities.get(i).x, (int)level.entities.get(i).y, (int)level.entities.get(i).w, (int)level.entities.get(i).h);
			if(tempBox.intersects(xrect))
			{
				if(level.entities.get(i) instanceof EntityPlayer)
				{
					level.entities.get(i).damage(level);
					dead=true;
				}
			}
		}
	}
	@Override
	public void tick(int delta, Level level)
	{
		super.tick(delta, level);
	}
	@Override
	public void spawnDustThingie(Level level)
	{
		level.entities.add(new EntityParticle((int)x+2, (int)y+2, 3, 3, xspeed*level.rand.nextDouble(), yspeed*level.rand.nextDouble(), 1000, 1000, 3, GRAVITY));
	}

}
