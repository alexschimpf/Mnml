package com.tender.saucer.shapebody;

import com.tender.saucer.collision.ICollide;
import com.tender.saucer.update.ITransientUpdate;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public abstract class DynamicShapeBody extends ShapeBody implements ICollide, ITransientUpdate 
{
	public float speed;
	
	public DynamicShapeBody() 
	{
		super();
	}
	
	public void done()
	{
		recycle();
	}
	
	public void setInMotion()
	{
		body.setLinearVelocity(0, speed);
	}	
}
