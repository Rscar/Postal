package com.bioh.postal.levelbuilders;

import com.bioh.postal.objects.Player;

public abstract class GenericLevelBuilder {
	
	public abstract void build();
	
	public abstract void loadAssets();
	
	public abstract Player getPlayer();

}
