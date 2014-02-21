
package com.tender.saucer.stuff;

import com.tender.saucer.entity.shapebody.enemy.BasicEnemy;
import com.tender.saucer.entity.shapebody.enemy.BigEnemy;
import com.tender.saucer.entity.shapebody.enemy.BounceEnemy;
import com.tender.saucer.entity.shapebody.enemy.MorphEnemy;
import com.tender.saucer.entity.shapebody.enemy.SplitEnemy;
import com.tender.saucer.entity.shapebody.powerup.BigShotPowerup;
import com.tender.saucer.entity.shapebody.powerup.BombPowerup;
import com.tender.saucer.entity.shapebody.powerup.HealthPowerup;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */
public final class Constants
{
	// Main
	public static final int PX_TO_M = 32;
	public static final int CAMERA_WIDTH = 400;
	public static final int CAMERA_HEIGHT = 800;
	public static final float TOP_BOT_HEIGHT = 50;
	public static final float OOB_WIDTH = 20;
	// Fonts
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
	public static final Class<?>[] ENEMY_CLASSES = new Class<?>[] { BasicEnemy.class, BigEnemy.class,
			BounceEnemy.class, MorphEnemy.class, SplitEnemy.class };
	public static final Class<?>[] POWERUP_CLASSES = new Class<?>[] { HealthPowerup.class, BigShotPowerup.class,
			BombPowerup.class };
	public static final String[] WAVE_INTERMISSION_MESSAGES = new String[] { "COOL.", "DUDE...", "GET\nA\nJOB",
			"WAVE\nCOMPLETE", "WAVE\nDONE", "DONE\nYET?", "YOU'RE\nAMAZING!", "YOU'RE\nPRETTY\nGOOD", "GREAT\nJOB!",
			"GREAT\nJOB?", "YOU'RE\nTRASH", "NOT\nDONE\nYET", "WAVE'S\nFINISHED", "THERE'S\nMORE...",
			"YOU'RE\nLIKE\nSO\nGOOD", "YOU'RE\nLITERALLY\nAMAZING", "VERY\nGOOD", "AWESOME\nBRO",
			"IT'S\nALL IN\nTHE\nREFLEXES" };
	public static final String[] GAME_OVER_MESSAGES = new String[] { "GAME OVER", "NICE TRY", "YOU'RE DONE",
			"GOOD GAME", "THAT'S IT", "THAT'S ALL SHE WROTE" };

	private Constants()
	{
	}
}
