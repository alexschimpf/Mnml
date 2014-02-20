package com.tender.saucer.update;

import java.util.LinkedList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.util.color.Color;

import com.tender.saucer.color.ColorUtilities;
import com.tender.saucer.entity.shapebody.player.Player;
import com.tender.saucer.stuff.Constants;
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
	
	private void updateHUDText()
	{
		Model.player.score++;
		Model.scoreText.setText("" + Model.player.score);
		
		float width = (Model.player.health / Player.DEFAULT_HEALTH) * (Constants.CAMERA_WIDTH - 10);
		float red = 1 - (Model.player.health / Player.DEFAULT_HEALTH);
		Model.lifeBar.setWidth(width);
		Model.lifeBar.setColor(ColorUtilities.brighten(new Color(red, 1 - red, 0), .5f));
		
		Model.waveText.setText("WAVE " + WaveMachine.level);
		Model.waveText.setX((Constants.CAMERA_WIDTH - Model.waveText.getWidth()) / 2);
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
}
