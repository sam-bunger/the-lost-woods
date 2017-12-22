package com.mygame.game.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.game.UI.UserInterface;
import com.mygame.game.handlers.*;
import com.mygame.game.states.*;

public class TheLostWoods extends ApplicationAdapter {
	
	//Game Window Basics
	public static final int WIDTH = 640;
	public static final int HEIGHT = 360;
	public static int SCALE = 3;
	public static final String TITLE = "The Lost Woods";
	
	//Game Delta Time Variables
	private static final float STEP = 1 / 60f;
	private float accum;
	
	//Game State Manager
	private GameStateManager gsm;
	
	//Graphics
	private SpriteBatch sb;
	private ShapeRenderer sr;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	
	private static UserInterface skt;
	protected Stage stage;
	
	public static Content res;
	
	public static BitmapFont font;
	
	private Viewport viewport;
	
	@SuppressWarnings("static-access")
	@Override
	public void create () {
		
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		res = new Content();
		res.loadTexture("Sprites/PlayerSprite.png", "player");
		
		//tree b
		res.loadTexture("Sprites/treeTrunkTest.png", "treeTrunk");
		res.loadTexture("Sprites/treeLeavesTest.png", "treeLeaves");
		res.loadTexture("Sprites/barrel.png", "barrel");
		
		//Forest
		res.loadTexture("Forest/grass.png", "grass");
		res.loadTexture("Forest/path_1.png", "path_1");
		res.loadTexture("Forest/path_2.png", "path_2");
		res.loadTexture("Forest/path_3.png", "path_3");
		
		sr = new ShapeRenderer();
		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, WIDTH, HEIGHT);
		
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, WIDTH, HEIGHT);
		
		viewport = new FitViewport(910, 540, cam);
		//viewport = new FitViewport(1920,1080,hudCam);
	    viewport.apply();

		stage = new Stage();
		
		gsm = new GameStateManager(this);
		
		skt = new UserInterface(stage, gsm);
		skt.create();
		
		try {
			gsm.push(new DungeonState(gsm));
			//gsm.push(new MenuState(gsm));
			//gsm.push(new LevelState(gsm));
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public void render () {
		
		accum += Gdx.graphics.getDeltaTime();
		while(accum >= STEP){
			
			Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			accum -= STEP;
			
			gsm.update(STEP);
			gsm.render();
			
			sb.setProjectionMatrix(hudCam.combined);
			
			sb.begin();
			font.draw(sb, ""+Gdx.graphics.getFramesPerSecond(), 10, 20);
			sb.end();
			
			stage.act();
	        stage.draw();
		}
		
	}
	
	public void dispose () {
		res.disposeTexture("player");
		stage.dispose();
	}
	
	
	public SpriteBatch getSpriteBatch() { return sb; }
	public ShapeRenderer getShapeRenderer() { return sr; }
	public OrthographicCamera getCamera() { return cam; }
	public OrthographicCamera getHUDCamera() { return hudCam; }

	public static UserInterface getUI() {
		return skt;
	}
	
}
