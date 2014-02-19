package com.tender.saucer.shapebody.enemy;

import android.util.FloatMath;
import android.util.Log;

import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.WaveMachine;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class MorphEnemy extends BasicEnemy
{
	private float origSize;
	private float dSize;
	private float sineArg;
	
	public MorphEnemy() 
	{
		super();
		
		origSize = shape.getWidth();
		sineArg = 0;
		dSize = origSize / 2;
	}

	@Override
	public boolean update() 
	{
		sineArg += .05;
		
		if(health <= 0)
		{
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
