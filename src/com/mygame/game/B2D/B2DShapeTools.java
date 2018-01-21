package com.mygame.game.B2D;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class B2DShapeTools {
	
	public static Body createCircle(World world, float x, float y, float r, boolean isStatic, boolean canRotate, boolean isSensor){
		BodyDef bd = new BodyDef();
		bd.fixedRotation = canRotate;
		bd.linearDamping = 10f;
		bd.position.set(x/B2DVars.PPM, y/B2DVars.PPM);
		
		if(isStatic){
			bd.type = BodyType.StaticBody;
		} else {
			bd.type = BodyType.DynamicBody;
		}
		
		
		CircleShape shape = new CircleShape();
		shape.setRadius(r/B2DVars.PPM);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.friction = .95f;
		fd.density = 1.0f;
		
		if(isSensor){
			fd.isSensor = true;
		}
		
		return world.createBody(bd).createFixture(fd).getBody();
	}
	
	public static Body createBox(World world, float x, float y, float w, float h, boolean isStatic, boolean canRotate, boolean isSensor){
		BodyDef bd = new BodyDef();
		bd.fixedRotation = canRotate;
		bd.linearDamping = 10f;
		bd.position.set(x/B2DVars.PPM, y/B2DVars.PPM);
		
		if(isStatic){
			bd.type = BodyType.StaticBody;
		} else {
			bd.type = BodyType.DynamicBody;
		}
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(w/B2DVars.PPM, h/B2DVars.PPM);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = 1.0f;
		
		if(isSensor){
			fd.isSensor = true;
		}
		
		return world.createBody(bd).createFixture(fd).getBody();
	}
}
