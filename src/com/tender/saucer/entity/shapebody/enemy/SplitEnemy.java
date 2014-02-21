
package com.tender.saucer.entity.shapebody.enemy;

import com.tender.saucer.collision.BodyData;
import com.tender.saucer.entity.particle.ParticleSystem;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.WaveMachine;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public class SplitEnemy extends BasicEnemy
{
	private boolean split = false;
	private float splitY;

	public SplitEnemy()
	{
		super();
		float playAreaHeight = Constants.CAMERA_HEIGHT - (2 * Constants.TOP_BOT_HEIGHT);
		splitY = (float)((Constants.TOP_BOT_HEIGHT + 20) + (Math.random() * (playAreaHeight / 5)));
	}

	@Override
	public void done()
	{
		if (!split)
		{
			ParticleSystem.begin(this);
		}
		WaveMachine.instance.numEnemiesLeft--;
		dispose();
	}

	@Override
	public boolean update()
	{
		if (shape.getY() >= splitY)
		{
			split = true;
			Enemy[] enemies = buildSplitEnemies(this);
			for (Enemy enemy : enemies)
			{
				enemy.attachToScene();
				enemy.setInMotion();
			}
			WaveMachine.instance.numEnemiesLeft += 2;
			return true;
		}
		return health <= 0;
	}

	protected Enemy[] buildSplitEnemies(SplitEnemy splitEnemy)
	{
		Enemy[] enemies = new Enemy[2];
		enemies[0] = new BasicEnemy(splitEnemy, false);
		enemies[1] = new BasicEnemy(splitEnemy, true);
		enemies[0].body.setUserData(new BodyData(enemies[0]));
		enemies[1].body.setUserData(new BodyData(enemies[1]));
		Model.transients.add(enemies[0]);
		Model.transients.add(enemies[1]);
		return enemies;
	}
}
