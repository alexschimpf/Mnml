
package com.tender.saucer.entity.shapebody.powerup;

import org.andengine.entity.sprite.Sprite;

import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.entity.shapebody.player.Player;
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
public class HealthPowerup extends Powerup
{
	public HealthPowerup()
	{
		super();
		overrides = false;
		float x = (float)(Math.random() * (Constants.CAMERA_WIDTH - Powerup.DEFAULT_SIZE));
		float y = -Powerup.DEFAULT_SIZE;
		shape = new Sprite(x, y, Powerup.DEFAULT_SIZE, Powerup.DEFAULT_SIZE, Textures.POWERUP_HEALTH, Model.main
				.getVertexBufferObjectManager());
		shape.setColor(ColorScheme.foreground);
		initBody();
	}

	@Override
	public void apply()
	{
		Model.player.health = Math.min(Model.player.health + 1, Player.DEFAULT_HEALTH);
	}

	@Override
	public void remove()
	{
	}
}
