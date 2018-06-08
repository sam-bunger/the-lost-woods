package com.mygame.game.states;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygame.game.UI.UserInterface;
import com.mygame.game.handlers.GameStateManager;
import com.mygame.game.main.TheLostWoods;

public class MenuState extends State{
	private Texture background;
	private UserInterface ui;

	public MenuState(GameStateManager gsm, Stage stage) {
		super(gsm, stage);
		background = new Texture(Gdx.files.internal("MenuBG/MenuBG_0.png"));
		ui = TheLostWoods.getUI();
		ui.changeToMenu();
		
	}

	public void render() {
		sb.begin();
		sb.draw(background, 0, 0, WIDTH, HEIGHT);
		sb.end();
	}
	
	public void dispose(){
		background.dispose();
	}

	@Override
	protected void handleInput() {

	}

	@Override
	public void update(float delta) {

	}
	
	

}
