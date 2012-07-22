package org.phonebuddy;
import org.andengine.entity.primitive.*;
import org.andengine.entity.sprite.Sprite;

    class Dog
    {
        
        

        public float statHunger;
        public float statHygiene;
        public float statThirst;
        public float statEntertainment;
        public float statHappiness;

        
        public Sprite dogContainer;
        public Sprite tugContainer;

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

        public Dog()
        {

            

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
            dogZ = 50;
            dogScale = 1.0f;
            dogRec = new Rectangle(0, 0, 200, 200, Game1.VBOM);
            dogRot = 0.0f;
            origin = new Vector2(dogX, dogY);
            dogPos = new Vector2(dogX, dogY);
            returnSpeedX = 2;
            returnSpeedY = 1;
            returnSpeedS = .005f;
            

        }

        public void LoadContent()
        {
        	
            tugContainer =  new Sprite(0, 0, Game1.tugContainer, Game1.VBOM);
        	dogContainer = new Sprite(0, 0, Game1.dogContainer, Game1.VBOM);
        	
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
                if (aniX < 600)
                {
                    aniX += 200;
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

            dogContainer.setPosition(dogX, dogY);
            tugContainer.setPosition(dogX, dogY);
            dogContainer.setZIndex(dogZ);
            tugContainer.setZIndex(dogZ);
            dogContainer.setScale(dogScale);
            tugContainer.setScale(dogScale);
        }

        public void Draw()
        {
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
            else
            {
                // This will draw the proper objects depending on what activity is running
                switch (myActivity)
                {
                    case dogFetch:
                    	Game1.cfetch.Draw();
                        break;

                    case dogTug:
                    	Game1.ctug.Draw();
                        break;

                    case dogFood:
                    	Game1.cfood.Draw();
                        break;

                    case dogWater:
                    	Game1.cwater.Draw();
                        break;

                    case dogIdle:
                        myAnimate = Dog.animate.dogSitting;
                        break;

                }
            }

            // This draws the dog depending on what animation should be shown on the sprite sheet currently
            switch (myAnimate)
            {
                case dogSitting:
                    aniY = 0;
                    //spriteBatch.Draw(dogContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogRunAway:
                    aniY = 200;
                    //spriteBatch.Draw(dogContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogRunTowards:
                    aniY = 400;
                    //spriteBatch.Draw(dogContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogRunRight:
                    aniY = 600;
                    //spriteBatch.Draw(dogContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogRunLeft:
                    aniY = 600;
                    //spriteBatch.Draw(dogContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.FlipHorizontally, dogZ);
                    break;

                case dogEatRight:
                    aniY = 800;
                    //spriteBatch.Draw(dogContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogEatLeft:
                    aniY = 1000;
                    //spriteBatch.Draw(dogContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogTug:
                    aniY = 0;
                    //spriteBatch.Draw(tugContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogTugRightUp:
                    aniY = 200;
                    //spriteBatch.Draw(tugContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogTugLeftUp:
                    aniY = 400;
                    //spriteBatch.Draw(tugContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogTugRightDown:
                    aniY = 600;
                    //spriteBatch.Draw(tugContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;

                case dogTugLeftDown:
                    aniY = 800;
                    //spriteBatch.Draw(tugContainer, dogPos, dogRec, Color.White, dogRot, new Vector2(100, 100), dogScale, SpriteEffects.None, dogZ);
                    break;
            }
        }

        public void vibrate()
        {
        	if (Game1.appDJ.volOn)
            {
                //VibrateController myVibrate = VibrateController.Default;
                //myVibrate.Start(new TimeSpan(0, 0, 0, 0, 100));
            }
        }
    }
