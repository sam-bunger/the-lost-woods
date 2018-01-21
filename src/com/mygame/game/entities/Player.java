package com.mygame.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.game.B2D.B2DVars;
import com.mygame.game.handlers.AttackManager;
import com.mygame.game.main.TheLostWoods;


public class Player extends GameObj {
	
	private Vector2 direction;
	private AttackManager am;
	private World world;
	
	// player attributes
	private int health;
	private int maxHealth;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
	
	//Movement//
	private float maxSpeed;
	
	//ACTIONS//
	private static final int WALKING_LEFT = 0;
	private static final int WALKING_RIGHT = 1;
	private static final int WALKING_UP = 2;
	private static final int WALKING_DOWN = 3;
	private static final int IDLE_LEFT = 4;
	private static final int IDLE_RIGHT = 5;
	private static final int IDLE_UP = 6;
	private static final int IDLE_DOWN = 7;
	
	private final int[] numFrames = { 8, 8, 8, 8, 1, 1, 1, 1 };
	
	private OrthographicCamera cam;
	
	
	public Player(Body body, OrthographicCamera cam, AttackManager am, World w){

		super(body);
		
		this.cam = cam;
		this.am = am;
		this.world = w;
		
		maxSpeed = 2.5f;
		
		previousAction = WALKING_DOWN;
		currentAction = WALKING_DOWN;
		Texture tex = TheLostWoods.res.getTexture("player");
		TextureRegion[][] sprites = TextureRegion.split(tex, 26, 50);
		
		setAnimation(sprites, 0.2f);
		animation.setNumFrames(numFrames);
		animation.setAnimation(currentAction);
		
		direction = new Vector2(0, 0);

	}


	public void handleInput() {
		
		direction.set(0, 0);
		
		if(Gdx.input.isKeyPressed(Keys.W)){
			direction.y = 1f;
			if(currentAction != WALKING_UP) {
				currentAction = WALKING_UP;
				previousAction = currentAction;
				animation.setAnimation(WALKING_UP);
				animation.setDelay(0.1f);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.A)){
			direction.x = -1f;
			if(currentAction != WALKING_LEFT) {
				currentAction = WALKING_LEFT;
				previousAction = currentAction;
				animation.setAnimation(WALKING_LEFT);
				animation.setDelay(0.1f);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.S)){
			direction.y = -1f;
			if(currentAction != WALKING_DOWN) {
				currentAction = WALKING_DOWN;
				previousAction = currentAction;
				animation.setAnimation(WALKING_DOWN);
				animation.setDelay(0.1f);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.D)){
			direction.x = 1f;
			if(currentAction != WALKING_RIGHT) {
				currentAction = WALKING_RIGHT;
				previousAction = currentAction;
				animation.setAnimation(WALKING_RIGHT);
				animation.setDelay(0.1f);
			}
		}
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			am.newAttack(new Attack(10, 1, world, body.getPosition().x, body.getPosition().y, 100, 100));
		}
		
		if(direction.x == 0 && direction.y == 0){
			if(previousAction == WALKING_UP){
				currentAction = IDLE_UP;
				animation.setAnimation(IDLE_UP);
				animation.setDelay(0.1f);
			}
			if(previousAction == WALKING_DOWN){
				currentAction = IDLE_DOWN;
				animation.setAnimation(IDLE_DOWN);
				animation.setDelay(0.1f);
			}
			if(previousAction == WALKING_RIGHT){
				currentAction = IDLE_RIGHT;
				animation.setAnimation(IDLE_RIGHT);
				animation.setDelay(0.1f);
			}
			if(previousAction == WALKING_LEFT){
				currentAction = IDLE_LEFT;
				animation.setAnimation(IDLE_LEFT);
				animation.setDelay(0.1f);
			}
		}
		
	}
	
	public void update(float delta){
		super.update(delta);
		vectorUpdate();
		body.setLinearVelocity(direction);
	}
	
	public void vectorUpdate(){
		if(direction.isZero()) return;
		float magnitude = 1 / direction.len();
		direction.set((magnitude * direction.x) * maxSpeed, (magnitude * direction.y) * maxSpeed);
	}
}

















