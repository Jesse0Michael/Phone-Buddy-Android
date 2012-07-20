package org.phonebuddy;

import org.andengine.entity.primitive.*;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;


    class Tug
    {
    	private Sprite s_ropetex;
    	private Sprite s_ropetex2;
    	
        private Rectangle ropeRec;
        private Vector2 ropePos;
        private int ropeZ;

        private Rectangle ropeRec2;
        private Vector2 ropePos2;
        private int rope2Z;

        private Rectangle startRec;

        private Vector2 playPlace;
        private float playScale;
        private float speedScale;
        private float speedY;


        private boolean playPos;
        private boolean inPlay;


        public TouchEvent oldMouse;


        public Tug()
        {

            restart();

        }

        public void restart()
        {
            Game1.dog.tugboolean = true;
            Game1.dog.myAnimate = Dog.animate.dogSitting;
            

            playPos = false;
            inPlay = false;

            oldMouse = Game1.mouse;

            
            playScale = 3.0f;
            playPlace = new Vector2((int)((float)Game1.screenWidth * .51), (int)((float)Game1.screenHeight * .62));

            ropePos = new Vector2((int)((float)Game1.screenWidth * .5), (int)((float)Game1.screenHeight * .6));
            ropePos2 = new Vector2((int)((float)Game1.screenWidth * .5), (int)((float)Game1.screenHeight * .6));
            rope2Z = 40;
            ropeZ = 41;

            speedY = 1.0f;
            speedScale = .01f;


            s_ropetex = new Sprite(ropePos.x, ropePos.y, Game1.ropeTex, Game1.VBOM);
            s_ropetex2 = new Sprite(ropePos2.x, ropePos2.y, Game1.ropeTex2, Game1.VBOM);
            
            s_ropetex.setVisible(false);
            s_ropetex2.setVisible(false);
            
            
            Game1.mScene.attachChild(s_ropetex);
            Game1.mScene.attachChild(s_ropetex2);            

        }

        public void LoadContent()
        {
            

            ropeRec = new Rectangle(0, 0,
                                    (int)((float)Game1.screenWidth * .45), (int)((float)Game1.screenHeight * .65), Game1.VBOM);
            ropeRec2 = new Rectangle(0, 0,
                                    (int)((float)Game1.screenWidth * .45), (int)((float)Game1.screenHeight * .65), Game1.VBOM);

            startRec = new Rectangle((int)ropePos.x - ( ropeRec.getWidth() / 2), (int)ropePos.y - (ropeRec.getHeight() / 2),
                                    (int)((float)Game1.screenWidth * .45), (int)((float)Game1.screenHeight * .65), Game1.VBOM);



        }


        public void Update(float gameTime)
        {
            

            if (playPos == false)
            {
                

                if (Game1.dog.dogPos.y != playPlace.y || Game1.dog.dogScale != playScale)
                {
                	Game1.appDJ.runningOn = true;
                    Game1.dog.myAnimate = Dog.animate.dogRunTowards;
                    if (Game1.dog.dogPos.y <= playPlace.y + speedY && Game1.dog.dogPos.y >= playPlace.y - speedY)
                    {
                        Game1.dog.dogPos.y = playPlace.y;
                    }
                    else if (Game1.dog.dogPos.y >= playPlace.y)
                    {
                        Game1.dog.dogPos.y -= speedY;
                    }
                    else if (Game1.dog.dogPos.y <= playPlace.y)
                    {
                        Game1.dog.dogPos.y += speedY;
                    }

                    if (Game1.dog.dogScale <= playScale + speedScale && Game1.dog.dogScale >= playScale - speedScale)
                    {
                        Game1.dog.dogScale = playScale;
                    }
                    else if (Game1.dog.dogScale >= playScale)
                    {
                        Game1.dog.dogScale -= speedScale;
                    }
                    else if (Game1.dog.dogScale <= playScale)
                    {
                        Game1.dog.dogScale += speedScale;
                    }

                }
                else
                {
                	Game1.appDJ.runningOn = false;
                    playPos = true;
                }



            }
            else
            {
               

                if (inPlay == true)
                {

                    if (Game1.mouse.getX() <= ((float)Game1.screenWidth * .33))
                    {
                        if (Game1.mouse.getY() <= ((float)Game1.screenHeight * .45))
                        {

                            Game1.dog.myAnimate = Dog.animate.dogTugLeftUp;
                            if (oldMouse.getY() > ((float)Game1.screenHeight * .45))
                            {
                                Game1.dog.vibrate();
                                Game1.appDJ.playGrowl();
                            }
                        }
                        else
                        {
                            Game1.dog.myAnimate = Dog.animate.dogTugLeftDown;

                            if (oldMouse.getY() <= ((float)Game1.screenHeight * .45))
                            {
                                Game1.dog.vibrate();
                                Game1.appDJ.playGrowl();
                            }
                        }

                        if (oldMouse.getX() > ((float)Game1.screenWidth * .33))
                        {
                            Game1.dog.vibrate();
                            Game1.appDJ.playGrowl();
                        }

                    }
                    else if (Game1.mouse.getX() >= ((float)Game1.screenWidth * .66))
                    {
                        if (Game1.mouse.getY() <= ((float)Game1.screenHeight * .45))
                        {

                            Game1.dog.myAnimate = Dog.animate.dogTugRightUp;

                            if (oldMouse.getY() > ((float)Game1.screenHeight * .45))
                            {
                                Game1.dog.vibrate();
                                Game1.appDJ.playGrowl();
                            }
                        }
                        else
                        {
                            Game1.dog.myAnimate = Dog.animate.dogTugRightDown;

                            if (oldMouse.getY() <= ((float)Game1.screenHeight * .45))
                            {
                                Game1.dog.vibrate();
                                Game1.appDJ.playGrowl();
                            }
                        }

                        if (oldMouse.getX() < ((float)Game1.screenWidth * .66))
                        {
                            Game1.dog.vibrate();
                            Game1.appDJ.playGrowl();
                        }

                    }
                    else
                    {
                        Game1.dog.myAnimate = Dog.animate.dogTug;
                        if (oldMouse.getX() > ((float)Game1.screenWidth * .66))
                        {
                            Game1.dog.vibrate();
                            Game1.appDJ.playGrowl();
                        }

                        if (oldMouse.getX() < ((float)Game1.screenWidth * .33))
                        {
                            Game1.dog.vibrate();
                            Game1.appDJ.playGrowl();
                        }

                    }

                    if (Game1.mouse.isActionDown())
                    {
                        restart();
                    }


                    ropePos2 = new Vector2(Game1.mouse.getX(), Game1.mouse.getY());

                }
                else
                {
                    Game1.dog.myAnimate = Dog.animate.dogSitting;
                    if (Game1.mouse.isActionDown() && startRec.contains((int)Game1.mouse.getX(), (int)Game1.mouse.getY()))
                    {

                        inPlay = true;
                    }


                }



            }

            s_ropetex.setPosition(ropePos.x, ropePos.y);
            s_ropetex2.setPosition(ropePos2.x, ropePos2.y);
            s_ropetex.setZIndex(ropeZ);
            s_ropetex2.setZIndex(rope2Z);
           oldMouse = Game1.mouse;
        }

        

        public void Draw()
        {

            
            if (playPos == true && inPlay == false)
            {
                //spriteBatch.Draw(ropetex, ropePos, ropeRec, Color.White, 0.0f, new Vector2(ropeRec.Width / 2, ropeRec.Height / 2), 1.0f, SpriteEffects.None, ropeZ);
            	s_ropetex.setVisible(true);
            }
            else
            {
            	
            	s_ropetex.setVisible(false);
            }
            
            if(inPlay == true)
            {
                //spriteBatch.Draw(ropetex2, ropePos2, ropeRec2, Color.White, 0.0f, new Vector2(ropeRec2.Width / 2, ropeRec2.Height / 2), 1.0f, SpriteEffects.None, rope2Z);
            	s_ropetex2.setVisible(true);
            }
            else
            {
            	
            	s_ropetex2.setVisible(false);
            }
        }
    }
