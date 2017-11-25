package com.mygame.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygame.game.B2D.B2DVars;
import com.mygame.game.handlers.Animation;

/**
 * 
 * Basic entity that exists in the game. Has three major components:
 * 
 * Box2d Body
 * Animation
 * AI steeringpath or pathfinding
 * 
 *
 */
public class GameObj {
	
	//B2D Body
	protected Body body;
	
	protected Animation animation;
	protected float width;
	protected float height;
	protected int currentAction;
	protected int previousAction;
	
	public GameObj(Body body){
		this.body = body;
		animation = new Animation();
	}
	
	public void setAnimation(TextureRegion[][] reg, float delay){
		animation.setFrames(reg, delay);
		width = reg[0][0].getRegionWidth();
		height = reg[0][0].getRegionHeight();
	}
	
	public void update(float delta){
		animation.update(delta);
	}
	
	public void renderAnim(SpriteBatch sb){
		sb.begin();
		sb.draw(animation.getFrame(), body.getPosition().x * B2DVars.PPM - width/2, body.getPosition().y * B2DVars.PPM - height/2);
		sb.end();
	}
	
	public Body getBody(){ return body; }
	
	public Vector2 getPosition() { return body.getPosition(); }
	
	public void setPosition(Vector2 v){
		body.setTransform(v, 0f);
	}
	public void setPosition(float x, float y){
		body.setTransform(x, y, 0f);
	}
	public float getWidth(){ return width; }
	
	public float getHeight() { return height; }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
