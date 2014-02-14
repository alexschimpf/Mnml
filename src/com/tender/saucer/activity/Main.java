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
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;

import com.badlogic.gdx.math.Vector2;
import com.tender.saucer.background.Background;
import com.tender.saucer.collision.CollisionHandler;
import com.tender.saucer.color.ColorScheme;
import com.tender.saucer.shapebody.enemy.Enemy;
import com.tender.saucer.shapebody.player.Player;
import com.tender.saucer.shapebody.powerup.Powerup;
import com.tender.saucer.shapebody.shot.Shot;
import com.tender.saucer.shapebody.wall.SideWall;
import com.tender.saucer.shapebody.wall.Wall;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.GameState;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.stuff.Textures;
import com.tender.saucer.update.UpdateHandler;
import com.tender.saucer.wave.WaveMachine;
import com.tender.saucer.wave.WaveIntermission;

/**
 * 
 * Copyright 2014
 * @author Alex Schimpf
 *
 */

public class Main extends SimpleBaseGameActivity implements IOnSceneTouchListener 
{
	public EngineOptions onCreateEngineOptions() 
	{
		Model.init();
		ColorScheme.init();
		Textures.init();

		Model.main = this;		
		Model.camera = new ZoomCamera(0, 0, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);
		
		showLevelChoiceDialog();

		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), Model.camera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		
		return engineOptions; 
	}

	@Override
	protected void onCreateResources() 
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		BitmapTextureAtlas fontTexture1 = new BitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		Model.hudFont = FontFactory.createStroke(getFontManager(), fontTexture1, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 
				Constants.HUD_FONT_SIZE, true, android.graphics.Color.BLACK, 0.0f, android.graphics.Color.BLACK);
		Model.hudFont.load(); 
		
		BitmapTextureAtlas fontTexture2 = new BitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		Model.waveIntermissionFont = FontFactory.createStroke(getFontManager(), fontTexture2, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 
				Constants.WAVE_INTERMISSION_FONT_SIZE, true, android.graphics.Color.WHITE, 0.0f, android.graphics.Color.WHITE);	
		Model.waveIntermissionFont.load();
		
		BuildableBitmapTextureAtlas mainAtlas = new BuildableBitmapTextureAtlas(getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		Textures.POWERUP_HEALTH = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainAtlas, this, "powerup_health.png", 1, 1);
		try{ mainAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1)); }
		catch(Exception e){ e.printStackTrace(); }
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
		Model.hudRect = new Rectangle(0, 0, Constants.CAMERA_WIDTH, Constants.TOP_BOT_HEIGHT, getVertexBufferObjectManager());
		Model.hudRect.setColor(Color.WHITE);		
		Model.scoreText = new Text(20, 0, Model.hudFont, "0", 100, getVertexBufferObjectManager());
		Model.scoreText.setY((Constants.TOP_BOT_HEIGHT - Model.scoreText.getHeight()) / 2);
		Model.waveText = new Text(0, 0, Model.hudFont, "", 100, getVertexBufferObjectManager());
		Model.waveText.setPosition((Constants.CAMERA_WIDTH - Model.waveText.getWidth()) / 2, Model.scoreText.getY());
		Model.lifeBar = new Rectangle(Constants.CAMERA_WIDTH - 80, (Constants.TOP_BOT_HEIGHT - Model.scoreText.getHeight()) / 2,
				60, Model.waveText.getHeight(), getVertexBufferObjectManager());
		Model.lifeBar.setColor(Color.GREEN);
		Model.hud.attachChild(Model.hudRect);
		Model.hud.attachChild(Model.scoreText);
		Model.hud.attachChild(Model.waveText);
		Model.hud.attachChild(Model.lifeBar);
		
		Model.player = new Player();
		Model.player.attachToScene();
		
		Model.wall = new Wall();
		Model.oobLeft = new SideWall(true);
		Model.oobRight = new SideWall(false);
		Model.hud.attachChild(Model.wall.shape);
		Model.oobLeft.attachToScene();
		Model.oobRight.attachToScene();
		
		Model.camera.setHUD(Model.hud);	
		
		Model.waveMachine = new WaveMachine();
		Model.waveIntermission = new WaveIntermission();

		return Model.scene;
	}
	
	@Override
	public void onBackPressed()
	{
		if(mEngine.isRunning())
		{
			showPausedDialog();
			mEngine.stop();
		}
		else
		{
			mEngine.start();
		}
	}

	public boolean onSceneTouchEvent(Scene scene, TouchEvent event) 
	{
		float x = event.getX();
		float y = event.getY();
		
		if(y > Constants.CAMERA_HEIGHT - Constants.TOP_BOT_HEIGHT - Constants.DEFAULT_PLAYER_HEIGHT)
		{
			Model.player.move(x);
		}
		else
		{
			Model.player.tryShoot();
		}
		
		return true;
	}
	
	public void restart()
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
	
	public void showLevelChoiceDialog()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Choose starting level");
		alert.setMessage("Choose the starting level, asshole.");

		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int button) 
			{
				Editable level = input.getText();
				Model.waveMachine = new WaveMachine(Integer.parseInt(level.toString()));
				beginGame();
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
		{			
			public void onClick(DialogInterface dialog, int whichButton) 
		    {
				Model.waveMachine = new WaveMachine(1);
				beginGame();
		    }
		});

		alert.show();
	}
	
	public void showPausedDialog()
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
	
	private void beginGame()
	{
		ColorScheme.repaint();
		Model.state = GameState.WAVE_RUNNING;
	}
}
