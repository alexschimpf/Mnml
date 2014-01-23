package com.tender.saucer.wave;

import java.util.Calendar;

import android.util.Log;

import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;

public class WaveIntermission 
{
	public static long startTime = 0;
	public static TextSequence countdown; 
	
	private WaveIntermission() 
	{		
	}
	
	public static void reset()
	{
		startTime = 0;
		countdown = null;
	}
	
	public static void begin()
	{
		Model model = Model.instance();
		
		countdown = new TextSequence(model.waveIntermissionFont, 
				new String[]{"WAVE " + Wave.level + "\nCOMPLETE", "", "Three.", "Two.", "One."}, new float[]{3000, 500, 1000, 1000, 1000});
		startTime = Calendar.getInstance().getTimeInMillis();
		model.state = GameState.WAVE_INTERMISSION;
	}
	
	public static void update()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - startTime;
		if(timeElapsed > Constants.WAVE_INTERMISSION_DURATION)
		{
			Wave.begin();
		}
		else
		{			
			countdown.playOnce();
		}
	}
}
