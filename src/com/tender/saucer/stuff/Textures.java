package com.tender.saucer.stuff;

public class Textures 
{
	private static Textures instance;

	private Textures() 
	{
		
	}
	
	public static void reset()
	{
		instance = null;
	}
	
	public static Textures instance()
	{
		if(instance == null)
		{
			instance = new Textures();
		}
		
		return instance;
	}
}
