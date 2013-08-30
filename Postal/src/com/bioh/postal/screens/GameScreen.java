package com.bioh.postal.screens;

import java.util.ArrayList;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.bioh.postal.Postal;
import com.bioh.postal.controllers.BackgroundCameraController;
import com.bioh.postal.controllers.GameCameraController;
import com.bioh.postal.controllers.ParticleController;
import com.bioh.postal.levelbuilders.GenericLevelBuilder;
import com.bioh.postal.levelbuilders.LevelBuilder1;
import com.bioh.postal.levelbuilders.LevelBuilder2;
import com.bioh.postal.objects.BackgroundLayer;
import com.bioh.postal.objects.GenericObject;
import com.bioh.postal.objects.Mothership;
import com.bioh.postal.objects.Player;
import com.bioh.postal.utilites.BodyLoader;

public class GameScreen extends GenericScreen implements InputProcessor{
	
	public OrthogonalTiledMapRenderer tiledMapRenderer;
	
	private Box2DDebugRenderer renderer;
	private World world;
	private GenericLevelBuilder levelBuilder;
	private GameCameraController gameCameraController;
	private BackgroundCameraController backgroundCameraController;
	private ParticleController particleController;
	private BodyLoader loader;
	private Postal postal;
	
	private SpriteBatch backgroundBatch;
	private SpriteBatch gameBatch;
	private SpriteBatch hudBatch;
	
	private boolean leftPressed;
	private boolean rightPressed;
	
	private Integer score;
	private int currentLevel;
	
	private ArrayList<GenericObject> objects = new ArrayList<GenericObject>();
	private ArrayList<BackgroundLayer> backgroundLayers = new ArrayList<BackgroundLayer>();

	private BitmapFont font;

	public GameScreen(int level){
		
		Gdx.input.setInputProcessor(this);
		
		backgroundBatch = new SpriteBatch();
		gameBatch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		
		buildWorld(level);
		
	}
	
	public void buildWorld(int level){
		
		postal = Postal.getInstance();
		
		this.currentLevel = level;
		
		loader = new BodyLoader(Gdx.files.internal("physics/postal.json"));
		
		world = new World(new Vector2(0, -9.8f), false);
		particleController = new ParticleController();
		
		//will control what level we build
		//loads a different builder based on level
		switch (currentLevel){
		case 1:
			levelBuilder = new LevelBuilder1(this);
			break;
		case 2:
			levelBuilder = new LevelBuilder2(this);
			break;
		case 3:
			break;
		}
		
		//physics renderer, this can be taken out at a later date once we have textures and actual graphics to see where
		//the physics objects are on the screen
		renderer = new Box2DDebugRenderer();
		
		tiledMapRenderer = new OrthogonalTiledMapRenderer(levelBuilder.getMap(), 0.5f);
		
		gameCameraController = new GameCameraController(this);
		backgroundCameraController = new BackgroundCameraController(this);
		
		score = 0;
		
		font = new BitmapFont();

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
		
		//update each of the background layers
		for (int i = 0; i < backgroundLayers.size(); i++){
			backgroundLayers.get(i).update(gameCameraController.getCamera().position.x - levelBuilder.getWidth() / 2, gameCameraController.getCamera().position.y);
		}
		
		//update each of the particles
		particleController.update(delta, levelBuilder.getPlayer());
		
		//update camera controller separately...its not really a dynamic object
		gameCameraController.update();
		
		cleanupObjects();
		world.step(1/30f, 2, 6);
		
		
	}

	@Override
	public void draw(float delta) {
		
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.2f, 1.0f);   
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    //start background draw
	    backgroundBatch.setProjectionMatrix(backgroundCameraController.getCamera().combined);
	    backgroundBatch.begin();
	    for (int i = 0; i < backgroundLayers.size(); i++){
			backgroundLayers.get(i).draw(backgroundBatch);
		}
	    backgroundBatch.end(); 
	    //end background draw
	    
	    //start map draw
	    tiledMapRenderer.setView((OrthographicCamera) gameCameraController.getCamera());
	    tiledMapRenderer.render();
		//renderer.render(world, gameCameraController.getCamera().combined);
		//end map draw
		
		//start game draw
		gameBatch.setProjectionMatrix(gameCameraController.getCamera().combined);
		gameBatch.begin();
		for (int i = 0; i < objects.size(); i++){
			objects.get(i).draw(gameBatch);
		}
		
		float offsetX = gameCameraController.getCamera().viewportWidth * gameCameraController.getCamera().zoom/2 - 10;
		float offsetY = gameCameraController.getCamera().viewportHeight * gameCameraController.getCamera().zoom/2 - 20;
		
		// Draw score
		font.draw(gameBatch, score.toString(), gameCameraController.getCamera().position.x - offsetX, gameCameraController.getCamera().position.y - offsetY);
				
		//draw particles
		particleController.draw(gameBatch);
		gameBatch.end();
		
		//end game draw

	}
	
	public Player getPlayer(){
		return levelBuilder.getPlayer();
	}
	
	public void incrementScore() {
		score++;
	}
	
	public Map getMap(){
		return levelBuilder.getMap();
	}
	
	public Mothership getMothership() {
		return levelBuilder.getMothership();
	}
	
	public void addObject(GenericObject object){
		objects.add(object);
	}
	
	public void addLayer(BackgroundLayer layer){
		backgroundLayers.add(layer);
	}
	
	public void addParticleEffect(ParticleEffect effect){
		particleController.addParticleEffect(effect);
	}
	
	public void addFixture(Body body, String name, FixtureDef fixtureDef, float width){
		loader.attachFixture(body, name, fixtureDef, width);
	}
	
	public void cleanupObjects() {
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i).flaggedForDelete) {
				GenericObject obj = objects.remove(i);
				Body body = obj.getBody();
				world.destroyBody(body);
			}
		}
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
			buildWorld(currentLevel);
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
