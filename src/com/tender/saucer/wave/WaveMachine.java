package com.tender.saucer.wave;

import java.util.Calendar;
import java.util.LinkedList;

import android.util.Log;

import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.shapebody.enemy.Enemy;
import com.tender.saucer.shapebody.powerup.Powerup;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.update.IPersistentUpdate;
import com.tender.saucer.update.ITransientUpdate;
import com.tender.saucer.update.UpdateHandler;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public final class WaveMachine implements IPersistentUpdate
{
	public int level = 1;	
	public int currNumEnemiesLeft = 10;	
	public int currNumPowerupsLeft = 0;
	public LinkedList<Class<?>> currEnemyTypes = new LinkedList<Class<?>>();
	
	private int numEnemyBuildsLeft = 10;
	private long lastEnemyBuildTime = 0;
	private float enemyBuildCooldown = Constants.DEFAULT_WAVE_ENEMY_BUILD_COOLDOWN;	
	private long lastPowerupBuildTime;
	private float powerupBuildCooldown = Constants.DEFAULT_WAVE_POWERUP_BUILD_COOLDOWN;
	
	public WaveMachine()
	{
		initCurrEnemyTypes();
		enemyBuildCooldown = Math.max(700, Constants.DEFAULT_WAVE_ENEMY_BUILD_COOLDOWN - (level * 100));
		lastPowerupBuildTime = Calendar.getInstance().getTimeInMillis();
	}
	
	public WaveMachine(int level) 
	{	
		this.level = level;
		initCurrEnemyTypes();
		currNumEnemiesLeft = level * 10;
		numEnemyBuildsLeft = currNumEnemiesLeft;
		enemyBuildCooldown = Math.max(700, Constants.DEFAULT_WAVE_ENEMY_BUILD_COOLDOWN - (level * 100));
		lastPowerupBuildTime = Calendar.getInstance().getTimeInMillis();
	}

	public void beginNextWave()
	{	
		ColorScheme.repaint();
		
		level++;
		initCurrEnemyTypes();
		lastEnemyBuildTime = 0;
		currNumEnemiesLeft = level * 10;
		numEnemyBuildsLeft = currNumEnemiesLeft;
		enemyBuildCooldown = Math.max(700, Constants.DEFAULT_WAVE_ENEMY_BUILD_COOLDOWN - (level * 100));
		lastPowerupBuildTime = Calendar.getInstance().getTimeInMillis();
		
		Model.state = GameState.WAVE_RUNNING;
	}
	
	public void update()
	{
		if(currNumEnemiesLeft <= 0 && currNumPowerupsLeft <= 0)
		{
			Model.state = GameState.WAVE_INTERMISSION;
			Model.waveIntermission.beginNextIntermission();
		}
		else 
		{
			Enemy enemy = tryBuildRandomEnemy();
			if(enemy != null)
			{
				enemy.attachToScene();
				enemy.setInMotion();
			}
			
			Powerup powerup = tryBuildRandomPowerup();
			if(powerup != null)
			{
				powerup.attachToScene();
				powerup.setInMotion();
			}
		}
	}

	private Enemy tryBuildRandomEnemy()
	{	
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - lastEnemyBuildTime;
		if(timeElapsed > enemyBuildCooldown && numEnemyBuildsLeft > 0)
		{
			numEnemyBuildsLeft--;
			lastEnemyBuildTime = currTime;
			return Enemy.buildRandomEnemy();
		}
		
		return null;
	}
	
	private Powerup tryBuildRandomPowerup()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - lastPowerupBuildTime;
		if(timeElapsed > powerupBuildCooldown)
		{
			currNumPowerupsLeft++;
			lastPowerupBuildTime = currTime;
			return Powerup.buildRandomPowerup();
		}
		
		return null;
	}
	
	private void initCurrEnemyTypes()
	{
		currEnemyTypes.clear();

		int numEnemyTypes = Constants.ENEMY_CLASSES.length;
		for(int i = 0; i < numEnemyTypes; i++)
		{
			int choice = (int)(Math.random() * numEnemyTypes);
			currEnemyTypes.add(Constants.ENEMY_CLASSES[choice]);
		}
	}
}
