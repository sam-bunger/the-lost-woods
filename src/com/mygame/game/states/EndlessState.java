package com.mygame.game.states;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygame.game.B2D.B2DShapeTools;
import com.mygame.game.B2D.B2DSteeringEntity;
import com.mygame.game.UI.UserInterface;
import com.mygame.game.entities.enemies.Enemy;
import com.mygame.game.entities.enemies.Slime;
import com.mygame.game.handlers.GameStateManager;
import com.mygame.game.main.TheLostWoods;

public class EndlessState extends GameState{
	private ArrayList<Enemy> enemies;
	private UserInterface ui;
	
	private int round = 1;
	private int difficulty = 3; //1 - easy, 2 - normal, 3 - hard
	private int remainingEnemies;
	private int spawnDelay;
	private int time;
	private B2DSteeringEntity target;

	public EndlessState(GameStateManager gsm) {
		super(gsm);
		
		ui = TheLostWoods.getUI();
		ui.changeToGame();
		
		B2DShapeTools.createBox(world, -300,0, 10, 200, true, false, false);
		B2DShapeTools.createBox(world, 300,0, 10, 200, true, false, false);
		B2DShapeTools.createBox(world, 0,200, 300, 10, true, false, false);
		B2DShapeTools.createBox(world, 0,-200, 300, 10, true, false, false);
		
		target = new B2DSteeringEntity(playerBody);
		
		remainingEnemies = (int) Math.pow(round,difficulty)+3;
		spawnDelay = 100/difficulty;
		time = 0;
		enemies = new ArrayList<Enemy>();
		
	}
	
	public void update(float delta) {
		super.update(delta);
		handleInput();
		
		for(int i=0;i<enemies.size();i++){
			enemies.get(i).update(delta);
			if(enemies.get(i).getHealth()<=0){
				enemies.get(i).dispose();
				layer.remove(enemies.get(i));
				enemies.remove(i);
				remainingEnemies--;
			}
		}
		
		if(time == spawnDelay){
			int a = MathUtils.random(0,1);
			if(a==0){
				Body body1 = B2DShapeTools.createCircle(world,MathUtils.random(-250,250),MathUtils.random(-150,150),8,false,false,false);
				Slime enemy = new Slime(20, body1, "blueSlime", new B2DSteeringEntity(body1, .1f, target));
				enemies.add(enemy);
				body1.setUserData(enemy);
				layer.add(enemy);
			}else{
				Body body1 = B2DShapeTools.createCircle(world,MathUtils.random(-250,250),MathUtils.random(-150,150),5,false,false,false);
				Slime enemy = new Slime(10, body1, "smallBlueSlime", new B2DSteeringEntity(body1, .1f, target));
				enemies.add(enemy);
				body1.setUserData(enemy);
				layer.add(enemy);
			}
			time=0;
		}else{
			time++;
		}
		
		if(remainingEnemies<=0){
			for(int i=0;i<enemies.size();i++){
				enemies.get(i).setHealth(0);
				enemies.get(i).dispose();
				layer.remove(enemies.get(i));
			}
			enemies.removeAll(enemies);
			remainingEnemies= (int) Math.pow(round,difficulty)+3;
			round++;
		}
		
		
	}
	
	
	public void render() {
		
		
		//Grass
		int currCol = (int) ((cam.position.x + (TheLostWoods.WIDTH))/TheLostWoods.WIDTH) - 1;
		int currRow = (int) ((cam.position.y + (TheLostWoods.HEIGHT))/TheLostWoods.HEIGHT) - 1;
		
		for(int row = (currRow - 2); row <= (currRow + 1); row++) {
			
			for(int col = (currCol - 2); col <= (currCol + 1); col++) {
				
				sb.begin();
				sb.draw(TheLostWoods.res.getTexture("grass"), col * TheLostWoods.WIDTH, row * TheLostWoods.HEIGHT);
				sb.end();
			}
		}
		
		super.render();
		
		sb.begin();
		TheLostWoods.font.draw(sb, "Round: " + round, 100, 100);
		TheLostWoods.font.draw(sb, "Enemies Remaining: " + remainingEnemies, 100, 80);
		sb.end();
		
		//Render Box2D Camera
		//b2dr.render(world, b2dCam.combined);
		
		
		
	}
	
	public void dispose() {
		super.dispose();
	}

}
