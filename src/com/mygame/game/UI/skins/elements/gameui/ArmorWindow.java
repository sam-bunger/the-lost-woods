package com.mygame.game.UI.skins.elements.gameui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygame.game.UI.skins.elements.gameui.dndinventory.Inventory;
import com.mygame.game.UI.skins.elements.gameui.dndinventory.Slot;
import com.mygame.game.UI.skins.elements.gameui.dndinventory.SlotActor;
import com.mygame.game.UI.skins.elements.gameui.dndinventory.SlotSource;
import com.mygame.game.UI.skins.elements.gameui.dndinventory.SlotTarget;

public class ArmorWindow extends Window{
	private Inventory inventory;
	
	public ArmorWindow(Skin skin, DragAndDrop dragAndDrop) {
        super("Armor", skin);
        
        inventory = new Inventory(5);
        
        // add an "X" button to the top right of the window, and make it hide the inventory
        //TextButton closeButton = new TextButton("X", skin);
        //closeButton.addListener(new HidingClickListener(this));
        //add(closeButton).height(getPadTop());

        // basic layout
        defaults().pad(3);
        row().fill().expandX();

        // run through all slots and create SlotActors for each
        int i = 0;
        for (Slot slot : inventory.getSlots()) {
        	
        	
        	switch (i){
        		case (0):
        			SlotActor slotActor0 = new SlotActor(skin, slot);
        			slotActor0.setStyle("helmSlot");
        			add(slotActor0);
        			
                    dragAndDrop.addSource(new SlotSource(slotActor0));
                    dragAndDrop.addTarget(new SlotTarget(slotActor0));
                    
                    break;
                    
        		case(1):
        			SlotActor slotActor1 = new SlotActor(skin, slot);
        			slotActor1.setStyle("shirtSlot");
        			add(slotActor1);
        			
                    dragAndDrop.addSource(new SlotSource(slotActor1));
                    dragAndDrop.addTarget(new SlotTarget(slotActor1));
                    
                    break;
                    
        		case(2):
        			SlotActor slotActor2 = new SlotActor(skin, slot);
        			slotActor2.setStyle("pantsSlot");
        			add(slotActor2);
        			
                    dragAndDrop.addSource(new SlotSource(slotActor2));
                    dragAndDrop.addTarget(new SlotTarget(slotActor2));
                    
                    break;
                    
        		case(3):
        			SlotActor slotActor3 = new SlotActor(skin, slot);
        			slotActor3.setStyle("bootSlot");
        			add(slotActor3);
        			
                    dragAndDrop.addSource(new SlotSource(slotActor3));
                    dragAndDrop.addTarget(new SlotTarget(slotActor3));
                    
                    break;
                    
        		case(4):
        			SlotActor slotActor4 = new SlotActor(skin, slot);
        			slotActor4.setStyle("backpackSlot");
        			add(slotActor4);
        			
                    dragAndDrop.addSource(new SlotSource(slotActor4));
                    dragAndDrop.addTarget(new SlotTarget(slotActor4));
                    
                    break;
        	
        	}
        	
        	

            row();
            i++; 
        }

        pack();

        setVisible(true);
        setMovable(true);
        setKeepWithinStage(true);
    }
}

