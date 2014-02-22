
package com.tender.saucer.achievements;

import android.content.Context;
import android.content.SharedPreferences;

import com.tender.saucer.entity.shapebody.enemy.Enemy;
import com.tender.saucer.entity.shapebody.enemy.IOnEnemyMissedListener;
import com.tender.saucer.entity.shapebody.enemy.IOnEnemyShotListener;
import com.tender.saucer.entity.shapebody.player.IOnPlayerPenaltyListener;
import com.tender.saucer.entity.shapebody.player.IOnPlayerPowerupListener;
import com.tender.saucer.entity.shapebody.powerup.Powerup;
import com.tender.saucer.stuff.Model;

public class Achievements implements IOnPlayerPenaltyListener, IOnPlayerPowerupListener, IOnEnemyShotListener,
		IOnEnemyMissedListener
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
		}
		editor.commit();
		return oldBestScore;
	}

	private Achievements()
	{
	}

	public void onEnemyMissed(Enemy enemy)
	{

	}

	public void onEnemyShot(float postHealth)
	{

	}

	public void onPlayerPenalty()
	{

	}

	public void onPlayerPowerup(Powerup powerup)
	{

	}
}
