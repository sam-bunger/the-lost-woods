package com.mygame.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygame.game.B2D.B2DVars;
import com.mygame.game.main.TheLostWoods;

public class Tree extends GameObj {
	
	private final int[] numFrames = { 1 };
	private Vector2 pos;
	
	private TextureRegion[][] leafSprite;
	private Texture trunkSprite;
	
	public Tree(Body body, String trunk, String leaves) {
		super(body);
		
		trunkSprite = TheLostWoods.res.getTexture(trunk);
		leafSprite = TextureRegion.split(TheLostWoods.res.getTexture(leaves), 133, 132);
		
		
		setAnimation(leafSprite, 0.1f);
		animation.setNumFrames(numFrames);
		animation.setAnimation(currentAction);
	}
	public void print(){System.out.println(body.getPosition().x+" "+body.getPosition().y);}
	public Texture getTrunkImg(){return trunkSprite;}
	
	public void renderTrunk(SpriteBatch sb){
		sb.begin();
		sb.draw(trunkSprite, body.getPosition().x * B2DVars.PPM - width/2, body.getPosition().y * B2DVars.PPM - height/2);
		sb.end();
	}
}
