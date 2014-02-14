package com.tender.saucer.color;

import java.util.LinkedList;

import org.andengine.util.color.Color;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class ColorUtilities 
{
	private ColorUtilities() 
	{
	}

	public static Color brighten(Color c, float percent)
	{
		float r = lum(c.getRed(), percent);
		float g = lum(c.getGreen(), percent);
		float b = lum(c.getBlue(), percent);
		return new Color(r, g, b);
	}
	
	public static Color darken(Color c, float percent)
	{
		return brighten(c, -percent);
	}
	
	public static LinkedList<Color> getComplementaryColors()
	{
		Color c1 = new Color(Color.WHITE);
		Color c2 = new Color(Color.WHITE);	
		while(colorDiff(c1, c2) < .7)
		{
			c1 = correct(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
			c2 = new Color(1 - c1.getRed(), 1 - c1.getGreen(), 1 - c1.getBlue());
		}

		LinkedList<Color> colors = new LinkedList<Color>();
		colors.add(c1);
		colors.add(c2);
		return colors;
	}
	
	public static float colorDiff(Color c1, Color c2)
	{
		return Math.abs(c1.getRed() - c2.getRed()) + Math.abs(c1.getGreen() - c2.getGreen()) + Math.abs(c1.getBlue() - c2.getBlue());
	}

	public static Color correct(Color c)
	{
		float r = c.getRed();
		float g = c.getGreen();
		float b = c.getBlue();
		float sum = r + g + b;
		float maxSum = 3; 

		while(sum > maxSum * .8) 
		{
			r -= .01;
			g -= .01;
			b -= .01;
			sum = r + g + b;
		}
		
		while(sum < maxSum * .2)
		{
			r += .01;
			g += .01;
			b += .01;
			sum = r + g + b;
		}

		return new Color(r, g, b);
	}
	
	private static float lum(float c, float percent)
	{
		return Math.max(0, Math.min(255, c + percent));
	}
}
