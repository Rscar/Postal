package com.bioh.postal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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

public class GameScreen extends GenericScreen{
	
	
	private Box2DDebugRenderer renderer;
	private World world;
	private Player player;
	private GenericLevelBuilder levelBuilder;
	private GameCameraController gameCameraController;
	
	private boolean leftPressed;
	private boolean rightPressed;
	
	
	public GameScreen(int level){
		
		gameCameraController = new GameCameraController(this);
		world = new World(new Vector2(0, -9.8f), false);
		renderer = new Box2DDebugRenderer();

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
		 
		if(Gdx.input.isKeyPressed(Keys.LEFT)) leftPressed = true;
	    else leftPressed = false;
		
		player.setLeft(leftPressed);
	    
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) rightPressed = true;
	    else rightPressed = false;
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

}
