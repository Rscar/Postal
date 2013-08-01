package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.bioh.postal.Postal;
import com.bioh.postal.screens.GameScreen;

public class Mothership extends GenericObject {
	
	private GameScreen gameScreen;
	private Sprite sprite;
	private Postal postal;
	
	public Vector2 position;
	
	
	public Mothership(Vector2 position, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		postal = Postal.getInstance();
		
		// It is static
		BodyDef mothershipDef = new BodyDef();
		
		mothershipDef.type = BodyType.StaticBody;
		mothershipDef.position.set(position);
		
		PolygonShape mothershipShape = new PolygonShape();
		mothershipShape.setAsBox(40, 2);
		
		FixtureDef mothershipFixture = new FixtureDef();
		mothershipFixture.shape = mothershipShape;
		
		body = gameScreen.getWorld().createBody(mothershipDef);
		body.createFixture(mothershipFixture);
		
		sprite = new Sprite(postal.assetManager.get("sprites/ufo.png", Texture.class));
		sprite.setSize(80, 4);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		
	}
	
	@Override
	public void update() {
		// If a block gets too close, then remove that block and increment the game score.
		position = body.getPosition();
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
		sprite.draw(batch);
		
	}

}
