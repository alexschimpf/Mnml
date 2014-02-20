package com.tender.saucer.wave;

import java.util.Calendar;

import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public final class WaveRecess
{
	private static float duration = 0;
	private static long startTime = 0;
	
	public static void begin()
	{
		String message = Constants.WAVE_INTERMISSION_MESSAGES[(int)(Math.random() * Constants.WAVE_INTERMISSION_MESSAGES.length)];
		duration = TextSequence.play(Model.waveIntermissionFont, new String[]{message, "", "Three", "Two", "One"}, 
				new float[]{2500, 500, 750, 750, 750});
		startTime = Calendar.getInstance().getTimeInMillis();
	}
	
	public static void init()
	{
		startTime = 0;
		duration = 0;
	}

	public static void update()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - startTime;
		if(timeElapsed > duration)
		{
			Model.state = GameState.WAVE_MACHINE_RUNNING;
			WaveMachine.beginNextWave();
		}
	}
	
	private WaveRecess() 
	{		
	}
}
