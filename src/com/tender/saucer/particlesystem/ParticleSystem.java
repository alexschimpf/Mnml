package com.tender.saucer.particlesystem;

import java.util.Iterator;
import java.util.LinkedList;

import com.tender.saucer.shapebody.IUpdate;
import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class ParticleSystem implements IUpdate
{
	private LinkedList<Particle> particles = new LinkedList<Particle>();

	private ParticleSystem()
	{
	}
	
	private ParticleSystem(ShapeBody shapeBody, int numParticles)
	{
		for(int i = 0; i < numParticles; i++)
		{
			Particle particle = Particle.buildRandomParticle(shapeBody);
			particles.add(particle);
		}
	}

	public static void init(ShapeBody shapeBody)
	{
		ParticleSystem ps = new ParticleSystem(shapeBody, Constants.NUM_PARTICLES_PER_SYSTEM);
		ps.attachToScene();
		Model.actives.add(ps);
	}
	
	public static void init(ShapeBody shapeBody, int numParticles)
	{
		ParticleSystem ps = new ParticleSystem(shapeBody, numParticles);
		ps.attachToScene();
		Model.actives.add(ps);
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
				particle.done();
				it.remove();
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
