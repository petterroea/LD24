package com.petterroea.ld24;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener, FocusListener{
	Game game;
	public static boolean[] keys;
	public static boolean focus = true;
	public Input(Game game)
	{
		this.game = game;
		keys = new boolean[600];
		for(int i = 0; i < keys.length; i++)
		{
			keys[i]=false;
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode()<600)
			keys[arg0.getKeyCode()]=true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyCode()<600)
			keys[arg0.getKeyCode()]=false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// Not used
		
	}
	@Override
	public void focusGained(FocusEvent arg0) {
		focus=true;
		
	}
	@Override
	public void focusLost(FocusEvent arg0) {
		focus=false;
		
	}

}
