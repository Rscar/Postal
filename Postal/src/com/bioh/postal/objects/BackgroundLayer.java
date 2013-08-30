package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bioh.postal.Postal;

public class BackgroundLayer{
	
	private Postal postal;
	private Sprite sprite;
	private float depth;
	
	//specify the sprite
	//specify the depth, lesser depth means it is close to the surface
	//0 depth indicates static background
	//>1 valid
	public BackgroundLayer(Texture texture, float depth){
		
		postal = Postal.getInstance();
		
		this.depth = depth;
		
		sprite = new Sprite(texture);
		sprite.setSize(1024,1024);
		sprite.setPosition(-sprite.getWidth()/2,-sprite.getHeight()/2);
	}

	public void update(float moveX, float moveY) {
		sprite.setPosition(-sprite.getWidth()/2 - moveX/depth - 400/depth, -moveY - 400);
		
	}

	public void draw(SpriteBatch batch) {
		
		sprite.draw(batch);
		
	}

}
