package com.tender.saucer.shapebody;

import org.andengine.entity.shape.IAreaShape;
import org.andengine.util.IDisposable;

import com.badlogic.gdx.physics.box2d.Body;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class ShapeBody implements IDispose
{
	public IAreaShape shape;
	public Body body;
	
	public ShapeBody() 
	{		
	}
	
	public void attachToScene()
	{
		Model.scene.attachChild(shape);
	}
	
	public void dispose()
	{ 
		Model.world.unregisterPhysicsConnector(Model.world.getPhysicsConnectorManager().findPhysicsConnectorByShape(shape));
		Model.scene.detachChild(shape);						
		Model.world.destroyBody(body);
	}
}
