package com.tender.saucer.shapebody.enemy;

import java.util.Calendar;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.color.Color;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.collision.BodyData;
import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.stuff.Textures;
import com.tender.saucer.wave.WaveMachine;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class PenaltyEnemy extends BasicEnemy
{
	private long lastFlashTime = 0;
	private float flashCooldown = 150;
	
	protected PenaltyEnemy() 
	{	
		health = 1;
		speed = 5 + (float)(Math.random() * 5);
		
		float m = Math.random() < .5 ? -1 : 1;
		tx = m * (float)(Math.random() * Constants.CAMERA_WIDTH * 2);
		
		float x = (float)(Math.random() * (Constants.CAMERA_WIDTH - Constants.POWERUP_SIZE));
		float y = -Constants.POWERUP_SIZE;
		shape = new Sprite(x, y, Constants.POWERUP_SIZE, Constants.POWERUP_SIZE, Textures.PENALTY, Model.main.getVertexBufferObjectManager());
		shape.setColor(ColorScheme.foreground);
		
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixDef.filter.categoryBits = Constants.ENEMY_BITMASK;
		fixDef.filter.maskBits = Constants.PLAYER_BITMASK | Constants.SHOT_BITMASK | Constants.SIDE_WALL_BITMASK | Constants.WALL_BITMASK;
		
		body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.DynamicBody, fixDef);
		body.setFixedRotation(true);	
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));		
	}
	
	@Override
	public boolean update()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - lastFlashTime;
		if(timeElapsed > flashCooldown)
		{
			lastFlashTime = currTime;
			
			if(shape.getColor().equals(ColorScheme.foreground))
			{
				shape.setColor(Color.WHITE);
			}
			else
			{
				shape.setColor(ColorScheme.foreground);
			}
		}
		
		if(health <= 0)
		{
			Model.waveMachine.currNumEnemiesLeft--;
			return true;
		}
		
		return false;
	}
}
