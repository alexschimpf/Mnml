
package com.tender.saucer.entity.shapebody.powerup;

import java.util.LinkedList;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.tender.saucer.collision.ICollide;
import com.tender.saucer.entity.particle.ParticleSystem;
import com.tender.saucer.entity.shapebody.TargetShapeBody;
import com.tender.saucer.entity.shapebody.wall.SideWall;
import com.tender.saucer.entity.shapebody.wall.Wall;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.WaveMachine;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public abstract class Powerup extends TargetShapeBody
{
	public static final float DEFAULT_DURATION = 8000;
	public static final float DEFAULT_SIZE = 50;
	public boolean overrides = true;
	protected boolean active = true;
	protected LinkedList<IOnPowerupMissedListener> onPowerupMissedListeners = new LinkedList<IOnPowerupMissedListener>();

	public Powerup()
	{
		speed = 5 + (float)(Math.random() * 5);

		float m = Math.random() < .5 ? -1 : 1;
		tx = m * (float)(Math.random() * Constants.CAMERA_WIDTH * 2);
	}

	public void addOnPowerupMissedListener(IOnPowerupMissedListener listener)
	{
		onPowerupMissedListeners.add(listener);
	}

	public abstract void apply();

	public void collide(ICollide other)
	{
		if (other instanceof Wall)
		{
			notifyOnPowerupMissedListeners(this);
		}

		if (!(other instanceof SideWall))
		{
			active = false;
		}
	}

	public void done()
	{
		WaveMachine.instance.numPowerupsLeft--;
		ParticleSystem.begin(this);

		super.done();
	}

	public abstract void remove();

	public void removeOnPowerupMissedListener(IOnPowerupMissedListener listener)
	{
		onPowerupMissedListeners.remove(listener);
	}

	public boolean update()
	{
		return !active;
	}

	protected void initBody()
	{
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixDef.filter.categoryBits = Constants.ENEMY_BITMASK;
		fixDef.filter.maskBits = Constants.PLAYER_BITMASK | Constants.SHOT_BITMASK | Constants.SIDE_WALL_BITMASK
				| Constants.WALL_BITMASK;
		body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.DynamicBody, fixDef);
		body.setFixedRotation(true);
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}

	protected void notifyOnPowerupMissedListeners(Powerup powerup)
	{
		for (IOnPowerupMissedListener listener : onPowerupMissedListeners)
		{
			listener.onPowerupMissed(powerup);
		}
	}
}
