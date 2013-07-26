package com.bioh.postal.levelbuilders;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
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
	TiledMap map;
	MapLayer tempLayer;
	
	MapObject tempObject;
	Polygon tempPolygon;
	int xTemp;
	int yTemp;
	
	public LevelBuilder1(GameScreen gameScreen){
		
		this.gameScreen = gameScreen;
		this.postal = Postal.getInstance();
		
		loadAssets();
		build();
	}

	@Override
	public void build() {	
		
		
		System.out.println("Trying to get map, amount loaded: " + postal.assetManager.getLoadedAssets());
		map = postal.assetManager.get("maps/level1.tmx", TiledMap.class);
		
		
		//for the static object layer
		//loop through objects, adding the polygons defined in the tmx file
		//we need to scale, considering map renders at .5 size
		tempLayer = map.getLayers().get("static");

		for (int i = 0; i < tempLayer.getObjects().getCount(); i++){
			tempObject = tempLayer.getObjects().get(i);

			tempPolygon = ((PolygonMapObject) tempObject).getPolygon();
			
			xTemp = (Integer) tempObject.getProperties().get("x") / 2;
			yTemp = (Integer) tempObject.getProperties().get("y") / 2;
			
			new StaticBox(new Vector2(xTemp, yTemp), tempPolygon, gameScreen);
		}
		
		
		//open up player layer
		//there is only one player, but just for sake of convention it is in a for loop
		//add the player
		tempLayer = map.getLayers().get("player");

		for (int i = 0; i < tempLayer.getObjects().getCount(); i++){
			tempObject = tempLayer.getObjects().get(i);
	
			xTemp = (Integer) tempObject.getProperties().get("x") / 2;
			yTemp = (Integer) tempObject.getProperties().get("y") / 2;
			
			//create a player, we name the object because it is heavily referenced by classes that "know" the gamescreen
			player = new Player(new Vector2(xTemp, yTemp), gameScreen);
			gameScreen.addObject(player);

		}
		
		
		//open the blocks layer
		//loop through the objects, add blocks
		tempLayer = map.getLayers().get("blocks");

		for (int i = 0; i < tempLayer.getObjects().getCount(); i++){
			tempObject = tempLayer.getObjects().get(i);
	
			xTemp = (Integer) tempObject.getProperties().get("x") / 2;
			yTemp = (Integer) tempObject.getProperties().get("y") / 2;
			
			gameScreen.addObject(new MountedCube(new Vector2(xTemp, yTemp), gameScreen));
		}


	}

	@Override
	public void loadAssets() {
		// Load assets
		postal.assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		postal.assetManager.load("maps/level1.tmx", TiledMap.class);
		
		
		// Block while loading assets.
		while(!postal.assetManager.update()) {
			
		};
	}

	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}

	@Override
	public TiledMap getMap() {
		return map;
	}

}
