package com.mygame.game.rng;

/** BACKGROUND MAP - GENERATES AND UPDATES THE BACKGROUND AND IT'S PATHWAYS **/

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.lang.Math;
import javax.imageio.ImageIO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygame.game.B2D.B2DVars;
import com.mygame.game.main.TheLostWoods;


public class Dungeon implements java.io.Serializable{
	
	// imageArray holds every image that has been loaded - the draw function uses this array
	transient private Texture[][][] imageArray;
	
	private Vector2 direction;
	
	// this map array is an array of strings that contains the room number for each corresponding room (ie 1001 or 1010 or 1111)
	// used to generate new paths
	private String[][] map;
	
	//The number of rooms in every row and collum
	protected int Size;
	
	// Size/2
	protected int mid;
	
	//position of BGMap
	private double x, y;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	//rate at which the background moves with the player
	private double tween;
	
	//image size
	private int width, height;
	
	//current row and collum in the background grid that the player is in
	private int currRow;
	private int currCol;
	
	//Used to update enemies
	private int colX;
	private int rowY;
	
	private boolean genComplete;
	
	//Number of rooms Generated
	private int rooms = 0;
	
	private Body body;
	
	private OrthographicCamera cam;
	
	public Dungeon(int Size, Body body, OrthographicCamera cam){
		this.Size = Size;
		
		mid = this.Size/2;
		
		this.cam = cam;
		
		direction = new Vector2(0, 0);
		
		width = 640;
		height = 432;
		
		xmax = (width * (Size-1));
		xmin = 0;
		ymax = (height * (Size-1));
		ymin = 0;
		
		tween = 0.2;
		
		map = new String[this.Size][this.Size];
		imageArray = new Texture[this.Size][this.Size][4];
		
		this.body = body;
		
		//fills map with empty strings - an empty string means a room has not been generated at the position yet
		for(int i = 0; i < this.Size; i++){
			for(int k = 0; k < this.Size; k++){
				this.map[i][k] = "    ";
			}
		}
		
		genComplete = false;
		
		generateMap();
	}
	
	private void generateMap(){
		String result = "";
		String st = "";
		String sb = "";
		
		//random numbers
		int rand_1;
		int rand_2;
		int rand_3;
		int rand_4;

		for(int row = 0; row < Size; row++) {
			
			if(row >= (Size) || row < 0) continue;
			
			for(int col = 0; col <= Size; col++) {
				
				if(col >= (Size) || col < 0) continue;
				
				if(!map[row][col].equals("    ")) continue;
					
					genComplete = false;
					
					// This creates the town BG in the middle of the map
					if(row == mid && col == mid){
						result = "1111";
					}else if(row == mid + 1 && col == mid - 1){
						result = "0110";
					}else if(row == mid - 1 && col == mid + 1){
						result = "1001";
					}else if(row == mid - 1 && col == mid - 1){
						result = "1100";
					}else if(row == mid + 1 && col == mid + 1){
						result = "0011";
					}else if(row == mid && col == mid + 1){
						result = "1111";
					}else if(row == mid - 1 && col == mid){
						result = "1111";
					}else if(row == mid && col == mid - 1){
						result = "1111";
					}else if(row == mid + 1 && col == mid){
						result = "1111";
					}else{
						result = "";
						
						rand_1 = (int) (Math.random()*2);
						rand_2 = (int) (Math.random()*2);
					    rand_3 = (int) (Math.random()*2);
						rand_4 = (int) (Math.random()*2);
						
						//check top
						if((row+1) > Size-1){
							result = "0";
						}else if(map[row+1][col].equals("    ")){
							result = "" + rand_1;
						}else if(map[row+1][col].substring(2,3).equals("0")){
							result = "0";
						}else{
							result = "1";
						}
						
						//check right
						if((col+1) > Size-1){
							result = result + "0";
						}else if(map[row][col+1].equals("    ")){
							result = result + rand_2;
						}else if(map[row][col+1].substring(3, 4).equals("0")){
							result = result + "0";
						}else{
							result = result + "1";
						}
						
						//check bottom
						if((row-1) < 0){
							result = result + "0";
						}else if(map[row-1][col].equals("    ")){
							result = result + rand_3;
						}else if(map[row-1][col].substring(0, 1).equals("0")){
							result = result + "0";
						}else{
							result = result + "1";
						}
						
						//check left
						if((col-1) < 0){
							result = result + "0";
						}else if(map[row][col-1].equals("    ")){
							result = result + rand_4;
						}else if(map[row][col-1].substring(1, 2).equals("0")){
							result = result + "0";
						}else{
							result = result + "1";
						}
					}
					map[row][col] = result;
					
					try {
						imageArray[row][col][0] = TheLostWoods.res.getTexture(result + ".0");
						//imageArray[row][col][1] = TheLostWoods.res.getTexture(result + ".1");
					} catch (Exception e) {
						e.printStackTrace();
					}
					rooms++;
					
			}
		}
		genComplete = true;
		vectorUpdate();
		body.setTransform(direction, 0);
	}
	
	
	
	//uses currRow and currCol to constantly generate a backgrounds around the player if necesary
	public void update(float delta){
		/*
		String result = "";
		String st = "";
		String sb = "";
		
		//random numbers
		int rand_1;
		int rand_2;
		int rand_3;
		int rand_4;

		for(int row = (currRow - 1); row <= (currRow + 1); row++) {
			
			if(row >= (Size) || row < 0) continue;
			
			for(int col = (currCol - 1); col <= (currCol + 1); col++) {
				
				if(col >= (Size) || col < 0) continue;
				
				if(!map[row][col].equals("    ")) continue;
					
					genComplete = false;
					
					// This creates the town BG in the middle of the map
					if(row == mid && col == mid){
						result = "1111";
					}else if(row == mid + 1 && col == mid - 1){
						result = "0110";
					}else if(row == mid - 1 && col == mid + 1){
						result = "1001";
					}else if(row == mid - 1 && col == mid - 1){
						result = "1100";
					}else if(row == mid + 1 && col == mid + 1){
						result = "0011";
					}else if(row == mid && col == mid + 1){
						result = "1111";
					}else if(row == mid - 1 && col == mid){
						result = "1111";
					}else if(row == mid && col == mid - 1){
						result = "1111";
					}else if(row == mid + 1 && col == mid){
						result = "1111";
					}else{
						result = "";
						
						rand_1 = (int) (Math.random()*2);
						rand_2 = (int) (Math.random()*2);
					    rand_3 = (int) (Math.random()*2);
						rand_4 = (int) (Math.random()*2);
						
						//check top
						if((row+1) > Size-1){
							result = "0";
						}else if(map[row+1][col].equals("    ")){
							result = "" + rand_1;
						}else if(map[row+1][col].substring(2,3).equals("0")){
							result = "0";
						}else{
							result = "1";
						}
						
						//check right
						if((col+1) > Size-1){
							result = result + "0";
						}else if(map[row][col+1].equals("    ")){
							result = result + rand_2;
						}else if(map[row][col+1].substring(3, 4).equals("0")){
							result = result + "0";
						}else{
							result = result + "1";
						}
						
						//check bottom
						if((row-1) < 0){
							result = result + "0";
						}else if(map[row-1][col].equals("    ")){
							result = result + rand_3;
						}else if(map[row-1][col].substring(0, 1).equals("0")){
							result = result + "0";
						}else{
							result = result + "1";
						}
						
						//check left
						if((col-1) < 0){
							result = result + "0";
						}else if(map[row][col-1].equals("    ")){
							result = result + rand_4;
						}else if(map[row][col-1].substring(1, 2).equals("0")){
							result = result + "0";
						}else{
							result = result + "1";
						}
					}
					map[row][col] = result;
					
					try {
						imageArray[row][col][0] = TheLostWoods.res.getTexture(result + ".0");
						//imageArray[row][col][1] = TheLostWoods.res.getTexture(result + ".1");
					} catch (Exception e) {
						e.printStackTrace();
					}
					rooms++;
					
			}
		}
		*/
		genComplete = true;
		vectorUpdate();
		body.setTransform(direction, 0);
	}
	
	public void vectorUpdate(){
		direction.set((currCol) * width / B2DVars.PPM, (currRow) * height / B2DVars.PPM);
	}
	
	public float getx() { return  (float)x; }
	public float gety() { return  (float)y; }
	public int getWidth() { return width;}
	public int getHeight() { return height;}
	
	//Returns the room string at any background X and Y positions
	public String getMap(int x, int y){
		if(!(x >= Size || x < 0 || y >= Size || y < 0))
			return map[x][y];
		else
			return "    ";
	}
	
	public String[][] getFullMap(){
		for (int i = map.length-1; i >= 0 ; i--) {
			for (int j = 0; j < map[0].length; j++) {
				System.out.print(map[i][j] + " ");
		    }
		    System.out.println("");
		}
	    return map;
	}
	
	public void printFullMap(){
		for (int i = map.length-1; i >= 0 ; i--) {
			for (int j = 0; j < map[0].length; j++) {
				System.out.print(map[i][j] + " ");
		    }
		    System.out.println("");
		}
	}
	
	public int getSize(){
		return Size;
	}
	
	//draws only the backgrounds in a 3x3 area around the player
	public void renderBottom(SpriteBatch sb) {
		
		//if(!genComplete) return;
			
		for(int row = (currRow - 1); row <= (currRow + 1); row++) {
			
			if(row >= (Size) || row < 0) continue;
			
			for(int col = (currCol - 1); col <= (currCol + 1); col++) {
				
				if(col >= (Size) || col < 0 || imageArray[row][col][0] == null) continue;
				
				sb.begin();
				sb.draw(imageArray[row][col][0], col * width, row * height);
				sb.end();
			}
		}
	}
	
	public void renderTop(SpriteBatch sb) {
		
		//if(!genComplete) return;
			
		for(int row = (currRow - 1); row <= (currRow + 1); row++) {
			
			if(row >= (Size) || row < 0) continue;
			
			for(int col = (currCol - 1); col <= (currCol + 1); col++) {
				
				if(col >= (Size) || col < 0 || imageArray[row][col][0] == null || imageArray[row][col][1] == null) continue;
				
				sb.begin();
				sb.draw(imageArray[row][col][1], col * width, row * height);
				sb.end();
			}
		}
	}
	
	public void setRooms(float x, float y) {
		
		currCol = (int) ((x + (width))/width) - 1;
		currRow = (int) ((y + (height))/height) - 1;

	}
	
	public int getMid(){
		return mid;
	}
	public int getRooms(){ return rooms;}	
	public int getCurrCol(){ return currCol;}
	public int getCurrRow(){ return currRow;}
	/*
	public void createImageArray(String[][] map) throws IOException{		
		String result = "";
		String s = "";
		Texture[][] ia = new Texture[this.Size][this.Size];
		for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
            	if(map[i][j].equals("    ")){
    				ia[i][j] = null;
            	}else{
	            	result = map[i][j];
					//System.out.print(map[i][j]);
					s = "/Backgrounds/BGTiles/" + result +".png";
					ia[i][j] = Texture.loadTexture(s);
            	}
			}
		}
		imageArray = ia;
	}
	*/
}
