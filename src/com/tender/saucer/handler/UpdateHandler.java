package com.tender.saucer.handler;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.andengine.engine.handler.IUpdateHandler;

import com.tender.saucer.shapebody.IUpdate;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.Wave;
import com.tender.saucer.wave.WaveIntermission;

public class UpdateHandler implements IUpdateHandler
{
	public void onUpdate(float secondsElapsed) 
	{
		Model model = Model.instance();
		
		update(model.player);
		update(model.wall);
		updateActives();
		updateHUDText();
		
		switch(model.state)
		{
			case WAVE_INTERMISSION:
				WaveIntermission.update();
				break;
			case RUNNING:	
				Wave.update();
				break;
			case DONE:
				model.main.restart();
				break;			
		}
	}

	public void reset() 
	{
	}
	
	private void updateActives()
	{
//			final Iterator<IUpdate> it = Model.instance().actives.listIterator();
//			while(it.hasNext())
//			{
//				IUpdate active = it.next();
//				if(active.update())
//				{
//					active.done();
//					it.remove();
//				}
//			}
//		
		@SuppressWarnings("unchecked")
		LinkedList<IUpdate> activesClone = (LinkedList<IUpdate>)Model.instance().actives.clone();
		for(IUpdate active : activesClone)
		{	
			if(active.update())
			{
				active.done();
				Model.instance().actives.remove(active);
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
		
		model.waveText.setText("Wave " + Wave.level);
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
