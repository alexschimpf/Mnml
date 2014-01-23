package com.tender.saucer.shapebody.wall;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.tender.saucer.shapebody.BodyData;
import com.tender.saucer.shapebody.ICollide;
import com.tender.saucer.shapebody.IUpdate;
import com.tender.saucer.shapebody.ShapeBody;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;

public class OOBWall extends ShapeBody implements ICollide
{
	private static final float WIDTH = 5;
	
	public OOBWall(boolean left) 
	{
		Model model = Model.instance();	
		
		float x = left ? -OOBWall.WIDTH : Constants.CAMERA_WIDTH;				
		shape = new Rectangle(x, 0, OOBWall.WIDTH, Constants.CAMERA_HEIGHT, model.main.getVertexBufferObjectManager());
		shape.setVisible(false);
		
		FixtureDef fixDef = PhysicsFactory.createFixtureDef(0, .5f, 0);
		fixDef.filter.categoryBits = Constants.OOB_BITMASK;
		fixDef.filter.maskBits = Constants.ENEMY_BITMASK | Constants.POWERUP_BITMASK | Constants.SHOT_BITMASK;
		
		Body body = PhysicsFactory.createBoxBody(model.world, shape, BodyType.KinematicBody, fixDef);	
		body.setFixedRotation(true);	
		body.setUserData(new BodyData(this));
		model.world.registerPhysicsConnector(new PhysicsConnector(shape, body, true, true)); 
	}
	
	public void collide(ICollide other)
	{
	}
}
