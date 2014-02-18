package com.tender.saucer.update;

import java.util.LinkedList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;

import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.WaveMachine;
import com.tender.saucer.wave.WaveRecess;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public final class UpdateHandler implements IUpdateHandler
{
	private boolean skip = false;
	
	public void onUpdate(float secondsElapsed) 
	{		
		switch(Model.state)
		{
			case WAVE_MACHINE_RUNNING:	
				WaveMachine.update();	
				updateHUDText();
				break;
			case WAVE_RECESS_RUNNING:
				WaveRecess.update();
				break;
			case GAME_PAUSED:
				return;
			case GAME_OVER:
				if(!skip)
				{
					skip = true;
					Model.main.runOnUiThread(new Runnable() 
					{
					    public void run() 
					    {
					    	Model.main.showGameOverDialog();
					    }
					});
				}
				break;
		}

		Model.player.update();
		Model.background.update();
		updateTransients();
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
		Model.player.score++;
		Model.scoreText.setText("" + Model.player.score);
		
		float width = (Model.player.health / Constants.DEFAULT_PLAYER_HEALTH) * (Constants.CAMERA_WIDTH - 10);		
		Model.lifeBar.setWidth(width);
		
		Model.waveText.setText("WAVE " + WaveMachine.level);
		Model.waveText.setX((Constants.CAMERA_WIDTH - Model.waveText.getWidth()) / 2);
	}
}
