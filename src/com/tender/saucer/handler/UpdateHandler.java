package com.tender.saucer.handler;

import java.util.LinkedList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;

import com.tender.saucer.shapebody.IUpdate;
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

public class UpdateHandler implements IUpdateHandler
{
	public void onUpdate(float secondsElapsed) 
	{		
		switch(Model.state)
		{
			case WAVE_RUNNING:	
				update(Model.waveMachine);			
				break;
			case WAVE_INTERMISSION:
				update(Model.waveIntermission);
				break;
			case PAUSED:
				return;
			case DONE:
				Model.main.restart();
				break;	
		}
		
		update(Model.player);
		update(Model.wall);
		updateActives();
		updateHUDText();
	}

	public void reset() 
	{
	}
	
	private void updateActives()
	{
		@SuppressWarnings("unchecked")
		LinkedList<IUpdate> activesClone = (LinkedList<IUpdate>)Model.actives.clone();
		for(IUpdate active : activesClone)
		{	
			if(active.update())
			{
				active.done();
				Model.actives.remove(active);
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
	
	private void update(IUpdate entity)
	{
		if(entity.update())
		{
			entity.done();
		}
	}
}
