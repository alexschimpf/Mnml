package com.tender.saucer.particle;

import java.util.Calendar;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.util.color.Color;

import android.util.Log;

import com.tender.saucer.color.ColorUtilities;
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

public class Particle implements ITransientUpdate
{
	public static final float DEFAULT_SIZE = 5;
	public static final int DEFAULT_MAX_DURATION = 2000;
	
	private Rectangle rect;
	private long startTime;
	private float vx, vy;
	private float duration;

	public Particle()
	{
		
	}
	
	public Particle(ShapeBody shapeBody)
	{
		this(shapeBody, Particle.DEFAULT_MAX_DURATION);	
	}
	
	public Particle(ShapeBody shapeBody, float maxDuration)
	{
		IAreaShape shape = shapeBody.shape;
		float x = shape.getX() + (shape.getWidth() / 2);
		float y = shape.getY() + (shape.getHeight() / 2);
		Color color = shape.getColor();
		
		rect = new Rectangle(x, y, Particle.DEFAULT_SIZE, Particle.DEFAULT_SIZE, Model.main.getVertexBufferObjectManager()); 
		rect.setColor(color);
		
		int dirx = Math.random() < .5 ? -1 : 1;
		int diry = Math.random() < .5 ? -1 : 1;
		vx = dirx * (float)Math.max(1, Math.random() * 2);
		vy = diry * (float)Math.max(1, Math.random() * 2);
		duration = (float)Math.max(500, Math.random() * maxDuration);
		
		startTime = Calendar.getInstance().getTimeInMillis();	
	}

	public Particle set(ShapeBody shapeBody)
	{
		set(shapeBody, Particle.DEFAULT_MAX_DURATION);
		return this;
	}
	
	public Particle set(ShapeBody shapeBody, float maxDuration)
	{
		IAreaShape shape = shapeBody.shape;
		float x = shape.getX() + (shape.getWidth() / 2);
		float y = shape.getY() + (shape.getHeight() / 2);
		Color color = shape.getColor();
		
		if(rect == null)
		{
			rect = new Rectangle(x, y, Particle.DEFAULT_SIZE, Particle.DEFAULT_SIZE, Model.main.getVertexBufferObjectManager()); 
		}
		else
		{		
			rect.setX(x);
			rect.setY(y);
		}
		rect.setColor(color);
		
		int dirx = Math.random() < .5 ? -1 : 1;
		int diry = Math.random() < .5 ? -1 : 1;
		vx = dirx * (float)Math.max(1, Math.random() * 2);
		vy = diry * (float)Math.max(1, Math.random() * 2);
		duration = (float)Math.max(500, Math.random() * maxDuration);
		
		startTime = Calendar.getInstance().getTimeInMillis();
		
		return this;
	}

	public boolean update() 
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - startTime;
		if(timeElapsed > duration)
		{
			return true;
		}
		
		rect.setX(rect.getX() + vx);
		rect.setY(rect.getY() + vy);		
		rect.setAlpha(Math.max(0, rect.getAlpha() - .009f));
		
		return false;
	}

	public void done() 
	{
		Model.scene.detachChild(rect);
		ParticlePool.recycle(this);
	}
	
	public void attachToScene()
	{
		Model.scene.attachChild(rect);
	}
}
