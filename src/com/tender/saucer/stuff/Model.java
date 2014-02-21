
package com.tender.saucer.stuff;

import java.util.LinkedList;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.Font;

import com.tender.saucer.activity.Main;
import com.tender.saucer.entity.background.Background;
import com.tender.saucer.entity.shapebody.player.Player;
import com.tender.saucer.entity.shapebody.wall.SideWall;
import com.tender.saucer.entity.shapebody.wall.Wall;
import com.tender.saucer.update.ITransientUpdate;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public final class Model
{
	public static Background background;
	public static ZoomCamera camera;
	public static HUD hud;
	public static Font hudFont;
	public static Rectangle hudRect;
	public static Rectangle lifeBar;
	public static Main main;
	public static SideWall oobLeft;
	public static SideWall oobRight;
	public static Player player;
	public static Scene scene;
	public static Text scoreText;
	public static GameState state;
	public static LinkedList<ITransientUpdate> transients;
	public static Wall wall;
	public static Font waveIntermissionFont;
	public static Text waveText;
	public static PhysicsWorld world;

	public static void init()
	{
		transients = new LinkedList<ITransientUpdate>();
		state = GameState.GAME_PAUSED;
	}

	private Model()
	{
	}
}
