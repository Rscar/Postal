package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.bioh.postal.screens.GameScreen;

public class MountedCube extends GenericObject{
	
	private Vector2 triggerLocation;
	private GameScreen gameScreen;
	
	private Body blockBody;
	private boolean mounted = true;
	
	//this is our ice cube, to be picked up by the player
	public MountedCube(Vector2 position, GameScreen gameScreen){
	
		this.gameScreen = gameScreen;
		
		//starts off as a static object
		BodyDef blockDef = new BodyDef();
	    blockDef.type = BodyType.StaticBody;;
 
	    blockBody = gameScreen.getWorld().createBody(blockDef);
	      
	    PolygonShape blockShape = new PolygonShape();
	    blockShape.setAsBox(2, 2, position, 0);
	       
	    FixtureDef blockFixture = new FixtureDef();
	    blockFixture.shape = blockShape;
	    blockFixture.density = 0.8f;
	    blockFixture.friction = 0.1f;
	    blockFixture.restitution = 0.01f;
	     
	    blockBody.setLinearDamping(0.2f);

	    blockBody.createFixture(blockFixture);
	    
	    //set trigger location. if player gets too close to trigger, we will "drop" the cube
	    triggerLocation = new Vector2(position.x, position.y - 10);
		
	}

	@Override
	public void update() {

		//if distance b/w trigger and player < 6, make the cube dynamic, dropping it from its static position
		if (triggerLocation.dst2(gameScreen.getPlayer().getPosition()) < 6 * 6){
			blockBody.setType(BodyType.DynamicBody);
		}
		
		//blockBody.applyForceToCenter(new Vector2(0,100), false);
		
	}

	@Override
	public void draw(ShapeRenderer shapeRenderer) {

		shapeRenderer.circle(triggerLocation.x, triggerLocation.y, 1);
	}

}
