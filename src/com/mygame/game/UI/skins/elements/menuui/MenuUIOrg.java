package com.mygame.game.UI.skins.elements.menuui;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.mygame.game.UI.skins.elements.Tab;
import com.mygame.game.UI.skins.elements.gameui.ArmorWindow;
import com.mygame.game.UI.skins.elements.gameui.InventoryWindow;
import com.mygame.game.UI.skins.elements.gameui.SpellWindow;
import com.mygame.game.UI.skins.elements.gameui.dndinventory.Inventory;
import com.mygame.game.UI.skins.tools.SkinContainer;
import com.mygame.game.handlers.GameStateManager;

public class MenuUIOrg{
	
	private MainMenu mainMenu;
	private OptionsMenu optionsMenu;

	private static ArrayList<Window> menui = new ArrayList<Window>();
	private static Window currentMenu;
	public final static int MAINMENU = 0;
	public final static int OPTIONSMENU = 1;
	
	private GameStateManager gsm;
	private SkinContainer skinContainer;
	private Stage stage;

	public MenuUIOrg(SkinContainer skinContainer, GameStateManager gsm, Stage stage) throws IOException {
		this.skinContainer = skinContainer;
		this.gsm = gsm;
		this.stage = stage;
		initiate();
	}

	public void initiate() throws IOException {
		mainMenu = new MainMenu(skinContainer.skin, gsm, stage);
		mainMenu.setPosition((Gdx.graphics.getWidth()/2) - (mainMenu.getWidth()/2), (Gdx.graphics.getHeight()/2) - (mainMenu.getHeight()/2));
        mainMenu.setVisible(false);
        menui.add(mainMenu);
        
        optionsMenu = new OptionsMenu(skinContainer.skin);
        optionsMenu.setPosition((Gdx.graphics.getWidth()/2)-(optionsMenu.getWidth()/2), (Gdx.graphics.getHeight()/2)-(optionsMenu.getHeight()/2));
        optionsMenu.setVisible(false);
        menui.add(optionsMenu);
        
        currentMenu = mainMenu;
        currentMenu.setVisible(true);
                
	}

	
	public static void changeMenu(int i){
		currentMenu.setVisible(false);
		currentMenu = menui.get(i);
		currentMenu.setVisible(true);
	}
	
	public void dispose(){
		mainMenu.remove();
		optionsMenu.remove();
		
	}
	
	public ArrayList<Window> getUIWindows(){
		return menui;
		
	}

}
