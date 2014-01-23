package com.tender.saucer.stuff;

import java.util.LinkedList;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.StrokeFont;

import com.tender.saucer.activity.Main;
import com.tender.saucer.background.Background;
import com.tender.saucer.shapebody.IUpdate;
import com.tender.saucer.shapebody.player.Player;
import com.tender.saucer.shapebody.wall.OOBWall;
import com.tender.saucer.shapebody.wall.Wall;

public class Model 
{
	private static Model instance;
	
	public Main main;
	public PhysicsWorld world;
	public Scene scene;
	public ZoomCamera camera;
	public HUD hud;
	public Player player;
	public Wall wall;
	public OOBWall oobLeft;
	public OOBWall oobRight;
	public Rectangle hudRect;
	public Background background;
	public StrokeFont hudFont;
	public StrokeFont waveIntermissionFont;
	public Text scoreText;
	public Text livesText;
	public Text waveText;
	public LinkedList<IUpdate> actives;	
	public GameState state;

	private Model() 
	{
		actives = new LinkedList<IUpdate>();
		state = GameState.WAVE_INTERMISSION;
	}
	
	public static void reset()
	{
		instance = null;
	}
	
	public static Model instance()
	{
		if(instance == null)
		{
			instance = new Model();
		}
		
		return instance;
	}
}
