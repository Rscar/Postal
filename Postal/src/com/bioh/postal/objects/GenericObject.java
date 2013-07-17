package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class GenericObject {
	
	public abstract void update();
	
	//later to be replaced with textures instead of shapes
	public abstract void draw(ShapeRenderer shapeRenderer);

}
