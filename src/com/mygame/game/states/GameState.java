package com.mygame.game.states;

import static com.mygame.game.B2D.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.game.B2D.B2DShapeTools;
import com.mygame.game.entities.Player;
import com.mygame.game.handlers.AttackManager;
import com.mygame.game.handlers.GameStateManager;
import com.mygame.game.main.TheLostWoods;

public class GameState extends State {	
	protected World world;
	protected Box2DDebugRenderer b2dr;
	protected OrthographicCamera b2dCam;
	
	protected Player player;
	protected Body playerBody;
	
	protected AttackManager am;

	public GameState(GameStateManager gsm) {
		super(gsm);
		world = new World(new Vector2(0, 0), true);
		b2dr = new Box2DDebugRenderer();
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, WIDTH / PPM, HEIGHT / PPM);
		
		//am = new AttackManager();
		//am.createContactListener(world);
		
		//Create player
		playerBody = B2DShapeTools.createBox(world, 0, 0, 12, 12, false, true, false);
		player = new Player(playerBody, cam, am, world);
		
		
	}

	@Override
	protected void handleInput() {
		
		//Zoom in
		if(Gdx.input.isKeyPressed(Keys.EQUALS)){
			if(cam.zoom >= 0.5f){
				cam.zoom -=.01f;
				b2dCam.zoom -=.01f;
			}
		}
		
		//Zoom out
		if(Gdx.input.isKeyPressed(Keys.MINUS)){
			if(cam.zoom <= 1.5f){
				cam.zoom +=.01f;
				b2dCam.zoom +=.01f;
			}
		}
		
	}

	@Override
	public void update(float delta) {
		handleInput();
		player.update(delta);
		player.handleInput();
		
		b2dCam.position.set(b2dCam.position.x + (player.getPosition().x - b2dCam.position.x) * 0.15f, b2dCam.position.y + (player.getPosition().y - b2dCam.position.y) * 0.15f, 0);
		b2dCam.update();
		cam.position.set(cam.position.x + (player.getPosition().x * PPM - cam.position.x) * 0.15f, cam.position.y + (player.getPosition().y * PPM - cam.position.y) * 0.15f, 0);
		cam.update();
		
	}

	@Override
	public void render() {
		sb.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);
		
	}

	@Override
	public void dispose() {
		b2dr.dispose();
		world.dispose();
		
	}
	
}
