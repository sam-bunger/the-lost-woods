package com.mygame.game.UI.skins.elements.gameui.dndinventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygame.game.UI.UserInterface;

public class SlotActor extends ImageButton implements SlotListener {

    private Slot slot;

    private Skin skin;
    
    private static ImageButtonStyle style;
    
    private static TextureAtlas icons;
    
    private static TextureRegion image;

    public SlotActor(Skin skin, Slot slot) {
        super(createStyle(skin, slot));
        this.slot = slot;
        this.skin = skin;

        // this actor has to be notified when the slot itself changes
        slot.addListener(this);

        
        SlotTooltip tooltip = new SlotTooltip(slot, skin);
        UserInterface.stage.addActor(tooltip);
        addListener(new TooltipListener(tooltip, true));
        
    }

    /**
     * This will create a new style for our image button, with the correct image for the item type.
     */
    private static ImageButtonStyle createStyle(Skin skin, Slot slot) {
        icons = new TextureAtlas(Gdx.files.internal("skins/inventory/test.atlas"));
        if (slot.getItem() != null) {
            image = icons.findRegion(slot.getItem().getTextureRegion());
        } else {
            image = icons.findRegion("emptySlot");
        }
        style = new ImageButtonStyle(skin.get(ButtonStyle.class));
        style.imageUp = new TextureRegionDrawable(image);
        style.imageDown = new TextureRegionDrawable(image);

        return style;
    }

    @Override
    public void hasChanged(Slot slot) {
        // when the slot changes, we switch the icon via a new style
        setStyle(createStyle(skin, slot));
    }

    public Slot getSlot() {
        return slot;
    }
    
    public void setStyle(String imageName){
    	image = icons.findRegion(imageName);
        style.imageUp = new TextureRegionDrawable(image);
        style.imageDown = new TextureRegionDrawable(image);
    }

}
