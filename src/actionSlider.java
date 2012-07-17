import org.andengine.input.touch.*;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.entity.primitive.*;


	class actionSlider
    {
		
        public ITextureRegion slider;
        public ITextureRegion sliderPull;
        public ITextureRegion actFetch;
        public ITextureRegion actTug;
        public ITextureRegion actFood;
        public ITextureRegion actWater;
        public ITextureRegion actPoo;
        
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

            

        }

        public void LoadContent()
        {

            //slider = Content.Load<ITextureRegion>("Textures/slider");
            //sliderPull = Content.Load<ITextureRegion>("Textures/sliderPull");
            //actFetch = Content.Load<ITextureRegion>("Textures/actFetch");
            //actTug = Content.Load<ITextureRegion>("Textures/actTug");
            //actFood = Content.Load<ITextureRegion>("Textures/actFood");
            //actWater = Content.Load<ITextureRegion>("Textures/actWater");
            //actPoo = Content.Load<ITextureRegion>("Textures/actPoo");
            

        }

        public void Update(float gameTime)
        {
        	
            if (Game1.mouse.isActionUp() && recSliderPull.contains((int)Game1.mouse.getX(), (int)Game1.mouse.getY()) && pullerClicked == false)
            {
                pullerClicked = true;
                sliderGap = (int)(Game1.mouse.getX() - (slider.getWidth() + sliderXloc));
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

            if (Game1.mouse.getX() <= slider.getWidth() -25  + sliderGap && Game1.mouse.getX() >= sliderGap && pullerClicked == true)
            {
                sliderXloc = (int)(Game1.mouse.getX() - slider.getWidth() - sliderGap);
            }
            else if (slideRight == true && sliderXloc < -25)
            {
                sliderXloc+= 5;
                slideLeft = false;
            }
            else if (slideLeft == true && sliderXloc > - slider.getWidth())
            {
                sliderXloc-= 5;
                slideRight = false;
            }
            else if (sliderXloc >= -25 && sliderXloc <= -30)
            {
                slideRight = false;
                sliderXloc = -25;
            }
            else if (sliderXloc >= -slider.getWidth() && sliderXloc <= -slider.getWidth() + 5)
            {
                slideLeft = false;
                sliderXloc = (int)-slider.getWidth();
            }

            

            this.recSlider = new Rectangle(sliderXloc, 0, slider.getWidth(), slider.getHeight(), Game1.VBOM);
            this.recSliderPull = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .22), (int)((float)Game1.screenHeight * .36), sliderPull.getWidth(), sliderPull.getHeight(), Game1.VBOM);
            this.recFetch = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .075), (int)((float)Game1.screenHeight * .04), actFetch.getWidth(), actFetch.getHeight(), Game1.VBOM);
            this.recTug = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .075), (int)((float)Game1.screenHeight * .24), actTug.getWidth(), actTug.getHeight(), Game1.VBOM);
            this.recFood = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .075), (int)((float)Game1.screenHeight * .43), actFood.getWidth(), actFood.getHeight(), Game1.VBOM);
            this.recWater = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .075), (int)((float)Game1.screenHeight * .62), actWater.getWidth(), actWater.getHeight(), Game1.VBOM);
            this.recPoo = new Rectangle(sliderXloc + (int)((float)Game1.screenWidth * .09), (int)((float)Game1.screenHeight * .77), actPoo.getWidth(), actPoo.getHeight(), Game1.VBOM);

        }

        public void Draw()
        {



            
            spriteBatch.Draw(slider, recSlider, new Rectangle(0, 0, slider.getWidth(), slider.getHeight()), Color.White, 0.0f, new Vector2(0, 0), SpriteEffects.None, .2f);
            spriteBatch.Draw(sliderPull, recSliderPull, new Rectangle(0, 0, sliderPull.getWidth(), sliderPull.getHeight()), Color.White, 0.0f, new Vector2(0, 0), SpriteEffects.None, .19f);
            spriteBatch.Draw(actFetch, recFetch, new Rectangle(0, 0, actFetch.getWidth(), actFetch.getHeight()), Color.White, 0.0f, new Vector2(0, 0), SpriteEffects.None, .18f);
            spriteBatch.Draw(actTug, recTug, new Rectangle(0, 0, actTug.getWidth(), actTug.getHeight()), Color.White, 0.0f, new Vector2(0, 0), SpriteEffects.None, .17f);
            spriteBatch.Draw(actFood, recFood, new Rectangle(0, 0, actFood.getWidth(), actFood.getHeight()), Color.White, 0.0f, new Vector2(0, 0), SpriteEffects.None, .16f);
            spriteBatch.Draw(actWater, recWater, new Rectangle(0, 0, actWater.getWidth(), actWater.getHeight()), Color.White, 0.0f, new Vector2(0,0), SpriteEffects.None, .15f);
            spriteBatch.Draw(actPoo, recPoo, new Rectangle(0, 0, actPoo.getWidth(), actPoo.getHeight()), Color.White, 0.0f, new Vector2(0,0), SpriteEffects.None, .13f);
            



        }
    }
