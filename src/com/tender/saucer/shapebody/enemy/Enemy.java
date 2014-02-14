package com.tender.saucer.shapebody.enemy;

import android.util.Log;

import com.tender.saucer.collision.BodyData;
import com.tender.saucer.shapebody.TargetShapeBody;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.WaveMachine;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public abstract class Enemy extends TargetShapeBody
{	
	public float health;
	
	protected Enemy() 
	{
	}
	
	public static Enemy buildRandomEnemy()
	{			
		try 
		{
			int choice = (int)(Math.random() * Model.waveMachine.currEnemyTypes.size());
			Enemy enemy = (Enemy)Model.waveMachine.currEnemyTypes.get(choice).newInstance();
			enemy.body.setUserData(new BodyData(enemy));			
			Model.transients.add(enemy);
			
			return enemy;		
		} 
		catch (Exception e) 
		{
			return null;
		}
	}
}
