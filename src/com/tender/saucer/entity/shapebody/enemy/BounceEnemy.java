
package com.tender.saucer.entity.shapebody.enemy;

import com.tender.saucer.stuff.Constants;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public class BounceEnemy extends BasicEnemy
{
	public BounceEnemy()
	{
		super();
		speed = 15 + (float)(Math.random() * 5);
		float m = Math.random() < .5 ? 1 : -1;
		tx = (float)(m * ((Constants.CAMERA_WIDTH * 3) + (Math.random() * Constants.CAMERA_WIDTH)));
		body.getFixtureList().get(0).setRestitution(.5f);
	}
}
