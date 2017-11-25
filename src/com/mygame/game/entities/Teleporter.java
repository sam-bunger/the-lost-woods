package com.mygame.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygame.game.handlers.GameStateManager;

public class Teleporter extends GameObj{
	
	private static final int LevelState = 1;
	private static final int DungeonState = 2;
	
	public Teleporter(Body body, GameStateManager gsm, int i){
		super(body);
	}
	
	public void createBox(){
		
	}

}
