package com.mygame.game.combat;

import java.util.Iterator;

import org.lwjgl.input.Mouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.mygame.game.B2D.B2DShapeTools;
import com.mygame.game.B2D.B2DVars;
import com.mygame.game.handlers.InteractionManager;

public class SwordAttack extends Attack{
	private Body playerBody;
	private RevoluteJointDef weaponDef;
	private RevoluteJoint weaponJoint;
	
	private World world;
	private InteractionManager im;

	public SwordAttack(Body playerBody, float damage, float attackTime, World world, float x, float y, float w, float h, InteractionManager im) {
		super(damage, attackTime, world, playerBody.getPosition().x*B2DVars.PPM +(.55f*B2DVars.PPM), playerBody.getPosition().y*B2DVars.PPM, w, h);
		this.world = world;
		this.playerBody = playerBody;
		this.im = im;
		
		double b = Gdx.input.getX() - Gdx.graphics.getWidth()/2;
		double c = Gdx.input.getY() - Gdx.graphics.getHeight()/2;
		double a = Math.hypot(b, c);
		
		double angle;
		if((Gdx.input.getY() - Gdx.graphics.getHeight()/2) < 0){
			angle = Math.PI - Math.acos((Math.pow(c, 2) - Math.pow(b, 2) - Math.pow(a, 2))/(2 * a * b));
		}else{
			angle = Math.PI + Math.acos((Math.pow(c, 2) - Math.pow(b, 2) - Math.pow(a, 2))/(2 * a * b));
		}
		//System.out.println("DeltaX = " + b + "   DeltaY = " + c + "   Hypotenuse = " + a + "   Angle = " + Math.toDegrees(angle));
		
		
		//Weapon Joint
	    weaponDef = new RevoluteJointDef();
	        weaponDef.bodyA = playerBody;
	        weaponDef.bodyB = hitBox;
	        weaponDef.collideConnected = false;
	        playerBody.setType(BodyType.StaticBody);
	        
	        
	        if(angle>Math.PI*1.5){
	        	hitBox.setTransform(hitBox.getPosition().set(playerBody.getPosition().x, playerBody.getPosition().y - .55f), (float)(Math.PI*1.5));
	        	weaponDef.referenceAngle = (float) (Math.PI*1.75);
	        }else if(angle>Math.PI){
	        	hitBox.setTransform(hitBox.getPosition().set(playerBody.getPosition().x - .55f, playerBody.getPosition().y), (float)(Math.PI));
	        	weaponDef.referenceAngle = (float) (Math.PI*1.25);
	        }else if(angle>Math.PI*0.5){
	        	hitBox.setTransform(hitBox.getPosition().set(playerBody.getPosition().x, playerBody.getPosition().y + .55f), (float)(Math.PI*0.5));
	        	weaponDef.referenceAngle = (float) (Math.PI*0.75);
	        }else{
	        	weaponDef.referenceAngle = (float) (Math.PI*0.25);
	        }

	        
	        weaponDef.localAnchorA.set(this.playerBody.getLocalCenter());
	        weaponDef.localAnchorB.set(this.hitBox.getLocalCenter().add(new Vector2(-0.55f, 0)));

	        weaponDef.enableMotor = true;
	        weaponDef.motorSpeed = 15;
	        weaponDef.maxMotorTorque = 20;

	        weaponDef.enableLimit = true;
	       
	        weaponDef.lowerAngle = (float) -(Math.PI*0.25);
        	weaponDef.upperAngle = (float) (Math.PI*0.25);
	        
	        
	    weaponJoint = (RevoluteJoint)world.createJoint(weaponDef);
    }
	
	public void update(float delta){
		if(weaponJoint.getJointAngle() >= weaponDef.upperAngle){
			world.destroyBody(hitBox);
			playerBody.setType(BodyType.DynamicBody);
			im.deleteAttack();
		}
	}
}
