package com.petterroea.ld24;

public class EntityEmitter extends Entity {

	int amount = 100, particle=0;
	double xMomentum=0.0, yMomentum=0.0;
	public EntityEmitter(int x, int y, double xmomentum, double ymomentum, int amount, int particle) {
		super(x, y, 8, 8);
		this.xMomentum=xmomentum;
		this.yMomentum=ymomentum;
		this.particle=particle;
		
		// TODO Auto-generated constructor stub
	}
	@Override
	public void tick(int delta, Level level)
	{
		if(level.rand.nextInt(amount/(delta+1))==0)
		{
			level.entities.add(new EntityParticle((int)x+4, (int)y+4, 3, 3, this.xMomentum*level.rand.nextDouble(), this.yMomentum*level.rand.nextDouble(), 1000, 1000, particle, GRAVITY));
		}
	}

}
