package com.tender.saucer.wave;

import java.util.Calendar;

import android.util.Log;

import com.tender.saucer.handler.UpdateHandler;
import com.tender.saucer.shapebody.IUpdate;
import com.tender.saucer.shapebody.enemy.Enemy;
import com.tender.saucer.stuff.ColorScheme;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;

public class WaveMachine implements IUpdate
{
	public int level = 1;	
	public int currNumEnemiesLeft = 10;	
	public int currNumEnemyTypes = 1;
	
	private int numBuildsLeft = 10;
	private long lastBuildTime = 0;
	private float buildCooldown = Constants.DEFAULT_WAVE_BUILD_COOLDOWN;
	
	public WaveMachine()
	{
	}
	
	public WaveMachine(int level) 
	{
		this.level = level;
		currNumEnemyTypes = getCurrNumEnemyTypes();
		lastBuildTime = 0;
		currNumEnemiesLeft = level * 10;
		numBuildsLeft = currNumEnemiesLeft;
		buildCooldown = Math.max(700, Constants.DEFAULT_WAVE_BUILD_COOLDOWN - (level * 100));
	}

	public void beginNextWave()
	{	
		ColorScheme.repaint();
		
		level++;
		currNumEnemyTypes = getCurrNumEnemyTypes();
		lastBuildTime = 0;
		currNumEnemiesLeft = level * 10;
		numBuildsLeft = currNumEnemiesLeft;
		buildCooldown = Math.max(700, Constants.DEFAULT_WAVE_BUILD_COOLDOWN - (level * 100));
		
		Model.state = GameState.WAVE_RUNNING;
	}
	
	public boolean update()
	{
		if(currNumEnemiesLeft <= 0)
		{
			return true;
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
		
		return false;
	}
	
	public void done()
	{
		Model.state = GameState.WAVE_INTERMISSION;
		Model.waveIntermission.beginNextIntermission();
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
	
	private int getCurrNumEnemyTypes()
	{
		int count = 1;	
		count += (level >= Constants.ENEMY_BIG_LEVEL) ? 1 : 0;
		count += (level >= Constants.ENEMY_MORPH_LEVEL) ? 1 : 0;
		count += (level >= Constants.ENEMY_SPLIT_LEVEL) ? 1 : 0;
		count += (level >= Constants.ENEMY_BOUNCE_LEVEL) ? 1 : 0;
		
		return count;
	}
}
