package com.mygame.game.UI.skins.elements.menuui;


import java.io.IOException;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygame.game.UI.skins.elements.UIController;
import com.mygame.game.handlers.GameStateManager;
import com.mygame.game.main.TheLostWoods;
import com.mygame.game.states.LevelState;

public class MainMenu extends Window{

	public MainMenu(Skin skin, GameStateManager gsm) {
		super("Main Menu", skin);
		
		defaults().pad(10);
	    
	    TextButton newGame = new TextButton("New Game", skin);
	    newGame.addListener( new ClickListener() {              
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	try {
					gsm.push(new LevelState(gsm));
				} catch (IOException e) {
					e.printStackTrace();
				}
	        };
	    });
	    
	    TextButton loadGame = new TextButton("Load Game", skin);
	    
	    TextButton options = new TextButton("Options", skin);
	    options.addListener( new ClickListener() {              
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	MenuUIOrg.changeMenu(MenuUIOrg.OPTIONSMENU);
	        };
	    });
	    
	    TextButton quitGame = new TextButton("Quit Game", skin);
	   
	    
	    Table table = new Table(skin);
	    
	    table.add(newGame).width(150).height(50);
	    table.row();
	    table.add(loadGame).width(150).height(50);
	    table.row();
	    table.add(options).width(150).height(50);
	    table.row();
	    table.add(quitGame).width(150).height(50);
	    table.row();
	    
	    add(table);
	    
	    pack();
        setKeepWithinStage(true);
        setMovable(true);
		
	}

	@Override
	public String getName() {
		return "MainMenu";
	}
}
