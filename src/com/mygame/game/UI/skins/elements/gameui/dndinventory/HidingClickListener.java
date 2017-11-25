package com.mygame.game.UI.skins.elements.gameui.dndinventory;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HidingClickListener extends ClickListener {

    private Actor actor;

    public HidingClickListener(Actor actor) {
        this.actor = actor;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        actor.setVisible(false);
    }

}