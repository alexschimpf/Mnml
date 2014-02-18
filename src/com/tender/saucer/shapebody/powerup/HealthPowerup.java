package com.tender.saucer.shapebody.powerup;

import java.util.Calendar;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.stuff.Textures;

public class HealthPowerup extends Powerup
{
	public HealthPowerup() 
	{
		super();
		
		overrides = false;
		
		float x = (float)(Math.random() * (Constants.CAMERA_WIDTH - Constants.POWERUP_SIZE));
		float y = -Constants.POWERUP_SIZE;
		shape = new Sprite(x, y, Constants.POWERUP_SIZE, Constants.POWERUP_SIZE, Textures.POWERUP_HEALTH, 
				Model.main.getVertexBufferObjectManager());	
		shape.setColor(ColorScheme.foreground);

		initBody();
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
