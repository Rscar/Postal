package com.bioh.postal.objects;

import java.text.DecimalFormat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.bioh.postal.Postal;
import com.bioh.postal.screens.GameScreen;

public class LaserCow extends GenericObject{
	
	public enum orientation{
		LEFT,
		RIGHT
	}
	
	public orientation direction;
	public boolean disabled = false;
	private boolean flipped = false;
	
	private GameScreen gameScreen;
	private Postal postal;
	
	private Vector2 localGunCoordinates;
	
	private BitmapFont font;
	
	private float localGunAngle;
	private float localGunHyp;
	
	private LaserCowGun gun;
	
	public LaserCow(Vector2 position, GameScreen gameScreen){
		this.gameScreen = gameScreen;
		postal = Postal.getInstance();
		font = new BitmapFont();
		
		localGunCoordinates = new Vector2(0,6);
		
		localGunHyp = (float) Math.sqrt(Math.pow(localGunCoordinates.x, 2) + Math.pow(localGunCoordinates.y, 2));
		localGunAngle = localGunCoordinates.angle();
		
		//starts off as a static object
		BodyDef blockDef = new BodyDef();

	      
	    blockDef.type = BodyType.DynamicBody;
	    blockDef.position.set(position);
	    
	    PolygonShape blockShape = new PolygonShape();
	    blockShape.setAsBox(8, 8);
	    
	    FixtureDef blockFixture = new FixtureDef();
	    blockFixture.shape = blockShape;

	    blockFixture.density = 0.6f;
	    blockFixture.friction = 0.6f;
	    blockFixture.restitution = 0.01f;
	     
	    body = gameScreen.getWorld().createBody(blockDef);
	    body.setLinearDamping(0.2f);
	    body.createFixture(blockFixture);
	    
	    sprite = new Sprite(postal.assetManager.get("sprites/lasercow.png", Texture.class));
		sprite.setSize(16,16);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		
		gun = new LaserCowGun(this, gameScreen);
	      
	    System.out.println("cow created at " + body.getPosition());
		
	}

	@Override
	public void update() {
		
		//if the body of the cow has rotated more than 45 degrees, it is disabled
		if (body.getAngle() > Math.PI/4 || body.getAngle() < -Math.PI/4){
			disabled = true;
		}
		
		//if the cow is not disabled, check orientation 
		if (!disabled){
			if (gameScreen.getPlayer().getPosition().x < body.getPosition().x){
				direction = orientation.LEFT;
			}
			else if (gameScreen.getPlayer().getPosition().x >= body.getPosition().x){
				direction = orientation.RIGHT;
			}
		}
		
		else {
			if (Math.random() < 0.01f){
				if (direction == orientation.LEFT) direction = orientation.RIGHT;
				else if (direction == orientation.RIGHT) direction = orientation.LEFT;
			}
		}
		gun.update();
	}

	@Override
	public void draw(SpriteBatch batch) {
		
		gun.draw(batch);
		
		if (direction == orientation.LEFT && flipped == false){
			sprite.flip(true, false);
			flipped = true;
		}
		else if (direction == orientation.RIGHT && flipped == true){
			sprite.flip(true, false);
			flipped = false;
		}
		
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
		sprite.draw(batch);

		//font.draw(batch,Float.toString(gun.getAngle()), body.getPosition().x, body.getPosition().y + 20);
		
	}
	
	public Vector2 getGunConnection(){
		return new Vector2(localGunHyp * (float) Math.cos(localGunAngle * Math.PI/180 + body.getAngle()), localGunHyp * (float) Math.sin(localGunAngle * Math.PI/180 + body.getAngle())).add(body.getPosition());
	}
	
	public float getAngle(){
		return (float)(body.getAngle() * 180/Math.PI);
	}

}
