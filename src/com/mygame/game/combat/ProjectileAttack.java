package com.mygame.game.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.game.B2D.B2DVars;
import com.mygame.game.handlers.InteractionManager;

public class ProjectileAttack extends Attack{
	private Body playerBody;
	private World world;
	private InteractionManager im;
	
	private Vector2 velocity;

	public ProjectileAttack(Body playerBody, float damage, float attackTime, World world, float w, float h, InteractionManager im) {
		super(damage, attackTime, world, playerBody.getPosition().x*B2DVars.PPM, playerBody.getPosition().y*B2DVars.PPM, w, h);
		this.world = world;
		this.playerBody = playerBody;
		this.im = im;
		

		Vector2 target = new Vector2(Gdx.input.getX(),Gdx.input.getY());
		Vector2 pos = new Vector2(playerBody.getPosition().x*B2DVars.PPM,playerBody.getPosition().y*B2DVars.PPM);
		velocity = new Vector2(Gdx.input.getX() - Gdx.graphics.getWidth()/2, Gdx.input.getY() - Gdx.graphics.getHeight()/2);
		
		hitBox.applyForceToCenter(velocity, false);
		
		double b = Gdx.input.getX() - Gdx.graphics.getWidth()/2;
		double c = Gdx.input.getY() - Gdx.graphics.getHeight()/2;
		double a = Math.hypot(b, c);
		
		double angle;
		if((Gdx.input.getY() - Gdx.graphics.getHeight()/2) < 0){
			angle = Math.PI - Math.acos((Math.pow(c, 2) - Math.pow(b, 2) - Math.pow(a, 2))/(2 * a * b));
		}else{
			angle = Math.PI + Math.acos((Math.pow(c, 2) - Math.pow(b, 2) - Math.pow(a, 2))/(2 * a * b));
		}
		
		hitBox.setTransform(0, 0, (float) angle);
		
	}

}
