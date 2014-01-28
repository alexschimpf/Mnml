package com.tender.saucer.shapebody.enemy;

import java.util.Calendar;

import org.andengine.util.color.Color;

import com.tender.saucer.shapebody.BodyData;
import com.tender.saucer.stuff.ColorScheme;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.WaveMachine;

public class PenaltyEnemy extends BasicEnemy
{
	private long lastFlashTime = 0;
	private float flashCooldown = 150;
	
	protected PenaltyEnemy() 
	{	
		super();		
	}
	
	@Override
	public boolean update()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - lastFlashTime;
		if(timeElapsed > flashCooldown)
		{
			lastFlashTime = currTime;
			
			if(shape.getColor().equals(ColorScheme.enemy))
			{
				shape.setColor(Color.WHITE);
			}
			else
			{
				shape.setColor(ColorScheme.enemy);
			}
		}
		
		if(health <= 0)
		{
			Model.waveMachine.currNumEnemiesLeft--;
			return true;
		}
		
		return false;
	}
}
