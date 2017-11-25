package com.mygame.game.handlers;

import java.util.Stack;

import com.mygame.game.main.TheLostWoods;
import com.mygame.game.states.State;

public class GameStateManager {
	
	private TheLostWoods game;
	
	private Stack<State> states;
	
	public GameStateManager(TheLostWoods game){
		this.game = game;
		states = new Stack<State>();
	}
	
	public void push(State state){
		states.push(state);
	}
	
	public void pop(){
		State s = states.pop();
		s.dispose();
	}
	
	public void set(State state){
		pop();
		push(state);
	}
	
	public void update(float delta){
		states.peek().update(delta);
	}
	
	public void render(){
		states.peek().render();
	}
	
	
	public TheLostWoods game(){ return game;}

}
