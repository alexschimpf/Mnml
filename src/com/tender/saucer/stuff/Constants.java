package com.tender.saucer.stuff;

public class Constants 
{
	public static final int PX_TO_M = 32;
	
	public static final int CAMERA_WIDTH = 400;
	public static final int CAMERA_HEIGHT = 800;	
	public static final float OOB_WIDTH = 20;
	public static final float TOP_BOT_HEIGHT = 50;
	public static final int DEFAULT_WALL_HEALTH = 5;

	public static final float DEFAULT_PLAYER_WIDTH = 50;
	public static final float DEFAULT_PLAYER_HEIGHT = 25;
	public static final int DEFAULT_PLAYER_HEALTH = 3;
	public static final float DEFAULT_PLAYER_SHOOT_COOLDOWN = 350;
	
	public static final float DEFAULT_SHOT_WIDTH = 10;
	public static final float DEFAULT_SHOT_HEIGHT = 10;
	public static final float DEFAULT_SHOT_SPEED = -20;
	
	public static final float PARTICLE_WIDTH = 5;
	public static final float PARTICLE_HEIGHT = 5;	
	public static final int NUM_PARTICLES_PER_SYSTEM = 20;
	
	public static final float DEFAULT_WAVE_BUILD_COOLDOWN = 2000;
	
	public static final float POWERUP_DURATION = 10000;
	public static final float DEFAULT_POWERUP_BUILD_COOLDOWN = 10000;
	
	public static final float PENALTY_DURATION = 10000;
	public static final float PENALTY_FACTOR = .5f;
	public static final float PENALTY_PROBABILITY = .05f;
	
	public static final float ENEMY_BASIC_LEVEL = 1;
	public static final float ENEMY_BIG_LEVEL = 3;
	public static final float ENEMY_MORPH_LEVEL = 5;
	public static final float ENEMY_SPLIT_LEVEL = 7;
	public static final float ENEMY_BOUNCE_LEVEL = 9;
	
	public static final float WAVE_INTERMISSION_DURATION = 6500;

	public static final int HUD_FONT_SIZE = 26;
	public static final int WAVE_INTERMISSION_FONT_SIZE = 60;
	
	public static final short PLAYER_BITMASK = 0x0001;
	public static final short SHOT_BITMASK = 0x0002;
	public static final short ENEMY_BITMASK = 0x0004;
	public static final short POWERUP_BITMASK = 0x0008;
	public static final short OOB_BITMASK = 0x0010;
	public static final short WALL_BITMASK = 0x0020;
}
