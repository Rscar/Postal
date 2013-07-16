package com.bioh.postal.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.bioh.postal.screens.GameScreen;

public class StaticBox {
	
	public StaticBox(int w, int h, Vector2 position, GameScreen gameScreen){
		
		BodyDef bodyDef = new BodyDef();
	    bodyDef.position.set(position.x,position.y);  
	    
	    Body body = gameScreen.getWorld().createBody(bodyDef);   
	    
	    PolygonShape bodyShape = new PolygonShape();
	    bodyShape.setAsBox(w/2, h/2);  
	    
	    body.createFixture(bodyShape, 0.0f);
		
	}

}
