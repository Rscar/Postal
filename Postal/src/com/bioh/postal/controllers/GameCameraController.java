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
		
		camera.zoom = 0.2f;
		camera.translate(0, 50);
		camera.update();
		
		
	}
	
	public void update(){
		
		camera.position.x = screen.getPlayer().getPosition().x;
		camera.position.y = 50 + screen.getPlayer().getPosition().y/3;
		camera.zoom = 0.2f + screen.getPlayer().getPosition().y/700;
		camera.update();
		
	}
	
	public Camera getCamera(){
		return camera;
	}

}
