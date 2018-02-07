package com.mygame.game.rng.dungeon;


import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.game.B2D.B2DShapeTools;
import com.mygame.game.rng.dungeon.map.Grid;
import com.mygame.game.rng.dungeon.map.generator.room.DungeonGenerator;

public class Dungeon {
    private TextureAtlas tiles;
    private TextureRegion[][] imageArray;
    private TextureRegion[][] topImageArray;
    private Grid grid;
	
	private int currRow, currCol, oldCol, oldRow;
	
	private int width = 100;
	private int height = 100;
	
	private World world;
	
	private Body[][][] collisionArray;
    
    public Dungeon(World world) {
    	this.world  = world;
    	
        grid = new Grid(512); // This algorithm likes odd-sized maps, although it works either way.
        imageArray = new TextureRegion[512][512];
        topImageArray = new TextureRegion[512][512];
        collisionArray = new Body[3][3][4];
        tiles = new TextureAtlas(Gdx.files.internal("dungeon/dungeonLabyrinthV3.atlas"));
       
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
            	//Make everything a background tile
            	imageArray[x][y] = tiles.findRegion("0000_labyrinth");
            }
        }

        final DungeonGenerator dungeonGenerator = new DungeonGenerator();
        dungeonGenerator.setRoomGenerationAttempts(10000);
        dungeonGenerator.setMaxRoomSize(7); //Must be odd
        dungeonGenerator.setTolerance(5); // Max difference between width and height.
        dungeonGenerator.setMinRoomSize(3);
        dungeonGenerator.generate(grid);

        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
            	
                final float cell = 1f - grid.get(x, y);
                
                if(cell == 1.0){ //Hallway
                	String code = "1111";
                	
                	if(1f - grid.get(x+1, y) == 0.0){
                		code = "0" + code.substring(1);
                	}
                	if(1f - grid.get(x-1, y) == 0.0){
                		code = code.substring(0, 2) + "0" + code.substring(3);
                	}
                	if(1f - grid.get(x, y+1) == 0.0){
                		code = code.substring(0, 1) + "0" + code.substring(2);
                	}
                	if(1f - grid.get(x, y-1) == 0.0){
                		code = code.substring(0,3) + "0";
                	}
                	
                	imageArray[x][y] = tiles.findRegion(code + "h_labyrinth");
                	
                	//Top Layer
                	if(code.substring(2,3).equals("0")){
                		topImageArray[x][y] = tiles.findRegion("1101t"); 
                	}else if(code.substring(3,4).equals("1") && code.substring(2,3).equals("1")){
                		topImageArray[x][y] = tiles.findRegion("1222t");
                	}else if(code.substring(1,2).equals("1")){
                		topImageArray[x][y] = tiles.findRegion("1221t");   
                	}else if(code.substring(3,4).equals("1")){
                		topImageArray[x][y] = tiles.findRegion("1122t");   
                	}
                		
                	
                    
                }else if(cell == 0.5){ //Room
                	String code = "1111";
                	
                	//Check for normal walls
                	if(1f - grid.get(x+1, y) == 0.0){
                		code = "0" + code.substring(1);
                	} else if(1f - grid.get(x+1, y) == 1.0){
                		code = "2" + code.substring(1);
                	}
                	
                	if(1f - grid.get(x-1, y) == 0.0){
                		code = code.substring(0, 2) + "0" + code.substring(3);
                	} else if(1f - grid.get(x-1, y) == 1.0){
                		code = code.substring(0, 2) + "2" + code.substring(3);
                	}
                	
                	if(1f - grid.get(x, y+1) == 0.0){
                		code = code.substring(0, 1) + "0" + code.substring(2);
                	} else if(1f - grid.get(x, y+1) == 1.0){
                		code = code.substring(0, 1) + "2" + code.substring(2);
                	}
                	
                	if(1f - grid.get(x, y-1) == 0.0){
                		code = code.substring(0,3) + "0";
                	}else if(1f - grid.get(x, y-1) == 1.0){
                		code = code.substring(0,3) + "2";
                	}
                	
                	imageArray[x][y] = tiles.findRegion(code + "r_labyrinth");
                	
                	if(code.substring(2,3).equals("0")){
                		topImageArray[x][y] = tiles.findRegion("1101t"); 
                	}else if(code.substring(3,4).equals("2") && code.substring(2,3).equals("2")){
                		topImageArray[x][y] = tiles.findRegion("1222t");
                	}else if(code.substring(1,2).equals("2")){
                		topImageArray[x][y] = tiles.findRegion("1221t");   
                	}else if(code.substring(3,4).equals("2")){
                		topImageArray[x][y] = tiles.findRegion("1122t");   
                	}
                }else{
                	topImageArray[x][y] = tiles.findRegion("0000_labyrinth");   
                }
                
            }
        }
    }

    public void render(SpriteBatch sb) {
    	
    	for(int row = (currRow - 4); row <= (currRow + 4); row++) {
    		
    		if(row >= (grid.getWidth()) || row < 0) continue;
    		
			for(int col = (currCol - 4); col <= (currCol + 4); col++) {
				
				if(col >= (grid.getHeight()) || col < 0 || imageArray[row][col] == null) continue;
				
				sb.begin();
				sb.draw(imageArray[row][col], col * width, row * height);
				sb.end();
			}
		}
    }
    
    public void renderTop(SpriteBatch sb) {
    	
    	for(int row = (currRow - 8); row <= (currRow + 8); row++) {
    		
    		if(row >= (grid.getWidth()) || row < 0) continue;
    		
			for(int col = (currCol - 4); col <= (currCol + 4); col++) {
				
				if(col >= (grid.getHeight()) || col < 0 || imageArray[row][col] == null) continue;
				
				if(topImageArray[row][col] != null){
					sb.begin();
					sb.draw(topImageArray[row][col], col * width, row * height);
					sb.end();
				}
			}
		}
    }
    
    
    public void setRooms(float x, float y) {
    	oldCol = currCol;
    	oldRow = currRow;
    	
		currCol = (int) ((x + (width))/width) - 1;
		currRow = (int) ((y + (height))/height) - 1;
	
		
		if(oldCol != currCol || oldRow != currRow){
			if(!(currRow<0 || currCol <0 || currRow==grid.getHeight() || currRow==grid.getHeight())){
		
				for(int row = (currRow - 1); row <= (currRow + 1); row++) {
		    		
		    		if(row >= (grid.getWidth()) || row < 0) continue;
		    		
					for(int col = (currCol - 1); col <= (currCol + 1); col++) {
						
						if(col >= (grid.getHeight()) || col < 0 || imageArray[row][col] == null) continue;
						
						createRoomCollision(row, col);
					}
				}
			}
		}
		
    }
    
    private void createRoomCollision(int x,int y){
    	
    	String str = imageArray[x][y].toString();
    	
    	//world.destroyBody(collisionArray[(x-currRow)+1][(y-currCol)+1][0]);
    	//world.destroyBody(collisionArray[(x-currRow)+1][(y-currCol)+1][1]);
    	//world.destroyBody(collisionArray[(x-currRow)+1][(y-currCol)+1][2]);
    	//world.destroyBody(collisionArray[(x-currRow)+1][(y-currCol)+1][3]);
    	
    	if(str.substring(0, 4).equals("0000")){
    		return;
    	}
    	
		if(str.substring(0, 1).equals("0")){ 
			if(collisionArray[(x-currRow)+1][(y-currCol)+1][0] != null){
				world.destroyBody(collisionArray[(x-currRow)+1][(y-currCol)+1][0]);
			}
			collisionArray[(x-currRow)+1][(y-currCol)+1][0] = B2DShapeTools.createBox(world, y * width + 50, x * height + 100, 70, 20, true, false, false);
		}
		if(str.substring(1,2).equals("0")){
			if(collisionArray[(x-currRow)+1][(y-currCol)+1][1] != null){
				world.destroyBody(collisionArray[(x-currRow)+1][(y-currCol)+1][1]);
			}
			collisionArray[(x-currRow)+1][(y-currCol)+1][1] = B2DShapeTools.createBox(world, y * width + 100, x * height+50, 20, 50, true, false, false);
		}
		if(str.substring(2,3).equals("0")){ 
			if(collisionArray[(x-currRow)+1][(y-currCol)+1][2] != null){
				world.destroyBody(collisionArray[(x-currRow)+1][(y-currCol)+1][2]);
			}
			collisionArray[(x-currRow)+1][(y-currCol)+1][2] = B2DShapeTools.createBox(world, y * width+50, x * height, 70, 5, true, false, false);
		}
		if(str.substring(3,4).equals("0")){
			if(collisionArray[(x-currRow)+1][(y-currCol)+1][3] != null){
				world.destroyBody(collisionArray[(x-currRow)+1][(y-currCol)+1][3]);
			}
			collisionArray[(x-currRow)+1][(y-currCol)+1][3] = B2DShapeTools.createBox(world, y * width, x * height + 50, 20, 50, true, false, false);
		}
    }
    
    
}