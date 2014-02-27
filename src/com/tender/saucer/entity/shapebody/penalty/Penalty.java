
package com.tender.saucer.entity.shapebody.penalty;

import java.util.Calendar;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.color.Color;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.tender.saucer.collision.ICollide;
import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.entity.particle.ParticleSystem;
import com.tender.saucer.entity.shapebody.TargetShapeBody;
import com.tender.saucer.entity.shapebody.powerup.Powerup;
import com.tender.saucer.entity.shapebody.wall.SideWall;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.stuff.Textures;
import com.tender.saucer.wave.WaveMachine;

public class Penalty extends TargetShapeBody
{
	public static final float DEFAULT_PENALTY_DURATION = 8000;
	public static final float DEFAULT_PENALTY_SLOWDOWN_FACTOR = .5f;
	private float flashCooldown = 150;
	private long lastFlashTime = 0;
	private boolean active = true;

	public Penalty()
	{
		speed = 5 + (float)(Math.random() * 5);

		float m = Math.random() < .5 ? -1 : 1;
		tx = m * (float)(Math.random() * Constants.CAMERA_WIDTH * 2);

		float x = (float)(Math.random() * (Constants.CAMERA_WIDTH - Powerup.DEFAULT_SIZE));
		float y = -Powerup.DEFAULT_SIZE;
		shape = new Sprite(x, y, Powerup.DEFAULT_SIZE, Powerup.DEFAULT_SIZE, Textures.PENALTY_ENEMY, Model.main
				.getVertexBufferObjectManager());
		shape.setColor(ColorScheme.foreground);

		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixDef.filter.categoryBits = Constants.ENEMY_BITMASK;
		fixDef.filter.maskBits = Constants.PLAYER_BITMASK | Constants.SHOT_BITMASK | Constants.SIDE_WALL_BITMASK
				| Constants.WALL_BITMASK;
		body = PhysicsFactory.createBoxBody(Model.world, shape, BodyType.DynamicBody, fixDef);
		body.setFixedRotation(true);
		Model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}

	public void collide(ICollide other)
	{
		if(!(other instanceof SideWall))
		{
			active = false;
		}
	}

	@Override
	public void done()
	{
		WaveMachine.instance.numPenaltiesLeft--;
		ParticleSystem.begin(this, ColorScheme.foreground);
		super.done();
	}

	public boolean update()
	{
		long currTime = Calendar.getInstance().getTimeInMillis();
		long timeElapsed = currTime - lastFlashTime;
		if(timeElapsed > flashCooldown)
		{
			lastFlashTime = currTime;

			if(shape.getColor().equals(ColorScheme.foreground))
			{
				shape.setColor(Color.WHITE);
			}
			else
			{
				shape.setColor(ColorScheme.foreground);
			}
		}

		return !active;
	}
}
