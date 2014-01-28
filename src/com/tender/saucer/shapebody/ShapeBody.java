package com.tender.saucer.shapebody;

import org.andengine.entity.shape.IAreaShape;

import com.badlogic.gdx.physics.box2d.Body;
import com.tender.saucer.stuff.Model;

public abstract class ShapeBody
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
	
	protected void recycle()
	{ 
		Model.world.unregisterPhysicsConnector(Model.world.getPhysicsConnectorManager().findPhysicsConnectorByShape(shape));
		Model.scene.detachChild(shape);						
		Model.world.destroyBody(body);
	}
}
