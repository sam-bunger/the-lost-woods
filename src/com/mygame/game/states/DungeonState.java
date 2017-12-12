package com.mygame.game.states;

import static com.mygame.game.B2D.B2DVars.BIT_PLAYER;
import static com.mygame.game.B2D.B2DVars.BIT_TELEPORTER;
import static com.mygame.game.B2D.B2DVars.BIT_WALL;
import static com.mygame.game.B2D.B2DVars.PPM;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygame.game.B2D.B2DShapeTools;
import com.mygame.game.UI.UserInterface;
import com.mygame.game.handlers.GameStateManager;
import com.mygame.game.main.TheLostWoods;
import com.mygame.game.dungeon.Dungeon;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class DungeonState extends LevelState {
	
	private Dungeon dungeon;
	private UserInterface ui;
	
	private RayHandler rayHandler;
	private PointLight playerLight;

	public DungeonState(GameStateManager gsm) throws IOException {
		super(gsm);
		
		
		dungeon = new Dungeon(world);
		dungeon.createCollision();
		
		ui = TheLostWoods.getUI();
		ui.changeToGame();
		
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(.5f);
		
		playerLight = new PointLight(rayHandler, 120, Color.GRAY, 2f, player.getPosition().x, player.getPosition().y + 5);
		playerLight.setSoftnessLength(0f);
		playerLight.attachToBody(playerBody,0,0);
		
	}
	

	public void handleInput(){
		super.handleInput();
		
		if(Gdx.input.isKeyPressed(Keys.M)){
			try {
				gsm.push(new LevelState(gsm));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void update(float delta) {
		
		handleInput();
		
		rayHandler.update();
		
		dungeon.setRooms(cam.position.x, cam.position.y);
		
		player.update(delta);
		
		follower1.update(delta);
		
		//dungeon.update(delta);
		
		setCamPosition();
		
		rayHandler.setCombinedMatrix(cam.combined.cpy().scl(PPM));
		
		world.step(delta, 6, 2);
	}

	public void render() {
		
		//Set Cameras to SpriteBatch
		sb.setProjectionMatrix(cam.combined);
		sr.setProjectionMatrix(cam.combined);
		
		dungeon.render(sb);
		
		follower1.renderAnim(sb);
		
		rayHandler.render();
		
		player.renderAnim(sb);
	
		
		//Render Box2D Camera
		b2dr.render(world, b2dCam.combined);
		
	}

	public void dispose() {
		rayHandler.dispose();
		super.dispose();
	}

}
