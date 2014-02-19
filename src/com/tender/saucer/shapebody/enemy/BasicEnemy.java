package com.tender.saucer.shapebody.enemy;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.color.Color;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.collision.BodyData;
import com.tender.saucer.collision.ICollide;
import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.particle.ParticleSystem;
import com.tender.saucer.shapebody.player.Player;
import com.tender.saucer.shapebody.shot.Shot;
import com.tender.saucer.shapebody.wall.Wall;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.WaveMachine;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class BasicEnemy extends Enemy
{
	public BasicEnemy() 
	{	
		speed = 5 + (float)(Math.random() * 5);
		
		float size = (float)(Shot.DEFAULT_SIZE + (Math.random() * Shot.DEFAULT_SIZE * 4));
		float m = Math.random() < .5 ? -1 : 1;
		tx = m * (float)(Math.random() * Constants.CAMERA_WIDTH * 2);
		
		float x = (float)(Math.random() * (Constants.CAMERA_WIDTH - size));
		float y = -size;
		shape = new Rectangle(x, y, size, size, Model.main.getVertexBufferObjectManager());
		shape.setColor(ColorScheme.foreground);
		
		initBody();
	}
	
	public BasicEnemy(SplitEnemy splitEnemy, boolean dirLeft) 
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
		shape.setColor(ColorScheme.foreground);
		
		initBody();
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
			health -= ((Shot)other).damage;
		}
	}
}
