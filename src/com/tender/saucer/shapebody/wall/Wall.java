package com.tender.saucer.shapebody.wall;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.color.Color;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.shapebody.BodyData;
import com.tender.saucer.shapebody.ICollide;
import com.tender.saucer.shapebody.IUpdate;
import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.shapebody.enemy.Enemy;
import com.tender.saucer.shapebody.enemy.PenaltyEnemy;
import com.tender.saucer.shapebody.powerup.Powerup;
import com.tender.saucer.stuff.ColorScheme;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;

public class Wall extends ShapeBody implements ICollide, IUpdate
{
	public float health = Constants.DEFAULT_WALL_HEALTH;
	
	public Wall() 
	{
		Model model = Model.instance();		
			
		float y = Constants.CAMERA_HEIGHT - Constants.TOP_BOT_HEIGHT;		
		shape = new Rectangle(0, y, Constants.CAMERA_WIDTH, Constants.TOP_BOT_HEIGHT, model.main.getVertexBufferObjectManager());
		shape.setColor(Color.WHITE);
		
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixDef.filter.categoryBits = Constants.WALL_BITMASK;
		fixDef.filter.maskBits = Constants.ENEMY_BITMASK | Constants.POWERUP_BITMASK;
		
		body = PhysicsFactory.createBoxBody(model.world, shape, BodyType.KinematicBody, fixDef);	
		body.setFixedRotation(true);	
		body.setUserData(new BodyData(this));
		model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true));
	}

	public boolean update() 
	{
		if(health <= 0 || ((BodyData)body.getUserData()).remove)
		{
			return true;
		}
		
		return false;
	}
	
	public void done()
	{
		recycle();
		Model.instance().state = GameState.DONE;
	}

	public void collide(ICollide other) 
	{ 
		if((other instanceof Enemy) && !(other instanceof PenaltyEnemy))
		{
			health--;
			Model.instance().background.flash();   
		}
		
		Color darker = ColorScheme.darken(Color.WHITE, 1 - (health / Constants.DEFAULT_WALL_HEALTH));
		shape.setColor(darker);
	}
}
