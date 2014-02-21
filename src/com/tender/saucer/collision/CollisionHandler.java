
package com.tender.saucer.collision;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */

public final class CollisionHandler implements ContactListener
{
	public void beginContact(Contact contact)
	{
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		Body bodyA = fixA.getBody();
		Body bodyB = fixB.getBody();

		try
		{
			ICollide ownerA = ((BodyData)bodyA.getUserData()).owner;
			ICollide ownerB = ((BodyData)bodyB.getUserData()).owner;

			ownerA.collide(ownerB);
			ownerB.collide(ownerA);
		}
		catch (NullPointerException e)
		{
			// TODO
		}
	}

	public void endContact(Contact contact)
	{

	}

	public void postSolve(Contact contact, ContactImpulse impulse)
	{

	}

	public void preSolve(Contact contact, Manifold oldManifold)
	{

	}
}
