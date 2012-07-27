package org.phonebuddy;
import org.andengine.entity.primitive.*;
import org.andengine.entity.sprite.Sprite;

    class Water
    {
        

        public Sprite s_water;


        public float waterScale;
        public float waterRot;
        public int waterPosZ;
        public float xFactor;
        public float yFactor;

        public Rectangle waterRec;

        public Boolean atWater;

        public Vector2 drinkingPos;
        public Vector2 waterPos;

        public TimeSpan timeDrinking;



        public Water()
        {
        	timeDrinking = new TimeSpan(3.5f, true)
            {
            	@Override
            	public void onTick()
            	{
            		Game1.dog.statThirst += 0.3f;
                    restart();
                    Game1.appDJ.drinkOn = false;
                    timeDrinking.reset();
                    timeDrinking.pause();
            		
            		
            		
            	}            	
            	
            };
            
            Game1.timers.add(timeDrinking);
        	
            restart();
            
            s_water = new Sprite(waterPos.x, waterPos.y, Game1.actWater, Game1.VBOM);
            s_water.setVisible(false);
            s_water.setZIndex(waterPosZ);
            Game1.mScene.attachChild(s_water);
            
            

        }

        public void restart()
        {

            waterPos = new Vector2((int)((float)Game1.screenWidth * .14), (int)((float)Game1.screenHeight * .77));
            drinkingPos = new Vector2((int)((float)Game1.screenWidth * .28), (int)((float)Game1.screenHeight * .68));
            waterRec = new Rectangle(0, 0, Game1.actWater.getWidth(), Game1.actWater.getHeight(), Game1.VBOM);
            
            waterPosZ = -60;
            waterScale = 1.0f;
            waterRot = 0.0f;

            xFactor = 1.0f;
            yFactor = 1.0f;

            atWater = false;
            //timeDrinking = new TimeSpan(0, 0, 0, 3, 500);
            timeDrinking.setInterval(3.5f);
            timeDrinking.pause();
            
        }
        
        public void loadContent()
        {
        	
        }


        public void Update(float gameTime)
        {
            if (atWater == false)
            {
                if (Game1.dog.statThirst <= 0.9f)
                {

                    if (Game1.dog.dogPos.y != drinkingPos.y || Game1.dog.dogPos.x != drinkingPos.x)
                    {
                        Game1.dog.myAnimate = Dog.animate.dogRunLeft;
                        Game1.appDJ.runningOn = true;
                        
                        if (Game1.dog.dogPos.y >= drinkingPos.y - (Game1.dog.returnSpeedY / yFactor) && Game1.dog.dogPos.y <= drinkingPos.y + (Game1.dog.returnSpeedY / yFactor))
                        {
                            Game1.dog.dogPos.y = drinkingPos.y;
                            
                        }
                        else if (Game1.dog.dogPos.y >= drinkingPos.y)
                        {
                            Game1.dog.dogPos.y -= (Game1.dog.returnSpeedY / yFactor);

                        }
                        else if (Game1.dog.dogPos.y <= drinkingPos.y)
                        {
                            Game1.dog.dogPos.y += (Game1.dog.returnSpeedY / yFactor);

                        }

                        if (Game1.dog.dogPos.x >= drinkingPos.x - (Game1.dog.returnSpeedX / xFactor) && Game1.dog.dogPos.x <= drinkingPos.x + (Game1.dog.returnSpeedX / xFactor))
                        {
                            Game1.dog.dogPos.x = drinkingPos.x;
                            

                        }
                        else if (Game1.dog.dogPos.x >= drinkingPos.x)
                        {
                            Game1.dog.dogPos.x -= (Game1.dog.returnSpeedX / xFactor);
                            
                        }
                        else if (Game1.dog.dogPos.x <= drinkingPos.x)
                        {
                            Game1.dog.dogPos.x += (Game1.dog.returnSpeedX / xFactor);
                            
                        }




                    }
                    else
                    {
                        Game1.dog.myAnimate = Dog.animate.dogEatLeft;
                        Game1.appDJ.runningOn = false;
                        atWater = true;
                        Game1.appDJ.drinkOn = true;
                        timeDrinking.start();
                    }

                }
                else
                {

                    drank();

                }

            }

        }

        public void drank()
        {
            if (Game1.dog.dogPos.y != Game1.dog.origin.y || Game1.dog.dogPos.x != Game1.dog.origin.x || Game1.dog.dogScale < 1.0f)
            {
            	Game1.appDJ.runningOn = true;
                Game1.dog.myAnimate = Dog.animate.dogRunRight;
                if (Game1.dog.dogPos.y >= Game1.dog.origin.y - (Game1.dog.returnSpeedY / yFactor) && Game1.dog.dogPos.y <= Game1.dog.origin.y + (Game1.dog.returnSpeedY / yFactor))
                {
                    Game1.dog.dogPos.y = Game1.dog.origin.y;
                }
                else if (Game1.dog.dogPos.y <= Game1.dog.origin.y)
                {
                    Game1.dog.dogPos.y += (Game1.dog.returnSpeedY / yFactor);

                }
                else if (Game1.dog.dogPos.y >= Game1.dog.origin.y)
                {
                    Game1.dog.dogPos.y -= (Game1.dog.returnSpeedY / yFactor);

                }


                if (Game1.dog.dogPos.x >= Game1.dog.origin.x - (Game1.dog.returnSpeedX / xFactor) && Game1.dog.dogPos.x <= Game1.dog.origin.x + (Game1.dog.returnSpeedX / xFactor))
                {
                    Game1.dog.dogPos.x = Game1.dog.origin.x;
                    

                }
                else if (Game1.dog.dogPos.x >= Game1.dog.origin.x)
                {
                    Game1.dog.dogPos.x -= (Game1.dog.returnSpeedX / xFactor);
                }
                else if (Game1.dog.dogPos.x <= Game1.dog.origin.x)
                {
                    Game1.dog.dogPos.x += (Game1.dog.returnSpeedX / xFactor);
                    
                }



            }
            else
            {
                Game1.dog.myAnimate = Dog.animate.dogSitting;
                Game1.appDJ.runningOn = false; 
                restart();
            }

        }
    }

