
package com.tender.saucer.wave;

import java.util.Calendar;

import com.tender.saucer.activity.IOnResumeGameListener;
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
public final class WaveRecess implements IOnResumeGameListener
{
	public static WaveRecess instance;

	public static void begin()
	{
		String message = Constants.WAVE_INTERMISSION_MESSAGES[(int)(Math.random() * Constants.WAVE_INTERMISSION_MESSAGES.length)];
		instance.duration = TextSequence.play(Model.waveIntermissionFont, new String[] { message, "", "Three", "Two",
				"One" }, new float[] { 2500, 500, 750, 750, 750 });
		instance.startTime = Calendar.getInstance().getTimeInMillis();
	}

	public static void init()
	{
		instance = new WaveRecess();
	}

	public static void update()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - instance.startTime;
		if (timeElapsed > instance.duration)
		{
			Model.state = GameState.WAVE_MACHINE_RUNNING;
			WaveMachine.beginNextWave();
		}
	}

	private float duration = 0;

	private long startTime = 0;

	private WaveRecess()
	{
	}

	public void onResumeGame(long awayDuration)
	{
		startTime += awayDuration;
	}
}
