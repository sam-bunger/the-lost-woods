package com.mygame.game.UI.skins.elements.pauseui;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygame.game.UI.skins.elements.menuui.OptionsMenu;
import com.mygame.game.UI.skins.tools.SkinContainer;
import com.mygame.game.handlers.GameStateManager;

public class PauseUIOrg {
	private PauseMenu pauseMenu;
	private OptionsMenu optionsMenu;

	private static ArrayList<Window> pmenui = new ArrayList<Window>();
	private static Window currentMenu;
	public final static int PAUSEMENU = 0;
	public final static int OPTIONSMENU = 1;
	
	private GameStateManager gsm;
	private SkinContainer skinContainer;
	private Stage stage;

	public PauseUIOrg(SkinContainer skinContainer, GameStateManager gsm, Stage stage) throws IOException {
		this.skinContainer = skinContainer;
		this.gsm = gsm;
		this.stage = stage;
		initiate();
	}

	public void initiate() throws IOException {
		pauseMenu = new PauseMenu(skinContainer.skin, gsm, stage);
		pauseMenu.setPosition((Gdx.graphics.getWidth()/2)-(optionsMenu.getWidth()/2), (Gdx.graphics.getHeight()/2)-(optionsMenu.getHeight()/2));
        pauseMenu.setVisible(true);
        pmenui.add(pauseMenu);
		
        optionsMenu = new OptionsMenu(skinContainer.skin);
        optionsMenu.setPosition((Gdx.graphics.getWidth()/2)-(optionsMenu.getWidth()/2), (Gdx.graphics.getHeight()/2)-(optionsMenu.getHeight()/2));
        optionsMenu.setVisible(false);
        pmenui.add(optionsMenu);
        
        currentMenu = pauseMenu;
        currentMenu.setVisible(true);
                
	}

	
	public static void changeMenu(int i){
		currentMenu.setVisible(false);
		currentMenu = pmenui.get(i);
		currentMenu.setVisible(true);
	}
	
	public void dispose(){
		optionsMenu.remove();
		
	}
	
	public ArrayList<Window> getUIWindows(){
		return pmenui;
		
	}

}

