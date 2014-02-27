
package com.tender.saucer.entity.shapebody.shot;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.color.Color;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.tender.saucer.collision.BodyData;
import com.tender.saucer.collision.ICollide;
import com.tender.saucer.entity.shapebody.DynamicShapeBody;
import com.tender.saucer.entity.shapebody.penalty.Penalty;
import com.tender.saucer.entity.shapebody.player.Player;
import com.tender.saucer.entity.shapebody.powerup.Powerup;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public class Shot extends DynamicShapeBody
{
	public static final float DEFAULT_SIZE = 10;
	public static final float DEFAULT_SPEED = -20;
	public static float shotDamage = 1;
	public static float shotSize = DEFAULT_SIZE;
	public static float shotSpeed = DEFAULT_SPEED;

	public static Shot buildShot()
	{
		Shot shot = new Shot();
		shot.body.setUserData(new BodyData(shot));
		Model.transients.add(shot);

		return shot;
	}

	public static void init()
	{
		shotSize = DEFAULT_SIZE;
		shotSpeed = DEFAULT_SPEED;
		shotDamage = 1;
	}

	public float damage = 1;
	private boolean active = true;

	private Shot()
	{
		damage = Shot.shotDamage;
		speed = Shot.shotSpeed;

		float x = Model.player.shape.getX() + (Player.DEFAULT_WIDTH / 2) - (Shot.shotSize / 2);
		float y = Model.player.shape.getY() - Shot.shotSize;
		shape = new Rectangle(x, y, Shot.shotSize, Shot.shotSize, Model.main.getVertexBufferObjectManager());
		shape.setColor(Color.WHITE);

		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0, true);
		fixDef.filter.categoryBits = Constants.SHOT_BITMASK;
		fixDef.filter.maskBits = Constants.ENEMY_BITMASK | Constants.POWERUP_BITMASK | Constants.SIDE_WALL_BITMASK;
		body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.DynamicBody, fixDef);
		body.setBullet(true);
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}

	public void collide(ICollide other)
	{
		if(other instanceof Penalty || other instanceof Powerup)
		{
			Model.player.collide(other);
		}

		active = false;
	}

	@Override
	public void setInMotion()
	{
		body.setLinearVelocity(0, speed);
	}

	public boolean update()
	{
		if(shape.getY() + shape.getHeight() < Constants.TOP_BOT_HEIGHT || !active)
		{
			return true;
		}

		return false;
	}
}
