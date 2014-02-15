package com.tender.saucer.color;


import java.util.LinkedList;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.BitmapTextureAtlasSource;
import org.andengine.util.color.Color;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

import com.tender.saucer.stuff.Model;
import com.tender.saucer.stuff.Textures;
import com.tender.saucer.untitledgame.R;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class ColorScheme 
{
	public static Color background;
	public static Color foreground;

	private ColorScheme() 
	{	
	}
	
	public static void init()
	{
		LinkedList<Color> complColors = ColorUtilities.getComplementaryColors();
		background = complColors.get(0);
		foreground = complColors.get(1);
	}
	
	public static void repaint()
	{	
		init();
		Model.background.setColor(background);
		
		if(Model.player.penalty)
		{
			Model.player.shape.setColor(foreground);
		}
	}
}
