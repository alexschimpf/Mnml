
package com.tender.saucer.entity.shapebody.powerup;

import org.andengine.entity.sprite.Sprite;

import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.entity.particle.ParticleSystem;
import com.tender.saucer.entity.shapebody.enemy.Enemy;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.stuff.Textures;
import com.tender.saucer.update.ITransientUpdate;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */

public class BombPowerup extends Powerup
{
	public BombPowerup()
	{
		super();

		overrides = false;

		float x = (float) (Math.random() * (Constants.CAMERA_WIDTH - Powerup.DEFAULT_SIZE));
		float y = -Powerup.DEFAULT_SIZE;
		shape = new Sprite(x, y, Powerup.DEFAULT_SIZE, Powerup.DEFAULT_SIZE, Textures.POWERUP_BOMB, Model.main
				.getVertexBufferObjectManager());
		shape.setColor(ColorScheme.foreground);

		initBody();
	}

	@Override
	public void apply()
	{
		Model.background.flash();
		ParticleSystem.begin(this);

		for (ITransientUpdate t : Model.transients)
		{
			if (t instanceof Enemy)
			{
				((Enemy) t).health = 0;
			}
		}
	}

	@Override
	public void remove()
	{
	}
}
