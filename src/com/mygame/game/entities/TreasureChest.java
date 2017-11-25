package com.mygame.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygame.game.UI.skins.elements.gameui.dndinventory.Inventory;
import com.mygame.game.main.TheLostWoods;

public class TreasureChest extends InteractObj{

	private final int[] numFrames = { 1 };
	private Vector2 pos;
	private Inventory inv;
	private int chestSize;
	
	public TreasureChest(Body body, int chestSize) {
		super(body);
		this.chestSize = chestSize;
		Texture tex = TheLostWoods.res.getTexture("barrel");
		
		TextureRegion[][] sprites = TextureRegion.split(tex, 43, 64);
		
		setAnimation(sprites, 0.1f);
		animation.setNumFrames(numFrames);
		animation.setAnimation(currentAction);
		
	}
	
	public void interactAction(){
		inv = new Inventory(chestSize);
		inv.addRandomItems(0, 6);
	}
	
	public Inventory getInventory(){return inv;}
	
	public void print(){System.out.println(body.getPosition().x+" "+body.getPosition().y);}

}