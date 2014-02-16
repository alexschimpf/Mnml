package com.tender.saucer.wave;

import java.util.Calendar;

import android.util.Log;

import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.update.IPersistentUpdate;
import com.tender.saucer.update.ITransientUpdate;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public final class WaveIntermission implements IPersistentUpdate
{
	private long startTime = 0;
	private TextSequence countdown = null; 
	
	public WaveIntermission() 
	{		
	}

	public void beginNextIntermission()
	{
		String message = Constants.WAVE_INTERMISSION_MESSAGES[(int)(Math.random() * Constants.WAVE_INTERMISSION_MESSAGES.length)];
		countdown = new TextSequence(Model.waveIntermissionFont, 
				new String[]{message, "", "Three", "Two", "One"}, new float[]{2500, 500, 750, 750, 750});
		startTime = Calendar.getInstance().getTimeInMillis();
	}
	
	public void update()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - startTime;
		if(timeElapsed > Constants.WAVE_INTERMISSION_DURATION)
		{
			Model.state = GameState.WAVE_RUNNING;
			Model.waveMachine.beginNextWave();
		}
		else
		{			
			countdown.playOnce();
		}
	}
}
