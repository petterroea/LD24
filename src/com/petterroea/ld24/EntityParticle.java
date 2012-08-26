package com.petterroea.ld24;

import java.awt.Graphics;
import java.util.Random;

public class EntityParticle extends Entity {
	long created;
	int lifetime;
	int particle=0;
	public EntityParticle(int x, int y, int w, int h, double xspeed, double yspeed, int lifetime, int randLife, int particle, double gravity) {
		super(x, y, w, h);
		this.xspeed=xspeed;
		this.yspeed=yspeed;
		this.GRAVITY=gravity;
		Random rand = new Random();
		created=System.currentTimeMillis();
		this.lifetime=lifetime+(rand.nextInt(randLife));
		this.particle=particle;
		// TODO Auto-generated constructor stub
	}
	public void tick(int delta, Level level)
	{
		if(System.currentTimeMillis()-created>lifetime)
		{
			dead=true;
		}
		super.tick(delta, level);
	}
	public void render(Graphics g, int xoff, int yoff, Level level)
	{
		int partX=particle%10;
		int partY=particle/10;
		g.drawImage(particles[partX][partY], (int)x-xoff, (int)y-yoff, null);
	}

}
