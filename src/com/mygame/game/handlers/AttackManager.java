package com.mygame.game.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.game.entities.Attack;

public class AttackManager {
	private ArrayList<Attack> attackQue;
	
	public AttackManager(){
		attackQue = new ArrayList<Attack>();
	}
	
	public void newAttack(Attack a){
		attackQue.add(a);
	}
	
	public void deleteAttack(){
		attackQue.remove(0);
	}
	
	public void createContactListener(World w){
		w.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Gdx.app.log("beginContact", "between " + fixtureA.getBody().getUserData() + " and " + fixtureB.getBody().getUserData());
                if(fixtureA.getBody().getUserData().equals("attack")){
                	//fixtureB.getBody().getUserData().getClass()
                	
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Gdx.app.log("beginContact", "between " + fixtureA.getBody().getUserData() + " and " + fixtureB.getBody().getUserData());
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }

        });
	}
}
