package com.tender.saucer.stuff;

import java.util.LinkedList;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.StrokeFont;

import com.tender.saucer.activity.Main;
import com.tender.saucer.background.Background;
import com.tender.saucer.shapebody.player.Player;
import com.tender.saucer.shapebody.wall.SideWall;
import com.tender.saucer.shapebody.wall.Wall;
import com.tender.saucer.update.ITransientUpdate;
import com.tender.saucer.wave.WaveRecess;
import com.tender.saucer.wave.WaveMachine;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public final class Model 
{
	public static Main main;
	public static PhysicsWorld world;
	public static Scene scene;
	public static ZoomCamera camera;
	public static HUD hud;
	public static Player player;
	public static Wall wall;
	public static SideWall oobLeft;
	public static SideWall oobRight;
	public static Rectangle hudRect;
	public static Background background;
	public static Font hudFont;
	public static Font waveIntermissionFont;
	public static Text scoreText;
	public static Text waveText;
	public static Rectangle lifeBar;
	public static LinkedList<ITransientUpdate> transients;	
	public static GameState state;

	private Model() 
	{
	}
	
	public static void init()
	{
		transients = new LinkedList<ITransientUpdate>();
		state = GameState.GAME_PAUSED;
	}
}
