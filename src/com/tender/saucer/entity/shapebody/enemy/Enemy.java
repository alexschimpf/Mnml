package com.tender.saucer.entity.shapebody.enemy;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.tender.saucer.entity.particle.ParticleSystem;
import com.tender.saucer.entity.shapebody.TargetShapeBody;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.WaveMachine;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public abstract class Enemy extends TargetShapeBody
{	
	public float health = 1;
	
	public Enemy() 
	{
	}
	
	@Override
	public void done()
	{
		WaveMachine.numEnemiesLeft--;
		ParticleSystem.begin(this);	
		super.done();
	}
	
	public boolean update()
	{
		return health <= 0;
	}
	
	protected void initBody()
	{
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixDef.filter.categoryBits = Constants.ENEMY_BITMASK;
		fixDef.filter.maskBits = Constants.PLAYER_BITMASK | Constants.SHOT_BITMASK | Constants.SIDE_WALL_BITMASK | Constants.WALL_BITMASK;
		
		body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.DynamicBody, fixDef);
		body.setFixedRotation(true);	
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}
}
