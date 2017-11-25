package com.mygame.game.entities;

import static com.mygame.game.B2D.B2DVars.PPM;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.game.B2D.B2DLight.PointLight;
import com.mygame.game.B2D.B2DLight.RayHandler;
import com.mygame.game.main.Settings;

/**
 * The DayNightCycle class controls the lights for both the sun and the player's night light.
 */
public class DayNightCycle {
	/**
	 * Things to keep in mind:
	 * 
	 * A day is 2400 units long
	 * Sunrise and sunset are both 200 units long
	 * timeScale controls the passage of time
	 * Sunrise and sunset each have their own update method
	 * 
	 * This class could probably be split into two in the future
	 * 
	 */
	
	private RayHandler rayHandler;
	private World world;
	private OrthographicCamera cam;
	
	//PLAYER VARIABLES
	private Body playerBody;
	private PointLight playerLight; //The player's night light

	
	//CLOCK VARIABLES
	private double currentTime; //The current time out of 2400
	private double timeScale; //recommended value 0.1 - 1
	
	private int sunrise; //sunset - 100 = actual sunrise time
	private int sunset;	//sunset + 100 = actual sunset time
	
	private double sunStep; //(sun travel distance)/dayTime;
	private int dayTime; //dayTime = sunset - sunrise + 200
	
	
	//SUN VARIABLES
	private PointLight sunLight; //Light object
	private float lightLevel; //Brightness
	private Color sunColor; //put value over 255 for an rgb scale 
	private Vector2 pos; //current position of the sun
	
	
	/**
	 * @param world
	 * @param cam the OrthographicCamera
	 * @param playerBody
	 * @param sunrise time out of 2400
	 * @param sunset time out of 2400
	 * @param timeScale value between 0 and 1
	 */
	public DayNightCycle(World world, OrthographicCamera cam, Body playerBody, int sunrise, int sunset, double timeScale){
		this.cam = cam;
		this.world = world;
		this.playerBody = playerBody;
		this.sunrise = sunrise;
		this.sunset = sunset;
		this.timeScale = timeScale;
		
		
		lightLevel = 0;
		rayHandler = new RayHandler(world);
		currentTime = 500;
		dayTime = sunset - sunrise + 200;
		sunStep = 400/(double)dayTime;
		
	
		daySettings();
	}
	
	private void daySettings(){
		System.out.println("\n\n\n day settings enabled \n\n");
		
		pos = new Vector2(200,100);
		
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(.5f);
		rayHandler.setPseudo3dLight(true);
		
		//rayHandler.setGammaCorrection(false);
		rayHandler.useDiffuseLight(true);
		
		if(Settings.shadowLevel >0){
			rayHandler.setBlur(true);
			rayHandler.setBlurNum(Settings.shadowLevel);
		}
		
		sunColor = new Color(255/255f,180/255f,0/255f,1);
		
	
		sunLight = new PointLight(rayHandler, 120, sunColor, 2f, 0, 0);
		sunLight.setSoftnessLength(1f);
		sunLight.attachToBody(playerBody, pos.x, pos.y);
		
		sunLight.setHeight(500f);
		sunLight.setDistance((float) currentTime);
	}
	
	private void nightSettings(){
		System.out.println("\n\n\n night settings enabled \n\n");
		
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(.5f);
		
		playerLight = new PointLight(rayHandler, 120, Color.GRAY, 3f, 0,0);
		playerLight.setSoftnessLength(0f);
		playerLight.attachToBody(playerBody,0,0);
		
	}
	
	
	public void update(){
		rayHandler.update();
		
		if(currentTime < (sunrise - 100)){	//night
			
		} else if(currentTime <= (sunrise + 100)){	//sunrise
			sunriseUpdate();
		}else if(currentTime < (sunset - 100)){	//day
			
			sunLight.setBodyOffset(pos.x -= sunStep * timeScale, pos.y);
			if(currentTime <= dayTime){	//FIX THIS LINE
				sunLight.setDistance(lightLevel += (3 *timeScale));
			} else {
				sunLight.setDistance(lightLevel -= (3 *timeScale));
			}
			
		}else if(currentTime <= sunset + 100){
			sunsetUpdate();
		}else{
			//Night
			if(currentTime == 2400) currentTime = 0;
		}
		
		currentTime += timeScale;
	}
	
	
	private void sunriseUpdate(){
		if(currentTime == sunrise - 100) daySettings();
		
		if(currentTime<sunrise){
			sunLight.setColor(sunColor.add(0, 0, (float) (1/255f * timeScale), 0));
		}else{
			sunLight.setColor(sunColor.add(0, (float) (1/255f * timeScale), (float) (2/255f * timeScale), 0));
		}
		sunLight.setBodyOffset(pos.x -= (sunStep * timeScale), pos.y);
		sunLight.setDistance(lightLevel += (6 * timeScale));
	}
	
	private void sunsetUpdate(){
		
		if(currentTime<sunset){
			sunLight.setColor(sunColor.sub(0, 0, (float) (1/255f * timeScale), 0));
		}else{
			sunLight.setColor(sunColor.sub(0, (float) (1/255f * timeScale), (float) (2/255f * timeScale), 0));
		}
		
		pos.x -= sunStep * timeScale;
		sunLight.setBodyOffset(pos.x, pos.y);
		sunLight.setDistance(lightLevel -= (sunStep * timeScale));
		
		if(currentTime == sunset + 100) nightSettings();
	}
	
	
	public void updateCam(){
		rayHandler.setCombinedMatrix(cam.combined.cpy().scl(PPM));
	}
	
	public void render(){
		rayHandler.render();
	}
	
	public void dispose(){
		rayHandler.dispose();
	}
	
	public String getCurrentTime(){
		String hours = Double.toString(Math.round(currentTime) / 100);
		
		double step = (double)(currentTime % 100)/100*60;
		String minutes = (Double.toString(step)).substring(0, 2);
		
		return hours + ":" + minutes;
	}
}
