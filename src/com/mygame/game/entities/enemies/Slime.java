package com.mygame.game.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygame.game.B2D.B2DSteeringEntity;
import com.mygame.game.handlers.Layer;
import com.mygame.game.main.TheLostWoods;

public class Slime extends Enemy{
	
	private final int[] numFrames = { 8, 4, 4, 4, 4, 15,15};
	
	public Slime(int health, String animName, B2DSteeringEntity entity, Layer layer) {
		super(entity.getBody(), entity, layer);
		
		this.body.setUserData(this);
		this.health = health;
		maxHealth = health;
		
		Texture tex = TheLostWoods.res.getTexture(animName);
		if(animName.substring(0, 5).equals("small")){
			TextureRegion[][] sprites = TextureRegion.split(tex, 16, 16);
			setAnimation(sprites, 0.3f);
		}else{
			TextureRegion[][] sprites = TextureRegion.split(tex, 32, 32);
			setAnimation(sprites, 0.3f);
		}
		
		animation.setNumFrames(numFrames);
		animation.setAnimation(0);
		
	}
	
	
	int umod = 0;
	int dmod = 0;
	int buffer = 15;
	public void update(float delta){
		super.update(delta);
		
		if(health>0){
			int angle = (int) Math.toDegrees(entity.getOrientation()) % 360;
			if(angle<0){
				angle = 360+angle;
			}
		
			if(entity.getAnyAccelerations()){
				animation.setDelay(0.3f);
				if(angle<45 + umod){
					//Up
					umod=buffer;
					animation.setAnimation(3);
				}else if(angle<135 + dmod){
					//Right
					umod=-buffer;
					dmod=buffer;
					animation.setAnimation(2);
				}else if(angle<225 + umod){
					//Down
					umod=buffer;
					dmod=-buffer;
					animation.setAnimation(4);
				} else if(angle<315 + dmod){
					//Left
					umod=-buffer;
					dmod=buffer;
					animation.setAnimation(1);
				}else{
					//Up
					dmod = -15;
					animation.setAnimation(3);
				}
			} else {
				animation.setDelay(0.1f);
				animation.setAnimation(5);
			}
		
		}else{
			super.dispose();
			animation.setDelay(0.1f);
			animation.setAnimation(6);
			if(animation.getTimesPlayed() >= 1){
				super.death();
			}
		}
	}
	
	public void attack(){
		//Slime jumps towards target
		
		
	}
	
	public void retreat(){
		//Slime runs from target
	}
	
	public void merge(){
		//Slime merges with another slime of same size
		
	}
	
}

