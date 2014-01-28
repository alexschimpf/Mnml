package com.tender.saucer.shapebody.enemy;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.color.Color;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.particlesystem.ParticleSystem;
import com.tender.saucer.shapebody.BodyData;
import com.tender.saucer.shapebody.ICollide;
import com.tender.saucer.shapebody.player.Player;
import com.tender.saucer.shapebody.shot.Shot;
import com.tender.saucer.shapebody.wall.Wall;
import com.tender.saucer.stuff.ColorScheme;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.wave.WaveMachine;

public class BigEnemy extends BasicEnemy
{
	protected BigEnemy()
	{
		Model model = Model.instance();
		
		health = 2;
		speed = 3 + (float)(Math.random() * 2);
		
		float size = (float)((Constants.DEFAULT_SHOT_WIDTH * 6) + (Math.random() * Constants.DEFAULT_SHOT_WIDTH * 2));
		float m = Math.random() < .5 ? -1 : 1;
		tx = m * (float)(Math.random() * Constants.CAMERA_WIDTH * 2);
		
		float x = (float)(Math.random() * (Constants.CAMERA_WIDTH - size));
		float y = -size;
		shape = new Rectangle(x, y, size, size, model.main.getVertexBufferObjectManager());
		shape.setColor(ColorScheme.darken(ColorScheme.instance().enemy, .2f));
		
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixDef.filter.categoryBits = Constants.ENEMY_BITMASK;
		fixDef.filter.maskBits = Constants.PLAYER_BITMASK | Constants.SHOT_BITMASK | Constants.OOB_BITMASK | Constants.WALL_BITMASK;
		
		body = PhysicsFactory.createBoxBody(model.world, shape, BodyType.DynamicBody, fixDef);
		body.setFixedRotation(true);	
		body.setUserData(new BodyData(this));
		model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}
	
	@Override
	public void collide(ICollide other)
	{
		if(other instanceof Player)
		{
			health = 0;	
		}
		else if(other instanceof Wall)
		{
			health = 0;
		}
		else if(other instanceof Shot)
		{
			health--;
			
			if(health > 0)
			{
				shape.setColor(ColorScheme.brighten(shape.getColor(), .2f));	
			}
		}
		
		if(health <= 0)
		{
			ParticleSystem.init(this, Constants.NUM_PARTICLES_PER_SYSTEM * 2);	
		}
	}
}
