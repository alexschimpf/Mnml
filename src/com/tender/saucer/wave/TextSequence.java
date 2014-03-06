
package com.tender.saucer.wave;

import java.util.Locale;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.util.HorizontalAlign;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public class TextSequence
{
	private static TextToSpeech textSpeaker;

	public static void init()
	{
		textSpeaker = new TextToSpeech(Model.main, new OnInitListener()
		{
			public void onInit(int status)
			{
				if(status == TextToSpeech.SUCCESS)
				{
					textSpeaker.setLanguage(Locale.US);
					textSpeaker.setPitch(.2f);
				}
				else
				{
					Log.e("test", "textToSpeech unsuccessful");
				}
			}
		});
	}

	public static float play(final Font font, final String[] sequence, final float[] durations, final boolean speak)
	{
		float totalDuration = 0;
		for (float duration : durations)
		{
			totalDuration += duration;
		}

		final TextSequence textSequence = new TextSequence(font);
		textSequence.setAndAlignText(sequence[textSequence.currFrame]);
		if(speak)
		{
			speakText(sequence[textSequence.currFrame], durations[textSequence.currFrame]);
		}

		Model.hud.attachChild(textSequence.text);

		TimerHandler timer = new TimerHandler(durations[textSequence.currFrame] / 1000, new ITimerCallback()
		{
			public void onTimePassed(TimerHandler timerHandler)
			{
				textSequence.currFrame++;

				if(textSequence.currFrame > sequence.length)
				{
					Model.scene.unregisterUpdateHandler(timerHandler);
				}
				else if(textSequence.currFrame == sequence.length)
				{
					Model.hud.detachChild(textSequence.text);
				}
				else
				{
					textSequence.setAndAlignText(sequence[textSequence.currFrame]);				
					timerHandler.setTimerSeconds(durations[textSequence.currFrame] / 1000);
					timerHandler.reset();
				}
			}
		});
		Model.scene.registerUpdateHandler(timer);

		return totalDuration;
	}

	private static void speakText(String text, float duration)
	{
		text = text.replace("\n", " ").toLowerCase(Locale.US);
		
		// This totally shouldn't work, but works perfectly.
		int wordCount = 1;
		for(int i = 0; i < text.length(); i++)
		{
			if(text.charAt(i) == ' ')
			{
				wordCount++;
			}				
		}
		textSpeaker.setSpeechRate((wordCount / duration) * 2);
		
		textSpeaker.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	private int currFrame = 0;
	private Text text;

	private TextSequence()
	{
	}

	private TextSequence(Font font)
	{
		text = new Text(0, 0, font, "", 100, Model.main.getVertexBufferObjectManager());
		text.setHorizontalAlign(HorizontalAlign.CENTER);
		text.setColor(ColorScheme.text);
	}

	private void setAndAlignText(String str)
	{
		text.setText(str);
		text.setPosition((Constants.CAMERA_WIDTH - text.getWidth()) / 2,
				(Constants.CAMERA_HEIGHT - text.getHeight()) / 2);
	}
}
