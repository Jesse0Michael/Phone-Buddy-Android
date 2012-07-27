package org.phonebuddy;
import org.andengine.entity.primitive.*;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

    class Dog
    {
        
        

        public float statHunger;
        public float statHygiene;
        public float statThirst;
        public float statEntertainment;
        public float statHappiness;

        
        public TiledSprite dogContainer;
        public TiledSprite tugContainer;

        public Rectangle dogRec;

        public int dogX;
        public int dogY;
        public int returnSpeedX;
        public int returnSpeedY;

        public float dogScale;
        public int dogZ;
        public float dogRot;
        public float returnSpeedS;

        public boolean returnHome;

        public Vector2 origin;
        public Vector2 dogPos;


        public enum activity
        {
            dogFetch,       //0
            dogTug,         //1
            dogFood,        //2
            dogWater,       //3
            dogPoo,         //4
            dogPet,         //5
            dogIdle         //6

        };
        public enum animate
        {
            dogSitting,       //0
            dogRunAway,       //1
            dogRunTowards,    //2
            dogRunRight,      //3
            dogRunLeft,       //4
            dogEatRight,      //5
            dogEatLeft,       //6
            dogTug,           //7
            dogTugRightUp,    //8
            dogTugLeftUp,     //9
            dogTugRightDown,  //10
            dogTugLeftDown    //11
        };

        public activity myActivity;
        public animate myAnimate;
        public int myFPS;
        public int FPS;
        public int aniX;
        public int aniY;
        public boolean tugboolean;
        
        public TimeSpan vibrateDelay;

        public Dog()
        {

        	vibrateDelay = new TimeSpan(.1f, true)
            {
            	@Override
            	public void onTick()
            	{
            		Game1.myVibrate.vibrate(100);
            		vibrateDelay.reset();
            		vibrateDelay.setInterval(.1f);
            		vibrateDelay.pause();
            		
            	}
            	
            };
            
            Game1.timers.add(vibrateDelay);

            statThirst = 1.0f;
            statHygiene = 1.0f;
            statHunger = 1.0f;
            statEntertainment = 1.0f;
            statHappiness = 1.0f;
            myActivity = activity.dogIdle;
            myAnimate = animate.dogSitting;
            aniX = 0;
            aniY = 0;
            myFPS = 0;
            FPS = 12;
            returnHome = false;
            tugboolean = false;

            dogX = (int)((float)Game1.screenWidth * .51);
            dogY = (int)((float)Game1.screenHeight * .54);
            dogZ = -50;
            dogScale = 1.0f;
            dogRec = new Rectangle(0, 0, 200, 200, Game1.VBOM);
            dogRot = 0.0f;
            origin = new Vector2(dogX, dogY);
            dogPos = new Vector2(dogX, dogY);
            returnSpeedX = 2;
            returnSpeedY = 1;
            returnSpeedS = .005f;
            
            
            tugContainer =  new TiledSprite(0, 0, 200, 200, Game1.tugContainer, Game1.VBOM);
        	dogContainer = new TiledSprite(0, 0, 200, 200, Game1.dogContainer, Game1.VBOM);
        	
        	Game1.mScene.attachChild(tugContainer);
        	Game1.mScene.attachChild(dogContainer);

        }
            

        public void Update(float gameTime)
        {
        	
        	
            // This part of the dog update method controls the frames per second for the animation
            myFPS++;
            dogRec = new Rectangle(aniX, aniY, 200, 200, Game1.VBOM);
            if (myFPS >= FPS)
            {
                myFPS = 0;
                if (aniX < 3)
                {
                    aniX ++;
                }
                else
                {
                    aniX = 0;
                }
            }
            
            
            // This part of the dog update method controls the dogs stats (hunger, entertainment, hygiene, thirst, and happyness.
            statHappiness = (statEntertainment + statHunger + statHygiene + statThirst) / 4.0f;

            if (statThirst > 0.0f)
            {
                statThirst -= .0001f;
            }
            if (statHunger > 0.0f)
            {
                statHunger -= .0001f;
            }
            if (statEntertainment > 0.0f)
            {
                statEntertainment -= .0001f;
            }
            if (statHygiene > 0.0f)
            {
                statHygiene -= .00005f;
            }


            // This part of the update mentod controls what activity is currently running
            if (returnHome == false)
            {
                switch (myActivity)
                {
                    case dogFetch:
                        Game1.cfetch.Update(gameTime);
                        break;

                    case dogTug:
                    	Game1.ctug.Update(gameTime);
                        break;

                    case dogFood:
                    	Game1.cfood.Update(gameTime);
                        break;

                    case dogWater:
                    	Game1.cwater.Update(gameTime);
                        break;

                    case dogIdle:

                        break;


                }
            }

            
        
            if (tugboolean == true)
            {
                returnSpeedS = .1f;
            }
            else
            {
                returnSpeedS = .005f;
            }
            
            // This will have the dog return to the start position if called.
            if (returnHome == true)
            {
            	Game1.appDJ.drinkOn = false;
                Game1.appDJ.foodOn = false;
                if (dogPos.x != origin.x || dogPos.y != origin.y || dogScale != 1.0)
                {
                	Game1.appDJ.runningOn = true;
                    if (dogPos.x <= origin.x + returnSpeedX && dogPos.x >= origin.x - returnSpeedX)
                    {
                        dogPos.x = origin.x;
                        myAnimate = Dog.animate.dogSitting;
                    }
                    else if (dogPos.x >= origin.x)
                    {
                        dogPos.x -= returnSpeedX;
                        myAnimate = Dog.animate.dogRunLeft;
                    }
                    else if (dogPos.x <= origin.x)
                    {
                        dogPos.x += returnSpeedX;
                        myAnimate = Dog.animate.dogRunRight;
                    }

                    if (dogPos.y <= origin.y + returnSpeedY && dogPos.y >= origin.y - returnSpeedY)
                    {
                        dogPos.y = origin.y;

                    }
                    else if (dogPos.y >= origin.y)
                    {
                        dogPos.y -= returnSpeedY;
                        myAnimate = Dog.animate.dogRunAway;
                        
                    }
                    else if (dogPos.y <= origin.y)
                    {
                        dogPos.y += returnSpeedY;
                        myAnimate = Dog.animate.dogRunTowards;
                        
                    }

                    

                    if (dogScale <= 1.0f + returnSpeedS && dogScale >= 1.0f - returnSpeedS)
                    {
                        dogScale = 1.0f;
                        
                    }
                    else if (dogScale >= 1.0f)
                    {
                        dogScale -= returnSpeedS;
                        
                    }
                    else if (dogScale <= 1.0f)
                    {
                        dogScale += returnSpeedS;
                        
                    }

                }
                else
                {
                	Game1.appDJ.runningOn = false;
                    returnHome = false;
                    tugboolean = false;
                }
            }
            
            
            
            
            
            // This will draw the proper objects depending on what activity is running
        	if(myActivity == Dog.activity.dogIdle)
            {
        		myAnimate = Dog.animate.dogSitting;
            	
            }
        	
        	if(myActivity == Dog.activity.dogFetch)
            {
            	Game1.cfetch.s_ball.setVisible(true);
            	Game1.cfetch.s_shadow.setVisible(true);
            	
            	
            }
            else
            {
            	Game1.cfetch.s_ball.setVisible(false);
            	Game1.cfetch.s_shadow.setVisible(false);
            	
            	
            }
            
            if(myActivity == Dog.activity.dogTug)
            {
            	if (Game1.ctug.playPos == true && Game1.ctug.inPlay == false)
                {
                    Game1.ctug.s_ropetex.setVisible(true);
                }
                else
                {
                	
                	Game1.ctug.s_ropetex.setVisible(false);
                }
                
                if(Game1.ctug.inPlay == true)
                {
                    Game1.ctug.s_ropetex2.setVisible(true);
                }
                else
                {
                	Game1.ctug.s_ropetex2.setVisible(false);
                	Game1.ctug.ropePos = new Vector2((int)((float)Game1.screenWidth * .5), (int)((float)Game1.screenHeight * .6));
                	Game1.ctug.ropePos2 = new Vector2((int)((float)Game1.screenWidth * .5 ), (int)((float)Game1.screenHeight * .6));
                    Game1.ctug.s_ropetex.setPosition(Game1.ctug.ropePos.x - (Game1.ropeTex.getWidth() / 2), Game1.ctug.ropePos.y - (Game1.ropeTex.getHeight() / 2));
                    Game1.ctug.s_ropetex2.setPosition(Game1.ctug.ropePos2.x - (Game1.ropeTex2.getWidth() / 2), Game1.ctug.ropePos2.y - (Game1.ropeTex2.getHeight() / 2));
                    
                	
                }
            }
            else
            {
            	Game1.ctug.s_ropetex2.setVisible(false);
            	Game1.ctug.s_ropetex.setVisible(false);
            }
        	
            
            if(myActivity == Dog.activity.dogFood)
            {
            	Game1.cfood.s_food.setVisible(true);
            	
            	
            }
            else
            {
            	Game1.cfood.s_food.setVisible(false);
            	
            	
            }
            
            
            if(myActivity == Dog.activity.dogWater)
            {
            	Game1.cwater.s_water.setVisible(true);
            	
            	
            }
            else
            {
            	Game1.cwater.s_water.setVisible(false);
            	
            	
            }

            // This draws the dog depending on what animation should be shown on the sprite sheet currently
            switch (myAnimate)
            {
                case dogSitting:
                    aniY = 0;
                    dogContainer.setFlippedHorizontal(false);
                    dogContainer.setVisible(true);
                    tugContainer.setVisible(false);
                    dogContainer.setCurrentTileIndex(aniX + aniY);
                    //spriteBatch.Draw(dogContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogRunAway:
                    aniY = 4;
                    dogContainer.setFlippedHorizontal(false);
                    dogContainer.setVisible(true);
                    tugContainer.setVisible(false);
                    dogContainer.setCurrentTileIndex(aniX + aniY);
                    //spriteBatch.Draw(dogContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogRunTowards:
                    aniY = 8;
                    dogContainer.setFlippedHorizontal(false);
                    dogContainer.setVisible(true);
                    tugContainer.setVisible(false);
                    dogContainer.setCurrentTileIndex(aniX + aniY);
                    //spriteBatch.Draw(dogContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogRunRight:
                    aniY = 12;
                    dogContainer.setFlippedHorizontal(false);
                    dogContainer.setVisible(true);
                    tugContainer.setVisible(false);
                    dogContainer.setCurrentTileIndex(aniX + aniY);
                    //spriteBatch.Draw(dogContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogRunLeft:
                    aniY = 12;
                    dogContainer.setFlippedHorizontal(true);
                    dogContainer.setVisible(true);
                    tugContainer.setVisible(false);
                    dogContainer.setCurrentTileIndex(aniX + aniY);
                    //spriteBatch.Draw(dogContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.FlipHorizontally, dogZ);
                    break;

                case dogEatRight:
                    aniY = 16;
                    dogContainer.setFlippedHorizontal(false);
                    dogContainer.setVisible(true);
                    tugContainer.setVisible(false);
                    dogContainer.setCurrentTileIndex(aniX + aniY);
                    //spriteBatch.Draw(dogContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogEatLeft:
                    aniY = 20;
                    dogContainer.setFlippedHorizontal(false);
                    dogContainer.setVisible(true);
                    tugContainer.setVisible(false);
                    dogContainer.setCurrentTileIndex(aniX + aniY);
                    //spriteBatch.Draw(dogContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogTug:
                    aniY = 0;
                    dogContainer.setFlippedHorizontal(false);
                    dogContainer.setVisible(false);
                    tugContainer.setVisible(true);
                    tugContainer.setCurrentTileIndex(aniX + aniY);
                    //spriteBatch.Draw(tugContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogTugRightUp:
                    aniY = 4;
                    dogContainer.setFlippedHorizontal(false);
                    dogContainer.setVisible(false);
                    tugContainer.setVisible(true);
                    tugContainer.setCurrentTileIndex(aniX + aniY);
                    //spriteBatch.Draw(tugContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogTugLeftUp:
                    aniY = 8;
                    dogContainer.setFlippedHorizontal(false);
                    dogContainer.setVisible(false);
                    tugContainer.setVisible(true);
                    tugContainer.setCurrentTileIndex(aniX + aniY);
                    //spriteBatch.Draw(tugContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogTugRightDown:
                    aniY = 12;
                    dogContainer.setFlippedHorizontal(false);
                    dogContainer.setVisible(false);
                    tugContainer.setVisible(true);
                    tugContainer.setCurrentTileIndex(aniX + aniY);
                    //spriteBatch.Draw(tugContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogTugLeftDown:
                    aniY = 16;
                    dogContainer.setFlippedHorizontal(false);
                    dogContainer.setVisible(false);
                    tugContainer.setVisible(true);
                    tugContainer.setCurrentTileIndex(aniX + aniY);
                    //spriteBatch.Draw(tugContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;
            }
            
            dogContainer.setPosition(dogPos.x - 100, dogPos.y - 100);
            tugContainer.setPosition(dogPos.x - 100, dogPos.y - 100);
            dogContainer.setZIndex(dogZ);
            tugContainer.setZIndex(dogZ);
            dogContainer.setScale(dogScale);
            tugContainer.setScale(dogScale);
        }

        public void vibrate()
        {
        	if (Game1.appDJ.volOn)
            {
                //VibrateController myVibrate = VibrateController.Default;
                //myVibrate.Start(new TimeSpan(0, 0, 0, 0, 100));
        		vibrateDelay.start();

        		
            }
        }
    }
