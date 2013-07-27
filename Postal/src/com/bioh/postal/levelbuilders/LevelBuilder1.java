package com.bioh.postal.levelbuilders;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.bioh.postal.screens.GameScreen;
import com.bioh.postal.Postal;

public class LevelBuilder1 extends GenericLevelBuilder{
	
	
	
	public LevelBuilder1(GameScreen gameScreen){
		
		this.gameScreen = gameScreen;
		this.postal = Postal.getInstance();
		
		loadAssets();
		build();
	}



	@Override
	public void loadAssets() {
		// Load assets
		postal.assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		postal.assetManager.load("maps/level1.tmx", TiledMap.class);

		// Block while loading assets.
		while(!postal.assetManager.update()) {
			
		};

		System.out.println("Trying to get map, number of files loaded: " + postal.assetManager.getLoadedAssets());
		map = postal.assetManager.get("maps/level1.tmx", TiledMap.class);
	}

}
