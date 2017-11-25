package com.mygame.game.UI;

import java.io.IOException;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygame.game.UI.skins.SkinManager;
import com.mygame.game.UI.skins.elements.Tab;
import com.mygame.game.UI.skins.elements.UIController;
import com.mygame.game.UI.skins.tools.SkinContainer;
import com.mygame.game.UI.skins.tools.SkinMeta;
import com.mygame.game.handlers.GameStateManager;


public class UserInterface extends ApplicationAdapter {

    public static Array<SkinContainer> skins = new Array<SkinContainer>();
    private SkinManager skinManager;
    public static Stage stage;
    private String skinString = "skins/visui";
    
    private Tab uiController;
    private GameStateManager gsm;

    public UserInterface(Stage stage, GameStateManager gsm){
    	this.stage = stage;
    	this.gsm = gsm;
    }
    
    
    @Override
    public void create () {
        Gdx.app.setLogLevel(Application.LOG_INFO);
        
        Json json = new Json();
            String skinjson = Gdx.files.internal(skinString + "/info.json").readString();
            SkinMeta meta = json.fromJson(SkinMeta.class, skinjson);
            Skin skin = new Skin(Gdx.files.internal(skinString + "/data/" + meta.filename));
            skins.add(new SkinContainer(meta, skin));

        Gdx.input.setInputProcessor(stage);

        SkinContainer initialSkin = skins.first();

        try {
        	uiController = new UIController(initialSkin, stage, gsm);
       
        	skinManager = new SkinManager(initialSkin);
        	skinManager.addTab(uiController);

        	
		} catch (IOException e) {
			System.out.println("Failed to initalize gameStateUI");
		}
        
        skinManager.initiate();

        stage.addActor(skinManager);
    }
    
    public void changeToGame(){
    	((UIController) uiController).changeToGame();
    }
    
    public void changeToMenu(){
    	((UIController) uiController).changeToMenu();
    }


    @Override
    public void dispose () {
        stage.dispose();
        for (SkinContainer skinContainer : skins) {
            skinContainer.dispose();
        }
    }
}
