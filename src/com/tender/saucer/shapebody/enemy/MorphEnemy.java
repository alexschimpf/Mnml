package com.tender.saucer.shapebody.enemy;

import android.util.FloatMath;
import android.util.Log;

import com.tender.saucer.wave.Wave;

public class MorphEnemy extends BasicEnemy
{
	private float origSize;
	private float dSize;
	private float sineArg;
	
	protected MorphEnemy() 
	{
		super();
		
		origSize = shape.getWidth();
		sineArg = 0;
		dSize = origSize / 2; //(float)Math.max(origSize / 3, Math.random() * origSize);
	}

	public boolean update() 
	{
		sineArg += .1;
		
		if(health <= 0)
		{
			Wave.numEnemiesLeft--;
			return true;
		}
		else
		{
			float size = origSize + (FloatMath.sin(sineArg) * dSize);
			shape.setSize(size, size);
			Log.e("schimpf", "" + size);
		}
		
		return false;
	}
}
