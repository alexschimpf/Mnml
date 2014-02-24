
package com.tender.saucer.achievements;

import android.content.Context;
import android.content.SharedPreferences;

import com.tender.saucer.entity.shapebody.enemy.Enemy;
import com.tender.saucer.entity.shapebody.enemy.IOnEnemyMissedListener;
import com.tender.saucer.entity.shapebody.enemy.IOnEnemyShotListener;
import com.tender.saucer.entity.shapebody.player.IOnPlayerPenaltyListener;
import com.tender.saucer.entity.shapebody.player.IOnPlayerPowerupListener;
import com.tender.saucer.entity.shapebody.powerup.IOnPowerupMissedListener;
import com.tender.saucer.entity.shapebody.powerup.Powerup;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.IOnEnemyBuildListener;
import com.tender.saucer.wave.IOnPowerupBuildListener;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public class Achievements implements IOnPlayerPenaltyListener, IOnPlayerPowerupListener, IOnEnemyShotListener,
		IOnEnemyMissedListener, IOnPowerupMissedListener, IOnEnemyBuildListener, IOnPowerupBuildListener
{
	public static Achievements instance;

	public static void init()
	{
		instance = new Achievements();
	}

	public static long updateBestScore(long score)
	{
		SharedPreferences prefs = Model.main.getSharedPreferences("com.tender.saucer.untitledgame",
				Context.MODE_PRIVATE);

		long oldBestScore = prefs.getLong("bestScore", 0);
		SharedPreferences.Editor editor = prefs.edit();
		if (score > oldBestScore)
		{
			editor.putLong("bestScore", score);
			instance.bestScore = score;
		}
		editor.commit();

		return oldBestScore;
	}

	public long bestScore;

	private Achievements()
	{
	}

	public void onEnemyBuild(Enemy enemy)
	{

	}

	public void onEnemyMissed(Enemy enemy)
	{

	}

	public void onEnemyShot(Enemy enemy)
	{

	}

	public void onPlayerPenalty()
	{

	}

	public void onPlayerPowerup(Powerup powerup)
	{

	}

	public void onPowerupBuild(Powerup powerup)
	{

	}

	public void onPowerupMissed(Powerup powerup)
	{

	}
}
