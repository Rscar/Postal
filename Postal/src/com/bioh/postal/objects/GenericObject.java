package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class GenericObject {
	
	public abstract void update();
	
	public abstract void draw(SpriteBatch batch);
	
	public abstract Sprite getSprite();

}
