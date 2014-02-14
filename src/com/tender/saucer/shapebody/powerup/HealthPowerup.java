package com.tender.saucer.shapebody.powerup;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.stuff.Textures;

public class HealthPowerup extends Powerup
{
	protected HealthPowerup() 
	{
		super();
		
		float x = (float)(Math.random() * (Constants.CAMERA_WIDTH - Constants.POWERUP_SIZE));
		float y = -Constants.POWERUP_SIZE;
		shape = new TiledSprite(x, y, Constants.POWERUP_SIZE, Constants.POWERUP_SIZE, Textures.POWERUP_HEALTH, 
				Model.main.getVertexBufferObjectManager());

		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixDef.filter.categoryBits = Constants.ENEMY_BITMASK;
		fixDef.filter.maskBits = Constants.PLAYER_BITMASK | Constants.SHOT_BITMASK | Constants.SIDE_WALL_BITMASK | Constants.WALL_BITMASK;
		
		body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.DynamicBody, fixDef);
		body.setFixedRotation(true);	
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}

	@Override
	public void apply() 
	{
		Model.player.health = Math.min(Model.player.health + 1, Constants.DEFAULT_PLAYER_HEALTH);
	}
	
	@Override
	public void remove() 
	{
	}
}
