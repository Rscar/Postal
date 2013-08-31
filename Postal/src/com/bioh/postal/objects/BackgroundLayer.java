package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bioh.postal.Postal;

public class BackgroundLayer{
	
	private Postal postal;
	private float depth;
	private Sprite spriteLeft;
	private Sprite spriteRight;
	
	public BackgroundLayer(Texture textureLeft, Texture textureRight, float depth){
		
		postal = Postal.getInstance();
		this.depth = depth;
		
		spriteLeft = new Sprite(textureLeft);
		spriteLeft.setSize(1024, 1024);
		spriteLeft.setOrigin(spriteLeft.getWidth()/2, spriteLeft.getHeight()/2);
		
		spriteRight = new Sprite(textureRight);
		spriteRight.setSize(1024, 1024);
		spriteRight.setOrigin(spriteRight.getWidth()/2, spriteRight.getHeight()/2);
		
	}
	
	public void update(float moveX, float moveY, float zoom){
		spriteLeft.setPosition(-moveX/depth - spriteLeft.getWidth() + 1, -moveY - spriteLeft.getHeight()/3.5f);
		spriteRight.setPosition(-moveX/depth, -moveY - spriteRight.getHeight()/3.5f);
	}

	public void draw(SpriteBatch batch){
		spriteLeft.draw(batch);
		spriteRight.draw(batch);
	}

}
