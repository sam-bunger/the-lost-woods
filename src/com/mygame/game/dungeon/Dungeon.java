package com.mygame.game.dungeon;

import static com.mygame.game.B2D.B2DVars.BIT_PLAYER;
import static com.mygame.game.B2D.B2DVars.BIT_WALL;
import static com.mygame.game.B2D.B2DVars.PPM;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygame.game.B2D.B2DShapeTools;
import com.mygame.game.dungeon.map.Grid;
import com.mygame.game.dungeon.map.generator.room.DungeonGenerator;

public class Dungeon {
    private TextureAtlas tiles;
    private TextureRegion[][] imageArray;
    private Grid grid;
    
    private int currRow;
	private int currCol;
	
	private int width = 100;
	private int height = 100;
	
	private World world;
    
    public Dungeon(World world) {
    	this.world  = world;
    	
        grid = new Grid(512); // This algorithm likes odd-sized maps, although it works either way.
        imageArray = new TextureRegion[512][512];
        tiles = new TextureAtlas(Gdx.files.internal("dungeon/dungeonLabyrinthV2.atlas"));
       
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
    
    public void setRooms(float x, float y) {
		
		currCol = (int) ((x + (width))/width) - 1;
		currRow = (int) ((y + (height))/height) - 1;

	}
    
	public void createCollision(){
		
		//Body topLeft = B2DShapeTools.createBox(world, 0, 0, 20, 40, true, false);
		//Body topRight = B2DShapeTools.createBox(world, 80, 0, 20, 40, true, false);
		//Body botRight = B2DShapeTools.createBox(world, 0, 0, 12, 12, true, false);
		//Body botLeft = B2DShapeTools.createBox(world, 0, 0, 12, 12, true, false);
		
	
	}

}