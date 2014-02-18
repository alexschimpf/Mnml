package com.tender.saucer.wave;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.util.HorizontalAlign;

import android.util.Log;

import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class TextSequence 
{	
	private Text text;
	private String[] sequence;
	private float[] durations;
	private int currFrame = 0;
	
	private TextSequence()
	{
	}
	
	private TextSequence(Font font, String[] sequence, float[] durations) 
	{
		text = new Text(0, 0, font, "", 100, Model.main.getVertexBufferObjectManager());
		text.setHorizontalAlign(HorizontalAlign.CENTER);
		this.sequence = sequence;
		this.durations = durations;
	}
	
	public static void play(Font font, String[] sequence, float[] durations)
	{
		final TextSequence ts = new TextSequence(font, sequence, durations);		
		ts.setAndAlignText(sequence[ts.currFrame]);
		Model.hud.attachChild(ts.text);
		
		TimerHandler timer = new TimerHandler(durations[ts.currFrame] / 1000, new ITimerCallback()
		{
			public void onTimePassed(TimerHandler timerHandler) 
			{				
				ts.currFrame++;	
							
				if(ts.currFrame > ts.sequence.length)
				{
					Model.scene.unregisterUpdateHandler(timerHandler);
				}
				else if(ts.currFrame == ts.sequence.length)
				{
					Model.hud.detachChild(ts.text);
				}
				else
				{
					ts.setAndAlignText(ts.sequence[ts.currFrame]);
					timerHandler.setTimerSeconds(ts.durations[ts.currFrame] / 1000);
					timerHandler.reset();
				}
			}			
		});		
		
		Model.scene.registerUpdateHandler(timer);
	}

	private void setAndAlignText(String str)
	{
		text.setText(str);
		text.setPosition((Constants.CAMERA_WIDTH - text.getWidth()) / 2, (Constants.CAMERA_HEIGHT - text.getHeight()) / 2);
	}
}
