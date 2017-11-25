package com.mygame.game.UI.skins.elements.gameui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class TextBox extends Window{
	private int lineLength = 80;
	
	public TextBox (Skin skin, String text){
		super("TextBox", skin);
		
		defaults().pad(2);
			
		Table textTable = new Table(skin);
		
		if(text.length()<=lineLength){
			textTable.add(new Label(text.substring(0, text.length()),skin));
		}else{
			int a = text.length()/lineLength;
			
			for(int i = 0; i<a; i++){
				textTable.add(new Label(text.substring(lineLength*(i), lineLength*(i+1)),skin));
				textTable.row();
				
			}
		}
		
		add(textTable);
		pack();
		setKeepWithinStage(true);
			
			
			
		
	}

}
