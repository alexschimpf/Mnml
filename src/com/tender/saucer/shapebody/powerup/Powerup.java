package com.tender.saucer.shapebody.powerup;

import java.util.Calendar;

import org.andengine.entity.primitive.DrawMode;
import org.andengine.entity.primitive.Mesh;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.vbo.HighPerformanceMeshVertexBufferObject;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.vbo.DrawType;

import android.graphics.Color;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.collision.BodyData;
import com.tender.saucer.collision.ICollide;
import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.particle.ParticleSystem;
import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.shapebody.TargetShapeBody;
import com.tender.saucer.shapebody.player.Player;
import com.tender.saucer.shapebody.shot.Shot;
import com.tender.saucer.shapebody.wall.SideWall;
import com.tender.saucer.shapebody.wall.Wall;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public abstract class Powerup extends TargetShapeBody
{
	protected boolean active = true;
	
	protected Powerup() 
	{	
		speed = 5 + (float)(Math.random() * 5);
		
		float size = Constants.POWERUP_WIDTH;
		float m = Math.random() < .5 ? -1 : 1;
		tx = m * (float)(Math.random() * Constants.CAMERA_WIDTH * 2);
		
		float x = (float)(Math.random() * (Constants.CAMERA_WIDTH - size));
		float y = -size;
		shape = new Rectangle(x, y, size, size, Model.main.getVertexBufferObjectManager());
		shape.setColor(ColorScheme.enemy);

		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixDef.filter.categoryBits = Constants.ENEMY_BITMASK;
		fixDef.filter.maskBits = Constants.PLAYER_BITMASK | Constants.SHOT_BITMASK | Constants.SIDE_WALL_BITMASK | Constants.WALL_BITMASK;
		
		body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.DynamicBody, fixDef);
		body.setFixedRotation(true);	
		body.setUserData(new BodyData(this));
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}

	private static Powerup buildRandomPowerup()
	{
		return null;
	}
	
	public abstract void apply();
	
	public abstract void remove();
	
	public boolean update()
	{
		return !active;
	}

	public void collide(ICollide other) 
	{
		if(!(other instanceof SideWall))
		{
			ParticleSystem.init(this);
		}
		
		active = false;
	}
}
