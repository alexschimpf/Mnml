
package com.tender.saucer.entity.shapebody.player;

import java.util.Calendar;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.color.Color;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.tender.saucer.activity.IOnResumeGameListener;
import com.tender.saucer.collision.BodyData;
import com.tender.saucer.collision.ICollide;
import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.entity.shapebody.ShapeBody;
import com.tender.saucer.entity.shapebody.enemy.Enemy;
import com.tender.saucer.entity.shapebody.enemy.PenaltyEnemy;
import com.tender.saucer.entity.shapebody.powerup.Powerup;
import com.tender.saucer.entity.shapebody.shot.Shot;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.update.IPersistentUpdate;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public final class Player extends ShapeBody implements ICollide, IPersistentUpdate, IOnResumeGameListener
{
	public static final float DEFAULT_HEALTH = 5;
	public static final float DEFAULT_HEIGHT = 25;
	public static final float DEFAULT_SHOOT_COOLDOWN = 350;
	public static final float DEFAULT_WIDTH = 50;
	public float health = Player.DEFAULT_HEALTH;
	public boolean penalty = false;
	public long score = 0;
	public float shootCooldown = Player.DEFAULT_SHOOT_COOLDOWN;
	private long lastPenaltyTime = 0;
	private long lastPowerupTime = 0;
	private long lastShotTime = 0;
	private Powerup powerup = null;

	public Player()
	{
		float x = (Constants.CAMERA_WIDTH - Player.DEFAULT_WIDTH) / 2;
		float y = Constants.CAMERA_HEIGHT - Constants.TOP_BOT_HEIGHT - Player.DEFAULT_HEIGHT;
		shape = new Rectangle(x, y, Player.DEFAULT_WIDTH, Player.DEFAULT_HEIGHT, Model.main
				.getVertexBufferObjectManager());
		shape.setColor(Color.WHITE);
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixDef.filter.categoryBits = Constants.PLAYER_BITMASK;
		fixDef.filter.maskBits = Constants.ENEMY_BITMASK | Constants.POWERUP_BITMASK;
		body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.KinematicBody, fixDef);
		body.setFixedRotation(true);
		body.setUserData(new BodyData(this));
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}

	public void applyPenalty()
	{
		lastPenaltyTime = Calendar.getInstance().getTimeInMillis();
		if (!penalty)
		{
			penalty = true;
			shape.setColor(ColorScheme.foreground);
			shootCooldown /= PenaltyEnemy.DEFAULT_PENALTY_SLOWDOWN_FACTOR;
			Shot.shotSpeed = Shot.DEFAULT_SPEED * PenaltyEnemy.DEFAULT_PENALTY_SLOWDOWN_FACTOR;
		}
	}

	public void applyPowerup(Powerup powerup)
	{
		if (!powerup.overrides)
		{
			powerup.apply();
			return;
		}
		if (this.powerup != null)
		{
			this.powerup.remove();
		}
		powerup.apply();
		this.powerup = powerup;
		lastPowerupTime = Calendar.getInstance().getTimeInMillis();
	}

	public void collide(ICollide other)
	{
		if (other instanceof PenaltyEnemy)
		{
			applyPenalty();
		}
		else
			if (other instanceof Enemy)
			{
				health--;
				Model.background.flash();
			}
			else
				if (other instanceof Powerup)
				{
					applyPowerup((Powerup)other);
				}
	}

	public void move(float x)
	{
		body.setTransform(x / Constants.PX_TO_M, body.getPosition().y, body.getAngle());
	}

	public void onResumeGame(long awayDuration)
	{
		lastPenaltyTime += awayDuration;
		lastPowerupTime += awayDuration;
		lastShotTime += awayDuration;
	}

	public void tryShoot()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - lastShotTime;
		if (timeElapsed > shootCooldown)
		{
			lastShotTime = currTime;
			shoot();
		}
	}

	public void update()
	{
		if (health <= 0)
		{
			Model.state = GameState.GAME_OVER;
		}
		if (powerup != null)
		{
			long currTime = Calendar.getInstance().getTimeInMillis();
			long elapsedTime = currTime - lastPowerupTime;
			if (elapsedTime > Powerup.DEFAULT_DURATION)
			{
				powerup.remove();
				powerup = null;
			}
		}
		if (penalty)
		{
			long currTime = Calendar.getInstance().getTimeInMillis();
			long elapsedTime = currTime - lastPenaltyTime;
			if (elapsedTime > PenaltyEnemy.DEFAULT_PENALTY_DURATION)
			{
				penalty = false;
				shape.setColor(Color.WHITE);
				shootCooldown = Player.DEFAULT_SHOOT_COOLDOWN;
				Shot.shotSpeed = Shot.DEFAULT_SPEED;
			}
		}
	}

	private void shoot()
	{
		Shot shot = Shot.buildShot();
		shot.attachToScene();
		shot.setInMotion();
	}
}
