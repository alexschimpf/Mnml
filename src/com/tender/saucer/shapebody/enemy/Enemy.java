package com.tender.saucer.shapebody.enemy;

import com.tender.saucer.shapebody.BodyData;
import com.tender.saucer.shapebody.TargetShapeBody;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.WaveMachine;

public abstract class Enemy extends TargetShapeBody
{	
	public float health;
	
	protected Enemy() 
	{
	}
	
	public static Enemy buildRandomEnemy()
	{	
		int choice = (int)(Math.random() * Model.instance().waveMachine.currNumEnemyTypes);
		
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
		else if(choice == 2)
		{
			enemy = new MorphEnemy();
		}
		else if(choice == 3)
		{
			enemy = new SplitEnemy();
		}
		else 
		{
			enemy = new BounceEnemy();
		}

		enemy.body.setUserData(new BodyData(enemy));
		
		Model.instance().actives.add(enemy);
		
		return enemy;
	}
}
