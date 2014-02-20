package com.tender.saucer.particle;

import org.andengine.util.adt.pool.GenericPool;

import com.tender.saucer.shapebody.ShapeBody;

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
	
	public static Particle obtain()
	{
		return POOL.obtainPoolItem();
	}
	
	public static Particle obtain(ShapeBody shapeBody)
	{
		return POOL.obtainPoolItem().set(shapeBody);
	}
	
	public static Particle obtain(ShapeBody shapeBody, float maxDuration)
	{
		return POOL.obtainPoolItem().set(shapeBody, maxDuration);
	}
	
	public static void recycle(Particle particle)
	{
		POOL.recyclePoolItem(particle);
	}
}
