package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class GenericObject {
	
	public abstract void update();
	
	//later to be replaced with textures instead of shapes
	public abstract void draw(ShapeRenderer shapeRenderer);
	
	public abstract void draw(SpriteBatch batch);
	
	public abstract Sprite getSprite();

}
