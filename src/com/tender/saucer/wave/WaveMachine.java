
package com.tender.saucer.wave;

import java.util.Calendar;
import java.util.LinkedList;

import com.tender.saucer.achievements.Achievements;
import com.tender.saucer.activity.IOnResumeGameListener;
import com.tender.saucer.collision.BodyData;
import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.entity.shapebody.enemy.Enemy;
import com.tender.saucer.entity.shapebody.enemy.PenaltyEnemy;
import com.tender.saucer.entity.shapebody.powerup.Powerup;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public final class WaveMachine implements IOnResumeGameListener
{
	public static final float DEFAULT_ENEMY_BUILD_COOLDOWN = 2200;
	public static final float DEFAULT_POWERUP_BUILD_COOLDOWN = 22000;
	public static WaveMachine instance;

	public static void beginNextWave()
	{
		ColorScheme.applyNewScheme();

		instance.level++;
		initCurrEnemyTypes();
		instance.lastEnemyBuildTime = 0;
		instance.numEnemiesLeft = instance.level * 10;
		instance.numEnemyBuildsLeft = instance.numEnemiesLeft;
		instance.enemyBuildCooldown = Math.max(700, DEFAULT_ENEMY_BUILD_COOLDOWN - (instance.level * 200));
		instance.powerupBuildCooldown = Math.max(15000, DEFAULT_POWERUP_BUILD_COOLDOWN - (instance.level * 1000));
		instance.lastPowerupBuildTime = Calendar.getInstance().getTimeInMillis();

		Model.state = GameState.WAVE_MACHINE_RUNNING;
	}

	public static void init()
	{
		instance = new WaveMachine();
	}

	public static void update()
	{
		if (instance.numEnemiesLeft <= 0 && instance.numPowerupsLeft <= 0)
		{
			Model.state = GameState.WAVE_RECESS_RUNNING;
			WaveRecess.begin();
		}
		else
		{
			Enemy enemy = tryBuildRandomEnemy();
			if (enemy != null)
			{
				enemy.attachToScene();
				enemy.setInMotion();
			}

			Powerup powerup = tryBuildRandomPowerup();
			if (powerup != null)
			{
				powerup.attachToScene();
				powerup.setInMotion();
			}
		}
	}

	private static Enemy buildRandomEnemy()
	{
		try
		{
			Enemy enemy;
			if (Math.random() <= PenaltyEnemy.DEFAULT_PROBABILITY)
			{
				enemy = new PenaltyEnemy();
			}
			else
			{
				int choice = (int)(Math.random() * instance.enemyTypes.size());
				enemy = (Enemy)instance.enemyTypes.get(choice).newInstance();
			}
			enemy.body.setUserData(new BodyData(enemy));
			Model.transients.add(enemy);

			enemy.addOnEnemyShotListener(Achievements.instance);
			enemy.addOnEnemyMissedListener(Achievements.instance);
			instance.notifyOnEnemyBuildListeners(enemy);

			return enemy;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private static Powerup buildRandomPowerup()
	{
		try
		{
			int choice = (int)(Math.random() * Constants.POWERUP_CLASSES.length);
			Powerup powerup = (Powerup)Constants.POWERUP_CLASSES[choice].newInstance();
			powerup.body.setUserData(new BodyData(powerup));
			powerup.addOnPowerupMissedListener(Achievements.instance);
			Model.transients.add(powerup);

			instance.notifyOnPowerupBuildListeners(powerup);

			return powerup;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private static void initCurrEnemyTypes()
	{
		instance.enemyTypes.clear();

		int numEnemyTypes = Constants.ENEMY_CLASSES.length;
		for (int i = 0; i < numEnemyTypes; i++)
		{
			int choice = (int)(Math.random() * numEnemyTypes);
			instance.enemyTypes.add(Constants.ENEMY_CLASSES[choice]);
		}
	}

	private static Enemy tryBuildRandomEnemy()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - instance.lastEnemyBuildTime;
		if (timeElapsed > instance.enemyBuildCooldown && instance.numEnemyBuildsLeft > 0)
		{
			instance.numEnemyBuildsLeft--;
			instance.lastEnemyBuildTime = currTime;
			return buildRandomEnemy();
		}

		return null;
	}

	private static Powerup tryBuildRandomPowerup()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - instance.lastPowerupBuildTime;
		if (timeElapsed > instance.powerupBuildCooldown)
		{
			instance.numPowerupsLeft++;
			instance.lastPowerupBuildTime = currTime;
			return buildRandomPowerup();
		}

		return null;
	}

	public LinkedList<Class<?>> enemyTypes = new LinkedList<Class<?>>();
	public int level = 0;
	public int numEnemiesLeft = 10;
	public int numPowerupsLeft = 0;
	private float enemyBuildCooldown = DEFAULT_ENEMY_BUILD_COOLDOWN;
	private long lastEnemyBuildTime = 0;
	private long lastPowerupBuildTime = 0;
	private int numEnemyBuildsLeft = 10;
	private float powerupBuildCooldown = DEFAULT_POWERUP_BUILD_COOLDOWN;
	private LinkedList<IOnEnemyBuildListener> onEnemyBuildListeners = new LinkedList<IOnEnemyBuildListener>();
	private LinkedList<IOnPowerupBuildListener> onPowerupBuildListeners = new LinkedList<IOnPowerupBuildListener>();

	private WaveMachine()
	{
	}

	public void addOnEnemyBuildListener(IOnEnemyBuildListener listener)
	{
		onEnemyBuildListeners.add(listener);
	}

	public void addOnPowerupBuildListener(IOnPowerupBuildListener listener)
	{
		onPowerupBuildListeners.add(listener);
	}

	public void onResumeGame(long awayDuration)
	{
		lastEnemyBuildTime += awayDuration;
		lastPowerupBuildTime += awayDuration;
	}

	public void removeOnEnemyBuildListener(IOnEnemyBuildListener listener)
	{
		onEnemyBuildListeners.remove(listener);
	}

	public void removeOnPowerupBuildListener(IOnPowerupBuildListener listener)
	{
		onPowerupBuildListeners.remove(listener);
	}

	private void notifyOnEnemyBuildListeners(Enemy enemy)
	{
		for (IOnEnemyBuildListener listener : onEnemyBuildListeners)
		{
			listener.onEnemyBuild(enemy);
		}
	}

	private void notifyOnPowerupBuildListeners(Powerup powerup)
	{
		for (IOnPowerupBuildListener listener : onPowerupBuildListeners)
		{
			listener.onPowerupBuild(powerup);
		}
	}
}
