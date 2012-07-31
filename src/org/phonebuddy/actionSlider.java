package org.phonebuddy;
import org.andengine.entity.primitive.*;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;


	class actionSlider
    {
		
        public Sprite s_slider;
        public Sprite s_sliderPull;
        public Sprite s_actFetch;
        public Sprite s_actTug;
        public Sprite s_actFood;
        public Sprite s_actWater;
        public Sprite s_actPoo;
        
        public Rectangle recSlider;
        public Rectangle recSliderPull;
        public Rectangle recFetch;
        public Rectangle recTug;
        public Rectangle recFood;
        public Rectangle recWater;
        public Rectangle recPoo;
        
        private int sliderXloc;
        private int sliderGap;
        private int mouseGrabbed;
        private int mouseLet;

        public Boolean pullerClicked;
        public Boolean slideRight;
        public Boolean slideLeft;



        public actionSlider()
        {
            this.sliderXloc = -25;
            this.pullerClicked = false;
            this.slideRight = false;
            this.slideLeft = false;
            
            
            s_slider = new Sprite(0, 0,(int)((float)Game1.screenWidth * .22), Game1.screenHeight, Game1.slider, Game1.VBOM);
            s_sliderPull = new Sprite(0, 0, Game1.sliderPull, Game1.VBOM);
            s_actFetch = new Sprite(0, 0, Game1.actFetch, Game1.VBOM);
            s_actTug = new Sprite(0, 0, Game1.actTug, Game1.VBOM);
            s_actFood = new Sprite(0, 0, Game1.actFood, Game1.VBOM);
            s_actWater = new Sprite(0, 0, Game1.actWater, Game1.VBOM);
            s_actPoo = new Sprite(0, 0, Game1.actPoo, Game1.VBOM);
            
            s_slider.setZIndex(-20);
            s_sliderPull.setZIndex(-19);
            s_actFetch.setZIndex(-18);
            s_actTug.setZIndex(-17);
            s_actFood.setZIndex(-16);
            s_actWater.setZIndex(-15);
            s_actPoo.setZIndex(-14);
            
            
            Game1.mScene.attachChild(s_slider);
            Game1.mScene.attachChild(s_sliderPull);
            Game1.mScene.attachChild(s_actFetch);
            Game1.mScene.attachChild(s_actTug);
            Game1.mScene.attachChild(s_actFood);
            Game1.mScene.attachChild(s_actWater);
            Game1.mScene.attachChild(s_actPoo);
            
            this.recSlider = new Rectangle(sliderXloc, 0, sliderXloc + (int)((float)Game1.screenWidth * .22), Game1.screenHeight, Game1.VBOM);
            this.recSliderPull = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .22) - 3, (int)((float)Game1.screenHeight * .36), s_sliderPull.getWidth(), s_sliderPull.getHeight(), Game1.VBOM);
            this.recFetch = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .075), (int)((float)Game1.screenHeight * .04), s_actFetch.getWidth(), s_actFetch.getHeight(), Game1.VBOM);
            this.recTug = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .075), (int)((float)Game1.screenHeight * .24), s_actTug.getWidth(), s_actTug.getHeight(), Game1.VBOM);
            this.recFood = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .075), (int)((float)Game1.screenHeight * .43), s_actFood.getWidth(), s_actFood.getHeight(), Game1.VBOM);
            this.recWater = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .075), (int)((float)Game1.screenHeight * .62), s_actWater.getWidth(), s_actWater.getHeight(), Game1.VBOM);
            this.recPoo = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .09), (int)((float)Game1.screenHeight * .77), s_actPoo.getWidth(), s_actPoo.getHeight(), Game1.VBOM);

            
        }

       

        public void Update(float gameTime)
        {
        	if ((Game1.mouse.isActionDown() || Game1.mouse.isActionMove()) && recSliderPull.contains((int)Game1.mouse.getX(), (int)Game1.mouse.getY()) && pullerClicked == false)
            {
                pullerClicked = true;
                sliderGap = (int)(Game1.mouse.getX() - (s_slider.getWidth() + sliderXloc));
                mouseGrabbed = (int)Game1.mouse.getX();
                slideLeft = false;
                slideRight = false;

            }
            else if(Game1.mouse.isActionUp() == true && pullerClicked == true)
            {
                pullerClicked = false;
                mouseLet = (int)Game1.mouse.getX();

                if (mouseGrabbed > mouseLet)
                {
                    if ((mouseGrabbed - mouseLet) > 50)
                    {
                        slideLeft = true;
                    }
                    else
                    {
                        slideRight = true;
                    }
                }
                else if (mouseGrabbed < mouseLet)
                {
                    if ((mouseLet - mouseGrabbed) < 50)
                    {
                        slideLeft = true;
                    }
                    else
                    {
                        slideRight = true;
                    }
                }

            }

            if (Game1.mouse.getX() <= s_slider.getWidth() -25  + sliderGap && Game1.mouse.getX() >= sliderGap && pullerClicked == true)
            {
                sliderXloc = (int)(Game1.mouse.getX() - s_slider.getWidth() - sliderGap);
            }
            else if (slideRight == true && sliderXloc < -25)
            {
                sliderXloc+= 5;
                slideLeft = false;
            }
            else if (slideLeft == true && sliderXloc > - s_slider.getWidth())
            {
                sliderXloc-= 5;
                slideRight = false;
            }
            else if (sliderXloc >= -25 && sliderXloc <= -30)
            {
                slideRight = false;
                sliderXloc = -25;
            }
            else if (sliderXloc >= -s_slider.getWidth() && sliderXloc <= -s_slider.getWidth() + 5)
            {
                slideLeft = false;
                sliderXloc = (int)-s_slider.getWidth();
            }

            

            this.recSlider = new Rectangle(sliderXloc, 0, sliderXloc + (int)((float)Game1.screenWidth * .22), Game1.screenHeight, Game1.VBOM);
            this.recSliderPull = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .22) - 3, (int)((float)Game1.screenHeight * .36), s_sliderPull.getWidth(), s_sliderPull.getHeight(), Game1.VBOM);
            this.recFetch = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .075), (int)((float)Game1.screenHeight * .04), s_actFetch.getWidth(), s_actFetch.getHeight(), Game1.VBOM);
            this.recTug = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .075), (int)((float)Game1.screenHeight * .24), s_actTug.getWidth(), s_actTug.getHeight(), Game1.VBOM);
            this.recFood = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .075), (int)((float)Game1.screenHeight * .43), s_actFood.getWidth(), s_actFood.getHeight(), Game1.VBOM);
            this.recWater = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .075), (int)((float)Game1.screenHeight * .62), s_actWater.getWidth(), s_actWater.getHeight(), Game1.VBOM);
            this.recPoo = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .09), (int)((float)Game1.screenHeight * .77), s_actPoo.getWidth(), s_actPoo.getHeight(), Game1.VBOM);

            
            
            s_slider.setPosition(recSlider);
            s_sliderPull.setPosition(recSliderPull);
            s_actFetch.setPosition(recFetch);
            s_actTug.setPosition(recTug);
            s_actFood.setPosition(recFood);
            s_actWater.setPosition(recWater);
            s_actPoo.setPosition(recPoo);
        }
    }
