package com.bioh.postal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.bioh.postal.controllers.GameCameraController;
import com.bioh.postal.levelbuilders.GenericLevelBuilder;
import com.bioh.postal.levelbuilders.LevelBuilder1;
import com.bioh.postal.objects.Player;

public class GameScreen extends GenericScreen implements InputProcessor{
	
	
	private Box2DDebugRenderer renderer;
	private World world;
	private Player player;
	private GenericLevelBuilder levelBuilder;
	private GameCameraController gameCameraController;
	
	private boolean leftPressed;
	private boolean rightPressed;
	
	
	public GameScreen(int level){
		
		Gdx.input.setInputProcessor(this);
		
		buildWorld(level);
		
	}
	
	public void buildWorld(int level){
		renderer = new Box2DDebugRenderer();
		gameCameraController = new GameCameraController(this);

		world = new World(new Vector2(0, -9.8f), false);
		

		switch (level){
		case 1:
			levelBuilder = new LevelBuilder1(this);
			break;
		case 2:
			break;
		case 3:
			break;
		}
		
		player = new Player(this);
	}
	
	public World getWorld(){
		return world;
	}

	@Override
	public void update(float delta) {
		 
		rightPressed = false;
		leftPressed = false;
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)) leftPressed = true;
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) rightPressed = true;
		

		for (int i = 0; i < 4; i++) {
			
			if (Gdx.input.isTouched(i) == false) continue;

			float x = Gdx.input.getX(i);
			float y = Gdx.input.getY(i);
			
			if (x < Gdx.graphics.getWidth()/2){
				leftPressed = true;
			}
			if (x > Gdx.graphics.getWidth()/2){
				rightPressed = true;
			}

		}
		
		player.setLeft(leftPressed);
		player.setRight(rightPressed);
		
		player.update();
		gameCameraController.update();
		
		world.step(1/30f, 2, 6);
		
	}

	@Override
	public void draw(float delta) {
		
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.2f, 1.0f);   
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    renderer.render(world, gameCameraController.getCamera().combined);

	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Player getPlayer(){
		return player;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.R) {
			buildWorld(1);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
