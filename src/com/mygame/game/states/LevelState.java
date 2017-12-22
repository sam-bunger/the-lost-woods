package com.mygame.game.states;

import static com.mygame.game.B2D.B2DVars.BIT_PLAYER;
import static com.mygame.game.B2D.B2DVars.BIT_TELEPORTER;
import static com.mygame.game.B2D.B2DVars.BIT_WALL;
import static com.mygame.game.B2D.B2DVars.PPM;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.LookWhereYouAreGoing;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.game.entities.Player;
import com.mygame.game.entities.DayNightCycle;
import com.mygame.game.entities.Follower;
import com.mygame.game.entities.InteractObj;
import com.mygame.game.entities.Teleporter;
import com.mygame.game.entities.TreasureChest;
import com.mygame.game.entities.Tree;
import com.mygame.game.handlers.GameStateManager;
import com.mygame.game.main.TheLostWoods;
import com.mygame.game.rng.Pathway;
import com.mygame.game.rng.RNTree;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.mygame.game.B2D.B2DShapeTools;
import com.mygame.game.B2D.B2DSteeringEntity;
import com.mygame.game.B2D.B2DLight.LightData;
import com.mygame.game.UI.UserInterface;
import com.mygame.game.UI.skins.elements.gameui.GameUIOrg;

public class LevelState extends GameState {
	
	private RNTree forest1;
	private Pathway pathNorth;
	private Teleporter tele;
	
	private Tree tree;
	private UserInterface ui;
	private TreasureChest chest;
	private DayNightCycle sun;
	
	private RayHandler rayHandler;
	private PointLight sunLight;
	
	//AI Test
	private B2DSteeringEntity entity1;
	private Follower follower1;
	
	public LevelState(GameStateManager gsm) throws IOException {
		super(gsm);
		
		ui = TheLostWoods.getUI();
		ui.changeToGame();
		
		//create paths
		pathNorth = new Pathway((WIDTH/2), (HEIGHT/2), (float)Math.toRadians(90), cam);
		
		B2DSteeringEntity target = new B2DSteeringEntity(playerBody);
		
		createTeleporter();
		
		createChest(200,200);
	
		//createCollisionListener();
		
		//forest1 = new RNTree(world);
		//forest1.genTreeSquare(100, new Vector2(0,0), new Vector2(1000, 1000));
		
		
		sun = new DayNightCycle(world, cam, playerBody, 600, 1800, 0.5);
		
		
		Body body1 = B2DShapeTools.createCircle(world,100,100,10,false,false);
		entity1 = new B2DSteeringEntity(body1, .1f, target);
		
		follower1 =  new Follower(body1, "player", entity1);
		
		//BODY Types
		/*
		 * static body - doesn't move, unaffected by forces
		 * kinematic body - doesn't get affected by forces, but can move
		 * dynamic body - affected by forces (eg. player, enemy)
		 */
	}

	public void handleInput() {
		super.handleInput();
		player.handleInput();
		GameUIOrg.handleInput();
		
		if(Gdx.input.isKeyPressed(Keys.M)){
			try {
				gsm.push(new DungeonState(gsm));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			isPaused = true;
		}
		
	}

	public void update(float delta) {
		super.update(delta);
		handleInput();
		
		sun.update();
		
		pathNorth.update(delta);
	
		sun.updateCam();
		
		follower1.update(delta);
		
		world.step(delta, 6, 2);
	}

	public void render() {
		super.render();
		pathNorth.render(sb, sr);
		
		//forest1.renderTrunks(sb);
		
		follower1.renderAnim(sb);
		
		chest.renderAnim(sb);
		
		//forest1.render(sb);
		
		player.renderAnim(sb);

		sun.render();
		
		sb.begin();
		TheLostWoods.font.draw(sb, ""+sun.getCurrentTime(), 100, 100);
		sb.end();
		
		//Render Box2D Camera
		b2dr.render(world, b2dCam.combined);
		
		
	}

	public void dispose() {
		super.dispose();
		sun.dispose();
	}

    private void createCollisionListener() {
        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Gdx.app.log("beginContact", "between " + fixtureA.getUserData() + " and " + fixtureB.getUserData());
                //if(fixtureB.getBody() instanceof InteractObj){
                	
                //}
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }

        });
    }
	
	public void createPlayer(){
		playerBody = B2DShapeTools.createBox(world, 0, 0, 12, 12, false, true);
		/*
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.position.set(WIDTH/2/PPM, (HEIGHT/2)/PPM);
		bdef.type = BodyType.DynamicBody;
		playerBody = world.createBody(bdef);
		
		
		shape.setAsBox(12/PPM, 12/PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_PLAYER;
		fdef.filter.maskBits = BIT_WALL;
		playerBody.createFixture(fdef).setUserData(new LightData(1.0f));
		*/
		//create player
		try {
			player = new Player(playerBody, cam);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createTeleporter(){
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.position.set(WIDTH/2/PPM, (HEIGHT/2)/PPM);
		bdef.type = BodyType.StaticBody;
		Body body = world.createBody(bdef);
		
		shape.setAsBox(12/PPM, 12/PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_TELEPORTER;
		fdef.filter.maskBits = BIT_PLAYER;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(new LightData(1.0f));
		
		tele = new Teleporter(body, gsm, 2);
		
	}	
	
	public void createChest(int x, int y){
		
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
		
		body.setUserData(chest);
		body.createFixture(fdef).setUserData(new LightData(1.0f));
		
		chest = new TreasureChest(body,36);
		
	}
}
