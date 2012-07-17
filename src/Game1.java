import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.TimerHandler;
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
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.math.*;




public class Game1 extends BaseGameActivity {
       
        final static int screenWidth = 800;  
        final static int screenHeight = 480;
        
        public static Scene mScene;
        
        public Camera mCamera;
               
        public static TouchEvent mouse;
        public static actionSlider actionSlider;
        public static Dog dog;
        public static AppDJ appDJ;
        
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
        
        
        Rectangle soundRec;
        BitmapTextureAtlas mBitmapTextureAtlas;
        
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
                        return engineOptions;
                }
               
                @Override
                public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)     throws Exception {
                		
                	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
                	VBOM = this.getVertexBufferObjectManager();
                		
                		mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
                		field = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "field.png", 0, 0);
                		pooBag = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "pooBag.png", 0, 0);
                		splash = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "phone buddy.png", 0, 0);
                		dogContainer  = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "dogSheet.png", 0, 0);
                        tugContainer  = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "tugSheet.png", 0, 0);
                        slider = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "slider.png", 0, 0);
                        sliderPull = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "sliderPull.png", 0, 0);
                        actFetch = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "actFetch.png", 0, 0);
                        actTug = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "actTug.png", 0, 0);
                        actFood = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "actFood.png", 0, 0);
                        actWater = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "actWater.png", 0, 0);
                        actPoo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "actPoo.png", 0, 0);
                        ropeTex = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "rope60.png", 0, 0);
                        ropeTex2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "rope.png", 0, 0);
                        water = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "actWater.png", 0, 0);
                        cloudImage1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "cloud1.png", 0, 0);
                        cloudImage2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "cloud2.png", 0, 0);
                        cloudImage3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "cloud3.png", 0, 0);
                        cloudImage4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "cloud4.png", 0, 0);
                        cloudImage5 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "cloud5.png", 0, 0);
                        pooImage1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "actPoo1.png", 0, 0);
                        pooImage2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "actPoo2.png", 0, 0);
                        shadow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "ballShadow.png", 0, 0);
                        soundOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "soundOn.png", 0, 0);
                        soundOff = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "soundOff.png", 0, 0);
                        
                        
                        
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
                		
                		mScene.attachChild(s_field);
                		mScene.attachChild(s_pooBag);
                		mScene.attachChild(s_splash);
                		mScene.attachChild(s_soundOn);
                		mScene.attachChild(s_soundOff);
                		
                		
                        actionSlider.LoadContent();
                        dog.LoadContent();
                        appDJ.LoadContent();
                		
                		
                		mBitmapTextureAtlas.load();
                		
                        
                	
                }
               
                @Override
                public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
                        
                		Scene mScene = new Scene();
                       
                        
                        actionSlider = new actionSlider();

                        appDJ = new AppDJ();

                        dog = new Dog();
                        
                        
                        soundRec = new Rectangle(screenWidth - 50, 0, 50, 50, VBOM);

                        pooList = new ArrayList<Poo>();
                        pooCounter = 0;
                        holdingPoo = false;

                        splashboolean = true;
                        
                        splashTime = new TimeSpan(5, false){
                        	@Override
                        	public void onTick()
                        	{
                        		splashboolean = false;
                        	}
                        	
                        };
                        
                        cloudList = new ArrayList<Cloud>();
                        rand = new Random();
                        newCloud = new TimeSpan((rand.nextInt(7)+5), true){
                        	@Override
                        	public void onTick()
                        	{
                        		if (direction == true)
                                {
                                    cloudList.add(new Cloud(rand.nextInt(7) + 1, new Vector2(-149, rand.nextInt((int)(screenHeight * .23)) - (int)(screenHeight * .15)), direction));

                                }
                                else
                                {
                                	cloudList.add(new Cloud(rand.nextInt(7) + 1, new Vector2(screenWidth, rand.nextInt((int)(screenHeight * .23)) - (int)(screenHeight * .15)), direction));
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
                        	cloudList.add(new Cloud(rand.nextInt(7)+1, new Vector2(rand.nextInt(screenWidth), rand.nextInt((int)((screenHeight * .25) - (screenHeight * .08)))), direction));
                    		
                        }
                        
                        		
                        mScene.registerUpdateHandler(new IUpdateHandler() {
                        	@Override
                        	public void onUpdate(float gameTime) {
                        	        
                        		
                        		
                        		
                        		
                        		//if (Keyboard.GetState().IsKeyDown(Keys.Escape) || (GamePad.GetState(Microsoft.Xna.Framework.PlayerIndex.One).Buttons.Back == ButtonState.Pressed)
                        			//	)
                        				//            {
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

                        				                    if (cloudList.get(i).cloudPos.x <= -150.0f || cloudList.get(i).cloudPos.x > screenWidth)
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
                        				                        dog.fetch.restart();

                        				                    }
                        				                    if (actionSlider.recTug.contains(mouse.getX(), mouse.getY()))
                        				                    {
                        				                        actionSlider.slideLeft = true;
                        				                        dog.returnHome = true;
                        				                        dog.myActivity = Dog.activity.dogTug;
                        				                        dog.tug.restart();

                        				                    }
                        				                    if (actionSlider.recFood.contains(mouse.getX(), mouse.getY()))
                        				                    {
                        				                        actionSlider.slideLeft = true;
                        				                        dog.returnHome = true;
                        				                        dog.myActivity = Dog.activity.dogFood;
                        				                        dog.food.restart();
                        				                    }
                        				                    if (actionSlider.recWater.contains(mouse.getX(), mouse.getY()))
                        				                    {
                        				                        actionSlider.slideLeft = true;
                        				                        dog.returnHome = true;
                        				                        dog.myActivity = Dog.activity.dogWater;
                        				                        dog.water.restart();
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
                                    Poo poo = new Poo(newRand.nextInt(4)+1);
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

							@Override
							public void reset() {
								// TODO Auto-generated method stub
								
							}
							
							
						});
                        
                        
                        mScene.setOnSceneTouchListener(new IOnSceneTouchListener(){
                        
                        @Override
            			public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
            				mouse = pSceneTouchEvent;
            				return true;
            				
            			}}) ;
                        
                        
                        //mScene.onDraw(pGLState, mCamera);
                        
                        
                }

				@Override
				public void onPopulateScene(Scene pScene,
						OnPopulateSceneCallback pOnPopulateSceneCallback)
						throws Exception {
					// TODO Auto-generated method stub
					
				}
                    
}