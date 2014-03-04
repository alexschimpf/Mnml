
package com.tender.saucer.entity.background;

import java.util.Calendar;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.color.ColorUtilities;
import com.tender.saucer.entity.Entity;
import com.tender.saucer.entity.particle.ParticleSystem;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.update.IPersistentUpdate;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public final class Background extends Entity implements IPersistentUpdate
{
	private final static float PARTICLE_SYSTEM_COOLDOWN = 500;
	
	private AlphaModifier alphaDecrease;
	private AlphaModifier alphaIncrease;
	private Rectangle rect;
	private long lastParticleSystemTime = 0;

	public Background()
	{
		rect = new Rectangle(0, Constants.TOP_BOT_HEIGHT, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT
				- (Constants.TOP_BOT_HEIGHT * 2), Model.main.getVertexBufferObjectManager());
		rect.setColor(ColorScheme.background);
	}

	@Override
	public void show()
	{
		Model.scene.attachChild(rect);
	}

	public void flash()
	{
		rect.setAlpha(1);

		if(rect.getEntityModifierCount() > 0)
		{
			rect.clearEntityModifiers();
		}

		alphaDecrease = new AlphaModifier(.5f, 1, 0);
		alphaDecrease.setAutoUnregisterWhenFinished(true);
		alphaDecrease.addModifierListener(new IModifierListener<IEntity>()
		{
			public void onModifierFinished(IModifier<IEntity> modifier, IEntity item)
			{
				alphaDecrease = null;

				alphaIncrease = new AlphaModifier(.5f, 0, 1);
				alphaIncrease.setAutoUnregisterWhenFinished(true);
				rect.registerEntityModifier(alphaIncrease);
			}

			public void onModifierStarted(IModifier<IEntity> modifier, IEntity item)
			{
			}
		});
		rect.registerEntityModifier(alphaDecrease);
	}

	public void setColor(Color color)
	{
		rect.setColor(color);
	}

	public void update()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		if(currTime - lastParticleSystemTime > PARTICLE_SYSTEM_COOLDOWN)
		{
			lastParticleSystemTime = currTime;
			
			float x = (float)(10 + (Math.random() * Constants.CAMERA_WIDTH) - 20);
			float y = Constants.CAMERA_HEIGHT - Constants.TOP_BOT_HEIGHT + 5;
			Color color = ColorUtilities.darken(ColorScheme.background, .09f);
			int numParticles = (int)(10 + (Math.random() * 10));
			float maxDuration = (float)(4000 + (Math.random() * 2000));
			ParticleSystem.begin(x, y, color, numParticles, maxDuration);
		}
	}
}
