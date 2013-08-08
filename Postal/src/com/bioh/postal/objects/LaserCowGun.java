package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bioh.postal.Postal;
import com.bioh.postal.screens.GameScreen;

public class LaserCowGun extends GenericObject{
	
	private GameScreen gameScreen;
	private Postal postal;
	private LaserCow cow;
	
	public LaserCowGun(LaserCow cow){
		this.cow = cow;
		this.gameScreen = gameScreen;
		postal = Postal.getInstance();
		
			    
		sprite = new Sprite(postal.assetManager.get("sprites/lasercowgun.png", Texture.class));
		sprite.setSize(16,8);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);

		
	}

	@Override
	public void update() {
		sprite.setPosition(cow.getGunConnection().x - sprite.getWidth()/2, cow.getGunConnection().y - sprite.getHeight()/2);
		
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
		
	}

}
