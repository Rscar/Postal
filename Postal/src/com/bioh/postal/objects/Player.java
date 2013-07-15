package com.bioh.postal.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.bioh.postal.screens.GameScreen;

public class Player {
	
	private boolean rightThrusterOn;
	private boolean leftThrusterOn;
	
	private Vector2 rightThrusterLoc = new Vector2();
	private Vector2 leftThrusterLoc = new Vector2();
	
	private Vector2 rightThrusterForce = new Vector2();
	private Vector2 leftThrusterForce = new Vector2();
	
	private Body playerBody;
	
	private float thrustAmount = 400f;

	
	public Player(GameScreen gameScreen){
		

		
		BodyDef playerDef = new BodyDef();
	    playerDef.type = BodyType.DynamicBody;
 
	    playerBody = gameScreen.getWorld().createBody(playerDef);
	      
	    PolygonShape shipShape = new PolygonShape();
	    shipShape.setAsBox(8, 1, new Vector2(0, 80), 0);
	       
	    FixtureDef playerFixture = new FixtureDef();
	    playerFixture.shape = shipShape;
	    playerFixture.density = 0.9f;
	    playerFixture.friction = 0.8f;
	    playerFixture.restitution = 0.4f;
	     
	    playerBody.setLinearDamping(0.2f);
	    playerBody.setAngularDamping(0.999f);
	    playerBody.createFixture(playerFixture);
		
	}
	public void update(){
		rightThrusterLoc.set(playerBody.getWorldCenter().x + MathUtils.sin(-playerBody.getAngle() + MathUtils.PI/2) * 3,playerBody.getWorldCenter().y + MathUtils.cos(-playerBody.getAngle() + MathUtils.PI/2) * 3);
		leftThrusterLoc.set(playerBody.getWorldCenter().x - MathUtils.sin(-playerBody.getAngle() + MathUtils.PI/2) * 3,playerBody.getWorldCenter().y - MathUtils.cos(-playerBody.getAngle() + MathUtils.PI/2) * 3);

		rightThrusterForce.set(MathUtils.cos(playerBody.getAngle() + MathUtils.PI/2 + MathUtils.PI/8) * thrustAmount, MathUtils.sin(playerBody.getAngle() + MathUtils.PI/2 + MathUtils.PI/8) * thrustAmount);
		leftThrusterForce.set(MathUtils.cos(playerBody.getAngle() + MathUtils.PI/2 - MathUtils.PI/8) * thrustAmount, MathUtils.sin(playerBody.getAngle() + MathUtils.PI/2 - MathUtils.PI/8) * thrustAmount);

		
		if(rightThrusterOn){	 
			playerBody.applyForce(rightThrusterForce, rightThrusterLoc, true);
	    }
	    if(leftThrusterOn){	 
	    	playerBody.applyForce(leftThrusterForce, leftThrusterLoc, true);
	    }
	}
	
	public void setLeft(boolean left){
		leftThrusterOn = left;
	}
	
	public void setRight(boolean right){
		rightThrusterOn = right;
	}
	
	public Vector2 getPosition(){
		return playerBody.getWorldCenter();
	}
}
