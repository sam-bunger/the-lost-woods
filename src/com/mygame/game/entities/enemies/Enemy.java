package com.mygame.game.entities.enemies;


import com.badlogic.gdx.physics.box2d.Body;
import com.mygame.game.B2D.B2DSteeringEntity;
import com.mygame.game.entities.GameObj;

public class Enemy extends GameObj{
	protected B2DSteeringEntity entity;

	public Enemy(Body body, B2DSteeringEntity entity) {
		super(body);
		
		this.entity = entity;
	}
	
	public void update(float delta){
		super.update(delta);
		entity.update(delta);
		
	}
	
	public void attack(){
		
	}
	
	public void dispose(){
		super.dispose();
	}

}