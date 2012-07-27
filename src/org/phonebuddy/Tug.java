package org.phonebuddy;

import org.andengine.entity.primitive.*;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;


    class Tug
    {
    	public Sprite s_ropetex;
    	public Sprite s_ropetex2;
    	
        public Vector2 ropePos;
        private int ropeZ;

        public Vector2 ropePos2;
        public int rope2Z;

        private Rectangle startRec;

        private Vector2 playPlace;
        private float playScale;
        private float speedScale;
        private float speedY;


        public boolean playPos;
        public boolean inPlay;


        public Vector2 oldMouse;


        public Tug()
        {
        	
        	
        	
            
            restart();
            
            s_ropetex = new Sprite(ropePos.x, ropePos.y, Game1.ropeTex, Game1.VBOM);
            s_ropetex2 = new Sprite(ropePos2.x, ropePos2.y, Game1.ropeTex2, Game1.VBOM);
            
            s_ropetex.setVisible(false);
            s_ropetex2.setVisible(false);
            
            Game1.mScene.attachChild(s_ropetex);
            Game1.mScene.attachChild(s_ropetex2);  

        }

        public void restart()
        {
           
            Game1.dog.myAnimate = Dog.animate.dogSitting;
            

            playPos = false;
            inPlay = false;

            oldMouse = new Vector2( Game1.screenWidth / 2, Game1.screenHeight / 2);

            
            playScale = 3.0f;
            playPlace = new Vector2((int)((float)Game1.screenWidth * .51), (int)((float)Game1.screenHeight * .62));

            ropePos = new Vector2((int)((float)Game1.screenWidth * .5), (int)((float)Game1.screenHeight * .6));
            ropePos2 = new Vector2((int)((float)Game1.screenWidth * .5 ), (int)((float)Game1.screenHeight * .6));
            rope2Z = -40;
            ropeZ = -41;

            startRec = new Rectangle((int)ropePos.x - (Game1.ropeTex.getWidth() / 2), (int)ropePos.y - (Game1.ropeTex.getHeight() / 2), Game1.ropeTex.getWidth(), Game1.ropeTex.getHeight() , Game1.VBOM);

            
            speedY = 1.0f;
            speedScale = .01f;


                      

        }


        public void Update(float gameTime)
        {
            

            if (playPos == false)
            {
                

                if (Game1.dog.dogPos.y != playPlace.y || Game1.dog.dogScale != playScale)
                {
                	Game1.dog.tugboolean = true;
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
                            if (oldMouse.y > ((float)Game1.screenHeight * .45))
                            {
                                Game1.dog.vibrate();
                                Game1.appDJ.playGrowl();
                            }
                        }
                        else
                        {
                            Game1.dog.myAnimate = Dog.animate.dogTugLeftDown;

                            if (oldMouse.y <= ((float)Game1.screenHeight * .45))
                            {
                                Game1.dog.vibrate();
                                Game1.appDJ.playGrowl();
                            }
                        }

                        if (oldMouse.x > ((float)Game1.screenWidth * .33))
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

                            if (oldMouse.y > ((float)Game1.screenHeight * .45))
                            {
                                Game1.dog.vibrate();
                                Game1.appDJ.playGrowl();
                            }
                        }
                        else
                        {
                            Game1.dog.myAnimate = Dog.animate.dogTugRightDown;

                            if (oldMouse.y <= ((float)Game1.screenHeight * .45))
                            {
                                Game1.dog.vibrate();
                                Game1.appDJ.playGrowl();
                            }
                        }

                        if (oldMouse.x < ((float)Game1.screenWidth * .66))
                        {
                            Game1.dog.vibrate();
                            Game1.appDJ.playGrowl();
                        }

                    }
                    else
                    {
                        Game1.dog.myAnimate = Dog.animate.dogTug;
                        if (oldMouse.x > ((float)Game1.screenWidth * .66))
                        {
                            Game1.dog.vibrate();
                            Game1.appDJ.playGrowl();
                        }

                        if (oldMouse.x < ((float)Game1.screenWidth * .33))
                        {
                            Game1.dog.vibrate();
                            Game1.appDJ.playGrowl();
                        }

                    }

                    if (Game1.mouse.isActionUp())
                    {
                        restart();
                        
                    }


                    ropePos2.x = Game1.mouse.getX();
                    ropePos2.y = Game1.mouse.getY();

                }
                else
                {
                    Game1.dog.myAnimate = Dog.animate.dogSitting;
                    if ((Game1.mouse.isActionDown() || Game1.mouse.isActionMove()) && startRec.contains((int)Game1.mouse.getX(), (int)Game1.mouse.getY()))
                    {

                        inPlay = true;
                    }


                }



            }

            s_ropetex.setPosition(ropePos.x - (Game1.ropeTex.getWidth() / 2), ropePos.y - (Game1.ropeTex.getHeight() / 2));
            s_ropetex2.setPosition(ropePos2.x - (Game1.ropeTex2.getWidth() / 2), ropePos2.y - (Game1.ropeTex2.getHeight() / 2));
            s_ropetex.setZIndex(ropeZ);
            s_ropetex2.setZIndex(rope2Z);
            oldMouse.x = Game1.mouse.getX();
            oldMouse.y = Game1.mouse.getY();
        }
    }
