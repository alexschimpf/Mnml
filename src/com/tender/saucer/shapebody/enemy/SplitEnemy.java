package com.tender.saucer.shapebody.enemy;

import com.tender.saucer.shapebody.BodyData;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
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
			Enemy[] enemies = buildSplitEnemies(this);
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
	
	protected Enemy[] buildSplitEnemies(SplitEnemy splitEnemy)
	{
		Enemy[] enemies = new Enemy[2];
		enemies[0] = new BasicEnemy(splitEnemy, false);
		enemies[1] = new BasicEnemy(splitEnemy, true);
		
		enemies[0].body.setUserData(new BodyData(enemies[0]));
		enemies[1].body.setUserData(new BodyData(enemies[1]));
		
		Model.instance().actives.add(enemies[0]);
		Model.instance().actives.add(enemies[1]);
		
		return enemies;
	}
}
