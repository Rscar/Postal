package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GenericObject {
	
	public boolean flaggedForDelete = false;
	protected Body body;
	protected Sprite sprite;
	
	public abstract void update();
	
	public abstract void draw(SpriteBatch batch);
	
	public Sprite getSprite() {
		return sprite;
	};
	
	public Body getBody() {
		return body;
	};

}
