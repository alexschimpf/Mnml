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

public class Powerup extends TargetShapeBody
{
	protected Powerup() 
	{		
		// Todo
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
		if(other instanceof Player)
		{
			((BodyData)body.getUserData()).remove = true;
		}
		else if(other instanceof Wall)
		{
			((BodyData)body.getUserData()).remove = true;
		}
		else if(other instanceof Shot)
		{
			((BodyData)body.getUserData()).remove = true;
		}
	}
}
