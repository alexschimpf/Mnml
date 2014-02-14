package com.tender.saucer.collision;


/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class BodyData 
{
	public ICollide owner;
	
	public BodyData(ICollide owner) 
	{
		this.owner = owner;
	}
}
