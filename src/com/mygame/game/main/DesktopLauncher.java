package com.mygame.game.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class DesktopLauncher {
	
	public static void main (String[] arg) {
		startUp();
	}
	
	public static void startUp(){
		
		//Settings.firstTimeSetUp();
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.vSyncEnabled = true;
		config.width = TheLostWoods.WIDTH * TheLostWoods.SCALE;
		config.height = TheLostWoods.HEIGHT * TheLostWoods.SCALE;
		config.title = TheLostWoods.TITLE;
		
		
		new LwjglApplication(new TheLostWoods(), config);
	}
	
}