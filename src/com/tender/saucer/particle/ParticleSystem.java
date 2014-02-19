package com.tender.saucer.particle;

import java.util.Iterator;
import java.util.LinkedList;

import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.update.ITransientUpdate;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class ParticleSystem implements ITransientUpdate
{
	public static final int DEFAULT_NUM_PARTICLES = 20;
	
	private static final ParticlePool PARTICLE_POOL = new ParticlePool(500);

	private LinkedList<Particle> particles = new LinkedList<Particle>();
	
	private ParticleSystem()
	{
	}
	
	private ParticleSystem(ShapeBody shapeBody, int numParticles)
	{
		this(shapeBody, numParticles, Particle.DEFAULT_MAX_DURATION);
	}
	
	private ParticleSystem(ShapeBody shapeBody, int numParticles, float maxDuration)
	{
		for(int i = 0; i < numParticles; i++)
		{
			Particle particle = PARTICLE_POOL.obtainParticle(shapeBody, maxDuration);
			particles.add(particle);
		}
	}

	public static void begin(ShapeBody shapeBody)
	{
		begin(shapeBody, ParticleSystem.DEFAULT_NUM_PARTICLES);
	}
	
	public static void begin(ShapeBody shapeBody, int numParticles)
	{
		begin(shapeBody, numParticles, Particle.DEFAULT_MAX_DURATION);
	}
	
	public static void begin(ShapeBody shapeBody, int numParticles, float maxDuration)
	{
		ParticleSystem ps = new ParticleSystem(shapeBody, numParticles, maxDuration);
		ps.attachToScene();
		Model.transients.add(ps);
	}

	public boolean update() 
	{
		if(particles.size() == 0)
		{
			return true;
		}
		
		Iterator<Particle> it = particles.iterator();
		while(it.hasNext())
		{
			Particle particle = it.next();
			if(particle.update())
			{
				it.remove();
				particle.done();
			}
		}
		
		return false;
	}

	public void done() 
	{
	}
	
	private void attachToScene()
	{
		for(Particle particle : particles)
		{
			particle.attachToScene();
		}
	}
}
