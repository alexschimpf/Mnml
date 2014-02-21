
package com.tender.saucer.entity.shapebody.enemy;

import java.util.Calendar;

import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.entity.shapebody.powerup.Powerup;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.stuff.Textures;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */

public class PenaltyEnemy extends BasicEnemy
{
	public static final float DEFAULT_PENALTY_DURATION = 10000;
	public static final float DEFAULT_PENALTY_SLOWDOWN_FACTOR = .5f;
	public static final float DEFAULT_PROBABILITY = .09f;

	private float flashCooldown = 150;
	private long lastFlashTime = 0;

	public PenaltyEnemy()
	{
		speed = 5 + (float)(Math.random() * 5);

		float m = Math.random() < .5 ? -1 : 1;
		tx = m * (float)(Math.random() * Constants.CAMERA_WIDTH * 2);

		float x = (float)(Math.random() * (Constants.CAMERA_WIDTH - Powerup.DEFAULT_SIZE));
		float y = -Powerup.DEFAULT_SIZE;
		shape = new Sprite(x, y, Powerup.DEFAULT_SIZE, Powerup.DEFAULT_SIZE, Textures.PENALTY_ENEMY, Model.main
				.getVertexBufferObjectManager());
		shape.setColor(ColorScheme.foreground);

		initBody();
	}

	@Override
	public boolean update()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - lastFlashTime;
		if (timeElapsed > flashCooldown)
		{
			lastFlashTime = currTime;

			if (shape.getColor().equals(ColorScheme.foreground))
			{
				shape.setColor(Color.WHITE);
			}
			else
			{
				shape.setColor(ColorScheme.foreground);
			}
		}

		return health <= 0;
	}
}
