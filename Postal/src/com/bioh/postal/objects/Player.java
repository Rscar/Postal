 package com.bioh.postal.objects;

import java.text.DecimalFormat;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.bioh.postal.Postal;
import com.bioh.postal.screens.GameScreen;

public class Player extends GenericObject{

	private boolean rightThrusterOn;
	private boolean leftThrusterOn;
	
	private Vector2 rightThrusterVisibleLoc = new Vector2();
	private Vector2 leftThrusterVisibleLoc = new Vector2();
	
	private Vector2 rightThrusterLoc = new Vector2();
	private Vector2 leftThrusterLoc = new Vector2();
	
	private Vector2 rightThrusterForce = new Vector2();
	private Vector2 leftThrusterForce = new Vector2();
	
	private BitmapFont font;
	
	private Postal postal;
	
	private float thrustAmount = 500f;
	private float width = 32f;
	
	private float textureWidth;
	private float textureHeight;

	
	public Player(Vector2 initpos, GameScreen gameScreen){
		postal = Postal.getInstance();
		
		//create bodydef
		BodyDef playerDef = new BodyDef();
	    playerDef.type = BodyType.DynamicBody;
	    playerDef.position.set(new Vector2(initpos.x, initpos.y));
 
	    //create fixturedef
	    FixtureDef playerFixture = new FixtureDef();
	    playerFixture.density = 0.15f;
	    playerFixture.friction = 0.8f;
	    playerFixture.restitution = 0.4f;
	    
	    body = gameScreen.getWorld().createBody(playerDef);

	    body.setLinearDamping(0.2f);
	    body.setAngularDamping(0.9f);
	    
	    gameScreen.addFixture(body, "player", playerFixture, width);
	    
	    sprite = new Sprite(postal.assetManager.get("sprites/platform.png", Texture.class));
	    textureWidth = postal.assetManager.get("sprites/platform.png", Texture.class).getWidth();
	    textureHeight = postal.assetManager.get("sprites/platform.png", Texture.class).getHeight();
		sprite.setSize(width , (textureHeight / textureWidth) * width);
		
		// Make sure you set your origin to correspond to the center of the body.
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/4);
		
		// Debug
		font = new BitmapFont();
	}
	
	@Override
	public void update(){
		
		//this is where the thruster is visible...for calculating where to put the thruster graphics
		rightThrusterVisibleLoc.set(body.getWorldCenter().x + MathUtils.sin(-body.getAngle() + MathUtils.PI/2) * 10,body.getWorldCenter().y + MathUtils.cos(-body.getAngle() + MathUtils.PI/2) * 10);
		leftThrusterVisibleLoc.set(body.getWorldCenter().x - MathUtils.sin(-body.getAngle() + MathUtils.PI/2) * 10,body.getWorldCenter().y - MathUtils.cos(-body.getAngle() + MathUtils.PI/2) * 10);
		
		//this is where the force is actually applied, closer to the center to minimize the amount of spin the ship experiences
		rightThrusterLoc.set(body.getWorldCenter().x + MathUtils.sin(-body.getAngle() + MathUtils.PI/2) * 8,body.getWorldCenter().y + MathUtils.cos(-body.getAngle() + MathUtils.PI/2) * 8);
		leftThrusterLoc.set(body.getWorldCenter().x - MathUtils.sin(-body.getAngle() + MathUtils.PI/2) * 8,body.getWorldCenter().y - MathUtils.cos(-body.getAngle() + MathUtils.PI/2) * 8);

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
	
	public boolean leftThrusterOn(){
		return leftThrusterOn;
	}
	
	public boolean rightThrusterOn(){
		return rightThrusterOn;
	}
	
	public float getRotation(){
		return body.getAngle() * 180/3.1416f - 90;
	}
	
	public Vector2 getRightThrusterPosition(){
		return rightThrusterVisibleLoc;
	}
	
	public Vector2 getLeftThrusterPosition(){
		return leftThrusterVisibleLoc;
	}
	
	public Vector2 getPosition(){
		return body.getWorldCenter();
	}
	
	public Vector2 getVelocity(){
		return body.getLinearVelocity();
	}
	
}
