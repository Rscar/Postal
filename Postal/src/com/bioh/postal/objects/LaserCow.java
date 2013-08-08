package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.bioh.postal.Postal;
import com.bioh.postal.screens.GameScreen;

public class LaserCow extends GenericObject{
	
	private GameScreen gameScreen;
	private Postal postal;
	
	private Vector2 localGunCoordinates;
	
	private float localGunAngle;
	private float localGunHyp;
	
	private LaserCowGun gun;
	
	public LaserCow(Vector2 position, GameScreen gameScreen){
		this.gameScreen = gameScreen;
		postal = Postal.getInstance();
		
		localGunCoordinates = new Vector2(0,6);
		
		localGunHyp = (float) Math.sqrt(Math.pow(localGunCoordinates.x, 2) + Math.pow(localGunCoordinates.y, 2));
		localGunAngle = localGunCoordinates.angle();
		
		//starts off as a static object
		BodyDef blockDef = new BodyDef();

	      
	    blockDef.type = BodyType.DynamicBody;
	    blockDef.position.set(position);
	    
	    PolygonShape blockShape = new PolygonShape();
	    blockShape.setAsBox(8, 8);
	    
	    FixtureDef blockFixture = new FixtureDef();
	    blockFixture.shape = blockShape;

	    blockFixture.density = 0.6f;
	    blockFixture.friction = 0.6f;
	    blockFixture.restitution = 0.01f;
	     
	    body = gameScreen.getWorld().createBody(blockDef);
	    body.setLinearDamping(0.2f);
	    body.createFixture(blockFixture);
	    
	    sprite = new Sprite(postal.assetManager.get("sprites/lasercow.png", Texture.class));
		sprite.setSize(16,16);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		
		gun = new LaserCowGun(this);
	      
	    System.out.println("cow created at " + body.getPosition());
		
	}

	@Override
	public void update() {
		gun.update();
		
		
	}

	@Override
	public void draw(SpriteBatch batch) {
		
		gun.draw(batch);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
		sprite.draw(batch);
		
		
	}
	
	public Vector2 getGunConnection(){
		return new Vector2(localGunHyp * (float) Math.cos(localGunAngle * Math.PI/180 + body.getAngle()), localGunHyp * (float) Math.sin(localGunAngle * Math.PI/180 + body.getAngle())).add(body.getPosition());
	}

}
