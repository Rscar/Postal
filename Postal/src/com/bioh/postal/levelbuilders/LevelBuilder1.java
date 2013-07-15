package com.bioh.postal.levelbuilders;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.bioh.postal.screens.GameScreen;

public class LevelBuilder1 extends GenericLevelBuilder{
	
	private GameScreen gameScreen;
	
	public LevelBuilder1(GameScreen gameScreen){
		
		this.gameScreen = gameScreen;
		
		loadAssets();
		build();
	}

	@Override
	public void build() {
		
		BodyDef floorDef = new BodyDef();
	    floorDef.position.set(0,0);  
	    
	    Body floorBody = gameScreen.getWorld().createBody(floorDef);   
	    
	    PolygonShape floorShape = new PolygonShape();
	    floorShape.setAsBox(100, 2);  
	    
	    floorBody.createFixture(floorShape, 0.0f);
		
	}

	@Override
	public void loadAssets() {
		// TODO Auto-generated method stub
		
	}

}
