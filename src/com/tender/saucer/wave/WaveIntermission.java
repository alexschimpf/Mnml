package com.tender.saucer.wave;

import java.util.Calendar;

import android.util.Log;

import com.tender.saucer.shapebody.IUpdate;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class WaveIntermission implements IUpdate
{
	private long startTime = 0;
	private TextSequence countdown = null; 
	
	public WaveIntermission() 
	{		
	}

	public void beginNextIntermission()
	{
		countdown = new TextSequence(Model.waveIntermissionFont, 
				new String[]{"WAVE " + Model.waveMachine.level + "\nCOMPLETE", "", "Three", "Two", "One"}, new float[]{3000, 500, 1000, 1000, 1000});
		startTime = Calendar.getInstance().getTimeInMillis();
	}
	
	public boolean update()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - startTime;
		if(timeElapsed > Constants.WAVE_INTERMISSION_DURATION)
		{
			return true;
		}
		else
		{			
			countdown.playOnce();
		}
		
		return false;
	}
	
	public void done()
	{
		Model.state = GameState.WAVE_RUNNING;
		Model.waveMachine.beginNextWave();
	}
}
