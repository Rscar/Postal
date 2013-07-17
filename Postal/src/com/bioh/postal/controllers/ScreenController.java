package com.bioh.postal.controllers;

import com.bioh.postal.Postal;
import com.bioh.postal.screens.*;


public class ScreenController {
	
	public Postal postal;
	
	public ScreenController(){
		postal = Postal.getInstance();
	}

	public void checkScreen(GenericScreen currentScreen){
		
		//if the screen is completed
		if (currentScreen.isDone()) {
			// dispose the resources of the current screen
			currentScreen.dispose();

			// if the current screen is a main menu screen we switch to
			// the game loop, if it splash then switch to menu
			if (currentScreen instanceof SplashScreen) {
				postal.setScreen(new MainMenuScreen());
			} 
			
			else if(currentScreen instanceof MainMenuScreen) {
				postal.setScreen(new GameScreen(1));
				
			}
		}
		
	}

}
