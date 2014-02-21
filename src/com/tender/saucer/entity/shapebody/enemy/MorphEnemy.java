
package com.tender.saucer.entity.shapebody.enemy;

import android.util.FloatMath;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */

public class MorphEnemy extends BasicEnemy
{
	private float dSize;
	private float origSize;
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

		if (health <= 0)
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
