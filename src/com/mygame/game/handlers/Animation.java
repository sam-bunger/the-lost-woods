package com.mygame.game.handlers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
	
	private TextureRegion[][] frames;
	private int[] numFrames;
	private float time;
	private float delay;
	private int currentFrame;
	private int timesPlayed;
	private int currentAnimation;
	
	
	public Animation(){}
	
	public Animation(TextureRegion[][] frames){
		this(frames, 1 / 12f);
		
	}
	
	public Animation(TextureRegion[][] frames, float delay){
		setFrames(frames, delay);
	}
	
	public void setFrames(TextureRegion[][] frames, float delay){
		this.frames = frames;
		this.delay = delay;
		time = 0;
		currentFrame = 0;
		timesPlayed = 0;
	}
	
	public void setNumFrames(int[] numFrames){
		this.numFrames = numFrames;
	}
	
	public void setDelay(float delay){
		this.delay = delay;
	}
	
	public void setAnimation(int animation){
		if(currentAnimation != animation){
			timesPlayed=0;
			currentAnimation = animation;
		}
		
	}
	
	public void update(float delta){
		if(delay <= 0) return;
		time += delta;
		while(time >= delay){
			step();
		}
	}
	
	public void step(){
		time -= delay;
		currentFrame++;
		if(currentFrame == frames[currentAnimation].length || currentFrame >= numFrames[currentAnimation]){
			currentFrame = 0;
			timesPlayed++;
		}
	}
	
	public TextureRegion getFrame(){ return frames[currentAnimation][currentFrame]; }
	public int getTimesPlayed() { return timesPlayed; }

}
