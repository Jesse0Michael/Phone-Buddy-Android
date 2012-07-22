package org.phonebuddy;
import org.andengine.entity.primitive.*;
import org.andengine.entity.sprite.Sprite;

    class Food
    {
        

        public Sprite s_food;


        public float foodScale;
        public float foodRot;
        public int foodPosZ;
        public float xFactor;
        public float yFactor;

        public Rectangle foodRec;

        public Vector2 foodPos;
        public Vector2 eatingPos;

        public boolean atFood;

        public TimeSpan timeEating;

        public int screenWidth;
        public int screenHeight;

        public Food()
        {
            
            restart();

        }

        public void restart()
        {
            foodPos = new Vector2((int)((float)screenWidth * .81), (int)((float)screenHeight * .77));
            eatingPos = new Vector2((int)((float)screenWidth * .78), (int)((float)screenHeight * .69));
            foodPosZ = 60;
            foodScale = 1.0f;
            foodRot = 0.0f;

            xFactor = 1.0f;
            yFactor = 1.0f;

            atFood = false;
            //timeEating = new TimeSpan(0, 0, 0, 5, 500);
            timeEating = new TimeSpan(5, true)
            {
            	@Override
            	public void onTick()
            	{
            		Game1.dog.statHunger += 0.3f;
                    restart();
                    Game1.appDJ.foodOn = false;
                    timeEating.reset();
                    timeEating.pause();
            		
            	}
            	
            	
            };
            
            s_food = new Sprite(foodPos.x, foodPos.y, Game1.actFood, Game1.VBOM);
            s_food.setVisible(false);
            s_food.setZIndex(foodPosZ);
            Game1.mScene.attachChild(s_food);

        }

        public void LoadContent()
        {
            //food = Content.Load<ITextureRegion>("Textures/actfood");

            foodRec = new Rectangle(0, 0, Game1.actFood.getWidth(), Game1.actFood.getHeight(), Game1.VBOM);
        }


        public void Update(float gameTime)
        {
            if (atFood == false)
            {
                if (Game1.dog.statHunger <= 0.9f)
                {

                    if (Game1.dog.dogPos.y != eatingPos.y || Game1.dog.dogPos.x != eatingPos.x)
                    {
                        Game1.dog.myAnimate = Dog.animate.dogRunRight;
                        Game1.appDJ.runningOn = true;
                        if (Game1.dog.dogPos.y >= eatingPos.y - (Game1.dog.returnSpeedY / yFactor) && Game1.dog.dogPos.y <= eatingPos.y + (Game1.dog.returnSpeedY / yFactor))
                        {
                            Game1.dog.dogPos.y = eatingPos.y;
                        }
                        else if (Game1.dog.dogPos.y >= eatingPos.y)
                        {
                            Game1.dog.dogPos.y -= (Game1.dog.returnSpeedY / yFactor);

                        }
                        else if (Game1.dog.dogPos.y <= eatingPos.y)
                        {
                            Game1.dog.dogPos.y += (Game1.dog.returnSpeedY / yFactor);

                        }

                        if (Game1.dog.dogPos.x >= eatingPos.x - (Game1.dog.returnSpeedX / xFactor) && Game1.dog.dogPos.x <= eatingPos.x + (Game1.dog.returnSpeedX / xFactor))
                        {
                            Game1.dog.dogPos.x = eatingPos.x;

                        }
                        else if (Game1.dog.dogPos.x >= eatingPos.x)
                        {
                            Game1.dog.dogPos.x -= (Game1.dog.returnSpeedX / xFactor);
                        }
                        else if (Game1.dog.dogPos.x <= eatingPos.x)
                        {
                            Game1.dog.dogPos.x += (Game1.dog.returnSpeedX / xFactor);
                            
                        }




                    }
                    else
                    {
                    	Game1.appDJ.runningOn = false;
                        Game1.dog.myAnimate = Dog.animate.dogEatRight;
                        atFood = true;
                        timeEating.start();
                        Game1.appDJ.foodOn = true;
                        
                    }


                }
                else
                {
                    ate();
                }
            }

        }

        public void ate()
        {
            if (Game1.dog.dogPos.y != Game1.dog.origin.y || Game1.dog.dogPos.x != Game1.dog.origin.x || Game1.dog.dogScale < 1.0f)
            {
                Game1.dog.myAnimate = Dog.animate.dogRunLeft;
                Game1.appDJ.runningOn = true;

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

        public void Draw()
        {
        	
           // spriteBatch.Draw(food, foodPos, foodRec, Color.White, foodRot, new Vector2(0, 0), foodScale, SpriteEffects.None, foodPosZ);

        }
    }
