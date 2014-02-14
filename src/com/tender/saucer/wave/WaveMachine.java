package com.tender.saucer.wave;

import java.util.Calendar;
import java.util.LinkedList;

import android.util.Log;

import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.shapebody.enemy.Enemy;
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
	public LinkedList<Class<?>> currEnemyTypes = new LinkedList<Class<?>>();
	
	private int numBuildsLeft = 10;
	private long lastBuildTime = 0;
	private float buildCooldown = Constants.DEFAULT_WAVE_BUILD_COOLDOWN;
	
	public WaveMachine()
	{
		this.level = 1;
		initCurrEnemyTypes();
		lastBuildTime = 0;
		currNumEnemiesLeft = 10;
		numBuildsLeft = currNumEnemiesLeft;
		buildCooldown = Math.max(700, Constants.DEFAULT_WAVE_BUILD_COOLDOWN - (level * 100));
	}
	
	public WaveMachine(int level) 
	{	
		this.level = level;
		initCurrEnemyTypes();
		lastBuildTime = 0;
		currNumEnemiesLeft = level * 10;
		numBuildsLeft = currNumEnemiesLeft;
		buildCooldown = Math.max(700, Constants.DEFAULT_WAVE_BUILD_COOLDOWN - (level * 100));
	}

	public void beginNextWave()
	{	
		ColorScheme.repaint();
		
		level++;
		initCurrEnemyTypes();
		lastBuildTime = 0;
		currNumEnemiesLeft = level * 10;
		numBuildsLeft = currNumEnemiesLeft;
		buildCooldown = Math.max(700, Constants.DEFAULT_WAVE_BUILD_COOLDOWN - (level * 100));
		
		Model.state = GameState.WAVE_RUNNING;
	}
	
	public void update()
	{
		if(currNumEnemiesLeft <= 0)
		{
			Model.state = GameState.WAVE_INTERMISSION;
			Model.waveIntermission.beginNextIntermission();
		}
		else 
		{
			Enemy enemy = tryBuildRandomEnemy();
			if(enemy != null)
			{
				numBuildsLeft--;
				enemy.attachToScene();
				enemy.setInMotion();
			}
		}
	}

	private Enemy tryBuildRandomEnemy()
	{	
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - lastBuildTime;
		if(timeElapsed > buildCooldown && numBuildsLeft > 0)
		{
			lastBuildTime = currTime;
			return Enemy.buildRandomEnemy();
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
