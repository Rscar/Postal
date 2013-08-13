package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bioh.postal.Postal;
import com.bioh.postal.objects.LaserCow.orientation;
import com.bioh.postal.screens.GameScreen;
import com.bioh.postal.utilites.BiohMath;

public class LaserCowGun extends GenericObject{
	
	private GameScreen gameScreen;
	private Postal postal;
	private LaserCow cow;
	
	private boolean flipped = false;
	
	private Vector2 firePoint;
	private Vector2 toPlayer;
	
	public LaserCowGun(LaserCow cow, GameScreen gameScreen){
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
		
		if(!cow.disabled){
			toPlayer = new Vector2(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2);
			toPlayer.sub(gameScreen.getPlayer().getPosition());
		
			sprite.setRotation(toPlayer.angle() + 180);
			
			while (sprite.getRotation() > 360) sprite.rotate(-360);
			while (sprite.getRotation() < 0) sprite.rotate(360);
		}
		else{
			
			while (sprite.getRotation() > 360) sprite.rotate(-360);
			while (sprite.getRotation() < 0) sprite.rotate(360);
			
			sprite.setRotation(cow.getAngle());
			
			if (cow.direction == orientation.LEFT && flipped == false){
				sprite.flip(true, false);
				flipped = true;
					
			}
			else if (cow.direction == orientation.RIGHT && flipped == true){
				sprite.flip(true, false);
				flipped = false;
			}
		}
		
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
		
	}
	
	public Vector2 getFirePoint(){
		return new Vector2(sprite.getWidth()/2 * (float) Math.cos(sprite.getWidth()/2 * Math.PI/180 + body.getAngle()), sprite.getWidth()/2 * (float) Math.sin(sprite.getWidth()/2 * Math.PI/180 + body.getAngle())).add(body.getPosition());
	}
	
	public float getAngle(){
		return sprite.getRotation();
	}

}
