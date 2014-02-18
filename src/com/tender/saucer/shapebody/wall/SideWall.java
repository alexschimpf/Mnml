package com.tender.saucer.shapebody.wall;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.collision.BodyData;
import com.tender.saucer.collision.ICollide;
import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.update.ITransientUpdate;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class SideWall extends ShapeBody implements ICollide
{
	public SideWall(boolean left) 
	{
		float x = left ? -5 : Constants.CAMERA_WIDTH;				
		shape = new Rectangle(x, 0, 5, Constants.CAMERA_HEIGHT, Model.main.getVertexBufferObjectManager());
		shape.setVisible(false);
		
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, .5f, 0);
		fixDef.filter.categoryBits = Constants.SIDE_WALL_BITMASK;
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
