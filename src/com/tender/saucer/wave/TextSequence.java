package com.tender.saucer.wave;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.util.HorizontalAlign;

import android.util.Log;

import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;

public class TextSequence 
{	
	private Text text;
	private String[] sequence;
	private float[] durations;
	private int currFrame = 0;
	private boolean started = false;
	
	public TextSequence(Font font, String[] sequence, float[] durations) 
	{
		text = new Text(0, 0, font, "", 100, Model.main.getVertexBufferObjectManager());
		text.setHorizontalAlign(HorizontalAlign.CENTER);
		resetTextPosition();
		this.sequence = sequence;
		this.durations = durations;
	}

	public void playOnce()
	{
		if(started)
		{
			return;
		}
		
		started = true;
		
		text.setText(sequence[currFrame]);
		resetTextPosition();
		Model.hud.attachChild(text);
		
		TimerHandler timer = new TimerHandler(durations[currFrame] / 1000, new ITimerCallback()
		{
			public void onTimePassed(TimerHandler timerHandler) 
			{				
				currFrame++;	
							
				if(currFrame > sequence.length)
				{
					Model.scene.unregisterUpdateHandler(timerHandler);
				}
				else if(currFrame == sequence.length)
				{
					Model.hud.detachChild(text);
				}
				else
				{
					text.setText(sequence[currFrame]);
					resetTextPosition();
					timerHandler.setTimerSeconds(durations[currFrame] / 1000);
					timerHandler.reset();
				}
			}			
		});		
		
		Model.scene.registerUpdateHandler(timer);
	}
	
	private void resetTextPosition()
	{
		text.setPosition((Constants.CAMERA_WIDTH - text.getWidth()) / 2, (Constants.CAMERA_HEIGHT - text.getHeight()) / 2);
	}
}
