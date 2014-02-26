
package com.tender.saucer.achievements;

import java.lang.reflect.Method;

import android.util.Log;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public class Achievement
{
	public String preferenceKey;
	public String description;
	public String updateFunctionName;
	public Object[] updateFunctionArgs;

	public Achievement(String preferenceKey, String description, String updateFunctionName,
			Object[] updateFunctionArgs)
	{
		this.preferenceKey = preferenceKey;
		this.description = description;
		this.updateFunctionName = updateFunctionName;
		this.updateFunctionArgs = updateFunctionArgs;
	}

	public void invokeUpdateFunction()
	{
		for (Method achievementManagerFunction : AchievementManager.class.getDeclaredMethods())
		{
			if (achievementManagerFunction.getName().equals(updateFunctionName)
					&& achievementManagerFunction.getParameterTypes().length == (updateFunctionArgs.length + 1))
			{
				try
				{
					achievementManagerFunction.invoke(null, preferenceKey, updateFunctionArgs);
					return;
				}
				catch (Exception e)
				{
					Log.e("test", e.toString());
				}
			}
		}
	}
}
