package com.tender.saucer.shapebody.enemy;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.color.Color;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.particlesystem.ParticleSystem;
import com.tender.saucer.shapebody.BodyData;
import com.tender.saucer.shapebody.ICollide;
import com.tender.saucer.shapebody.player.Player;
import com.tender.saucer.shapebody.shot.Shot;
import com.tender.saucer.shapebody.wall.Wall;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.stuff.ColorScheme;
import com.tender.saucer.wave.WaveMachine;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class BasicEnemy extends Enemy
{
	protected BasicEnemy() 
	{	
		health = 1;
		speed = 5 + (float)(Math.random() * 5);
		
		float size = (float)(Constants.DEFAULT_SHOT_WIDTH + (Math.random() * Constants.DEFAULT_SHOT_WIDTH * 4));
		float m = Math.random() < .5 ? -1 : 1;
		tx = m * (float)(Math.random() * Constants.CAMERA_WIDTH * 2);
		
		float x = (float)(Math.random() * (Constants.CAMERA_WIDTH - size));
		float y = -size;
		shape = new Rectangle(x, y, size, size, Model.main.getVertexBufferObjectManager());
		shape.setColor(ColorScheme.enemy);
		
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixDef.filter.categoryBits = Constants.ENEMY_BITMASK;
		fixDef.filter.maskBits = Constants.PLAYER_BITMASK | Constants.SHOT_BITMASK | Constants.OOB_BITMASK | Constants.WALL_BITMASK;
		
		body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.DynamicBody, fixDef);
		body.setFixedRotation(true);	
		body.setUserData(new BodyData(this));
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}
	
	protected BasicEnemy(SplitEnemy splitEnemy, boolean dirLeft) 
	{	
		health = 1;
		speed = Math.abs(splitEnemy.speed) / 1.5f;
		
		float dx = dirLeft ? 0 : splitEnemy.shape.getWidth() / 2;
		float x = splitEnemy.shape.getX() + dx;
		float y = splitEnemy.shape.getY();
		float size = splitEnemy.shape.getWidth() / 1.25f;
		
		float txOffset = dirLeft ? -Constants.CAMERA_WIDTH / 2 : Constants.CAMERA_WIDTH / 2;
		tx = x + txOffset;
		
		shape = new Rectangle(x, y, size, size, Model.main.getVertexBufferObjectManager());
		shape.setColor(ColorScheme.enemy);
		
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixDef.filter.categoryBits = Constants.ENEMY_BITMASK;
		fixDef.filter.maskBits = Constants.PLAYER_BITMASK | Constants.SHOT_BITMASK | Constants.OOB_BITMASK | Constants.WALL_BITMASK;
		
		body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.DynamicBody, fixDef);
		body.setFixedRotation(true);	
		body.setUserData(new BodyData(this));
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}

	public boolean update()
	{
		if(health <= 0)
		{
			Model.waveMachine.currNumEnemiesLeft--;
			return true;
		}
		
		return false;
	}

	public void collide(ICollide other)
	{
		if(other instanceof Player)
		{
			health = 0;	
		}
		else if(other instanceof Wall)
		{
			health = 0;
		}
		else if(other instanceof Shot)
		{
			health--;
		}
		
		if(health <= 0)
		{
			ParticleSystem.init(this);	
		}
	}
}
