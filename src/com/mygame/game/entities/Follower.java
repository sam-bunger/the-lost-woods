package com.mygame.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygame.game.B2D.B2DSteeringEntity;
import com.mygame.game.main.TheLostWoods;

public class Follower extends GameObj{
	
	private final int[] numFrames = { 8, 8, 8, 8, 1, 1, 1, 1};
	private B2DSteeringEntity entity;
	

	public Follower(Body body, String animName, B2DSteeringEntity entity) {
		super(body);
		
		this.entity = entity;
		
		health = 25;
		maxHealth = 25;
		
		currentAction = 3;
		previousAction = 3;
		
		Texture tex = TheLostWoods.res.getTexture(animName);
		TextureRegion[][] sprites = TextureRegion.split(tex, 26, 50);
		
		setAnimation(sprites, 0.2f);
		animation.setNumFrames(numFrames);
		animation.setAnimation(currentAction);
		
	}
	
	int umod = 0;
	int dmod = 0;
	int buffer = 15;
	public void update(float delta){
		super.update(delta);
		entity.update(delta);
		
		int angle = (int) Math.toDegrees(entity.getOrientation()) % 360;
		if(angle<0){
			angle = 360+angle;
		}
		
		
		if(entity.getAnyAccelerations()){
			if(angle<45 + umod){
				//Up
				umod=buffer;
				currentAction = 2;
				animation.setAnimation(2);
				animation.setDelay(0.1f);
			}else if(angle<135 + dmod){
				//Right
				umod=-buffer;
				dmod=buffer;
				currentAction = 0;
				animation.setAnimation(0);
				animation.setDelay(0.1f);
			}else if(angle<225 + umod){
				//Down
				umod=buffer;
				dmod=-buffer;
				currentAction = 3;
				animation.setAnimation(3);
				animation.setDelay(0.1f);
			} else if(angle<315 + dmod){
				//Left
				umod=-buffer;
				dmod=buffer;
				currentAction = 1;
				animation.setAnimation(1);
				animation.setDelay(0.1f);
			}else{
				//Up
				dmod = -15;
				currentAction = 2;
				animation.setAnimation(2);
				animation.setDelay(0.1f);
			}
		} else {
			if(angle<45 + umod){
				//Up
				umod=buffer;
				currentAction = 6;
				animation.setAnimation(6);
				animation.setDelay(0.1f);
			}else if(angle<135 + dmod){
				//Right
				umod=-buffer;
				dmod=buffer;
				currentAction = 4;
				animation.setAnimation(4);
				animation.setDelay(0.1f);
			}else if(angle<225 + umod){
				//Down
				umod=buffer;
				dmod=-buffer;
				currentAction = 7;
				animation.setAnimation(7);
				animation.setDelay(0.1f);
			} else if(angle<315 + dmod){
				//Left
				umod=-buffer;
				dmod=buffer;
				currentAction = 5;
				animation.setAnimation(5);
				animation.setDelay(0.1f);
			}else{
				//Up
				dmod = -15;
				currentAction = 6;
				animation.setAnimation(6);
				animation.setDelay(0.1f);
			}
		}
	}
	

}
