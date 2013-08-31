package com.bioh.postal.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bioh.postal.screens.GameScreen;

public class HudCameraController {
	
	private OrthographicCamera camera;
	private GameScreen screen;
	
	public HudCameraController(GameScreen screen){
		
		this.screen = screen;
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(960, h/w * 960);
		camera.zoom = 1.0f;
		camera.update();
	}
	
	public OrthographicCamera getCamera(){
		return camera;
	}

}
