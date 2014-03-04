
package com.tender.saucer.entity.shapebody;

import org.andengine.entity.shape.IAreaShape;

import com.badlogic.gdx.physics.box2d.Body;
import com.tender.saucer.entity.Entity;
import com.tender.saucer.stuff.Model;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public class ShapeBody extends Entity implements IDispose
{
	public Body body;
	public IAreaShape shape;

	public ShapeBody()
	{
	}

	@Override
	public void show()
	{
		Model.scene.attachChild(shape);
	}

	public void dispose()
	{
		Model.world.unregisterPhysicsConnector(Model.world.getPhysicsConnectorManager().findPhysicsConnectorByShape(
				shape));
		Model.scene.detachChild(shape);
		Model.world.destroyBody(body);
	}
}
