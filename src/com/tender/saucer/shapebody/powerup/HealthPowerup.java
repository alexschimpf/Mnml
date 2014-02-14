package com.tender.saucer.shapebody.powerup;

import com.tender.saucer.stuff.Model;

public class HealthPowerup extends Powerup
{
	protected HealthPowerup() 
	{
		super();
	}

	@Override
	public void apply() 
	{
		Model.player.health++;
	}
	
	@Override
	public void remove() 
	{
	}
}
