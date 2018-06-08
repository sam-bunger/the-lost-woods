package com.mygame.game.states;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygame.game.B2D.B2DShapeTools;
import com.mygame.game.B2D.B2DSteeringEntity;
import com.mygame.game.UI.UserInterface;
import com.mygame.game.UI.skins.elements.gameui.GameUIOrg;
import com.mygame.game.entities.DayNightCycle;
import com.mygame.game.entities.Follower;
import com.mygame.game.entities.Tree;
import com.mygame.game.handlers.GameStateManager;
import com.mygame.game.main.TheLostWoods;
import com.mygame.game.rng.Pathway;
import com.mygame.game.rng.RNTree;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class LevelState extends GameState {
	
	private RNTree forest1;
	private Pathway pathNorth;
	
	private Tree tree;
	private UserInterface ui;
	private DayNightCycle sun;
	
	private RayHandler rayHandler;
	private PointLight sunLight;
	
	//AI Test
	private B2DSteeringEntity entity1;
	private Follower follower1;
	
	public LevelState(GameStateManager gsm) throws IOException {
		super(gsm);
		
		ui = TheLostWoods.getUI();
		ui.changeToGame();
		
		//create paths
		pathNorth = new Pathway((WIDTH/2), (HEIGHT/2), (float)Math.toRadians(90), cam);
		
		B2DSteeringEntity target = new B2DSteeringEntity(playerBody);
		
		//forest1 = new RNTree(world);
		//forest1.genTreeSquare(100, new Vector2(0,0), new Vector2(1000, 1000));
		
		
		//sun = new DayNightCycle(world, cam, playerBody, 600, 1800, 0.5);
		
	
		entity1 = new B2DSteeringEntity(world, .1f, target);
		
		//BODY Types
		/*
		 * static body - doesn't move, unaffected by forces
		 * kinematic body - doesn't get affected by forces, but can move
		 * dynamic body - affected by forces (eg. player, enemy)
		 */
	}

	public void handleInput() {
		super.handleInput();
		GameUIOrg.handleInput();
		
		if(Gdx.input.isKeyPressed(Keys.M)){
			try {
				gsm.push(new DungeonState(gsm));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			isPaused = true;
		}
		
	}

	public void update(float delta) {
		super.update(delta);
		handleInput();
		
		//sun.update();
		
		pathNorth.update(delta);
	
		//sun.updateCam();
		
		follower1.update(delta);
		
	}

	public void render() {
		super.render();
		pathNorth.render(sb, sr);
		
		//forest1.renderTrunks(sb);
		
		follower1.renderAnim(sb);
		
		//forest1.render(sb);
		
		player.renderAnim(sb);

		//sun.render();
		
		//sb.begin();
		//TheLostWoods.font.draw(sb, ""+sun.getCurrentTime(), 100, 100);
		//sb.end();
		
		//Render Box2D Camera
		b2dr.render(world, b2dCam.combined);
		
		
	}

	public void dispose() {
		super.dispose();
		//sun.dispose();
	}
}
