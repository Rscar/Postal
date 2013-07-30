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
	private Sprite sprite;
	private Postal postal;
	
	private Body blockBody;
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
	     
	    blockBody = gameScreen.getWorld().createBody(blockDef);
	    blockBody.setLinearDamping(0.2f);
	    blockBody.createFixture(blockFixture);
	    
	    sprite = new Sprite(postal.assetManager.get("sprites/cube.png", Texture.class));
		sprite.setSize(8,8);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
	      
	    System.out.println("cube created at " + blockBody.getPosition());
		
	}

	@Override
	public void update() {

		//if the block moves at all, "drop" it
		if (blockBody.getLinearVelocity().x > 0 || blockBody.getLinearVelocity().y > 0){
			mounted = false;
		}

		if (mounted){
			blockBody.applyForceToCenter(new Vector2(0,(float) (9.8 * blockBody.getMass())), false);
		}

	}

	
	@Override
	public void draw(SpriteBatch batch) {
//		System.out.println("Rotation: " + MathUtils.radiansToDegrees * blockBody.getAngle() + " Position: " + blockBody.getPosition());
		sprite.setRotation(blockBody.getAngle() * MathUtils.radiansToDegrees);
		sprite.setPosition(blockBody.getPosition().x - sprite.getWidth()/2, blockBody.getPosition().y - sprite.getWidth()/2);
		sprite.draw(batch);
	}
	
	@Override
	public Sprite getSprite() {
		return sprite;
	}


}
