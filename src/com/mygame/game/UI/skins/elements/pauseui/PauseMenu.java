package com.mygame.game.UI.skins.elements.pauseui;

import java.io.IOException;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygame.game.UI.skins.elements.menuui.MenuUIOrg;
import com.mygame.game.handlers.GameStateManager;
import com.mygame.game.states.LevelState;
import com.mygame.game.states.MenuState;
import com.mygame.game.states.State;

public class PauseMenu extends Window{

		public PauseMenu(Skin skin, GameStateManager gsm, Stage stage) {
			super("Pause Menu", skin);
			
			defaults().pad(10);
		    
		    TextButton resume = new TextButton("Resume", skin);
		    resume.addListener( new ClickListener() {              
		        @Override
		        public void clicked(InputEvent event, float x, float y) {
		        	State.setPause(false);
		        };
		    });
		    
		    TextButton loadGame = new TextButton("Load Game", skin);
		    
		    TextButton options = new TextButton("Options", skin);
		    options.addListener( new ClickListener() {              
		        @Override
		        public void clicked(InputEvent event, float x, float y) {
		        	PauseUIOrg.changeMenu(MenuUIOrg.OPTIONSMENU);
		        };
		    });
		    
		    TextButton quitGame = new TextButton("Quit Game", skin);
		    quitGame.addListener( new ClickListener() {              
		        @Override
		        public void clicked(InputEvent event, float x, float y) {
		        	gsm.push(new MenuState(gsm, stage));
		        };
		    });
		    
		    Table table = new Table(skin);
		    
		    table.add(resume).width(150).height(50);
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
