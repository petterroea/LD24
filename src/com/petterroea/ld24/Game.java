package com.petterroea.ld24;
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;



public class Game extends Applet implements Runnable{
	Thread gameThread;
	boolean running = true;
	Screen currentScreen;
	public static void main(String[] args)
	{
		JFrame window = new JFrame("LD24 - Evolution");
		if(!small)
		{
			window.setSize(TILES_W*16, TILES_H*16);
			System.out.println("Set size: " + (TILES_W*16) + "x" + (TILES_H*16));
		}
		else
		{
			window.setSize(TILES_W*8, TILES_H*8);
			System.out.println("Set size: " + (TILES_W*8) + "x" + (TILES_H*8));
		}
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game game = new Game();
		window.add(game);
		game.init();
		window.setVisible(true);
		game.start();
	}
	static final int TILES_W=64;
	static final int TILES_H=48;
	static boolean small = true;
	public int getScaledWidth()
	{
//		if(small)
//		return TILES_W*8;
		return TILES_W*4;
	}
	public int getScaledHeight()
	{
//		if(small)
//		return TILES_H*8;
		return TILES_H*4;
	}
	@Override
	public void init()
	{
		gameThread = new Thread(this);
		currentScreen = new GameScreen(this);
	}
	@Override
	public void start()
	{
		gameThread.start();
	}
	@Override
	public void stop()
	{
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	BufferedImage buffer;
	@Override
	public void run() {
		long lastUpdate = System.currentTimeMillis();
		int fps = 0;
		long lastFpsUpdate = System.currentTimeMillis();
		int frames = 0;
		while(running)
		{
			int delta = (int) (System.currentTimeMillis()-lastUpdate);
			lastUpdate = System.currentTimeMillis();
			if(System.currentTimeMillis()-1000>lastFpsUpdate)
			{
				lastFpsUpdate=System.currentTimeMillis();
				fps = frames;
				frames = 0;
				System.out.println(fps);
			}
			frames++;
			//The back buffer
			if(buffer==null||buffer.getWidth()!=this.getScaledWidth()||buffer.getHeight()!=this.getScaledHeight())
			{
				buffer = new BufferedImage(this.getScaledWidth(), this.getScaledHeight(), BufferedImage.TYPE_INT_RGB);
			}
			//Screen system
			Graphics g = buffer.getGraphics();
			currentScreen.tick(delta, g);
			//Draw to applet
			//this.getGraphics().drawImage(buffer, 0, 0, null);
			this.getGraphics().drawImage(buffer, 0, 0, this.getWidth(), this.getHeight(), null);
			g.dispose();
		}
	}
}
