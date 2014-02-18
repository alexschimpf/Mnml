package com.tender.saucer.shapebody.powerup;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.shapebody.player.Player;
import com.tender.saucer.shapebody.shot.Shot;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.stuff.Textures;

public class BigShotPowerup extends Powerup
{
	public BigShotPowerup() 
	{
		super();
		
		float x = (float)(Math.random() * (Constants.CAMERA_WIDTH - Powerup.DEFAULT_SIZE));
		float y = -Powerup.DEFAULT_SIZE;
		shape = new Sprite(x, y, Powerup.DEFAULT_SIZE, Powerup.DEFAULT_SIZE, Textures.POWERUP_BIG_SHOT, 
				Model.main.getVertexBufferObjectManager());	
		shape.setColor(ColorScheme.foreground);

		initBody();
	}

	@Override
	public void apply() 
	{
		Shot.shotSize = Player.DEFAULT_WIDTH * .75f;
		Shot.shotDamage = 3;
	}

	@Override
	public void remove() 
	{
		Shot.shotSize = Shot.DEFAULT_SIZE;
		Shot.shotDamage = 1;
	}
}
