package com.tender.saucer.shapebody;

public class BodyData 
{
	public ICollide owner;
	public boolean remove = false;
	
	public BodyData(ICollide owner) 
	{
		this.owner = owner;
	}
}
