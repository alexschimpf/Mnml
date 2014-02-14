package com.tender.saucer.update;

import java.util.LinkedList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;

import android.util.Log;

import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.WaveMachine;
import com.tender.saucer.wave.WaveIntermission;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public final class UpdateHandler implements IUpdateHandler
{
	public void onUpdate(float secondsElapsed) 
	{		
		switch(Model.state)
		{
			case WAVE_RUNNING:	
				Model.waveMachine.update();			
				break;
			case WAVE_INTERMISSION:
				Model.waveIntermission.update();
				break;
			case PAUSED:
				return;
			case DONE:
				Model.main.restart();
				break;	
		}

		Model.player.update();
		Model.wall.update();
		Model.background.update();
		updateTransients();
		updateHUDText();
	}

	public void reset() 
	{
	}
	
	private void updateTransients()
	{
		@SuppressWarnings("unchecked")
		LinkedList<ITransientUpdate> transientsClone = (LinkedList<ITransientUpdate>)Model.transients.clone();
		for(ITransientUpdate transientClone : transientsClone)
		{	
			if(transientClone.update())
			{
				transientClone.done();
				Model.transients.remove(transientClone);
			}
		}
	}
	
	private void updateHUDText()
	{
		Model.scoreText.setText("" + Model.player.score);
		
		float width = 0;
		for(int i = 0; i < Model.player.health; i++)
		{
			width += 20;
		}		
		Model.lifeBar.setWidth(width);
		Model.lifeBar.setPosition(Constants.CAMERA_WIDTH - width - 20, (Constants.TOP_BOT_HEIGHT - Model.lifeBar.getHeight()) / 2);
		
		Model.waveText.setText("Wave " + Model.waveMachine.level);
		Model.waveText.setX((Constants.CAMERA_WIDTH - Model.waveText.getWidth()) / 2);
	}
}
