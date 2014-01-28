package com.tender.saucer.shapebody;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public abstract class DynamicShapeBody extends ShapeBody implements ICollide, IUpdate 
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
