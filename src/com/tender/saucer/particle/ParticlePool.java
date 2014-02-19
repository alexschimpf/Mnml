package com.tender.saucer.particle;

import java.util.ArrayList;

import android.util.Log;

import com.tender.saucer.shapebody.ShapeBody;


public class ParticlePool
{
	private ArrayList<Particle> particles;
	private int maxSize;
	
	public ParticlePool(int size)
	{
		this.maxSize = size;
		particles = new ArrayList<Particle>(size);
	}
	
	public Particle obtainParticle()
	{		
		if(particles.size() <= 0)
		{
			allocateParticles();
		}
		
		return particles.remove(particles.size() - 1);
	}
	
	public Particle obtainParticle(ShapeBody shapeBody)
	{
		return obtainParticle().set(shapeBody);
	}
	
	public Particle obtainParticle(ShapeBody shapeBody, float maxDuration)
	{
		return obtainParticle().set(shapeBody, maxDuration);
	}
	
	public void recycleParticle(Particle poolItem)
	{
		if(particles.size() < maxSize)
		{
			particles.add(poolItem);
		}
	}
	
	public void allocateParticles()
	{
		while(particles.size() < maxSize)
		{
			particles.add(new Particle(this));
		}
	}
}