package com.mygame.game.handlers.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mygame.game.main.Settings;

public class SoundHandler {
	private Sound sound;
	public SoundHandler(){
		sound = Gdx.audio.newSound(Gdx.files.internal("data/mysound.mp3"));
		
		long id = sound.play(1.0f); // play new sound and keep handle for further manipulation
		sound.stop(id);             // stops the sound instance immediately
		sound.setPitch(id, 2);      // increases the pitch to 2x the original pitch

		id = sound.play(1.0f);      // plays the sound a second time, this is treated as a different instance
		sound.setPan(id, -1, 1);    // sets the pan of the sound to the left side at full volume
		sound.setLooping(id, true); // keeps the sound looping
		sound.stop(id);             // stops the looping sound 
	}
	
	public void play(){
		sound.play(Settings.sfxVolume * Settings.mainVolume);
	}
	
	public void dispose(){
		sound.dispose();
	}
}
