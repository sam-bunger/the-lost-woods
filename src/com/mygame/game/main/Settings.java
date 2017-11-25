package com.mygame.game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings{
	public static int shadowLevel;
	public static String resolution;
	public static int width;
	public static int height;
	public static boolean isFullscreen;
	
	public static float mainVolume;
	public static float sfxVolume;
	public static float musicVolume;
	
	protected static Preferences prefs;
	
	public static void printSettings(){
		System.out.println("----------------------------");
		System.out.println("New options settings:\n");
		
		System.out.println("Main Volume:    " + Float.toString(mainVolume));
		System.out.println("SFX Volume:     " + Float.toString(sfxVolume));
		System.out.println("Music Volume:   " + Float.toString(musicVolume));
		
		System.out.println("Resolution:     " + resolution);
		
		System.out.println("Fullscreen:    " + isFullscreen);
		
		System.out.println("Shadow Level:    " + shadowLevel);
		System.out.println("----------------------------");
	}
	
	public static void firstTimeSetUp(){
		prefs = Gdx.app.getPreferences("com.mygame.game.settings"); //<--Change this to final name of game
		
		
		
		prefs.putFloat("mainVol", 100);
		prefs.putFloat("sfxVol", 100);
		prefs.putFloat("musicVol", 100);
		
		prefs.putString("res", "1920 x 1080");

		prefs.putBoolean("fullscreen", false);
		
		prefs.putInteger("shadeLvl", 0);
		
		prefs.flush();
	}
	
	public static void writeSettings(){
		prefs = Gdx.app.getPreferences("gameSettings"); //<--Change this to final name of game
		
		prefs.putFloat("mainVol", mainVolume);
		prefs.putFloat("sfxVol", sfxVolume);
		prefs.putFloat("musicVol", musicVolume);
		
		prefs.putString("res", resolution);

		prefs.putBoolean("fullscreen", isFullscreen);
		
		prefs.putInteger("shadeLvl", shadowLevel);
		
		prefs.flush();
	}
	
	public static void readSettings(){
		
		mainVolume = prefs.getFloat("mainVol");
		sfxVolume  = prefs.getFloat("sfxVol");
		musicVolume = prefs.getFloat("musicVol");
		
		resolution = prefs.getString("res");

		isFullscreen = prefs.getBoolean("fullscreen");
		
		shadowLevel = prefs.getInteger("shadeLvl");
		
	}
	
	
}
