package com.mygame.game.UI.skins.tools;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class SkinContainer {
    public SkinMeta skinMeta;
    public Skin skin;

    public SkinContainer (SkinMeta meta, Skin skin) {
        this.skinMeta = meta;
        this.skin = skin;
    }

    public void dispose () {
        skin.dispose();
    }

    @Override
    public String toString () {
        return skinMeta.title;
    }
}
