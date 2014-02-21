
package com.tender.saucer.entity.background;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.entity.Entity;
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
	private AlphaModifier alphaDecrease;
	private AlphaModifier alphaIncrease;
	private Rectangle rect;

	public Background()
	{
		rect = new Rectangle(0, Constants.TOP_BOT_HEIGHT, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT
				- (Constants.TOP_BOT_HEIGHT * 2), Model.main.getVertexBufferObjectManager());
		rect.setColor(ColorScheme.background);
	}

	@Override
	public void attachToScene()
	{
		Model.scene.attachChild(rect);
	}

	public void flash()
	{
		rect.setAlpha(1);
		if (rect.getEntityModifierCount() > 0)
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
	}
}
