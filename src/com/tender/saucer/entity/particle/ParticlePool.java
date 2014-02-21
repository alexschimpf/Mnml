
package com.tender.saucer.entity.particle;

import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.color.Color;

import com.tender.saucer.entity.shapebody.ShapeBody;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public class ParticlePool
{
	private static final GenericPool<Particle> POOL = new GenericPool<Particle>()
	{
		@Override
		protected Particle onAllocatePoolItem()
		{
			return new Particle();
		}
	};

	public static Particle obtain(ShapeBody shapeBody, Color color, float maxDuration)
	{
		return POOL.obtainPoolItem().set(shapeBody, color, maxDuration);
	}

	public static void recycle(Particle particle)
	{
		Model.main.removeOnResumeGameListener(particle);
		POOL.recyclePoolItem(particle);
	}
}
