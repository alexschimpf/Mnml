package com.tender.saucer.particlesystem;

import java.util.Calendar;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.util.color.Color;

import android.util.Log;

import com.tender.saucer.shapebody.IUpdate;
import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;

public class Particle implements IUpdate
{
	private Rectangle rect;
	private long startTime;
	private float vx, vy;
	private float duration;
	
	private Particle() 
	{
	}
	
	private Particle(Color color, float x, float y) 
	{
		Model model = Model.instance();
		
		rect = new Rectangle(x, y, Constants.PARTICLE_WIDTH, Constants.PARTICLE_HEIGHT, model.main.getVertexBufferObjectManager()); 
		
		if(color == null)
		{
			color = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
		}
		rect.setColor(color);
		
		int dirx = Math.random() < .5 ? -1 : 1;
		int diry = Math.random() < .5 ? -1 : 1;
		vx = dirx * (float)Math.max(1, Math.random() * 2);
		vy = diry * (float)Math.max(1, Math.random() * 2);
		duration = (float)Math.max(500, Math.random() * 2000);
		
		startTime = Calendar.getInstance().getTimeInMillis();
	}
	
	public static Particle buildRandomParticle(float x, float y)
	{
		return new Particle(null, x, y);
	}
	
	public static Particle buildRandomParticle(ShapeBody shapeBody)
	{
		IAreaShape shape = shapeBody.shape;
		float x = shape.getX() + (shape.getWidth() / 2);
		float y = shape.getY() + (shape.getHeight() / 2);
		return new Particle(shape.getColor(), x, y);
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
		
		return false;
	}

	public void done() 
	{
		Model.instance().scene.detachChild(rect);
	}
	
	public void attachToScene()
	{
		Model.instance().scene.attachChild(rect);
	}
}
