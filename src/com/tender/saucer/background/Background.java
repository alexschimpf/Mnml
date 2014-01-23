package com.tender.saucer.background;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.util.Log;

import com.tender.saucer.stuff.ColorScheme;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;

public class Background
{
	private Rectangle rect;
	private AlphaModifier alphaDecrease;
	private AlphaModifier alphaIncrease;
	
	public Background() 
	{	
		rect = new Rectangle(0, Constants.TOP_BOT_HEIGHT, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT - (Constants.TOP_BOT_HEIGHT * 2), 
				Model.instance().main.getVertexBufferObjectManager());
		rect.setColor(ColorScheme.instance().background);
	}
	
	public void attachToScene()
	{
		Model.instance().scene.attachChild(rect);
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
			public void onModifierStarted(IModifier<IEntity> modifier, IEntity item) {}

			public void onModifierFinished(IModifier<IEntity> modifier, IEntity item) 
			{
				alphaDecrease = null;
				
				alphaIncrease = new AlphaModifier(.5f, 0, 1);
				alphaIncrease.setAutoUnregisterWhenFinished(true);
				rect.registerEntityModifier(alphaIncrease);
			}
		});
		
		rect.registerEntityModifier(alphaDecrease);	
	}
	
	public void setColor(Color color)
	{
		rect.setColor(color);
	}
}
