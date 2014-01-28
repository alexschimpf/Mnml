package com.tender.saucer.handler;

import java.util.LinkedList;

import org.andengine.engine.handler.IUpdateHandler;

import com.tender.saucer.shapebody.IUpdate;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.WaveMachine;
import com.tender.saucer.wave.WaveIntermission;

public class UpdateHandler implements IUpdateHandler
{
	public void onUpdate(float secondsElapsed) 
	{		
		Model model = Model.instance();
		
		switch(model.state)
		{
			case WAVE_RUNNING:	
				update(model.waveMachine);			
				break;
			case WAVE_INTERMISSION:
				update(model.waveIntermission);
				break;
			case PAUSED:
				return;
			case DONE:
				model.main.restart();
				break;	
		}
		
		update(model.player);
		update(model.wall);
		updateActives();
		updateHUDText();
	}

	public void reset() 
	{
	}
	
	private void updateActives()
	{
		Model model = Model.instance();
		
		@SuppressWarnings("unchecked")
		LinkedList<IUpdate> activesClone = (LinkedList<IUpdate>)model.actives.clone();
		for(IUpdate active : activesClone)
		{	
			if(active.update())
			{
				active.done();
				model.actives.remove(active);
			}
		}
	}
	
	private void updateHUDText()
	{
		Model model = Model.instance();
		
		model.scoreText.setText("" + model.player.score);
		
		String lives = "";
		for(int i = 0; i < model.player.health; i++)
		{
			lives += "+";
		}	
		model.livesText.setText("" + lives);
		model.livesText.setX(Constants.CAMERA_WIDTH - model.livesText.getWidth() - 20);
		
		model.waveText.setText("Wave " + model.waveMachine.level);
		model.waveText.setX((Constants.CAMERA_WIDTH - model.waveText.getWidth()) / 2);
	}
	
	private void update(IUpdate entity)
	{
		if(entity.update())
		{
			entity.done();
		}
	}
}
