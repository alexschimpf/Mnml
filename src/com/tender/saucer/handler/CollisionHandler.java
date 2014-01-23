package com.tender.saucer.handler;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tender.saucer.shapebody.BodyData;
import com.tender.saucer.shapebody.ICollide;
import com.tender.saucer.shapebody.ShapeBody;

public class CollisionHandler implements ContactListener
{
	public void beginContact(Contact contact) 
	{
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		Body bodyA = fixA.getBody();
		Body bodyB = fixB.getBody();
		
		ICollide ownerA = ((BodyData)bodyA.getUserData()).owner;
		ICollide ownerB = ((BodyData)bodyB.getUserData()).owner;
		
		ownerA.collide(ownerB);
		ownerB.collide(ownerA);
	}

	public void endContact(Contact contact) 
	{

	}

	public void preSolve(Contact contact, Manifold oldManifold) 
	{
		
	}

	public void postSolve(Contact contact, ContactImpulse impulse) 
	{
		
	}
}
