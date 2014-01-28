package com.tender.saucer.stuff;

import java.util.LinkedList;

import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;

import android.util.Log;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class ColorScheme 
{
	public static Color background;
	public static Color enemy;

	private ColorScheme() 
	{	
	}
	
	public static void init()
	{
		LinkedList<Color> complColors = ColorUtilities.getComplementaryColors();
		background = complColors.get(0);
		enemy = complColors.get(1);
	}
	
	public static void repaint()
	{	
		LinkedList<Color> complColors = ColorUtilities.getComplementaryColors();
		background = complColors.get(0);
		enemy = complColors.get(1);

		Model.background.setColor(background);
	}
}
