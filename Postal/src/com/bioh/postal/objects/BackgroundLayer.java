package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bioh.postal.Postal;

public class BackgroundLayer {
	
	private Postal postal;
	private float depth;
	private Sprite sprite;
	
	public BackgroundLayer(Texture texture, float depth){
		
		postal = Postal.getInstance();
		this.depth = depth;
		sprite = new Sprite(texture);
		sprite.setSize(2048, 1024);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		
	}
	
	public void update(float moveX, float moveY, float zoom){
		sprite.setPosition(-moveX/depth - sprite.getWidth()/2, -moveY - sprite.getHeight()/3.5f);
	}

	public void draw(SpriteBatch batch){
		sprite.draw(batch);
	}
}
