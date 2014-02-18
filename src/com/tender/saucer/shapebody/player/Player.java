package com.tender.saucer.shapebody.player;

import java.util.Calendar;

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
import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.shapebody.enemy.Enemy;
import com.tender.saucer.shapebody.enemy.PenaltyEnemy;
import com.tender.saucer.shapebody.powerup.HealthPowerup;
import com.tender.saucer.shapebody.powerup.Powerup;
import com.tender.saucer.shapebody.shot.Shot;
import com.tender.saucer.shapebody.wall.Wall;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.update.IPersistentUpdate;
import com.tender.saucer.update.ITransientUpdate;
import com.tender.saucer.wave.WaveMachine;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class Player extends ShapeBody implements ICollide, IPersistentUpdate
{
	public float health = Constants.DEFAULT_PLAYER_HEALTH;
	public long score = 0;	
	public float shootCooldown = Constants.DEFAULT_PLAYER_SHOOT_COOLDOWN;
	public boolean penalty = false;
	
	private long lastShotTime = 0;
	private long lastPowerupTime = 0;
	private long lastPenaltyTime = 0;
	private Powerup powerup = null;

	public Player() 
	{
		float x = (Constants.CAMERA_WIDTH - Constants.DEFAULT_PLAYER_WIDTH) / 2;
		float y = Constants.CAMERA_HEIGHT - Constants.TOP_BOT_HEIGHT - Constants.DEFAULT_PLAYER_HEIGHT;
		shape = new Rectangle(x, y, Constants.DEFAULT_PLAYER_WIDTH, Constants.DEFAULT_PLAYER_HEIGHT, Model.main.getVertexBufferObjectManager());
		shape.setColor(Color.WHITE);

		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixDef.filter.categoryBits = Constants.PLAYER_BITMASK;
		fixDef.filter.maskBits = Constants.ENEMY_BITMASK | Constants.POWERUP_BITMASK;
		
		body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.KinematicBody, fixDef);
		body.setFixedRotation(true);	
		body.setUserData(new BodyData(this));
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}
	
	public void update()
	{
		if(health <= 0)
		{
			Model.state = GameState.GAME_OVER;
		}
		
		if(powerup != null)
		{		
			long currTime = Calendar.getInstance().getTimeInMillis();
			long elapsedTime = currTime - lastPowerupTime;
			if(elapsedTime > Constants.DEFAULT_POWERUP_DURATION)
			{
				powerup.remove();
				powerup = null;
			}
		}
		
		if(penalty)
		{
			long currTime = Calendar.getInstance().getTimeInMillis();
			long elapsedTime = currTime - lastPenaltyTime;
			if(elapsedTime > Constants.PENALTY_DURATION)
			{
				penalty = false;
				shape.setColor(Color.WHITE);
				shootCooldown = Constants.DEFAULT_PLAYER_SHOOT_COOLDOWN;
				Shot.shotSpeed = Constants.DEFAULT_SHOT_SPEED;
			}
		}
	}

	public void collide(ICollide other)
	{
		if(other instanceof PenaltyEnemy)
		{
			applyPenalty();
		}
		else if(other instanceof Enemy)
		{
			health--;
			Model.background.flash();
		}
		else if(other instanceof Powerup)
		{
			applyPowerup((Powerup)other);
		}
	}
	
	public void tryShoot()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - lastShotTime;
		if(timeElapsed > shootCooldown)
		{
			lastShotTime = currTime;
			shoot();
		}
	}
	
	public void move(float x)
	{
		body.setTransform(x / Constants.PX_TO_M, body.getPosition().y, body.getAngle());
	}
	
	public void applyPowerup(Powerup powerup)
	{
		if(!powerup.overrides)
		{
			powerup.apply();
			return;
		}
		
		if(this.powerup != null)
		{
			this.powerup.remove();
		}
		
		powerup.apply();
		this.powerup = powerup;
		lastPowerupTime = Calendar.getInstance().getTimeInMillis();
	}
	
	public void applyPenalty()
	{
		lastPenaltyTime = Calendar.getInstance().getTimeInMillis();
		
		if(!penalty)
		{
			penalty = true;
			shape.setColor(ColorScheme.foreground);
			shootCooldown /= Constants.PENALTY_FACTOR;
			Shot.shotSpeed = Constants.DEFAULT_SHOT_SPEED * Constants.PENALTY_FACTOR;
		}
	}
	
	private void shoot()
	{		
		Shot shot = Shot.buildShot();
		shot.attachToScene();
		shot.setInMotion();
	}
}
