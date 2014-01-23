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
		Model.instance().scene.attachChild(shape);
	}
	
	protected void recycle()
	{ 
		Model model = Model.instance();		
		model.world.unregisterPhysicsConnector(model.world.getPhysicsConnectorManager().findPhysicsConnectorByShape(shape));
		model.scene.detachChild(shape);						
		model.world.destroyBody(body);
	}
}
