package com.bioh.postal.utilites;

import com.badlogic.gdx.math.Vector2;

public class BiohMath {

	public static float getVectorAngle(Vector2 vector){
		return (float) (Math.atan2(vector.y, vector.x) * 180/Math.PI);
	}

}
