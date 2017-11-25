package com.mygame.game.UI.skins.elements.gameui.dndinventory;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Inventory {

    private Array<Slot> slots;

    public Inventory(int size) {
        slots = new Array<Slot>(size);
        for (int i = 0; i < size; i++) {
            slots.add(new Slot(null, 0));
        }
        
        // create some random items
        for (Slot slot : slots) {
            slot.add(Item.values()[MathUtils.random(0, Item.values().length - 1)], 1);
    	}

        // create a few random empty slots
        for (int i = 0; i < 10; i++) {
            Slot randomSlot = slots.get(MathUtils.random(0, slots.size - 1));
            randomSlot.take(randomSlot.getAmount());
        }
        
    }

    public int checkInventory(Item item) {
        int amount = 0;

        for (Slot slot : slots) {
            if (slot.getItem() == item) {
                amount += slot.getAmount();
            }
        }

        return amount;
    }

    public boolean store(Item item, int amount) {
        // first check for a slot with the same item type
        Slot itemSlot = firstSlotWithItem(item);
        if (itemSlot != null) {
            itemSlot.add(item, amount);
            return true;
        } else {
            // now check for an available empty slot
            Slot emptySlot = firstSlotWithItem(null);
            if (emptySlot != null) {
                emptySlot.add(item, amount);
                return true;
            }
        }

        // no slot to add
        return false;
    }

    public Array<Slot> getSlots() {
        return slots;
    }
    
    public void addRandomItems(int rarity, int number){
    	if(rarity == 0){
	    	for (int i = 0; i<number;i++) {
	    		Slot slot = slots.get(MathUtils.random(0, slots.size - 1));
	            slot.add(Item.values()[MathUtils.random(0, Item.values().length - 1)], 1);
	        }
    	}
    }

    private Slot firstSlotWithItem(Item item) {
        for (Slot slot : slots) {
            if (slot.getItem() == item) {
                return slot;
            }
        }

        return null;
    }

}