package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.bioh.postal.Postal;
import com.bioh.postal.screens.GameScreen;

public class MountedCube extends GenericObject{
	
	private GameScreen gameScreen;
	private Postal postal;
	
	private Body body;
	private boolean mounted = true;
	
	//this is our ice cube, to be picked up by the player
	public MountedCube(Vector2 position, GameScreen gameScreen){
	
		this.gameScreen = gameScreen;
		postal = Postal.getInstance();
		
		
		
		//starts off as a static object
		BodyDef blockDef = new BodyDef();

	      
	    blockDef.type = BodyType.DynamicBody;
	    blockDef.position.set(position);
	    
	    PolygonShape blockShape = new PolygonShape();
	    blockShape.setAsBox(4, 4);
	    
	    FixtureDef blockFixture = new FixtureDef();
	    blockFixture.shape = blockShape;

	    blockFixture.density = 0.08f;
	    blockFixture.friction = 0.1f;
	    blockFixture.restitution = 0.01f;
	     
	    body = gameScreen.getWorld().createBody(blockDef);
	    body.setLinearDamping(0.2f);
	    body.createFixture(blockFixture);
	    
	    sprite = new Sprite(postal.assetManager.get("sprites/cube.png", Texture.class));
		sprite.setSize(8,8);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
	      
	    System.out.println("cube created at " + body.getPosition());
		
	}

	@Override
	public void update() {

		//if the block moves at all, "drop" it
		if (body.getLinearVelocity().x > 0 || body.getLinearVelocity().y > 0){
			mounted = false;
		}

		if (mounted){
			body.applyForceToCenter(new Vector2(0,(float) (9.8 * body.getMass())), false);
		}
		
		Mothership mothership = gameScreen.getMothership();
		
		if (Math.abs(mothership.position.x - body.getPosition().x) < 10 && Math.abs(mothership.position.y - body.getPosition().y)< 10) {
			flaggedForDelete = true;
			gameScreen.incrementScore();
		}

	}

	
	@Override
	public void draw(SpriteBatch batch) {
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
		sprite.draw(batch);
	}


}
