package com.bioh.postal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.bioh.postal.objects.Player;

public class GameScreen extends GenericScreen{
	
	private OrthographicCamera camera;
	private Box2DDebugRenderer renderer;
	private World world;
	private Player player;
	
	private boolean leftPressed;
	private boolean rightPressed;
	
	
	public GameScreen(){

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w,h);
		
		world = new World(new Vector2(0, -9.8f), false);
		renderer = new Box2DDebugRenderer();
		
		BodyDef floorDef = new BodyDef();
	    floorDef.position.set(0,0);
	      
	    Body floorBody = world.createBody(floorDef);
	      
	    PolygonShape floorShape = new PolygonShape();
	    floorShape.setAsBox(30, 2);
	      
	    floorBody.createFixture(floorShape, 0.0f);
		
		player = new Player(this);
		
		camera.zoom = 0.2f;
		camera.translate(0, 30);
		
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
		
		world.step(1/30f, 2, 6);
		
	}

	@Override
	public void draw(float delta) {
		
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.2f, 1.0f);   
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    camera.update();
	    
	    renderer.render(world, camera.combined);
	    
	    
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

}
