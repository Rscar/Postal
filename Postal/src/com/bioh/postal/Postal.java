package com.bioh.postal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.bioh.postal.controllers.ScreenController;
import com.bioh.postal.screens.GameScreen;
import com.bioh.postal.screens.GenericScreen;

public class Postal extends Game implements ApplicationListener {
	

	public GenericScreen currentScreen;
	public ScreenController screenController;
	public static Postal postal;
	
	
	
	@Override
	public void create() {	
		
		postal = Postal.getInstance();
		
		screenController = new ScreenController();
		
		setScreen (new GameScreen(1));

	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {	
		
		currentScreen = (GenericScreen) getScreen();
		screenController.checkScreen(currentScreen);
		currentScreen.render(Gdx.graphics.getDeltaTime());
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	public static Postal getInstance(){
		return postal;
	}
}
