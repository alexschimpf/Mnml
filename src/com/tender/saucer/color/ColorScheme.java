
package com.tender.saucer.color;

import java.util.LinkedList;

import org.andengine.util.color.Color;

import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public final class ColorScheme
{
	public static Color background;
	public static Color foreground;
	public static Color text;

	public static void applyNewScheme()
	{
		generateColors();

		Model.background.setColor(background);

		if(Model.player.penalty)
		{
			Model.player.shape.setColor(foreground);
		}
	}

	public static void generateColors()
	{
		LinkedList<Color> complColors = ColorUtilities.getComplementaryColors();
		background = complColors.get(0);
		foreground = complColors.get(1);

		float backgroundBrightness = ColorUtilities.getBrightness(background);
		text = backgroundBrightness > .5f ? Color.BLACK : Color.WHITE;
	}

	private ColorScheme()
	{
	}
}
