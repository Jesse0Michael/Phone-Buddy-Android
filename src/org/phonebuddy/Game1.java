package org.phonebuddy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.audio.sound.SoundManager;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.util.Log;




public class Game1 extends SimpleBaseGameActivity implements IOnSceneTouchListener, IUpdateHandler {
       
        final static int screenWidth = 800;  
        final static int screenHeight = 480;
        
        public static Scene mScene;
        
        public Camera mCamera;
               
        public static TouchEvent mouse;
        public static actionSlider actionSlider;
        public static Dog dog;
        public static AppDJ appDJ;
        
        public static Fetch cfetch;
        public static Water cwater;
        public static Food cfood;
        public static Tug ctug;
        
        public static ITextureRegion field;
        public static ITextureRegion pooBag;
        public static ITextureRegion splash;
        public static ITextureRegion tugContainer;
        public static ITextureRegion dogContainer;
        public static ITextureRegion slider;
        public static ITextureRegion sliderPull;
        public static ITextureRegion actFetch;
        public static ITextureRegion actTug;
        public static ITextureRegion actFood;
        public static ITextureRegion actWater;
        public static ITextureRegion actPoo;
        public static ITextureRegion ropeTex;
        public static ITextureRegion ropeTex2;
        public static ITextureRegion water;
        public static ITextureRegion cloudImage1;
        public static ITextureRegion cloudImage2;
        public static ITextureRegion cloudImage3;
        public static ITextureRegion cloudImage4;
        public static ITextureRegion cloudImage5;
        public static ITextureRegion pooImage1;
        public static ITextureRegion pooImage2;
        public static ITextureRegion food;
        public static ITextureRegion shadow;
        public static ITextureRegion soundOn;
        public static ITextureRegion soundOff;
        
        public static Sound ibark1;
        public static Sound ibark2;
        public static Sound ibark3;
        public static Sound ibark4;
        public static Sound ibark5;
        public static Sound ibird1;
        public static Sound ibird2;
        public static Sound ibird3;
        public static Sound ibird4;
        public static Sound ibird5;
        public static Sound idrink;
        public static Sound ifood;
        public static Music igrowl1;
        public static Music igrowl2;
        public static Music igrowl3;
        public static Music igrowl4;
        public static Sound ithud;
        public static Sound ibag;
        
        public static SoundManager soundManager;
        public static MusicManager musicManager;
        
        private Rectangle soundRec;
        private BitmapTextureAtlas mBitmapTextureAtlasActivites;
        private BitmapTextureAtlas mBitmapTextureAtlasUI;
        private BitmapTextureAtlas mBitmapTextureAtlasDogSheet;
        private BitmapTextureAtlas mBitmapTextureAtlasTugSheet;
        private BitmapTextureAtlas mBitmapTextureAtlasWalls;
        
        public static VertexBufferObjectManager VBOM;
        
        private Sprite s_field;
        private Sprite s_pooBag;
        private Sprite s_splash;
        private Sprite s_soundOn;
        private Sprite s_soundOff;
        
        boolean splashboolean;
        private TimeSpan splashTime;

        private int pooCounter;
        private List<Poo> pooList;
        private boolean holdingPoo;

        private List<Cloud> cloudList;
        private boolean direction;
        private Random rand;
        private int initialCloud;
        private TimeSpan newCloud;
        
        
               
                @Override
                public EngineOptions onCreateEngineOptions() {
                        this.mCamera = new Camera(0, 0, screenWidth, screenHeight);
                        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(screenWidth, screenHeight), mCamera);
                        engineOptions.getAudioOptions().setNeedsMusic(true);
                        engineOptions.getAudioOptions().setNeedsSound(true);
                        return engineOptions;
                }
               
                @Override
                public void onCreateResources() {
                	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
                	VBOM = this.getVertexBufferObjectManager();
                	soundManager = this.getSoundManager();
                	musicManager = this.getMusicManager();
                	
                	SoundFactory.setAssetBasePath("sfx/");
                	MusicFactory.setAssetBasePath("sfx/");
                		mBitmapTextureAtlasActivites= new BitmapTextureAtlas(this.getTextureManager(), 2052, 2052, TextureOptions.BILINEAR);
                		mBitmapTextureAtlasDogSheet= new BitmapTextureAtlas(this.getTextureManager(), 2052, 2052, TextureOptions.BILINEAR);
                		mBitmapTextureAtlasTugSheet= new BitmapTextureAtlas(this.getTextureManager(), 2052, 2052, TextureOptions.BILINEAR);
                		mBitmapTextureAtlasWalls= new BitmapTextureAtlas(this.getTextureManager(), 2052, 2052, TextureOptions.BILINEAR);
                		mBitmapTextureAtlasUI= new BitmapTextureAtlas(this.getTextureManager(), 2052, 2052, TextureOptions.BILINEAR);
                		
                		
                		pooBag = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasActivites, this, "pooBag.png", 0, 0); 			//100 x 102
                        slider = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasActivites, this, "slider.png", 0, 0); 			//179 x 480 
                        sliderPull = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasActivites, this, "sliderPull.png", 0, 0); 	//42  x 123
                        actFetch = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasActivites, this, "actFetch.png", 0, 0); 		//82  x 84
                        actTug = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasActivites, this, "actTug.png", 0, 0); 			//86  x 67
                        actFood = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasActivites, this, "actFood.png", 0, 0); 		//86  x 54
                        actWater = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasActivites, this, "actWater.png", 0, 0); 		//86  x 50
                        actPoo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasActivites, this, "actPoo.png", 0, 0); 			//53  x 88
                        ropeTex = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasActivites, this, "rope60.png", 0, 0); 			//360 x 312
                        ropeTex2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasActivites, this, "rope.png", 0, 0); 			//360 x 312
                        shadow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasActivites, this, "ballShadow.png", 0, 0); 		//82  x 84
                        cloudImage1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasUI, this, "cloud1.png", 0, 0); 			//139 x 78
                        cloudImage2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasUI, this, "cloud2.png", 0, 0); 			//123 x 78
                        cloudImage3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasUI, this, "cloud3.png", 0, 0); 			//135 x 86
                        cloudImage4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasUI, this, "cloud4.png", 0, 0); 			//138 x 74
                        cloudImage5 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasUI, this, "cloud5.png", 0, 0); 			//152 x 100
                        pooImage1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasUI, this, "actPoo1.png", 0, 0); 				//50  x 51
                        pooImage2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasUI, this, "actPoo2.png", 0, 0); 				//50  x 51
                        soundOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasUI, this, "soundOn.png", 0, 0); 				//50  x 50
                        soundOff = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasUI, this, "soundOff.png", 0, 0); 				//50  x 50
                		splash = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasWalls, this, "phone buddy.png", 0, 0); 			//800 x 480
                		field = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasWalls, this, "field.png",0, 0); 					//800 x 480
                		dogContainer  = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasDogSheet, this, "dogSheet.png", 0, 0); 	//800 x 1200
                        tugContainer  = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasTugSheet, this, "tugSheet.png", 0, 0);	//800 x 1000
                        
                        
                        
                        
                        s_field = new Sprite(0, 0, field, VBOM);
                        s_pooBag = new Sprite(screenWidth * .8f, screenHeight * .75f, pooBag, VBOM );
                		s_splash = new Sprite(0, 0, splash, VBOM);
                		s_soundOn = new Sprite(screenWidth - 50, 0, soundOn, VBOM);
                		s_soundOff = new Sprite(screenWidth - 50, 0, soundOff, VBOM);
                		
                		s_field.setVisible(false);
                		s_pooBag.setVisible(false);
                		s_splash.setVisible(false);
                		s_soundOn.setVisible(false);
                		s_soundOff.setVisible(false);
                		
                		s_field.setZIndex(100);
                		s_pooBag.setZIndex(90);
                		s_splash.setZIndex(100);
                		s_soundOn.setZIndex(67);
                		s_soundOff.setZIndex(67);
                		
                		
                		
                		
                		
                		try {
                			
							ibark1 = SoundFactory.createSoundFromAsset(soundManager, this, "bark1.wav");
							ibark2 = SoundFactory.createSoundFromAsset(soundManager, this, "bark2.wav");
	                        ibark3 = SoundFactory.createSoundFromAsset(soundManager, this, "bark3.wav");
	                        ibark4 = SoundFactory.createSoundFromAsset(soundManager, this, "bark4.wav");
	                        ibark5 = SoundFactory.createSoundFromAsset(soundManager, this, "bark5.wav");
	                        ibird1 = SoundFactory.createSoundFromAsset(soundManager, this, "bird1.wav");
	                        ibird2 = SoundFactory.createSoundFromAsset(soundManager, this, "bird2.wav");
	                        ibird3 = SoundFactory.createSoundFromAsset(soundManager, this, "bird3.wav");
	                        ibird4 = SoundFactory.createSoundFromAsset(soundManager, this, "bird4.wav");
	                        ibird5 = SoundFactory.createSoundFromAsset(soundManager, this, "bird5.wav");
	                        idrink = SoundFactory.createSoundFromAsset(soundManager, this, "drink.wav");
	                        ifood = SoundFactory.createSoundFromAsset(soundManager, this, "food.wav");
	                        igrowl1 = MusicFactory.createMusicFromAsset(musicManager, this, "growl1.wav");
	                        igrowl2 = MusicFactory.createMusicFromAsset(musicManager, this, "growl2.wav");
	                        igrowl3 = MusicFactory.createMusicFromAsset(musicManager, this, "growl3.wav");
	                        igrowl4 = MusicFactory.createMusicFromAsset(musicManager, this, "growl4.wav");
	                        ithud = SoundFactory.createSoundFromAsset(soundManager, this, "thud.wav");
	                        ibag = SoundFactory.createSoundFromAsset(soundManager, this, "bag.wav");
	                		
                		
                		
                		} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                		
                		
                        
                		
                		
                		mBitmapTextureAtlasActivites.load();
                		mBitmapTextureAtlasDogSheet.load();
                		mBitmapTextureAtlasTugSheet.load();
                		mBitmapTextureAtlasWalls.load();
                		mBitmapTextureAtlasUI.load();
                		

                	
                }
                
                
               
                @Override
                public Scene onCreateScene() {
                        
                		mScene = new Scene();
                       
                		actionSlider = new actionSlider();

                        appDJ = new AppDJ();

                        dog = new Dog();
                        
                        cfetch = new Fetch();
                        cwater = new Water();
                        cfood = new Food();
                        ctug = new Tug();
                        
                        mouse = new TouchEvent();
                        
                       
                        
                        
                        mScene.attachChild(s_field);
                		mScene.attachChild(s_pooBag);
                		mScene.attachChild(s_splash);
                		mScene.attachChild(s_soundOn);
                		mScene.attachChild(s_soundOff);
                		
                		actionSlider.LoadContent();
                        dog.LoadContent();
                        appDJ.LoadContent();
                        
                        cfetch.LoadContent();
                        cwater.LoadContent();
                        cfood.LoadContent();
                        ctug.LoadContent();
                        
                        
                        soundRec = new Rectangle(screenWidth - 50, 0, 50, 50, VBOM);

                        pooList = new ArrayList<Poo>();
                        pooCounter = 0;
                        holdingPoo = false;
                        splashboolean = false;
                        
                       
                        
                        splashTime = new TimeSpan(5, false)
                        {
                        	@Override
                        	public void onTick()
                        	{
                        		splashboolean = false;
                        		
                        	}
                        };
                        
                        
                        cloudList = new ArrayList<Cloud>();
                        rand = new Random();
                        
                        newCloud = new TimeSpan((rand.nextInt(7)+5), true)
                        {
                        	@Override
                        	public void onTick()
                        	{
                        		if (direction == true)
                                {
                                    cloudList.add(new Cloud(rand.nextInt(5) + 1, new Vector2(-149, rand.nextInt((int)(screenHeight * .23)) - (int)(screenHeight * .15)), direction));

                                }
                                else
                                {
                                	cloudList.add(new Cloud(rand.nextInt(5) + 1, new Vector2(screenWidth, rand.nextInt((int)(screenHeight * .23)) - (int)(screenHeight * .15)), direction));
                                }

                                appDJ.playBird();
                                
                                newCloud.setInterval(rand.nextInt(65)+25);
                        		newCloud.reset();
                        		
                        	}
                        };
                        
                        newCloud.start();

                        if (rand.nextInt(2) == 0)
                        {
                            direction = false;
                        }
                        else
                        {
                            direction = true;
                        }


                        initialCloud = rand.nextInt(3) + 5;
                        for (int i = 0; i < initialCloud; i++)
                        {
                        	cloudList.add(new Cloud(rand.nextInt(5)+1, new Vector2(rand.nextInt(screenWidth), rand.nextInt((int)((screenHeight * .23) - (screenHeight * .15)))), direction));
                    		
                        }
                        
                        mScene.setOnSceneTouchListener(this);
                        mScene.registerUpdateHandler(this);
                        
                        
                        return mScene;
                        
                }
                
                	
                @Override
                public void onUpdate(float gameTime) 
                {
                	
	                //if (Keyboard.GetState().IsKeyDown(Keys.Escape) || (GamePad.GetState(Microsoft.Xna.Framework.PlayerIndex.One).Buttons.Back == ButtonState.Pressed))
	                //        {
	                //              this.Exit();
	                //        }
                	
	                if (splashboolean == true)
	                {
	                	splashTime.start();
	                				                
	                }
	                else
	                {
	                	actionSlider.Update(gameTime);
	                	dog.Update(gameTime);
	                	pooControl(gameTime);
	                	appDJ.Update(gameTime);
	
	                				                
	                	for (int i = 0; i < cloudList.size(); i++)
	                	{
	                		cloudList.get(i).Update(gameTime);
	
	                		if (cloudList.get(i).s_cloud.getX() <= -150.0f || cloudList.get(i).s_cloud.getX() > screenWidth)
	                		{
	                			cloudList.remove(cloudList.get(i));
	
	                		}
	                	}
	
	                	if (dog.myActivity == Dog.activity.dogPoo)
	                	{
	                		for (int i = 0; i < pooList.size(); i++)
	                		{
	                			if (pooList.get(i).grabbedPoo == true)
	                			{
	                				 holdingPoo = true;
	                				 pooList.get(i).Update(gameTime);
	                			}
	                		}
	
	                		if (holdingPoo == false)
	                		{
	                			for (Poo poo : pooList)
	                			{
	                				poo.Update(gameTime);
	                			}
	                		}
	                		else if (holdingPoo == true)
	                		{
	
	                			if (mouse.isActionUp() == true)
	                			{
	                				  holdingPoo = false;
	                			}
	                		}
	
	                	}
	
	                	if (mouse.isActionDown() == true)
	                	{
	                		if (actionSlider.recFetch.contains(mouse.getX(), mouse.getY()))
	                		{
	                			actionSlider.slideLeft = true;
	                			dog.returnHome = true;
	                			dog.myActivity = Dog.activity.dogFetch;
	                			cfetch.restart();
	
	                		}
	                		if (actionSlider.recTug.contains(mouse.getX(), mouse.getY()))
	                		{
	                			actionSlider.slideLeft = true;
	                			dog.returnHome = true;
	                			dog.myActivity = Dog.activity.dogTug;
	                			ctug.restart();
	
	                		}
	                		if (actionSlider.recFood.contains(mouse.getX(), mouse.getY()))
	                		{
	                			actionSlider.slideLeft = true;
	                			dog.returnHome = true;
	                			dog.myActivity = Dog.activity.dogFood;
	                			cfood.restart();
	                			
	                		}
	                		if (actionSlider.recWater.contains(mouse.getX(), mouse.getY()))
	                		{
	                			actionSlider.slideLeft = true;
	                			dog.returnHome = true;
	                			dog.myActivity = Dog.activity.dogWater;
	                			cwater.restart();
	                		}
	                		if (actionSlider.recPoo.contains(mouse.getX(), mouse.getY()))
	                		{
	                			actionSlider.slideLeft = true;
	                			dog.returnHome = true;
	                			dog.myActivity = Dog.activity.dogPoo;
	                		}
	                				                    
	                		if (soundRec.contains(mouse.getX(), mouse.getY()))
	                		{
	                			if (appDJ.volOn == true)
	                			{
	                				appDJ.volOn = false;
	                				appDJ.stopEverything();
	                			}
	                			else
	                			{
	                				appDJ.volOn = true;
	                			}
	
	                		}
	
	                	}
	                }
	                				            
	                		
	                // Draw Section
	                if (splashboolean == true)
	                {
	                	//spriteBatch.Draw(splash, new Rectangle(0, 0, screenWidth, screenHeight), new Rectangle(0, 0, screenWidth, screenHeight), Color.White, 0.0f, new Vector2(0, 0), SpriteEffects.None, 1.0f);
	                	s_splash.setVisible(true);
	                }
	                else
	                {
	                	s_splash.setVisible(false);
	                	                        	
	                	actionSlider.Draw();
	                	dog.Draw();
	                	                            
	               	    for(Poo poo : pooList)
	               	    {
	               	       poo.Draw();
	               	    }
	               	                                
	               	    for(Cloud cloud : cloudList)
	               	    {
	               	        cloud.Draw();
	
	               	    }
	
	
	               	    if (dog.myActivity == Dog.activity.dogPoo)
	               	    {
	               	    	s_pooBag.setVisible(true);
	               	       	//spriteBatch.Draw(pooBag, new Vector2((int)((float)screenWidth * .80), (int)((float)screenHeight * .75)), new Rectangle(0, 0, pooBag.Width, pooBag.Height), Color.White, 0.0f, new Vector2(0, 0), 1.0f, SpriteEffects.None, 0.9f);
	
	               	    }
	               	    else
	               	    {
	               	        s_pooBag.setVisible(false);
	               	    }
	
	               	    s_field.setVisible(true);
	               	    //spriteBatch.Draw(field, new Rectangle(0, 0, screenWidth, screenHeight), new Rectangle(0, 0, field.Width, field.Height), Color.White, 0.0f, new Vector2(0, 0), SpriteEffects.None, 1.0f);
	               	                            
	               	                                
	               	    if (appDJ.volOn == true)
	               	    {
	               	        //spriteBatch.Draw(soundOn, soundRec, new Rectangle(0, 0, soundOn.Width, soundOn.Height), Color.White, 0.0f, new Vector2(0, 0), SpriteEffects.None, 0.66f);
	               	        s_soundOn.setVisible(true);
	               	        s_soundOff.setVisible(false);
	               	    }
	               	    else
	               	    {
	               	    	//spriteBatch.Draw(soundOff, soundRec, new Rectangle(0, 0, soundOff.Width, soundOff.Height), Color.White, 0.0f, new Vector2(0, 0), SpriteEffects.None, 0.66f);
	               	        s_soundOn.setVisible(false);
	              	        s_soundOff.setVisible(true);
	               	    }
	                }
                }
                	
                	
                @Override
                public void reset() {
                	// TODO Auto-generated method stub
						
                }
					
                
                @Override
    			public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) 
                {
    				mouse = pSceneTouchEvent;
    				return true;
    				
    			}
                
                
                public void pooControl(float gameTime)
                {
                    if (dog.statHygiene >= 0.8f)
                    {
                        pooCounter = 0;
                    }
                    else if (dog.statHygiene >= 0.6f && dog.statHygiene < 0.8f)
                    {
                        pooCounter = 1;
                    }
                    else if (dog.statHygiene >= 0.4f && dog.statHygiene < 0.6f)
                    {
                        pooCounter = 2;
                    }
                    else if (dog.statHygiene >= 0.2f && dog.statHygiene < 0.4f)
                    {
                        pooCounter = 3;
                    }
                    else if (dog.statHygiene < 0.2f)
                    {
                        pooCounter = 4;
                    }


                    if (pooCounter > pooList.size())
                    {
                        Random newRand = new Random();
                        Poo poo = new Poo(newRand.nextInt(2)+1);
                        pooList.add(poo);

                    }


                    for (int i = 0; i < pooList.size(); i++)
                    {
                        if (pooList.get(i).pooPos.x < 0)
                        {
                            pooList.remove(pooList.get(i));

                        }
                    }


                }

				
                    
}