
package com.tender.saucer.color;

import java.util.LinkedList;

import org.andengine.util.color.Color;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */

public final class ColorUtilities
{
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

	public static float getBrightness(Color color)
	{
		return (0.299f * color.getRed()) + (0.587f * color.getGreen()) + (0.114f * color.getBlue());
	}

	public static LinkedList<Color> getComplementaryColors()
	{
		Color c1 = new Color(Color.WHITE);
		Color c2 = new Color(Color.WHITE);
		while (colorDiff(c1, c2) < .7)
		{
			c1 = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
			correct(c1);
			c2 = new Color(1 - c1.getRed(), 1 - c1.getGreen(), 1 - c1.getBlue());
		}

		LinkedList<Color> colors = new LinkedList<Color>();
		colors.add(c1);
		colors.add(c2);
		return colors;
	}

	private static float colorDiff(Color c1, Color c2)
	{
		return Math.abs(c1.getRed() - c2.getRed()) + Math.abs(c1.getGreen() - c2.getGreen())
				+ Math.abs(c1.getBlue() - c2.getBlue());
	}

	private static void correct(Color c)
	{
		while (getBrightness(c) > .75f)
		{
			c.set(c.getRed() - .01f, c.getGreen() - .01f, c.getBlue() - .01f);
		}

		while (getBrightness(c) < .25f)
		{
			c.set(c.getRed() + .01f, c.getGreen() + .01f, c.getBlue() + .01f);
		}
	}

	private static float lum(float c, float percent)
	{
		return Math.max(0, Math.min(1, c + percent));
	}

	private ColorUtilities()
	{
	}
}
