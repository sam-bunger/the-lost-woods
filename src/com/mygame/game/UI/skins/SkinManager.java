package com.mygame.game.UI.skins;

import java.io.IOException;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.mygame.game.UI.skins.elements.Tab;
import com.mygame.game.UI.skins.tools.SkinContainer;


public class SkinManager extends Table {

    private SkinContainer skinContainer;
    private Array<Tab> tabsArray;
    private List<Tab> tabs;

    private ScrollPane scroller;

    public SkinManager (SkinContainer skinContainer) {
        this.skinContainer = skinContainer;

        setFillParent(true);

        tabsArray = new Array<Tab>();
        tabs = new List<Tab>(skinContainer.skin);
        tabs.setItems(tabsArray);
        tabs.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                changeTab(tabs.getSelected());
            }
        });

        scroller = new ScrollPane(tabs);


    }

    public void initiate () {
        tabs.setSelectedIndex(0);
        changeTab(tabs.getSelected());
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act (float delta) {
        super.act(delta);
    }

    public void addTab (Tab tab) {
        tabsArray.add(tab);
        tabs.setItems(tabsArray);
    }

    public void changeTab (Tab tab) {
        clearChildren();

        Table table = new Table();
        
        add(table).width(250).fill();
        add(tab).expand().fill();
    }

    public void changeSkin (SkinContainer selected) throws IOException {
        for (Tab tab : tabsArray) {
            tab.changeSkin(selected);
        }
    }
}
