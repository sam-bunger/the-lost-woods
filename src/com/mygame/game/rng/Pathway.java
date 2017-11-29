package com.mygame.game.rng;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygame.game.handlers.TreeNode;
import com.mygame.game.main.TheLostWoods;

public class Pathway {
	
	private Vector2 start;
	private float angle;
	
	private int pathSizeMin = 500;
	private int pathSizeMax = 1000;
	
	private TreeNode playerOnPath;
	
	private OrthographicCamera cam;
	
	private TextureRegion reg = new TextureRegion(TheLostWoods.res.getTexture("pathSegment"));
	
	public Pathway(OrthographicCamera cam){
		this.start = new Vector2(0, 0);
		angle = 0f;
		this.cam = cam;
		playerOnPath = new TreeNode(new PathSegment(start, randomLength(), angle), null);
	}
	
	public Pathway(float x, float y, float angle, OrthographicCamera cam){
		this.start = new Vector2(x, y);
		this.angle = angle;

		this.cam = cam;
		playerOnPath = new TreeNode(new PathSegment(start, randomLength(), angle), null);
	}

	public void generate(){
		
		double randomNum = Math.random();
		PathSegment current = playerOnPath.getPath();
		
		if(playerOnPath.getChildren().size() == 0){
			if(randomNum > 0.5){
				float[] angles = getTwoAngles(current.getAngle());
				playerOnPath.addChild(new TreeNode(new PathSegment(current.getEnd(), randomLength(), angles[0], current), playerOnPath));
				playerOnPath.addChild(new TreeNode(new PathSegment(current.getEnd(), randomLength(), angles[1], current), playerOnPath));
			}else{
				float angle = getOneAngle(current.getAngle());
				playerOnPath.addChild(new TreeNode(new PathSegment(current.getEnd(), randomLength(), angle, current), playerOnPath));
			}
		}
		
		Vector2 position = new Vector2(cam.position.x, cam.position.y);
		float currentDistance = current.getDistanceFromPoint(position);
		for(int i = 0; i < playerOnPath.getChildren().size(); i++){
			
			if(currentDistance > playerOnPath.getChildren().get(i).getPath().getDistanceFromPoint(position)){
				playerOnPath = playerOnPath.getChildren().get(i);
			}
				
		}
		
		for(int i = 0; i < playerOnPath.getSibilings().size(); i++){
			
			if(currentDistance > playerOnPath.getSibilings().get(i).getPath().getDistanceFromPoint(position)){
				playerOnPath = playerOnPath.getSibilings().get(i);
			}
				
		}
		
		if(playerOnPath.getParent() != null && currentDistance > playerOnPath.getParent().getPath().getDistanceFromPoint(position)){
			playerOnPath = playerOnPath.getParent();
		}
		
	}
	
	public float randomLength(){
		return (float) (Math.random() * (pathSizeMax - pathSizeMin)) + pathSizeMin;
	}
	
	public float[] getTwoAngles(float angle){
		float[] result = new float[2];
		
		float lower0 = (float) (angle + Math.toRadians(22.5));
		float upper0 = (float) (angle + Math.toRadians(90)) - lower0;
		
		float lower1 = (float) (angle - Math.toRadians(90));
		float upper1 = (float) (angle - Math.toRadians(22.5)) - lower1;
		
		result[0] = (float) ((Math.random() * upper0) + lower0);
		result[1] = (float) ((Math.random() * upper1) + lower1);
		return result;
	}
	
	public float getOneAngle(float angle){
		
		float lower = (float) (angle - Math.toRadians(5));
		float upper = (float) (angle + Math.toRadians(5)) - lower;
		
		return (float)((Math.random() * upper) + lower);
	}
	
	public void update(float delta){
		generate();
	}
    
    public void drawGrass(SpriteBatch sb){
    	
    	int currCol = (int) ((cam.position.x + (TheLostWoods.WIDTH))/TheLostWoods.WIDTH) - 1;
		int currRow = (int) ((cam.position.y + (TheLostWoods.HEIGHT))/TheLostWoods.HEIGHT) - 1;
		
		for(int row = (currRow - 2); row <= (currRow + 1); row++) {
			
			for(int col = (currCol - 2); col <= (currCol + 1); col++) {
				
				sb.begin();
				sb.draw(TheLostWoods.res.getTexture("grass"), col * TheLostWoods.WIDTH, row * TheLostWoods.HEIGHT);
				sb.end();
			}
		}
    	
    }
    
    public void drawAlongPath(SpriteBatch sb, PathSegment path){
    	
    	if(path.getLength() - 30 < 0) return;
    	
    	sb.begin();
    	sb.draw(reg, path.getEnd().x, path.getEnd().y, 50f, 15f, 100f, 30f, 1f, 1f, (float)Math.toDegrees(path.getAngle()) - 90f);
    	sb.end();
    	
    	drawAlongPath(sb, new PathSegment(path.getStart(), path.getLength() - 30, path.getAngle(), path));
    	
    }
	
	public void render(SpriteBatch sb, ShapeRenderer sr){
		sr.setAutoShapeType(true);
        sr.setColor(Color.RED);
		sr.begin();
		
		drawGrass(sb);
		
		drawAlongPath(sb, playerOnPath.getPath());
		
		for(int i = 0; i < playerOnPath.getChildren().size(); i++){
			drawAlongPath(sb, playerOnPath.getChildren().get(i).getPath());
		}
		
		for(int i = 0; i < playerOnPath.getSibilings().size(); i++){
			drawAlongPath(sb, playerOnPath.getSibilings().get(i).getPath());
		}
		
		if(playerOnPath.getParent() != null){
			drawAlongPath(sb, playerOnPath.getParent().getPath());
			
			for(int i = 0; i < playerOnPath.getParent().getSibilings().size(); i++){
				drawAlongPath(sb, playerOnPath.getParent().getSibilings().get(i).getPath());
			}
			
		}
		
		sr.end();
		
	}
	
	
	
	
}
	
	
	


