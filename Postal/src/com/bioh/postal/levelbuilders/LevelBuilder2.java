package com.bioh.postal.levelbuilders;

import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.bioh.postal.objects.BackgroundLayer;
import com.bioh.postal.objects.HudScore;
import com.bioh.postal.screens.GameScreen;
import com.bioh.postal.Postal;

public class LevelBuilder2 extends GenericLevelBuilder{
	
	
	
	public LevelBuilder2(GameScreen gameScreen){
		
		this.gameScreen = gameScreen;
		this.postal = Postal.getInstance();
		
		loadAssets();
		addBackgroundLayers();
		build();
	}
	
	public void addBackgroundLayers(){
		gameScreen.addLayer(new BackgroundLayer(postal.assetManager.get("sprites/background_1_3.png", Texture.class), postal.assetManager.get("sprites/background_1_3.png", Texture.class), 10f));
		gameScreen.addLayer(new BackgroundLayer(postal.assetManager.get("sprites/background_1_2_left.png", Texture.class), postal.assetManager.get("sprites/background_1_2_right.png", Texture.class), 5f));
		gameScreen.addLayer(new BackgroundLayer(postal.assetManager.get("sprites/background_1_1_left.png", Texture.class), postal.assetManager.get("sprites/background_1_1_right.png", Texture.class), 1.2f));
		
		gameScreen.addHudObject(new HudScore(gameScreen));
	}

	@Override
	public void loadAssets() {
		// Load assets
		postal.assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		postal.assetManager.load("maps/level2.tmx", TiledMap.class);
		postal.assetManager.setLoader(Texture.class, new TextureLoader(new InternalFileHandleResolver()));
		postal.assetManager.load("sprites/cube.png", Texture.class);
		postal.assetManager.load("sprites/lasercow.png", Texture.class);
		postal.assetManager.load("sprites/lasercowgun.png", Texture.class);
		postal.assetManager.load("sprites/platform.png", Texture.class);
		postal.assetManager.load("sprites/ufo.png", Texture.class);
		postal.assetManager.load("sprites/background_1_1_left.png", Texture.class);
		postal.assetManager.load("sprites/background_1_1_right.png", Texture.class);
		postal.assetManager.load("sprites/background_1_2_left.png", Texture.class);
		postal.assetManager.load("sprites/background_1_2_right.png", Texture.class);
		postal.assetManager.load("sprites/background_1_3.png", Texture.class);

		// Block while loading assets.
		while(!postal.assetManager.update()) {
			
		};

		System.out.println("Trying to get map, number of files loaded: " + postal.assetManager.getLoadedAssets());
		map = postal.assetManager.get("maps/level2.tmx", TiledMap.class);
	}

}
