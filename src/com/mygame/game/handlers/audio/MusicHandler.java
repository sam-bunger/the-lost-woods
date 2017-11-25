package com.mygame.game.handlers.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygame.game.main.Settings;

public class MusicHandler {
	private Music music;
	
	public MusicHandler(String songName){
		
		music = Gdx.audio.newMusic(Gdx.files.internal("data/" + songName));
		
		music.setVolume(Settings.musicVolume * Settings.mainVolume);
		music.setLooping(true);                // will repeat playback until music.stop() is called
		//music.stop();
		//music.pause();
		music.play();                          // resumes the playback
		boolean isPlaying = music.isPlaying();
		boolean isLooping = music.isLooping();
		float position = music.getPosition();  // returns the playback position in seconds
	}
	
	public void dispose(){
		music.dispose();
	}
	
}
