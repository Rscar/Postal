package com.bioh.postal.objects;

import java.text.DecimalFormat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.bioh.postal.Postal;
import com.bioh.postal.screens.GameScreen;

public class Player extends GenericObject{
	
	private boolean rightThrusterOn;
	private boolean leftThrusterOn;
	
	private Vector2 rightThrusterLoc = new Vector2();
	private Vector2 leftThrusterLoc = new Vector2();
	
	private Vector2 rightThrusterForce = new Vector2();
	private Vector2 leftThrusterForce = new Vector2();
	
	private BitmapFont font;
	
	private Postal postal;
	
	private float thrustAmount = 400f;

	
	public Player(Vector2 initpos, GameScreen gameScreen){
		postal = Postal.getInstance();
		
		BodyDef playerDef = new BodyDef();
	    playerDef.type = BodyType.DynamicBody;
	    playerDef.position.set(new Vector2(initpos.x, initpos.y));
 
	    body = gameScreen.getWorld().createBody(playerDef);
	      
	    PolygonShape shipShape = new PolygonShape();
	    shipShape.setAsBox(9, 1);

	    //fixtures are the parts that are attached to the ship body, for now we have 3, one bottom and 2 walls
	    FixtureDef playerFixture = new FixtureDef();
	    playerFixture.shape = shipShape;
	    playerFixture.density = 0.9f;
	    playerFixture.friction = 0.8f;
	    playerFixture.restitution = 0.4f;
	    
	    PolygonShape shipShape2 = new PolygonShape();
	    shipShape2.setAsBox(1, 2, new Vector2(-10, 1), 0);
	    
	    FixtureDef playerFixture2 = new FixtureDef();
	    playerFixture2.shape = shipShape2;
	    playerFixture2.density = 0.0f;
	    playerFixture2.friction = 0.8f;
	    playerFixture2.restitution = 0.4f;
	    
	    PolygonShape shipShape3 = new PolygonShape();
	    shipShape3.setAsBox(1, 2, new Vector2(10, 1), 0);
	    
	    FixtureDef playerFixture3 = new FixtureDef();
	    playerFixture3.shape = shipShape3;
	    playerFixture3.density = 0.0f;
	    playerFixture3.friction = 0.8f;
	    playerFixture3.restitution = 0.4f;
	     
	    body.setLinearDamping(0.2f);
	    body.setAngularDamping(0.9f);
	    body.createFixture(playerFixture);
	    body.createFixture(playerFixture2);
	    body.createFixture(playerFixture3);
	    
	    sprite = new Sprite(postal.assetManager.get("sprites/platform.png", Texture.class));
		sprite.setSize(22,4);
		
		// Make sure you set your origin to correspond to the center of the body.
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/4);
		
		// Debug
		font = new BitmapFont();
	}
	
	@Override
	public void update(){
		rightThrusterLoc.set(body.getWorldCenter().x + MathUtils.sin(-body.getAngle() + MathUtils.PI/2) * 3,body.getWorldCenter().y + MathUtils.cos(-body.getAngle() + MathUtils.PI/2) * 3);
		leftThrusterLoc.set(body.getWorldCenter().x - MathUtils.sin(-body.getAngle() + MathUtils.PI/2) * 3,body.getWorldCenter().y - MathUtils.cos(-body.getAngle() + MathUtils.PI/2) * 3);

		rightThrusterForce.set(MathUtils.cos(body.getAngle() + MathUtils.PI/2 + MathUtils.PI/8) * thrustAmount, MathUtils.sin(body.getAngle() + MathUtils.PI/2 + MathUtils.PI/8) * thrustAmount);
		leftThrusterForce.set(MathUtils.cos(body.getAngle() + MathUtils.PI/2 - MathUtils.PI/8) * thrustAmount, MathUtils.sin(body.getAngle() + MathUtils.PI/2 - MathUtils.PI/8) * thrustAmount);

		
		if(rightThrusterOn){	 
			body.applyForce(rightThrusterForce, rightThrusterLoc, true);
	    }
	    if(leftThrusterOn){	 
	    	body.applyForce(leftThrusterForce, leftThrusterLoc, true);
	    }
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		
		// Divide by 4 because we are only offsetting the sprite by 1. The sprite is taller than the body, if
		// you were to offset it by half the sprite's height it would be below the body!
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/4);
		
		sprite.draw(batch);
		
		// Debug stats
		DecimalFormat format = new DecimalFormat("0.00");
		font.draw(batch, "x:" + format.format(body.getPosition().x) + " y:" + format.format(body.getPosition().y), body.getPosition().x, body.getPosition().y + 20);
	}
	
	public void setLeft(boolean left){
		leftThrusterOn = left;
	}
	
	public void setRight(boolean right){
		rightThrusterOn = right;
	}
	
	public Vector2 getPosition(){
		return body.getWorldCenter();
	}
	
	public Vector2 getVelocity(){
		return body.getLinearVelocity();
	}
	
}
