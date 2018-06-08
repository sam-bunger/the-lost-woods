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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygame.game.B2D.B2DShapeTools;
import com.mygame.game.UI.UserInterface;
import com.mygame.game.handlers.GameStateManager;
import com.mygame.game.main.TheLostWoods;
import com.mygame.game.dungeon.Dungeon;
import com.mygame.game.entities.Player;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class DungeonState extends GameState {
	
	private Dungeon dungeon;
	private UserInterface ui;
	
	private RayHandler rayHandler;
	private PointLight playerLight;
	private Vector2 playerSpawn;

	public DungeonState(GameStateManager gsm, Stage stage) throws IOException {
		super(gsm, stage);
		
		dungeon = new Dungeon(world);
		
		ui = TheLostWoods.getUI();
		ui.changeToGame();
		
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(0.4f);
		
		playerLight = new PointLight(rayHandler, 120, Color.GRAY, 2f, player.getPosition().x, player.getPosition().y + 5);
		playerLight.setSoftnessLength(0f);
		playerLight.attachToBody(playerBody,0,0);
		
		
	}
	

	public void handleInput(){
		super.handleInput();
		
		if(Gdx.input.isKeyPressed(Keys.M)){
			try {
				gsm.push(new LevelState(gsm, stage));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void update(float delta) {
		super.update(delta);
		
		handleInput();
		
		rayHandler.update();
		
		dungeon.setRooms(cam.position.x, cam.position.y);
		
		rayHandler.setCombinedMatrix(cam.combined.cpy().scl(PPM));
		
		world.step(delta, 6, 2);
	}

	public void render() {
		super.render();

		dungeon.render(sb);
		
		rayHandler.render();
		
		player.renderAnim(sb);
		
		dungeon.renderTop(sb);
		
		//Render Box2D Camera
		b2dr.render(world, b2dCam.combined);
		
	}

	public void dispose() {
		super.dispose();
		rayHandler.dispose();
	}

}
