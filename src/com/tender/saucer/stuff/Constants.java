package com.tender.saucer.stuff;

import com.tender.saucer.shapebody.enemy.BasicEnemy;
import com.tender.saucer.shapebody.enemy.BigEnemy;
import com.tender.saucer.shapebody.enemy.BounceEnemy;
import com.tender.saucer.shapebody.enemy.Enemy;
import com.tender.saucer.shapebody.enemy.MorphEnemy;
import com.tender.saucer.shapebody.enemy.SplitEnemy;
import com.tender.saucer.shapebody.powerup.BigShotPowerup;
import com.tender.saucer.shapebody.powerup.BombPowerup;
import com.tender.saucer.shapebody.powerup.HealthPowerup;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class Constants 
{	
	private Constants()
	{
	}
	
	// Game
	public static final int PX_TO_M = 32;
	public static final int CAMERA_WIDTH = 400;
	public static final int CAMERA_HEIGHT = 800;	
	public static final float OOB_WIDTH = 20;
	public static final float TOP_BOT_HEIGHT = 50;

	// Player
	public static final float DEFAULT_PLAYER_WIDTH = 50;
	public static final float DEFAULT_PLAYER_HEIGHT = 25;
	public static final int DEFAULT_PLAYER_HEALTH = 5;
	public static final float DEFAULT_PLAYER_SHOOT_COOLDOWN = 350;
	
	// Shot
	public static final float DEFAULT_SHOT_SIZE = 10;
	public static final float DEFAULT_SHOT_SPEED = -20;
	
	// Powerup
	public static final float DEFAULT_POWERUP_DURATION = 8000;
	public static final float POWERUP_SIZE = 50;
	
	// Penalty
	public static final float PENALTY_DURATION = 10000;
	public static final float PENALTY_FACTOR = .5f;
	public static final float PENALTY_PROBABILITY = .09f;
	
	// Particle
	public static final float DEFAULT_PARTICLE_SIZE = 5;
	public static final int DEFAULT_NUM_PARTICLES_PER_SYSTEM = 20;
	public static final int DEFAULT_MAX_PARTICLE_DURATION = 2000;
	
	// Wave
	public static final float DEFAULT_WAVE_ENEMY_BUILD_COOLDOWN = 2200;
	public static final float DEFAULT_WAVE_POWERUP_BUILD_COOLDOWN = 22000;

	// Font
	public static final int HUD_FONT_SIZE = 22;
	public static final int WAVE_INTERMISSION_FONT_SIZE = 45;
	
	// Collision
	public static final short PLAYER_BITMASK = 0x0001;
	public static final short SHOT_BITMASK = 0x0002;
	public static final short ENEMY_BITMASK = 0x0004;
	public static final short POWERUP_BITMASK = 0x0008;
	public static final short SIDE_WALL_BITMASK = 0x0010;
	public static final short WALL_BITMASK = 0x0020;
	
	// Miscellaneous
	public static final Class<?>[] ENEMY_CLASSES = new Class<?>[]
	{
		BasicEnemy.class,
		BigEnemy.class,
		BounceEnemy.class,
		MorphEnemy.class,
		SplitEnemy.class
	};	
	public static final Class<?>[] POWERUP_CLASSES = new Class<?>[]
	{
		HealthPowerup.class,
		BigShotPowerup.class,
		BombPowerup.class
	};
	public static final String[] WAVE_INTERMISSION_MESSAGES = new String[]
	{
		"COOL.",
		"DUDE...",
		"GET\nA\nJOB",
		"WAVE\nCOMPLETE",
		"WAVE\nDONE",
		"DONE\nYET?",
		"YOU'RE\nAMAZING!",
		"YOU'RE\nPRETTY\nGOOD",
		"GREAT\nJOB!",
		"GREAT\nJOB?",
		"YOU'RE\nTRASH",
		"NOT\nDONE\nYET",
		":)",
		"(.)(.)",
		"8===>",
		"<3",
		"YOU'RE\nBEAUTIFUL",
		"WAVE'S\nFINISHED",
		"THERE'S\nMORE...",
		"YOU'RE\nLIKE\nSO\nGOOD",
		"YOU'RE\nLITERALLY\nAMAZING",
		"VERY\nGOOD",
		"AWESOME\nBRO",
		"IT'S\nALL IN\nTHE\nREFLEXES"
	};
}
