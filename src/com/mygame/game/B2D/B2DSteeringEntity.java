package com.mygame.game.B2D;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Face;
import com.badlogic.gdx.ai.steer.behaviors.LookWhereYouAreGoing;
import com.badlogic.gdx.ai.steer.behaviors.ReachOrientation;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class B2DSteeringEntity implements Steerable<Vector2>{
	
	private Body body;
	private boolean tagged;
	private float boundingRadius;
	private float maxLinearSpeed, maxLinearAcceleration;
	private float maxAngularSpeed, maxAngularAcceleration;
	
	private SteeringBehavior<Vector2> behavior;
	private SteeringAcceleration<Vector2> steeringOutput;
	
	private boolean anyAccelerations;
	
	private Face face;
	private B2DSteeringEntity target;
	
	private Arrive<Vector2> arrive;
	
	
	//Target
	public B2DSteeringEntity(Body body){
		this.body = body;
		
		this.tagged = false;
		
		this.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
		this.body.setUserData(this);
	
	}
	
	//AI Entity
	//Make this more customizable
	public B2DSteeringEntity(World world, float boundingRadius, B2DSteeringEntity target){
		this.body = B2DShapeTools.createCircle(world,MathUtils.random(-250,250),MathUtils.random(-150,150),5,false,false,false);
		this.boundingRadius = boundingRadius;
		this.target = target;
		
		this.maxLinearSpeed = 100;
		this.maxLinearAcceleration = 1000;
		this.maxAngularSpeed = 6;
		this.maxAngularAcceleration = 1;
		
		this.tagged = false;
		
		this.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
		this.body.setUserData(this);
		
		arrive = new Arrive<Vector2>(this, target)
				.setTimeToTarget(0.1f)
				.setArrivalTolerance(.5f)
				.setDecelerationRadius(80);
		this.setBehavior(arrive);
		
		
		face = new Face(this);
		face.setTarget(target);
				
	}
	
	public void attack(){
		
	}
	
	
	
	@Override
	public Vector2 angleToVector(Vector2 arg0, float arg1) {
		// TODO Auto-generated method stub
		arg0.x = -(float)Math.sin(arg1);
		arg0.y = (float)Math.cos(arg1);
		return arg0;
	}
	
	public void update(float delta){
		if(behavior != null){
			behavior.calculateSteering(steeringOutput);
			applySteering(delta);
		}
	}
	
	public void applySteering(float delta){
		anyAccelerations = false;
		
		if(!steeringOutput.linear.isZero()){
			Vector2 force = steeringOutput.linear.scl(delta);
			body.applyForceToCenter(force, true);
			anyAccelerations = true;
		}
		
		face.calculateSteering(steeringOutput);
		face.setAlignTolerance(0f);
		
		
		if(steeringOutput.angular != 0){
			body.applyTorque(steeringOutput.angular * delta, true);
			//anyAccelerations = true;
		}
		
		if(anyAccelerations){
			Vector2 velocity = body.getLinearVelocity();
			float currentSpeedSquare = velocity.len2();
			if(currentSpeedSquare > maxLinearSpeed * maxLinearSpeed){
				body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float)Math.sqrt(currentSpeedSquare)));
			}
			
			if(body.getAngularVelocity() > maxAngularSpeed){
				body.setAngularVelocity(maxAngularSpeed);
			}
		}
		
	}
	

	@Override
	public float getOrientation() {
		// TODO Auto-generated method stub
		return body.getAngle();
	}
	
	public boolean getAnyAccelerations(){
		return anyAccelerations;
	}

	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return body.getPosition();
	}

	@Override
	public Location<Vector2> newLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOrientation(float arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float vectorToAngle(Vector2 arg0) {
		// TODO Auto-generated method stub
		return (float)Math.atan2(-arg0.x, arg0.y);
	}

	@Override
	public float getMaxAngularAcceleration() {
		// TODO Auto-generated method stub
		return maxAngularAcceleration;
	}

	@Override
	public float getMaxAngularSpeed() {
		// TODO Auto-generated method stub
		return maxAngularSpeed;
	}

	@Override
	public float getMaxLinearAcceleration() {
		// TODO Auto-generated method stub
		return maxLinearAcceleration;
	}

	@Override
	public float getMaxLinearSpeed() {
		// TODO Auto-generated method stub
		return maxLinearSpeed;
	}

	@Override
	public float getZeroLinearSpeedThreshold() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaxAngularAcceleration(float arg0) {
		// TODO Auto-generated method stub
		maxAngularAcceleration = arg0;
	}

	@Override
	public void setMaxAngularSpeed(float arg0) {
		// TODO Auto-generated method stub
		maxAngularSpeed = arg0;
	}

	@Override
	public void setMaxLinearAcceleration(float arg0) {
		// TODO Auto-generated method stub
		maxLinearAcceleration = arg0;
	}

	@Override
	public void setMaxLinearSpeed(float arg0) {
		// TODO Auto-generated method stub
		maxLinearSpeed = arg0;
	}

	@Override
	public void setZeroLinearSpeedThreshold(float arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getAngularVelocity() {
		// TODO Auto-generated method stub
		return body.getAngularVelocity();
	}

	@Override
	public float getBoundingRadius() {
		// TODO Auto-generated method stub
		return boundingRadius;
	}

	@Override
	public Vector2 getLinearVelocity() {
		// TODO Auto-generated method stub
		return body.getLinearVelocity();
	}

	@Override
	public boolean isTagged() {
		// TODO Auto-generated method stub
		return tagged;
	}

	@Override
	public void setTagged(boolean arg0) {
		tagged = arg0;
		
	}
	
	public Body getBody(){
		return body;
	}
	
	public void setBehavior(SteeringBehavior<Vector2> behavior){
		this.behavior = behavior;
	}

	public SteeringBehavior<Vector2> getBehavior(){
		return behavior;
	}
}
