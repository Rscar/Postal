package com.bioh.postal.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.bioh.postal.Postal;
import com.bioh.postal.controllers.GameCameraController;
import com.bioh.postal.levelbuilders.GenericLevelBuilder;
import com.bioh.postal.levelbuilders.LevelBuilder1;
import com.bioh.postal.objects.GenericObject;
import com.bioh.postal.objects.Player;

public class GameScreen extends GenericScreen implements InputProcessor{
	
	public OrthogonalTiledMapRenderer tiledMapRenderer;
	
	private Box2DDebugRenderer renderer;
	private World world;
	private GenericLevelBuilder levelBuilder;
	private GameCameraController gameCameraController;
	private Postal postal;
	
	private SpriteBatch batch;
	
	private boolean leftPressed;
	private boolean rightPressed;
	
	private ArrayList<GenericObject> objects = new ArrayList<GenericObject>();

	public GameScreen(int level){
		
		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();
		
		buildWorld(level);
		
	}
	
	public void buildWorld(int level){
		
		postal = Postal.getInstance();
		
		world = new World(new Vector2(0, -9.8f), false);
		
		//will control what level we build
		//loads a different builder based on level
		switch (level){
		case 1:
			levelBuilder = new LevelBuilder1(this);
			break;
		case 2:
			break;
		case 3:
			break;
		}
		
		//physics renderer, this can be taken out at a later date once we have textures and actual graphics to see where
		//the physics objects are on the screen
		renderer = new Box2DDebugRenderer();
		
		tiledMapRenderer = new OrthogonalTiledMapRenderer(levelBuilder.getMap(), 0.5f);
		
		gameCameraController = new GameCameraController(this);

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
		
		//loop through multitouches, if its touched, turn on thrusters accordingly
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
		
		levelBuilder.getPlayer().setLeft(leftPressed);
		levelBuilder.getPlayer().setRight(rightPressed);
		
		//update each of the dynamic objects
		for (int i = 0; i < objects.size(); i++){
			objects.get(i).update();
		}
		
		//update camera controller separately...its not really a dynamic object
		gameCameraController.update();
		
		world.step(1/30f, 2, 6);
		
	}

	@Override
	public void draw(float delta) {
		
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.2f, 1.0f);   
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    tiledMapRenderer.setView((OrthographicCamera) gameCameraController.getCamera());
	    tiledMapRenderer.render();
		renderer.render(world, gameCameraController.getCamera().combined);
		
		// Call sprite batch version of draw now
		batch.setProjectionMatrix(gameCameraController.getCamera().combined);
		batch.begin();
		for (int i = 0; i < objects.size(); i++){
			objects.get(i).draw(batch);
		}
		batch.end();	

	}
	
	public Player getPlayer(){
		return levelBuilder.getPlayer();
	}
	
	public Map getMap(){
		return levelBuilder.getMap();
	}
	
	public void addObject(GenericObject object){
		objects.add(object);
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.R) {
			objects.clear();
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
