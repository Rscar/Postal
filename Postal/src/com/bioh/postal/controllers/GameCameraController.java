package com.bioh.postal.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bioh.postal.screens.GameScreen;

public class GameCameraController {
	
	private OrthographicCamera camera;
	private GameScreen screen;
	
	public GameCameraController(GameScreen screen){
		
		this.screen = screen;
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		
		camera = new OrthographicCamera(960, h/w * 960);
		
		camera.zoom = .15f;
		camera.translate(0, 40);
		camera.update();
		


	}
	
	public void update(){
		
		//follow the players x coordinate
		//update the y coordinate to keep the ground at the bottom of the screen while zooming in and out
		//zoom based on players height off the ground
		camera.position.x = screen.getPlayer().getPosition().x;
		camera.position.y = 40 + screen.getPlayer().getPosition().y/3;
		camera.zoom = .15f + screen.getPlayer().getPosition().y/600;
		
		//need to call update after changing camera zoom and position
		camera.update();
		
	}
	
	public Camera getCamera(){
		return camera;
	}

}
