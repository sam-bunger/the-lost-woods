package com.mygame.game.UI.skins.elements.gameui;


import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygame.game.UI.skins.elements.UIController;
import com.mygame.game.UI.skins.elements.gameui.dndinventory.Inventory;
import com.mygame.game.UI.skins.tools.SkinContainer;


public class GameUIOrg{
	private SpellWindow spellWindow;
	private static Stage stage;
	private static InventoryWindow inventoryActor;
	private static ArmorWindow armorWindow;
	private DragAndDrop dragAndDrop;
	
	private String garbage = "You think you can get away with saying that shit to me over the Internet? Think again, fucker. As we speak I am contacting my secret network of street racers across Tokyo and your car is being detuned and riced right now so you better prepare for the storm, maggot. The storm that wipes out the pathetic little thing you call your life. You’re fucking lame, kid. I can be anywhere, anytime, and I can defeat you in over seven hundred ways, and that’s just with my rusty MX-5 Miata. Not only am I extensively trained in motorsport, but I have access to the entire team of the Ferrari Scuderia and I will use it to its full extent to wipe your miserable ass off the face of the track, you little shit. If only you could have known what unholy retribution your little “clever” comment was about to bring down upon you, maybe you would have held your fucking tongue. But you couldn’t, you didn’t, and now you’re paying the price, you goddamn idiot. I will race against you and you will lose to me. By the way, your car is fucking weak, kiddo.";
	
	private static ArrayList<Window> gameui = new ArrayList<Window>();
	public final static int HOTBAR = 0;
	public final static int INVENTORY = 1;
	public final static int ARMORINVENTORY = 2;
	
	private SkinContainer skinContainer;
	
    public GameUIOrg (SkinContainer skinContainer, Stage stage) throws IOException {
        this.stage = stage;
        this.skinContainer = skinContainer;
        initiate();
    }
   
    

    public void initiate() throws IOException {
        spellWindow = new SpellWindow(skinContainer.skin);
        spellWindow.setPosition((Gdx.graphics.getWidth() / 2) - (spellWindow.getWidth()) + 50, 10);
        gameui.add(spellWindow);
        
        /*
        TextBox tb = new TextBox(skinContainer.skin, garbage);
        tb.setPosition(200, 200);
        addActor(tb);
        gameui.add(tb);
        */
        
        dragAndDrop = new DragAndDrop();
        inventoryActor = new InventoryWindow(skinContainer.skin, new Inventory(48), dragAndDrop);
        inventoryActor.setPosition((Gdx.graphics.getWidth()/2), (Gdx.graphics.getHeight()/2));
        inventoryActor.setVisible(false);
        gameui.add(inventoryActor);
        
        armorWindow = new ArmorWindow(skinContainer.skin, dragAndDrop);
        armorWindow.setPosition((Gdx.graphics.getWidth()/2), (Gdx.graphics.getHeight()/2));
        armorWindow.setVisible(false);
        gameui.add(armorWindow);
        
       

    }
    

    
    public void createBackpack(){
    	 BackpackWindow backpackWindow = new BackpackWindow(skinContainer.skin, new Inventory(25), 5, dragAndDrop);
    	 backpackWindow.setPosition((Gdx.graphics.getWidth()/2), (Gdx.graphics.getHeight()/2));
    	 backpackWindow.setVisible(true);
    }
    
    
    public static void handleInput() {
    	if(Gdx.input.isKeyJustPressed(Keys.I)){
    		if(!inventoryActor.isVisible()){
    			inventoryActor.setVisible(true);
    			armorWindow.setVisible(true);
    		} else{
    			inventoryActor.setVisible(false);
    			armorWindow.setVisible(false);
    		}
    	}
    }
    
	public void dispose(){
		spellWindow.remove();
		inventoryActor.remove();
		armorWindow.remove();
		
	}
	
	public ArrayList<Window> getUIWindows(){
		return gameui;
		
	}
}
