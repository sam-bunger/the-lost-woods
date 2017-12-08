package com.mygame.game.rng;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygame.game.handlers.TreeNode;
import com.mygame.game.main.TheLostWoods;

public class Pathway {
	
	private Vector2 start;
	private float angle;
	
	private final int numImages = 3;
	
	private int pathSizeMin = 500;
	private int pathSizeMax = 1000;
	
	private TreeNode playerOnPath;
	
	private OrthographicCamera cam;
	
	private TextureRegion path_1 = new TextureRegion(TheLostWoods.res.getTexture("path_1"));
	private TextureRegion path_2 = new TextureRegion(TheLostWoods.res.getTexture("path_2"));
	private TextureRegion path_3 = new TextureRegion(TheLostWoods.res.getTexture("path_3"));
	
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
			playerOnPath.setSibilings();
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
		
		if(playerOnPath.getPath().getPathData().size() == 0){
			drawAlongPath(playerOnPath.getPath(), 0);
		}
		
		for(int i = 0; i < playerOnPath.getChildren().size(); i++){
			if(playerOnPath.getChildren().get(i).getPath().getPathData().size() == 0){
				drawAlongPath(playerOnPath.getChildren().get(i).getPath(), 0);
			}
		}
		
		
	}
    
    public void drawAlongPath(PathSegment path, float length){
    	
    	float rand1 = (float)(Math.random()*10) + 60;
    	
    	if(path.getLength() < length) return;
    	
    	int rand2 = (int) (Math.random()*30) + 20;
    	int rand3 = (int) (Math.random()*30) + 20;
    	int randImage = (int) (Math.random()*numImages);

    	Vector2 end = path.getEndWithLength(length);
    	
    	path.getPathData().add(new Vector3(end.x - rand2, end.y - rand3, randImage));
    	
    	drawAlongPath(path, length + rand1);
    	
    }

    private TextureRegion selectImage(int randomImage){
    	
    	switch(randomImage){
    		case 0: return path_1;
    		case 1: return path_2;
    		case 2: return path_3;
    	}
    	
    	return path_1;
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
	
	public void render(SpriteBatch sb, ShapeRenderer sr){
		
		drawGrass(sb);
		
		sb.begin();
		
		PathSegment path = playerOnPath.getPath();
		
		for(int i = 0; i < path.getPathData().size(); i++){
			sb.draw(selectImage((int)path.getPathData().get(i).z), path.getPathData().get(i).x, path.getPathData().get(i).y, 50f, 15f, 32f, 32f, 1f, 1f, (float)Math.toDegrees(path.getAngle()) - 90f);
		}
		
		for(int j = 0; j < playerOnPath.getChildren().size(); j++){
			path = playerOnPath.getChildren().get(j).getPath();
			for(int i = 0; i < path.getPathData().size(); i++){
				sb.draw(selectImage((int)path.getPathData().get(i).z), path.getPathData().get(i).x, path.getPathData().get(i).y, 50f, 15f, 32f, 32f, 1f, 1f, (float)Math.toDegrees(path.getAngle()) - 90f);
			}
		}
		
		for(int j = 0; j < playerOnPath.getSibilings().size(); j++){
			path = playerOnPath.getSibilings().get(j).getPath();
			for(int i = 0; i < path.getPathData().size(); i++){
				sb.draw(selectImage((int)path.getPathData().get(i).z), path.getPathData().get(i).x, path.getPathData().get(i).y, 50f, 15f, 32f, 32f, 1f, 1f, (float)Math.toDegrees(path.getAngle()) - 90f);
			}
		}
		
		if(playerOnPath.getParent() != null){
			path = playerOnPath.getParent().getPath();
			for(int i = 0; i < path.getPathData().size(); i++){
				sb.draw(selectImage((int)path.getPathData().get(i).z), path.getPathData().get(i).x, path.getPathData().get(i).y, 50f, 15f, 32f, 32f, 1f, 1f, (float)Math.toDegrees(path.getAngle()) - 90f);
			}
		}
		
		sb.end();
		
		sr.setAutoShapeType(true);
		sr.setColor(Color.CYAN);
		sr.begin();
		sr.line(playerOnPath.getPath().getStart(), playerOnPath.getPath().getEnd());
		sr.end();
		
	}
	
	
	
	
}
	
	
	


