package com.mygame.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygame.game.B2D.B2DVars;
import com.mygame.game.handlers.Animation;
import com.mygame.game.main.TheLostWoods;

/**
 * 
 * Basic entity that exists in the game. Has three major components:
 * 
 * Box2d Body
 * Animation
 * AI steeringpath or pathfinding
 * 
 */
public class GameObj {
	
	//B2D Body
	protected float depth;
	protected Body body;
	
	protected float health;
	protected float maxHealth;
	protected HealthBar hb;
	
	protected Animation animation;
	protected float width;
	protected float height;
	
	public GameObj(Body body){
		this.body = body;
		depth = body.getPosition().y*B2DVars.PPM;
		animation = new Animation();
		hb = new HealthBar(20, 1);
		hb.setRange(0, 100);
		TheLostWoods.gameStage.addActor(hb);
	}
	
	public void setAnimation(TextureRegion[][] reg, float delay){
		animation.setFrames(reg, delay);
		width = reg[0][0].getRegionWidth();
		height = reg[0][0].getRegionHeight();
	}
	
	public void update(float delta){
		animation.update(delta);
		depth = body.getPosition().y*B2DVars.PPM;
		hb.setPosition(body.getPosition().x*B2DVars.PPM - hb.getWidth()/2, body.getPosition().y*B2DVars.PPM + 20);
		hb.setValue((health/maxHealth)*100);
	}
	
	public void renderAnim(SpriteBatch sb){
		//sb.begin();
		//sb.draw(animation.getFrame(), body.getPosition().x * B2DVars.PPM - width/2, body.getPosition().y * B2DVars.PPM - height/2);
		//sb.end();
	}
	
	public void dispose(){
		hb.remove();
	}
	
	public Body getBody(){ return body; }
	
	public Vector2 getPosition() { return body.getPosition(); }
	
	public void setPosition(Vector2 v){
		body.setTransform(v, 0f);
	}
	public void setPosition(float x, float y){
		body.setTransform(x, y, 0f);
	}
	public void setHealth(float h) {
		health = h;
	}
	
	public float getWidth(){ return width; }
	
	public float getHeight() { return height; }
	
	public float getHealth() { return health; }
	
	public float getDepth(){ return depth; }
	
	public Animation getAnimation(){ return animation; }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
