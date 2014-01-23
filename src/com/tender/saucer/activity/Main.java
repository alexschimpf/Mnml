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
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.tender.saucer.background.Background;
import com.tender.saucer.handler.CollisionHandler;
import com.tender.saucer.handler.UpdateHandler;
import com.tender.saucer.shapebody.enemy.Enemy;
import com.tender.saucer.shapebody.player.Player;
import com.tender.saucer.shapebody.powerup.Powerup;
import com.tender.saucer.shapebody.shot.Shot;
import com.tender.saucer.shapebody.wall.OOBWall;
import com.tender.saucer.shapebody.wall.Wall;
import com.tender.saucer.stuff.ColorScheme;
import com.tender.saucer.stuff.Constants;
import com.tender.saucer.stuff.Model;
import com.tender.saucer.stuff.Textures;
import com.tender.saucer.wave.Wave;
import com.tender.saucer.wave.WaveIntermission;

public class Main extends SimpleBaseGameActivity implements IOnSceneTouchListener 
{
	public EngineOptions onCreateEngineOptions() 
	{
		Powerup.reset();
		Model.reset();
		Textures.reset();
		Wave.reset();
		WaveIntermission.reset();
		ColorScheme.reset();
		
		Model model = Model.instance();
		
		model.main = this;		
		model.camera = new ZoomCamera(0, 0, Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT);

		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), model.camera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		
		return engineOptions; 
	}

	@Override
	protected void onCreateResources() 
	{
		Model model = Model.instance();
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		BitmapTextureAtlas fontTexture1 = new BitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		model.hudFont = FontFactory.createStroke(getFontManager(), fontTexture1, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 
				Constants.HUD_FONT_SIZE, true, android.graphics.Color.BLACK, 0.0f, android.graphics.Color.BLACK);
		model.hudFont.load(); 
		
		BitmapTextureAtlas fontTexture2 = new BitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		model.waveIntermissionFont = FontFactory.createStroke(getFontManager(), fontTexture2, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 
				Constants.WAVE_INTERMISSION_FONT_SIZE, true, android.graphics.Color.WHITE, 0.0f, android.graphics.Color.WHITE);	
		model.waveIntermissionFont.load();
	}

	@Override
	protected Scene onCreateScene() 
	{
		Model model = Model.instance();
		
		model.scene = new Scene();		
		model.scene.setOnSceneTouchListener(this);
		model.scene.registerUpdateHandler(new UpdateHandler());
		
		model.world = new PhysicsWorld(new Vector2(0, 0), false);	
		model.world.setContactListener(new CollisionHandler());
		model.scene.registerUpdateHandler(model.world);

		model.background = new Background();
		model.background.attachToScene();
		
		model.hud = new HUD();	
		model.hudRect = new Rectangle(0, 0, Constants.CAMERA_WIDTH, Constants.TOP_BOT_HEIGHT, getVertexBufferObjectManager());
		model.hudRect.setColor(Color.WHITE);		
		model.scoreText = new Text(20, 0, model.hudFont, "0", 100, getVertexBufferObjectManager());
		model.scoreText.setY((Constants.TOP_BOT_HEIGHT - model.scoreText.getHeight()) / 2);
		model.livesText = new Text(0, 0, model.hudFont, "", 100, getVertexBufferObjectManager());
		model.livesText.setPosition(Constants.CAMERA_WIDTH - model.livesText.getWidth() - 20, model.scoreText.getY());
		model.livesText.setColor(Color.RED);
		model.waveText = new Text(0, 0, model.hudFont, "", 100, getVertexBufferObjectManager());
		model.waveText.setPosition((Constants.CAMERA_WIDTH - model.waveText.getWidth()) / 2, model.scoreText.getY());
		model.hud.attachChild(model.hudRect);
		model.hud.attachChild(model.scoreText);
		model.hud.attachChild(model.livesText);
		model.hud.attachChild(model.waveText);
		
		model.player = new Player();
		model.player.attachToScene();
		
		model.wall = new Wall();
		model.oobLeft = new OOBWall(true);
		model.oobRight = new OOBWall(false);
		model.hud.attachChild(model.wall.shape);
		model.oobLeft.attachToScene();
		model.oobRight.attachToScene();
		
		model.camera.setHUD(model.hud);	

		return model.scene;
	}

	public boolean onSceneTouchEvent(Scene scene, TouchEvent event) 
	{
		Model model = Model.instance();
		
		float x = event.getX();
		float y = event.getY();
		
		if(y > Constants.CAMERA_HEIGHT - Constants.TOP_BOT_HEIGHT - Constants.DEFAULT_PLAYER_HEIGHT)
		{
			model.player.move(x);
		}
		else
		{
			model.player.tryShoot();
		}
		
		return true;
	}
	
	public void restart()
	{	
		Model.instance().scene.clearUpdateHandlers();
		Model.instance().world.clearPhysicsConnectors();
		
		Intent intent = new Intent(Main.this, Main.class);
	    overridePendingTransition(0, 0);
	    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    finish();

	    overridePendingTransition(0, 0);
	    startActivity(intent);
	}
}
