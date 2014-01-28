package com.tender.saucer.shapebody.wall;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.shapebody.BodyData;
import com.tender.saucer.shapebody.ICollide;
import com.tender.saucer.shapebody.IUpdate;
import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class SideWall extends ShapeBody implements ICollide
{
	private static final float WIDTH = 5;
	
	public SideWall(boolean left) 
	{
		float x = left ? -SideWall.WIDTH : Constants.CAMERA_WIDTH;				
		shape = new Rectangle(x, 0, SideWall.WIDTH, Constants.CAMERA_HEIGHT, Model.main.getVertexBufferObjectManager());
		shape.setVisible(false);
		
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, .5f, 0);
		fixDef.filter.categoryBits = Constants.OOB_BITMASK;
		fixDef.filter.maskBits = Constants.ENEMY_BITMASK | Constants.POWERUP_BITMASK | Constants.SHOT_BITMASK;
		
		Body body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.KinematicBody, fixDef);	
		body.setFixedRotation(true);	
		body.setUserData(new BodyData(this));
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true)); 
	}
	
	public void collide(ICollide other)
	{
	}
}
