package com.tender.saucer.entity.shapebody;

import android.util.FloatMath;

import com.tender.saucer.stuff.Constants;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public abstract class TargetShapeBody extends DynamicShapeBody
{
	public float tx;
	
	protected TargetShapeBody() 
	{
	}
	
	@Override
	public void setInMotion()
	{
		float dx = tx - shape.getX();
		if(dx < 0) 
		{
			speed = 0 - speed;
		}
		float dy = Constants.CAMERA_HEIGHT - Constants.TOP_BOT_HEIGHT - shape.getY();
		float theta = (float)Math.atan(dy / dx);	
		
		float vx = speed * FloatMath.cos(theta);
		float vy = speed * FloatMath.sin(theta);	
		body.setLinearVelocity(vx, vy);
	}	
}
