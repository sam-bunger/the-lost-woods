package com.mygame.game.combat;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.game.B2D.B2DShapeTools;

public class Attack {
	protected Body hitBox;
	private float damage;
	private float attackTime;
	private World world;
	
	public Attack(float damage, float attackTime, World world, float x, float y, float w, float h){
		this.damage = damage;
		this.attackTime = attackTime;
		this.world = world;
		hitBox = B2DShapeTools.createBox(world,x,y,w,h,false,false,true);
		hitBox.setUserData(this);
	}
	
	public void update(float delta) {}
	
	public void destroyAttack(){
		world.destroyBody(hitBox);
	}
	
	public float getDamage(){
		return damage;
	}
	
	public float getTime(){
		return attackTime;
	}

}
