package com.tender.saucer.shapebody.enemy;

import android.util.Log;

import com.tender.saucer.stuff.Constants;
import com.tender.saucer.wave.Wave;

public class SplitEnemy extends BasicEnemy 
{
	private float splitY;
	
	protected SplitEnemy() 
	{
		super();
		
		float playAreaHeight = Constants.CAMERA_HEIGHT - (2 * Constants.TOP_BOT_HEIGHT);
		splitY = (float)((Constants.TOP_BOT_HEIGHT + 20) + (Math.random() * (playAreaHeight / 5)));
	}

	public boolean update() 
	{
		if(health <= 0)
		{
			Wave.numEnemiesLeft--;
			return true;
		}
		else if(shape.getY() >= splitY)
		{
			Enemy[] enemies = Enemy.buildSplitEnemies(this);
			for(Enemy enemy : enemies)
			{
				enemy.attachToScene();
				enemy.setInMotion();
			}
			
			Wave.numEnemiesLeft++;
			return true;
		}
		
		return false;
	}
}
