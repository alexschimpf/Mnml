package com.tender.saucer.shapebody.enemy;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.color.Color;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.collision.BodyData;
import com.tender.saucer.collision.ICollide;
import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.color.ColorUtilities;
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

public class BigEnemy extends BasicEnemy
{
	public BigEnemy()
	{
		health = 3;
		speed = 3 + (float)(Math.random() * 2);
		
		float size = (float)((Shot.DEFAULT_SIZE * 6) + (Math.random() * Shot.DEFAULT_SIZE * 2));
		float m = Math.random() < .5 ? -1 : 1;
		tx = m * (float)(Math.random() * Constants.CAMERA_WIDTH * 2);
		
		float x = (float)(Math.random() * (Constants.CAMERA_WIDTH - size));
		float y = -size;
		shape = new Rectangle(x, y, size, size, Model.main.getVertexBufferObjectManager());
		shape.setColor(ColorUtilities.darken(ColorScheme.foreground, .2f));
		
		initBody();
	}
	
	@Override
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
			
			if(health > 0)
			{
				shape.setColor(ColorUtilities.brighten(shape.getColor(), .1f));	
			}
		}
		
		if(health <= 0)
		{
			ParticleSystem.begin(this, ParticleSystem.DEFAULT_NUM_PARTICLES * 2);	
		}
	}
}
