package com.bioh.postal.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.bioh.postal.screens.GameScreen;

public class GameCameraController {
	
	private OrthographicCamera camera;
	private GameScreen screen;
	
	// Going to be used as boundary check for camera.
	private BoundingBox bounds;
	
	public GameCameraController(GameScreen screen){
		
		this.screen = screen;
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		
		
		
		camera = new OrthographicCamera(960, h/w * 960);
		
		camera.zoom = .15f;
		camera.update();
		


	}
	
	public void update(){
		
		//follow the players x coordinate
		//update the y coordinate to keep the ground at the bottom of the screen while zooming in and out
		//zoom based on players height off the ground
		camera.position.x = screen.getPlayer().getPosition().x;
		camera.position.y = (camera.viewportHeight * camera.zoom)/2;
		camera.zoom = .15f + screen.getPlayer().getPosition().y/600;
		
		
		if (camera.viewportWidth * camera.zoom > screen.getMap().getProperties().get("width", Integer.class) * screen.getMap().getProperties().get("tilewidth", Integer.class)/2){
			camera.zoom = (screen.getMap().getProperties().get("width", Integer.class) * screen.getMap().getProperties().get("tilewidth", Integer.class)/2) / camera.viewportWidth;
		}
		
		if (camera.position.x + (camera.viewportWidth * camera.zoom)/2 > screen.getMap().getProperties().get("width", Integer.class) * screen.getMap().getProperties().get("tilewidth", Integer.class)/2){
			camera.position.x = screen.getMap().getProperties().get("width", Integer.class) * screen.getMap().getProperties().get("tilewidth", Integer.class)/2 - (camera.viewportWidth * camera.zoom)/2;
		}
		
		else if (camera.position.x - (camera.viewportWidth * camera.zoom)/2 < 0){
			camera.position.x = (camera.viewportWidth * camera.zoom)/2;
		}
		
		//need to call update after changing camera zoom and position
		camera.update();
		
	}
	
	public Camera getCamera(){
		return camera;
	}

}
