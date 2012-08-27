package com.petterroea.ld24;

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	private Clip clip;
	public Sound(InputStream in)
	{
		try{
			clip=AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(in));
		} catch(Exception e) {
			
		}
	}
	public void play()
	{
		try{
			new Thread(){
				@Override
				public void run()
				{
					synchronized(clip)
					{
						clip.stop();
						clip.setFramePosition(0);
						clip.start();
					}
				}
			}.start();
		} catch(Exception e) {
			
		}
	}

}
