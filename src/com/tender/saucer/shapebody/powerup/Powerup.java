package com.tender.saucer.shapebody.powerup;

import java.util.Calendar;

import com.tender.saucer.shapebody.BodyData;
import com.tender.saucer.shapebody.ICollide;
import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.shapebody.TargetShapeBody;
import com.tender.saucer.shapebody.player.Player;
import com.tender.saucer.shapebody.shot.Shot;
import com.tender.saucer.shapebody.wall.Wall;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class Powerup extends TargetShapeBody
{
	protected Powerup() 
	{		
		//TODO
	}

	private static Powerup buildRandomPowerup()
	{
		Powerup powerup = new Powerup();
		powerup.body.setUserData(new BodyData(powerup));
		
		Model.actives.add(powerup);
		
		return powerup;
	}

	public boolean update() 
	{	
		return false;
	}

	public void collide(ICollide other) 
	{
		
	}
}
