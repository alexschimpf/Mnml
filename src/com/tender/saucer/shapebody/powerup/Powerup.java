package com.tender.saucer.shapebody.powerup;

import java.util.Calendar;

import org.andengine.entity.primitive.DrawMode;
import org.andengine.entity.primitive.Mesh;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.vbo.HighPerformanceMeshVertexBufferObject;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.vbo.DrawType;

import android.graphics.Color;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.collision.BodyData;
import com.tender.saucer.collision.ICollide;
import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.particle.ParticleSystem;
import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.shapebody.TargetShapeBody;
import com.tender.saucer.shapebody.enemy.Enemy;
import com.tender.saucer.shapebody.player.Player;
import com.tender.saucer.shapebody.shot.Shot;
import com.tender.saucer.shapebody.wall.SideWall;
import com.tender.saucer.shapebody.wall.Wall;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public abstract class Powerup extends TargetShapeBody
{
	protected boolean active = true;
	
	protected Powerup() 
	{	
		speed = 5 + (float)(Math.random() * 5);
		
		float m = Math.random() < .5 ? -1 : 1;
		tx = m * (float)(Math.random() * Constants.CAMERA_WIDTH * 2);
	}

	public static Powerup buildRandomPowerup()
	{
		try 
		{
			int choice = (int)(Math.random() * Constants.POWERUP_CLASSES.length);
			Powerup powerup = (Powerup)Constants.POWERUP_CLASSES[choice].newInstance();
			powerup.body.setUserData(new BodyData(powerup));			
			Model.transients.add(powerup);
			
			return powerup;		
		} 
		catch (Exception e) 
		{
			return null;
		}
	}
	
	public abstract void apply();
	
	public abstract void remove();
	
	public boolean update()
	{
		return !active;
	}
	
	public void done()
	{
		Model.waveMachine.currNumPowerupsLeft--;
		recycle();
	}

	public void collide(ICollide other) 
	{
		if(!(other instanceof SideWall))
		{
			ParticleSystem.init(this);
			active = false;
		}
	}
}
