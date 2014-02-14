package com.tender.saucer.color;

import java.util.LinkedList;

import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;
import com.tender.saucer.stuff.Model;

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
		init();
		
		Model.background.setColor(background);
	}
}
