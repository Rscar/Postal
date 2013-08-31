package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GenericHudObject{
	
	public boolean flaggedForDelete = false;
	public boolean isButton = false;
	protected Sprite sprite;
	
	public abstract void update();
	
	public abstract void draw(SpriteBatch batch);
	
	public Sprite getSprite() {
		return sprite;
	};
}

