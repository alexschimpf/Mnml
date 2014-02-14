package com.tender.saucer.shapebody.shot;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.color.Color;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.collision.BodyData;
import com.tender.saucer.collision.ICollide;
import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.shapebody.DynamicShapeBody;
import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.shapebody.enemy.PenaltyEnemy;
import com.tender.saucer.shapebody.player.Player;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class Shot extends DynamicShapeBody
{	
	public float damage = 1;	
	private boolean active = true;
	
	private Shot(float width, float height, float speed) 
	{	
		this.speed = speed;

		float x = Model.player.shape.getX() + (Constants.DEFAULT_PLAYER_WIDTH / 2) - (width / 2);
		float y = Model.player.shape.getY() - height;		
		shape = new Rectangle(x, y, width, height, Model.main.getVertexBufferObjectManager());
		shape.setColor(Color.WHITE);
		
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0, true);
		fixDef.filter.categoryBits = Constants.SHOT_BITMASK;
		fixDef.filter.maskBits = Constants.ENEMY_BITMASK | Constants.POWERUP_BITMASK | Constants.SIDE_WALL_BITMASK;
		
		body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.DynamicBody, fixDef);	
		body.setBullet(true);
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}

	public static Shot buildShot(float width, float height, float speed)
	{	
		Shot shot = new Shot(width, height, speed);
		shot.body.setUserData(new BodyData(shot));
		
		Model.transients.add(shot);
		
		return shot;
	}
	
	public boolean update()
	{
		if(shape.getY() + shape.getHeight() < Constants.TOP_BOT_HEIGHT || !active)
		{
			return true;
		}
		
		return false;
	}
	
	public void collide(ICollide other)
	{
		if(other instanceof PenaltyEnemy)
		{
			Model.player.applyPenalty();
		}
		
		active = false;
	}
}
