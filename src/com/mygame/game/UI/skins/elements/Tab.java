package com.mygame.game.UI.skins.elements;

import java.io.IOException;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygame.game.UI.skins.tools.SkinContainer;


public abstract class Tab extends Table {
    public static SkinContainer skinContainer;

    public Tab (SkinContainer skinContainer) {
        super(skinContainer.skin);

        this.skinContainer = skinContainer;
    }

    public abstract void initiate () throws IOException;

    public abstract String getName ();

    @Override
    public String toString () {
        return getName();
    }

    public void changeSkin (SkinContainer skinContainer) throws IOException {
        clearChildren();
        this.skinContainer = skinContainer;
        initiate();
    }

}
