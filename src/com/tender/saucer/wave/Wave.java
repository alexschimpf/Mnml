package com.tender.saucer.wave;

import java.util.Calendar;

import android.util.Log;

import com.tender.saucer.handler.UpdateHandler;
import com.tender.saucer.shapebody.enemy.Enemy;
import com.tender.saucer.stuff.ColorScheme;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;

public class Wave
{
	public static int level = 0;	
	public static int numEnemiesLeft = 0;	
	public static int numEnemyTypes = 0;
	
	private static int numBuildsLeft = 0;
	private static long lastBuildTime = 0;
	private static float buildCooldown = Constants.DEFAULT_WAVE_BUILD_COOLDOWN;
	
	private Wave() 
	{
	}
	
	public static void reset()
	{
		level = 0;
		numEnemiesLeft = 0;
		numEnemyTypes = 0;
		numBuildsLeft = 0;
		lastBuildTime = 0;
		buildCooldown = Constants.DEFAULT_WAVE_BUILD_COOLDOWN; 
	}
	
	public static void begin()
	{	
		level++;
		ColorScheme.repaint();

		numEnemyTypes = getNumEnemyTypes();

		lastBuildTime = 0;
		numEnemiesLeft = level * 10;
		numBuildsLeft = numEnemiesLeft;
		buildCooldown = Math.max(700, Constants.DEFAULT_WAVE_BUILD_COOLDOWN - (level * 100));
		
		Model.instance().state = GameState.RUNNING;
	}
	
	public static void update()
	{
		if(numEnemiesLeft <= 0)
		{
			WaveIntermission.begin();
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
	
	private static Enemy tryBuildRandomEnemy()
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
	
	private static int getNumEnemyTypes()
	{
		int count = 1;
		
		count += (level >= Constants.ENEMY_BIG_LEVEL) ? 1 : 0;
		count += (level >= Constants.ENEMY_MORPH_LEVEL) ? 1 : 0;
		count += (level >= Constants.ENEMY_SPLIT_LEVEL) ? 1 : 0;
		
		return count;
	}
}
