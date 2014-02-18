package com.tender.saucer.shapebody.wall;

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
import com.tender.saucer.color.ColorUtilities;
import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.shapebody.enemy.Enemy;
import com.tender.saucer.shapebody.enemy.PenaltyEnemy;
import com.tender.saucer.shapebody.powerup.Powerup;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.update.IPersistentUpdate;
import com.tender.saucer.update.ITransientUpdate;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public final class Wall extends ShapeBody implements ICollide
{
	public Wall() 
	{
		float y = Constants.CAMERA_HEIGHT - Constants.TOP_BOT_HEIGHT;		
		shape = new Rectangle(0, y, Constants.CAMERA_WIDTH, Constants.TOP_BOT_HEIGHT, Model.main.getVertexBufferObjectManager());
		shape.setColor(Color.WHITE);
		
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixDef.filter.categoryBits = Constants.WALL_BITMASK;
		fixDef.filter.maskBits = Constants.ENEMY_BITMASK | Constants.POWERUP_BITMASK;
		
		body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.KinematicBody, fixDef);	
		body.setFixedRotation(true);	
		body.setUserData(new BodyData(this));
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}

	public void collide(ICollide other) 
	{ 
		if((other instanceof Enemy) && !(other instanceof PenaltyEnemy))
		{
			Model.player.health--;
			Model.background.flash();   
		}
	}
}
