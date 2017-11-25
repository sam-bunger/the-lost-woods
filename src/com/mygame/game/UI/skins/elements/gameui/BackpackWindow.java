package com.mygame.game.UI.skins.elements.gameui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygame.game.UI.skins.elements.gameui.dndinventory.Inventory;
import com.mygame.game.UI.skins.elements.gameui.dndinventory.Slot;
import com.mygame.game.UI.skins.elements.gameui.dndinventory.SlotActor;
import com.mygame.game.UI.skins.elements.gameui.dndinventory.SlotSource;
import com.mygame.game.UI.skins.elements.gameui.dndinventory.SlotTarget;

public class BackpackWindow extends Window{

    public BackpackWindow(Skin skin, Inventory inventory, int rows, DragAndDrop dragAndDrop) {
        super("Backpack", skin);
        
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
            SlotActor slotActor = new SlotActor(skin, slot);
            add(slotActor);

            // this can be ignored for now and will be explained in part III
            dragAndDrop.addSource(new SlotSource(slotActor));
            dragAndDrop.addTarget(new SlotTarget(slotActor));
        
            i++;
            // every 8 cells, we are going to jump to a new row
            if (i % rows == 0) {
                row();
            }
        }

        pack();

        setVisible(true);
        setMovable(true);
        setKeepWithinStage(true);
    }
}

