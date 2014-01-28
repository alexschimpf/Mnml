package com.tender.saucer.shapebody.shot;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.color.Color;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.shapebody.BodyData;
import com.tender.saucer.shapebody.DynamicShapeBody;
import com.tender.saucer.shapebody.ICollide;
import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.shapebody.enemy.PenaltyEnemy;
import com.tender.saucer.shapebody.player.Player;
import com.tender.saucer.stuff.ColorScheme;
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
	private float health = 1;
	
	private Shot(float width, float height, float speed) 
	{	
		Player player = Model.player;
		
		this.speed = speed;

		float x = player.shape.getX() + (Constants.DEFAULT_PLAYER_WIDTH / 2) - (width / 2);
		float y = player.shape.getY() - height;		
		shape = new Rectangle(x, y, width, height, Model.main.getVertexBufferObjectManager());
		shape.setColor(Color.WHITE);
		
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0, true);
		fixDef.filter.categoryBits = Constants.SHOT_BITMASK;
		fixDef.filter.maskBits = Constants.ENEMY_BITMASK | Constants.POWERUP_BITMASK | Constants.OOB_BITMASK;
		
		body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.DynamicBody, fixDef);	
		body.setBullet(true);
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}

	public static Shot buildShot(float width, float height, float speed)
	{	
		Shot shot = new Shot(width, height, speed);
		shot.body.setUserData(new BodyData(shot));
		
		Model.actives.add(shot);
		
		return shot;
	}
	
	public boolean update()
	{
		if(shape.getY() + shape.getHeight() < Constants.TOP_BOT_HEIGHT || health <= 0)
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
		
		health--;
	}
}
