package com.mygame.game.UI.skins.elements.menuui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygame.game.main.Settings;

public class OptionsMenu extends Window{
	private TextButton backButton;
	private TextButton resetButton;
	private TextButton applyButton;
	private SelectBox<String> resolutionSelectBox;
	private SelectBox<String> shadowSelectBox;
	private CheckBox fullscreen;
	private Slider mainVolume;
	private Slider sfxVolume;
	private Slider musicVolume;
	
	private String currentResolution;
	
	//private boolean isFullscreen = false;
	
	
	public OptionsMenu(Skin skin) {
		super("Options", skin);
		
		defaults().pad(10);
		
	    //Bottom buttons
		backButton = new TextButton("Back", skin);
		backButton.addListener( new ClickListener() {              
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	MenuUIOrg.changeMenu(MenuUIOrg.MAINMENU);
	        };
		});
		
	    resetButton = new TextButton("Reset", skin);
	    resetButton.addListener( new ClickListener() {              
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	           resetSettings();
	        };
	        
	    });
	    
	    applyButton = new TextButton("Apply", skin);
	    applyButton.addListener( new ClickListener() {              
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	           applySettings();
	        };
	    });
	   

	    //Resolution Select
	    Label resolutionLabel = new Label("Resolution: ", skin);
	    String[] resolutions = new String[]{"1024 x 768"
	    		, "1280 x 800"
	    		, "1280 x 1024"
	    		, "1360 x 768"
	    		, "1366 x 768"
	    		, "1440 x 900"
	    		, "1600 x 900"
	    		, "1680 x 1050"
	    		, "1920 x 1080"
	    		, "2560 x 1440"};
	    resolutionSelectBox = new SelectBox<String>(skin);
	    resolutionSelectBox.setItems(resolutions);
	    resolutionSelectBox.setSelected("1920 x 1080");
	    
	    
	  //shadow Select
	    Label shadowLabel = new Label("Shadow: ", skin);
	    String[] shadowlevel = new String[]{"Low"
	    		, "Medium"
	    		, "High"
	    		, "Very High"};
	    shadowSelectBox = new SelectBox<String>(skin);
	    shadowSelectBox.setItems(shadowlevel);
	    shadowSelectBox.setSelected("Low");
	    
	    //Fullscreen Checkbox
	    fullscreen = new CheckBox("Fullscreen", skin);

	    //Volume Controls
	    Label mainVolumeLabel = new Label("Main Volume: ", skin);
	    mainVolume  = new Slider(0, 100, 1, false,skin);
	    mainVolume.setValue(100);
	    Label mainVolumeValueLabel = new Label(Float.toString(mainVolume.getValue()), skin);
	    mainVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	mainVolumeValueLabel.setText(Float.toString(mainVolume.getValue()));
            }
        });
	  
	    Label sfxVolumeLabel = new Label("SFX Volume: ", skin);
	    sfxVolume = new Slider(0, 100, 1, false,skin);
	    sfxVolume.setValue(100);
	    Label sfxVolumeValueLabel = new Label(Float.toString(sfxVolume.getValue()), skin);
	    sfxVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	sfxVolumeValueLabel.setText(Float.toString(sfxVolume.getValue()));
            }
        });
	    
	    Label musicVolumeLabel = new Label("Music Volume: ", skin);
	    musicVolume = new Slider(0, 100, 1, false,skin);
	    musicVolume.setValue(100);
	    Label musicVolumeValueLabel = new Label(Float.toString(musicVolume.getValue()), skin);
	    musicVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	musicVolumeValueLabel.setText(Float.toString(musicVolume.getValue()));
            }
        });
	    
	    
	    
	    Table table = new Table(skin);
	    
	    table.add(mainVolumeLabel);
	    table.add(mainVolume);
	    table.add(mainVolumeValueLabel);
	    table.row();
	    
	    table.add(sfxVolumeLabel);
	    table.add(sfxVolume);
	    table.add(sfxVolumeValueLabel);
	    table.row();
	    
	    table.add(musicVolumeLabel);
	    table.add(musicVolume);
	    table.add(musicVolumeValueLabel);
	    table.row();
	    
	    table.add(resolutionLabel);
	    table.add(resolutionSelectBox);
	    table.row();
	    
	    table.add(shadowLabel);
	    table.add(shadowSelectBox);
	    table.row();
	    
	    table.add(fullscreen);
	    table.padBottom(10);
	    table.row();
	    
	    table.add(backButton).width(80).height(30);
	    table.add(resetButton).width(80).height(30);
	    table.add(applyButton).width(80).height(30);
	    
	    
	    
	    add(table);
	    
	    pack();
        setKeepWithinStage(true);
        setMovable(true);
		
	}

	@Override
	public String getName() {
		return "MainMenu";
	}
	
	private void applySettings(){
		Settings.mainVolume = mainVolume.getValue();
		Settings.sfxVolume = sfxVolume.getValue();
		Settings.musicVolume = musicVolume.getValue();
		
		String s = resolutionSelectBox.getSelected();
		Settings.resolution = s;
		Settings.width = Integer.parseInt(s.substring(0,4));
		Settings.height = Integer.parseInt(s.substring(7, s.length()));
		
		Settings.isFullscreen = fullscreen.isChecked();
	
		
		if(shadowSelectBox.getSelected().equals("Very High")){
			Settings.shadowLevel = 3;
		}else if(shadowSelectBox.getSelected().equals("High")){
			Settings.shadowLevel = 2;
		}else if(shadowSelectBox.getSelected().equals("Medium")){
			Settings.shadowLevel = 1;
		}else{
			Settings.shadowLevel = 0;
		}
		
	
		
		Settings.printSettings();
	}
	
	private void resetSettings(){
		mainVolume.setValue(100);
		sfxVolume.setValue(100);
		musicVolume.setValue(100);
		
		resolutionSelectBox.setSelected("1920 x 1080");

		fullscreen.setChecked(false);
		
		shadowSelectBox.setSelected("Medium");

		applySettings();
	}
	
}


