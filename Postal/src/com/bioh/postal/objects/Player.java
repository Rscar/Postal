package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.bioh.postal.screens.GameScreen;

public class Player extends GenericObject{
	
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
	    shipShape.setAsBox(9, 1, new Vector2(300, 80), 0);

	    //fixtures are the parts that are attached to the ship body, for now we have 3, one bottom and 2 walls
	    FixtureDef playerFixture = new FixtureDef();
	    playerFixture.shape = shipShape;
	    playerFixture.density = 0.9f;
	    playerFixture.friction = 0.8f;
	    playerFixture.restitution = 0.4f;
	    
	    PolygonShape shipShape2 = new PolygonShape();
	    shipShape2.setAsBox(1, 2, new Vector2(290, 81), 0);
	    
	    FixtureDef playerFixture2 = new FixtureDef();
	    playerFixture2.shape = shipShape2;
	    playerFixture2.density = 0.0f;
	    playerFixture2.friction = 0.8f;
	    playerFixture2.restitution = 0.4f;
	    
	    PolygonShape shipShape3 = new PolygonShape();
	    shipShape3.setAsBox(1, 2, new Vector2(310, 81), 0);
	    
	    FixtureDef playerFixture3 = new FixtureDef();
	    playerFixture3.shape = shipShape3;
	    playerFixture3.density = 0.0f;
	    playerFixture3.friction = 0.8f;
	    playerFixture3.restitution = 0.4f;
	     
	    playerBody.setLinearDamping(0.2f);
	    playerBody.setAngularDamping(0.9f);
	    playerBody.createFixture(playerFixture);
	    playerBody.createFixture(playerFixture2);
	    playerBody.createFixture(playerFixture3);
		
	}
	
	@Override
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
	
	@Override
	public void draw(ShapeRenderer shapeRenderer) {
		// TODO Auto-generated method stub
		
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
