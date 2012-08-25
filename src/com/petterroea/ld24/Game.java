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
		window.setSize(800, 600);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game game = new Game();
		window.add(game);
		game.init();
		window.setVisible(true);
		game.start();
	}
	@Override
	public void init()
	{
		gameThread = new Thread(this);
		currentScreen = new Screen(this);
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
			if(buffer==null||buffer.getWidth()!=this.getWidth()||buffer.getHeight()!=this.getHeight())
			{
				buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
			}
			//Screen system
			Graphics g = buffer.getGraphics();
			currentScreen.tick(delta, g);
			//Draw to applet
			this.getGraphics().drawImage(buffer, 0, 0, null);
			g.dispose();
		}
	}
}
