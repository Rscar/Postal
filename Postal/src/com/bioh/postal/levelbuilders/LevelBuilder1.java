package com.bioh.postal.levelbuilders;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.bioh.postal.objects.MountedCube;
import com.bioh.postal.objects.Player;
import com.bioh.postal.objects.StaticBox;
import com.bioh.postal.screens.GameScreen;
import com.bioh.postal.Postal;

public class LevelBuilder1 extends GenericLevelBuilder{
	
	private Player player;
	private GameScreen gameScreen;
	private Postal postal;
	
	public LevelBuilder1(GameScreen gameScreen){
		
		this.gameScreen = gameScreen;
		this.postal = Postal.getInstance();
		
		loadAssets();
		build();
	}

	@Override
	public void build() {	
		
		//build some boxes, static
		//they don't need to be added to the gamescreen object arraylist because 
		//they wont be updated, they are created and sit there
		new StaticBox(1000, 4, new Vector2(300,-2), gameScreen);
		new StaticBox(60, 150, new Vector2(150,75), gameScreen);
		new StaticBox(10, 20, new Vector2(140,160), gameScreen);
		new StaticBox(150, 80, new Vector2(0,40), gameScreen);
		new StaticBox(110, 20, new Vector2(0,90), gameScreen);
		new StaticBox(70, 20, new Vector2(0,110), gameScreen);
		new StaticBox(20, 20, new Vector2(20,130), gameScreen);
		new StaticBox(100, 40, new Vector2(370,20), gameScreen);
		new StaticBox(60, 70, new Vector2(450,35), gameScreen);
		new StaticBox(80, 250, new Vector2(600,125), gameScreen);
		
		//add some dynamic objects, update and draw functions will be called by the gamescreen
		//through referencing the objects arraylist
		gameScreen.addObject(new MountedCube(new Vector2(270,30), gameScreen));
		gameScreen.addObject(new MountedCube(new Vector2(500,30), gameScreen));
		gameScreen.addObject(new MountedCube(new Vector2(100, 30), gameScreen));
		
		//create a player, we name the object because it is heavily referenced by classes that "know" the gamescreen
		player = new Player(gameScreen);
		gameScreen.addObject(player);

	}

	@Override
	public void loadAssets() {
		// Load assets
		postal.assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		postal.assetManager.load("maps/test.tmx", TiledMap.class);
		
		
		// Block while loading assets.
		while(!postal.assetManager.update()) {
			
		};
	}

	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}

}
