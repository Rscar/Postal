package com.bioh.postal.objects;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.bioh.postal.screens.GameScreen;

public class StaticBox {
	
	public StaticBox(Vector2 position, Polygon polygonShape, GameScreen gameScreen){
		
		//scale down the polygon shape that is to be created
		polygonShape.setScale(0.5f, 0.5f);

		//make the bodydef at -x,-y, its really x-2x, y-2y, I dont really get it....but this way makes it work so deal with it 8\
		BodyDef bodyDef = new BodyDef();
	    bodyDef.position.set(-position.x, -position.y);  
	    
	    Body body = gameScreen.getWorld().createBody(bodyDef);   
	    
	    PolygonShape bodyShape = new PolygonShape();
	    bodyShape.set(polygonShape.getTransformedVertices());
	    
	    body.createFixture(bodyShape, 0.0f);
		
	}

}
