package com.tender.saucer.shapebody.enemy;

import android.util.FloatMath;
import android.util.Log;

import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.WaveMachine;

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
		dSize = origSize / 2;
	}

	public boolean update() 
	{
		sineArg += .05;
		
		if(health <= 0)
		{
			Model.instance().waveMachine.currNumEnemiesLeft--;
			return true;
		}
		else
		{
			float size = origSize + (FloatMath.sin(sineArg) * dSize);
			shape.setSize(size, size);
		}
		
		return false;
	}
}
