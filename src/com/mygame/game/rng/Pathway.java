package com.mygame.game.rng;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.mygame.game.main.TheLostWoods;

public class Pathway {
	
	private Vector2 start;
	private float angle;
	
	private ArrayList<PathSegment> open;
	private ArrayList<PathSegment> closed;
	
	private int pathSizeMin = 100;
	private int pathSizeMax = 1000;
	
	private float timeElapsed;
	
	//This threshold must be reached for the path to split into two paths
	private double splitThreshold;
	
	private int maxCount;
	
	private OrthographicCamera cam;
	
	private TextureRegion reg = new TextureRegion(TheLostWoods.res.getTexture("pathSegment"));
	
	public Pathway(OrthographicCamera cam){
		this.start = new Vector2(0, 0);
		angle = 0f;
		splitThreshold = 0.9;
		maxCount = 100;
		open = new ArrayList<PathSegment>();
		closed = new ArrayList<PathSegment>();
		timeElapsed = 0;
		this.cam = cam;
		
		generatePath();
	}
	
	public Pathway(float x, float y, float angle, OrthographicCamera cam){
		this.start = new Vector2(x, y);
		this.angle = angle;
		splitThreshold = 0.9;
		maxCount = 100;
		open = new ArrayList<PathSegment>();
		closed = new ArrayList<PathSegment>();
		timeElapsed = 0;
		this.cam = cam;
		
		generatePath();
	}
	
	public void clearPath(){
		open.clear();
		closed.clear();
	}
	
	public void generatePath(){
		open.add(new PathSegment(start, randomLength(), angle));
		Generation(0);
	}
	
	public boolean Generation(int count){
		
		if(count > maxCount) return true;
		
		ArrayList<PathSegment> temp = new ArrayList<PathSegment>();
		
		double rand;
		boolean intersect1;
		boolean intersect2;
		
		
		for(int i = 0; i < open.size(); i++){
			
			PathSegment current = open.get(i);
			
			closed.add(current);
			
			PathSegment a, b;
			
			rand = Math.random();
			
			intersect1 = false;
			intersect2 = false;

			if(rand > splitThreshold){
				
				float[] angles = getTwoAngles(current.getAngle());
				
				a = new PathSegment(current.getEnd(), randomLength(), angles[0], current);
				b = new PathSegment(current.getEnd(), randomLength(), angles[1], current);
				
				for(int k = 0; k < closed.size(); k++){
					
					//System.out.println(a.getIntersection(closed.get(k)));
					if(a.getIntersection(closed.get(k)) && !a.getParent().equals(closed.get(k))){
						intersect1 = true;
						closed.add(new PathSegment(current.getEnd(), closed.get(k).getDistanceFromPoint(current.getEnd()), angles[0], current));
						break;
					}	
				}
				
				if(!intersect1){
					temp.add(a);
				}
				
				for(int k = 0; k < closed.size(); k++){
					
					//System.out.println(b.getIntersection(closed.get(k)));
					if(b.getIntersection(closed.get(k)) && !b.getParent().equals(closed.get(k))){
						intersect2 = true;
						closed.add(new PathSegment(current.getEnd(), closed.get(k).getDistanceFromPoint(current.getEnd()), angles[1], current));
						break;
					}
					
				}
				
				if(!intersect2){
					temp.add(b);
				}
				
				
			}else{
				
				float angle = getOneAngle(current.getAngle());
				
				a = new PathSegment(current.getEnd(), randomLength(), angle, current);
				
				for(int k = 0; k < closed.size(); k++){
					//System.out.println(a.getIntersection(closed.get(k)));
					if(a.getIntersection(closed.get(k)) && !a.getParent().equals(closed.get(k))){
						intersect1 = true;
						closed.add(new PathSegment(current.getEnd(), closed.get(k).getDistanceFromPoint(current.getEnd()), angle, current));
						break;
					}
					
				}
				
				if(!intersect1){
					temp.add(a);
				}
				
			}
			
		}
			
		open.clear();
		
		while(temp.size() > 0){
			open.add(temp.get(0));
			temp.remove(0);
		}
		
		
		return Generation(count += 1);
		
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
		
		timeElapsed += delta;

		if(timeElapsed > 0.2){
			timeElapsed = 0;
			sort(closed);
		}
		
	}
	
	public void sort(ArrayList<PathSegment> arr){
		
        int n = arr.size();
 
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);
 
        // One by one extract an element from heap
        for (int i=n-1; i>=0; i--)
        {
            // Move current root to end
            PathSegment temp = arr.get(0);
            arr.set(0, arr.get(i));
            arr.set(i, temp);
            
            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }
 
    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
	/**
	 * 
	 * @param arr  
	 * @param n  
	 * @param i
	 */
    void heapify(ArrayList<PathSegment> arr, int n, int i)
    {
        int largest = i;  // Initialize largest as root
        int l = 2*i + 1;  // left = 2*i + 1
        int r = 2*i + 2;  // right = 2*i + 2
        
        // If left child is larger than root
        if (l < n && (arr.get(l).endDistFromPlayer(cam.position.x, cam.position.y) > arr.get(largest).endDistFromPlayer(cam.position.x, cam.position.y) || 
        		      arr.get(l).startDistFromPlayer(cam.position.x, cam.position.y) > arr.get(largest).startDistFromPlayer(cam.position.x, cam.position.y))){
            largest = l;
        }
        // If right child is larger than largest so far
        if (r < n && (arr.get(r).endDistFromPlayer(cam.position.x, cam.position.y) > arr.get(largest).endDistFromPlayer(cam.position.x, cam.position.y) ||
        			  arr.get(r).startDistFromPlayer(cam.position.x, cam.position.y) > arr.get(largest).startDistFromPlayer(cam.position.x, cam.position.y))){
            largest = r;
        }
        // If largest is not root
        if (largest != i)
        {
            PathSegment swap = arr.get(i);
            arr.set(i, arr.get(largest));
            arr.set(largest, swap);
 
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
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
		
		for(int i = 0; i < 10; i++){
			
			PathSegment current = closed.get(i);	   
			
			drawAlongPath(sb, current);
			
	        //sr.line(current.getStart().x, current.getStart().y, current.getEnd().x, current.getEnd().y);
	        
		}
		
		sr.end();
		
	}
	
	
	
	
}
	
	
	


