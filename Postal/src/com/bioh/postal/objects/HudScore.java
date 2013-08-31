package com.bioh.postal.objects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bioh.postal.screens.GameScreen;

public class HudScore extends GenericHudObject{
	
	private GameScreen gameScreen;
	private BitmapFont font;
	
	public HudScore(GameScreen gameScreen){
		
		this.gameScreen = gameScreen;
		font = new BitmapFont();
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(SpriteBatch batch) {

		font.draw(batch, gameScreen.getScore().toString(), -gameScreen.getHudCamera().viewportWidth / 2 + 50, -gameScreen.getHudCamera().viewportHeight / 2 + 50);
		
	}

}
