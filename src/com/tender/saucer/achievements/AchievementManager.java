
package com.tender.saucer.achievements;

import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.tender.saucer.entity.shapebody.enemy.Enemy;
import com.tender.saucer.entity.shapebody.enemy.IOnEnemyMissedListener;
import com.tender.saucer.entity.shapebody.enemy.IOnEnemyShotDeadListener;
import com.tender.saucer.entity.shapebody.enemy.IOnEnemyShotListener;
import com.tender.saucer.entity.shapebody.player.IOnPlayerPenaltyListener;
import com.tender.saucer.entity.shapebody.player.IOnPlayerPowerupListener;
import com.tender.saucer.entity.shapebody.player.IOnPlayerShotListener;
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
public class AchievementManager implements IOnPlayerPenaltyListener, IOnPlayerPowerupListener, IOnEnemyShotListener,
		IOnEnemyMissedListener, IOnPowerupMissedListener, IOnEnemyBuildListener, IOnPowerupBuildListener,
		IOnEnemyShotDeadListener, IOnPlayerShotListener
{
	public static AchievementManager instance;
	public static LinkedList<Achievement> achievements = new LinkedList<Achievement>();

	public static void init()
	{
		instance = new AchievementManager();
		loadAchievementsFromXML();
	}

	public static void tryUpdateAchievements()
	{
		for (Achievement achievement : achievements)
		{
			achievement.invokeUpdateFunction();
		}
	}

	public static long tryUpdateBestScore(long score)
	{
		SharedPreferences prefs = Model.main.getSharedPreferences("com.tender.saucer.untitledgame",
				Context.MODE_PRIVATE);

		long oldBestScore = prefs.getLong("bestScore", 0);
		SharedPreferences.Editor editor = prefs.edit();
		if(score > oldBestScore)
		{
			editor.putLong("bestScore", score);
		}
		editor.commit();

		return oldBestScore;
	}

	/**
	 * Described in achievements.xml
	 * 
	 * @param preferenceKey
	 * @param goal
	 *            Only contains a single element - the hit streak goal. It was
	 *            initially a String but should be treated as an int.
	 */
	public static void tryUpdateEnemyHitStreakAchievement(String preferenceKey, Object[] args)
	{
		SharedPreferences prefs = Model.main.getSharedPreferences("com.tender.saucer.untitledgame",
				Context.MODE_PRIVATE);

		boolean achieved = prefs.getBoolean(preferenceKey, false);
		if(!achieved)
		{
			int goalInt = Integer.valueOf(args[0].toString());
			if(instance.currBestEnemyHitStreak >= goalInt)
			{
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean(preferenceKey, true);
				editor.commit();
			}
		}
	}

	public static void tryUpdatePowerupHitStreakAchievement(String preferenceKey, Object goal)
	{

	}

	private static void loadAchievementsFromXML()
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(Model.main.getAssets().open("achievements.xml"));
			document.getDocumentElement().normalize();

			Element root = (Element)document.getElementsByTagName("achievements").item(0);
			NodeList achievementList = root.getElementsByTagName("achievement");
			for (int i = 0; i < achievementList.getLength(); i++)
			{
				Element achievementElem = (Element)achievementList.item(i);
				String preferenceKey = achievementElem.getElementsByTagName("preferenceKey").item(0).getTextContent()
						.trim();
				String description = achievementElem.getElementsByTagName("description").item(0).getTextContent()
						.trim();

				Element updateFunctionElem = (Element)achievementElem.getElementsByTagName("updateFunction").item(0);
				String updateFunctionName = updateFunctionElem.getAttribute("name");
				Element updateFuntionArgsRoot = (Element)updateFunctionElem.getElementsByTagName("arguments").item(0);
				NodeList updateFunctionArgsList = updateFuntionArgsRoot.getElementsByTagName("argument");
				Object[] updateFunctionArgs = new Object[updateFunctionArgsList.getLength()];
				for (int j = 0; j < updateFunctionArgsList.getLength(); j++)
				{
					String updateFunctionArg = updateFunctionArgsList.item(j).getTextContent().trim();
					updateFunctionArgs[j] = updateFunctionArg;
				}

				achievements.add(new Achievement(preferenceKey, description, updateFunctionName, updateFunctionArgs));
			}
		}
		catch (Exception e)
		{
			Log.e("test", e.toString());
		}
	}

	public int currEnemyHitStreak = 0;
	public int currBestEnemyHitStreak = 0;

	private AchievementManager()
	{
	}

	public void onEnemyBuild(Enemy enemy)
	{

	}

	public void onEnemyMissed(Enemy enemy)
	{
		if(currEnemyHitStreak > currBestEnemyHitStreak)
		{
			currBestEnemyHitStreak = currEnemyHitStreak;
		}
		currEnemyHitStreak = 0;
	}

	public void onEnemyShot(Enemy enemy)
	{

	}

	public void onEnemyShotDead(Enemy enemy)
	{
		currEnemyHitStreak++;
	}

	public void onPlayerPenalty()
	{

	}

	public void onPlayerPowerup(Powerup powerup)
	{

	}

	public void onPlayerShot()
	{

	}

	public void onPowerupBuild(Powerup powerup)
	{

	}

	public void onPowerupMissed(Powerup powerup)
	{

	}
}
