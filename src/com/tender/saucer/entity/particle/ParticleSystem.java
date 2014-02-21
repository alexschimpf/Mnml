
package com.tender.saucer.entity.particle;

import java.util.Iterator;
import java.util.LinkedList;

import org.andengine.util.color.Color;

import com.tender.saucer.entity.Entity;
import com.tender.saucer.entity.shapebody.ShapeBody;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.update.ITransientUpdate;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public class ParticleSystem extends Entity implements ITransientUpdate
{
	public static final int DEFAULT_NUM_PARTICLES = 20;

	public static void begin(ShapeBody shapeBody)
	{
		begin(shapeBody, null, ParticleSystem.DEFAULT_NUM_PARTICLES, Particle.DEFAULT_MAX_DURATION);
	}

	public static void begin(ShapeBody shapeBody, Color color)
	{
		begin(shapeBody, color, ParticleSystem.DEFAULT_NUM_PARTICLES, Particle.DEFAULT_MAX_DURATION);
	}

	public static void begin(ShapeBody shapeBody, Color color, int numParticles)
	{
		begin(shapeBody, color, numParticles, Particle.DEFAULT_MAX_DURATION);
	}

	public static void begin(ShapeBody shapeBody, Color color, int numParticles, float maxDuration)
	{
		ParticleSystem ps = new ParticleSystem(shapeBody, color, numParticles, maxDuration);
		ps.attachToScene();
		Model.transients.add(ps);
	}

	public static void begin(ShapeBody shapeBody, int numParticles)
	{
		begin(shapeBody, null, numParticles, Particle.DEFAULT_MAX_DURATION);
	}

	private LinkedList<Particle> particles = new LinkedList<Particle>();

	private ParticleSystem()
	{
	}

	private ParticleSystem(ShapeBody shapeBody, Color color, int numParticles, float maxDuration)
	{
		for (int i = 0; i < numParticles; i++)
		{
			Particle particle = ParticlePool.obtain(shapeBody, color, maxDuration);
			Model.main.addOnResumeGameListener(particle);
			particles.add(particle);
		}
	}

	@Override
	public void attachToScene()
	{
		for (Particle particle : particles)
		{
			particle.attachToScene();
		}
	}

	public void done()
	{
	}

	public boolean update()
	{
		if (particles.size() == 0)
		{
			return true;
		}
		Iterator<Particle> it = particles.iterator();
		while (it.hasNext())
		{
			Particle particle = it.next();
			if (particle.update())
			{
				it.remove();
				particle.done();
			}
		}
		return false;
	}
}
