 package com.mygame.game.UI.skins.elements.gameui.dndinventory;

public enum Item {
    POTION_PINK("pinkPotion"), POTION_GREEN("greenPotion"), POTION_BLUE("bluePotion")  ;

    private String textureRegion;

    private Item(String textureRegion) {
        this.textureRegion = textureRegion;
    }

    public String getTextureRegion() {
        return textureRegion;
    }
}