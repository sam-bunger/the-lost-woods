package com.mygame.game.UI.skins.elements;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygame.game.UI.skins.elements.gameui.GameUIOrg;
import com.mygame.game.UI.skins.elements.menuui.MenuUIOrg;
import com.mygame.game.UI.skins.elements.pauseui.PauseUIOrg;
import com.mygame.game.UI.skins.tools.SkinContainer;
import com.mygame.game.handlers.GameStateManager;

public class UIController extends Tab{
	private Stage stage;
	private SkinContainer skinContainer;
	
	private MenuUIOrg menuUI;
	private GameUIOrg gameUI;
	private PauseUIOrg pauseUI;
	
	private static ArrayList<Window> currentWindows;
	
	private GameStateManager gsm;

	public UIController(SkinContainer skinContainer, Stage stage, GameStateManager gsm) throws IOException {
		super(skinContainer);
		this.skinContainer = skinContainer;
		this.stage = stage;
		this.gsm = gsm;
		initiate();
	}

	@Override
	public void initiate() throws IOException {
		menuUI = new MenuUIOrg(skinContainer, gsm, stage);
		gameUI = new GameUIOrg(skinContainer, stage);
		//pauseUI = new PauseUIOrg(skinContainer, gsm);
		
		currentWindows = menuUI.getUIWindows();
		setUI();
	
	}
	
	
	public void setUI(){
		for(int i = 0; i < currentWindows.size(); i++){
			addActor(currentWindows.get(i));
		}
	}
	
	public void changeToGame(){
		menuUI.dispose();
		currentWindows = gameUI.getUIWindows();
		setUI();
	}
	
	public void changeToMenu(){
		gameUI.dispose();
		currentWindows = menuUI.getUIWindows();
		setUI();
	}
	

	@Override
	public String getName() {
		return "UI Controller";
	}

}
