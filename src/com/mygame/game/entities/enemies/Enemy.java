package com.mygame.game.entities.enemies;


import com.badlogic.gdx.physics.box2d.Body;
import com.mygame.game.B2D.B2DSteeringEntity;
import com.mygame.game.entities.GameObj;
import com.mygame.game.handlers.Layer;

public class Enemy extends GameObj{
	protected B2DSteeringEntity entity;
	protected Layer layer;

	public Enemy(Body body, B2DSteeringEntity entity, Layer layer) {
		super(body);
		
		this.layer = layer;
		this.entity = entity;
		
		layer.add(this);
	}
	
	public void update(float delta){
		super.update(delta);
		entity.update(delta);
		
	}
	
	public void attack(){
		
	}
	
	public void death(){
		layer.remove(this);
	}
	
	public void dispose(){
		super.dispose();
	}

}