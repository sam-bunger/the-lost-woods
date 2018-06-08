package com.mygame.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.game.handlers.GameStateManager;
import com.mygame.game.main.TheLostWoods;

public abstract class State {
	
	protected TheLostWoods game;
	protected GameStateManager gsm;
	protected SpriteBatch sb;
	protected ShapeRenderer sr;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;
	protected static boolean isPaused;
	protected static Stage stage;
	
	protected int SCALE = TheLostWoods.SCALE;
	protected int WIDTH = TheLostWoods.WIDTH;
	protected int HEIGHT = TheLostWoods.HEIGHT;
	
	protected State(GameStateManager gsm, Stage stage){
		this.gsm = gsm;
		this.stage = stage;
		game = gsm.game();
		sb = game.getSpriteBatch();
		sr = game.getShapeRenderer();
		cam = game.getCamera();
		hudCam = game.getHUDCamera();
		isPaused = false;
	}
	
	protected abstract void handleInput();
	public abstract void update(float delta);
	public abstract void render();
	public abstract void dispose();
	
	public static void setPause(boolean b){
		isPaused = b;
	}
	
	public static boolean getPause(){
		return isPaused;
	}

}
