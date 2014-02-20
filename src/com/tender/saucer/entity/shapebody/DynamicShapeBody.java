package com.tender.saucer.entity.shapebody;

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
		dispose();
	}
	
	public abstract void setInMotion();
}
