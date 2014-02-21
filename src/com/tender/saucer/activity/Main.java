
package com.tender.saucer.activity;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badlogic.gdx.math.Vector2;
import com.tender.saucer.collision.CollisionHandler;
import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.color.ColorUtilities;
import com.tender.saucer.entity.background.Background;
import com.tender.saucer.entity.shapebody.player.Player;
import com.tender.saucer.entity.shapebody.shot.Shot;
import com.tender.saucer.entity.shapebody.wall.SideWall;
import com.tender.saucer.entity.shapebody.wall.Wall;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.stuff.Textures;
import com.tender.saucer.untitledgame.R;
import com.tender.saucer.update.UpdateHandler;
import com.tender.saucer.wave.WaveMachine;
import com.tender.saucer.wave.WaveRecess;

/**
 * 
 * Copyright 2014
 * 
 * @author Alex Schimpf
 * 
 */

public class Main extends SimpleBaseGameActivity implements IOnSceneTouchListener
{
	@Override
	public void onBackPressed()
	{
		if (mEngine.isRunning())
		{
			showPausedDialog();
			mEngine.stop();
		}
		else
		{
			mEngine.start();
		}
	}

	public EngineOptions onCreateEngineOptions()
	{
		ColorScheme.generateColors();

		Model.init();
		Shot.init();
		WaveMachine.init();
		WaveRecess.init();

		Model.main = this;
		Model.camera = new ZoomCamera(0, 0, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);

		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
				new FillResolutionPolicy(), Model.camera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);

		return engineOptions;
	}

	public boolean onSceneTouchEvent(Scene scene, TouchEvent event)
	{
		if (Model.state == GameState.GAME_OVER || Model.state == GameState.GAME_PAUSED)
		{
			return true;
		}

		float x = event.getX();
		float y = event.getY();

		if (y > Constants.CAMERA_HEIGHT - Constants.TOP_BOT_HEIGHT - Player.DEFAULT_HEIGHT)
		{
			Model.player.move(x);
		}
		else
		{
			Model.player.tryShoot();
		}

		return true;
	}

	public void showGameOverDialog()
	{
		long currScore = Model.player.score;
		long bestScore = updateBestScore(currScore);
		int backgroundColor = ColorScheme.foreground.getARGBPackedInt();
		int foregroundColor = ColorScheme.background.getARGBPackedInt();

		Typeface typeface = Typeface.createFromAsset(getAssets(), "Pixel Berry.TTF");

		LinearLayout menuLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.game_over_menu, null);
		menuLayout.setBackgroundColor(backgroundColor);

		TextView titleView = (TextView) menuLayout.getChildAt(0);
		titleView.setText(Constants.GAME_OVER_MESSAGES[(int) (Math.random() * Constants.GAME_OVER_MESSAGES.length)]);
		titleView.setTextColor(foregroundColor);
		titleView.setTypeface(typeface);

		TextView currScoreView = (TextView) menuLayout.getChildAt(2);
		currScoreView.setText("- " + currScore + " -");
		currScoreView.setTextColor(Color.BLACK_ARGB_PACKED_INT);
		currScoreView.setTypeface(typeface);

		TextView bestScoreView = (TextView) menuLayout.getChildAt(4);
		bestScoreView.setText("PREVIOUS BEST:\n" + bestScore);
		bestScoreView.setTextColor(Color.BLACK_ARGB_PACKED_INT);
		bestScoreView.setTypeface(typeface);

		Button restartButton = (Button) menuLayout.getChildAt(6);
		restartButton.setTypeface(typeface);
		restartButton.setTextColor(foregroundColor);
		restartButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				restart();
			}
		});

		Button mainMenuButton = (Button) menuLayout.getChildAt(7);
		mainMenuButton.setTypeface(typeface);
		mainMenuButton.setTextColor(foregroundColor);
		mainMenuButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Model.main.finish();
			}
		});

		final AlertDialog.Builder alert = new AlertDialog.Builder(Model.main);
		alert.setView(menuLayout);
		alert.show();
	}

	@Override
	protected void onCreateResources()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		BitmapTextureAtlas fontTexture1 = new BitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		Model.hudFont = FontFactory.createFromAsset(getFontManager(), fontTexture1, getAssets(), "Pixel Berry.TTF",
				Constants.HUD_FONT_SIZE, false, android.graphics.Color.BLACK);
		Model.hudFont.load();

		BitmapTextureAtlas fontTexture2 = new BitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		Model.waveIntermissionFont = FontFactory.createFromAsset(getFontManager(), fontTexture2, getAssets(),
				"Pixel Berry.TTF", Constants.WAVE_INTERMISSION_FONT_SIZE, false, android.graphics.Color.BLACK);
		Model.waveIntermissionFont.load();

		BuildableBitmapTextureAtlas mainAtlas = new BuildableBitmapTextureAtlas(getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		Textures.POWERUP_HEALTH = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainAtlas, this,
				"powerup_health.png");
		Textures.POWERUP_BIG_SHOT = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainAtlas, this,
				"powerup_big_shot.png");
		Textures.POWERUP_BOMB = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainAtlas, this,
				"powerup_bomb.png");
		Textures.PENALTY_ENEMY = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainAtlas, this, "penalty.png");
		Textures.ENEMY = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mainAtlas, this, "enemy.png");
		try
		{
			mainAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		mainAtlas.load();
	}

	@Override
	protected Scene onCreateScene()
	{
		Model.scene = new Scene();
		Model.scene.setOnSceneTouchListener(this);
		Model.scene.registerUpdateHandler(new UpdateHandler());

		Model.world = new PhysicsWorld(new Vector2(0, 0), false);
		Model.world.setContactListener(new CollisionHandler());
		Model.scene.registerUpdateHandler(Model.world);

		Model.background = new Background();
		Model.background.attachToScene();

		Model.hud = new HUD();
		Model.hudRect = new Rectangle(0, 0, Constants.CAMERA_WIDTH, Constants.TOP_BOT_HEIGHT,
				getVertexBufferObjectManager());
		Model.hudRect.setColor(Color.WHITE);
		Model.scoreText = new Text(20, 0, Model.hudFont, "0", 100, getVertexBufferObjectManager());
		Model.scoreText.setY((Constants.TOP_BOT_HEIGHT - Model.scoreText.getHeight()) / 2 + 2);
		Model.waveText = new Text(0, 0, Model.hudFont, "", 100, getVertexBufferObjectManager());
		Model.waveText.setPosition((Constants.CAMERA_WIDTH - Model.waveText.getWidth()) / 2, Model.scoreText.getY());
		Model.lifeBar = new Rectangle(5, 5, Constants.CAMERA_WIDTH - 10, Constants.TOP_BOT_HEIGHT - 10,
				getVertexBufferObjectManager());
		Model.lifeBar.setColor(ColorUtilities.brighten(Color.GREEN, .8f));
		Model.hud.attachChild(Model.hudRect);
		Model.hud.attachChild(Model.lifeBar);
		attachLifeBarBorder();
		Model.hud.attachChild(Model.scoreText);
		Model.hud.attachChild(Model.waveText);

		Model.player = new Player();
		Model.player.attachToScene();

		Model.wall = new Wall();
		Model.oobLeft = new SideWall(true);
		Model.oobRight = new SideWall(false);
		Model.hud.attachChild(Model.wall.shape);
		Model.oobLeft.attachToScene();
		Model.oobRight.attachToScene();

		Model.camera.setHUD(Model.hud);

		beginGame();

		return Model.scene;
	}

	private void attachLifeBarBorder()
	{
		float lifeBarX = Model.lifeBar.getX();
		float lifeBarY = Model.lifeBar.getY();
		float lifeBarWidth = Model.lifeBar.getWidth();
		float lifeBarHeight = Model.lifeBar.getHeight();

		Rectangle top = new Rectangle(lifeBarX, lifeBarY - 2, lifeBarWidth, 2, getVertexBufferObjectManager());
		top.setColor(Color.BLACK);
		Rectangle bottom = new Rectangle(lifeBarX, lifeBarY + lifeBarHeight, lifeBarWidth, 2,
				getVertexBufferObjectManager());
		bottom.setColor(Color.BLACK);
		Rectangle left = new Rectangle(lifeBarX - 2, lifeBarY, 2, lifeBarHeight, getVertexBufferObjectManager());
		left.setColor(Color.BLACK);
		Rectangle right = new Rectangle(lifeBarX + lifeBarWidth, lifeBarY, 2, lifeBarHeight,
				getVertexBufferObjectManager());
		right.setColor(Color.BLACK);

		Model.hud.attachChild(top);
		Model.hud.attachChild(bottom);
		Model.hud.attachChild(left);
		Model.hud.attachChild(right);
	}

	private void beginGame()
	{
		Model.state = GameState.WAVE_MACHINE_RUNNING;
		WaveMachine.beginNextWave();
	}

	private void restart()
	{
		Model.scene.clearUpdateHandlers();
		Model.world.clearPhysicsConnectors();

		Intent intent = new Intent(Main.this, Main.class);
		overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		finish();

		overridePendingTransition(0, 0);
		startActivity(intent);
	}

	private void showPausedDialog()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Paused");

		alert.setPositiveButton("Resume", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int button)
			{
				mEngine.start();
			}
		});

		alert.show();
	}

	private long updateBestScore(long score)
	{
		SharedPreferences prefs = getSharedPreferences("com.tender.saucer.untitledgame", Context.MODE_PRIVATE);
		long oldBestScore = prefs.getLong("bestScore", 0);

		SharedPreferences.Editor editor = prefs.edit();
		if (score > oldBestScore)
		{
			editor.putLong("bestScore", score);
		}

		editor.commit();

		return oldBestScore;
	}
}
