package com.tender.saucer.shapebody.enemy;

import com.tender.saucer.shapebody.BodyData;
import com.tender.saucer.shapebody.TargetShapeBody;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.Wave;

public abstract class Enemy extends TargetShapeBody
{	
	public float health;
	
	protected Enemy() 
	{
	}
	
	public static Enemy buildRandomEnemy()
	{	
		int choice = (int)(Math.random() * Wave.numEnemyTypes);
		
		Enemy enemy;
		if(Math.random() < Constants.PENALTY_PROBABILITY)
		{
			enemy = new PenaltyEnemy();
		}
		else if(choice == 0)
		{
			enemy = new BasicEnemy(); 
		}
		else if(choice == 1)
		{
			enemy = new BigEnemy();
		}
		else 
		{
			enemy = new SplitEnemy();
		}

		enemy.body.setUserData(new BodyData(enemy));
		
		Model.instance().actives.add(enemy);
		
		return enemy;
	}
	
	public static Enemy[] buildSplitEnemies(SplitEnemy splitEnemy)
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
