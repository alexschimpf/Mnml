package com.tender.saucer.shapebody.powerup;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.particle.ParticleSystem;
import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.shapebody.enemy.Enemy;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.stuff.Textures;
import com.tender.saucer.update.ITransientUpdate;

public class BombPowerup extends Powerup
{

	protected BombPowerup()
	{
		super();
		
		overrides = false;
		
		float x = (float)(Math.random() * (Constants.CAMERA_WIDTH - Constants.POWERUP_SIZE));
		float y = -Constants.POWERUP_SIZE;
		shape = new Sprite(x, y, Constants.POWERUP_SIZE, Constants.POWERUP_SIZE, Textures.POWERUP_BOMB, 
				Model.main.getVertexBufferObjectManager());	
		shape.setColor(ColorScheme.foreground);

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
		Model.background.flash();
		ParticleSystem.init(this);
		
		for(ITransientUpdate t : Model.transients)
		{
			if(t instanceof Enemy)
			{
				((Enemy)t).health = 0;
			}
		}
	}

	@Override
	public void remove() 
	{
	}
}
