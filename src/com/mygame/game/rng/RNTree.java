package com.mygame.game.rng;

import static com.mygame.game.B2D.B2DVars.BIT_PLAYER;
import static com.mygame.game.B2D.B2DVars.BIT_TELEPORTER;
import static com.mygame.game.B2D.B2DVars.BIT_WALL;
import static com.mygame.game.B2D.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygame.game.B2D.B2DLight.LightData;
import com.mygame.game.entities.Tree;

public class RNTree {
	protected World world;
	private ArrayList<Tree> forest;
	
	
	public RNTree(World w) {
		world = w;
		forest = new ArrayList<Tree>();
	}
	
	public void createTree(int x, int y){
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.position.set(x/PPM, y/PPM);
		bdef.type = BodyType.StaticBody;
		Body body = world.createBody(bdef);
		
		
		shape.setAsBox(12/PPM, 12/PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_WALL;
		fdef.filter.maskBits = BIT_PLAYER;
		body.createFixture(fdef).setUserData(new LightData(1.0f));
		
		Tree tree = new Tree(body, "treeTrunk", "treeLeaves");
		forest.add(tree);
		
	}
	
	public void generateForest(PathSegment path){
		
		int pathDist = 30;
		
		Vector2 topCorner = new Vector2((float)(path.getStart().x + pathDist*Math.cos(path.getAngle())), path.getStart().y + (float)(pathDist*Math.sin(path.getAngle())));
		Vector2 topCorner2 = new Vector2((float)(path.getEnd().x + pathDist*3*Math.cos(path.getAngle())), path.getEnd().y + (float)(pathDist*3*Math.sin(path.getAngle())));
		
		genTreeBlock(topCorner, topCorner2, path.getAngle());
		
	}
	
	public void genTreeBlock(Vector2 corner, Vector2 corner2, float angle){
		
		
		
	}
	
	public void genTreeSquare(int amount, Vector2 center, Vector2 size){
		for(int i=0;i<amount;i++){
			createTree((int)(center.x+Math.random()*size.x),(int) (center.y+Math.random()*size.y));
		}
	}
	
	public ArrayList<Tree> getForest(){return forest;}
	
	public void render(SpriteBatch sb){
		for(int i=0;i < forest.size();i++){
			forest.get(i).renderAnim(sb);
			//forest.get(i).print();
		}
	}
	
	public void renderTrunks(SpriteBatch sb){
		for(int i=0;i < forest.size();i++){
			forest.get(i).renderTrunk(sb);
		}
	}
}

