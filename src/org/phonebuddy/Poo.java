package org.phonebuddy;
import java.util.Random;

import org.andengine.entity.primitive.*;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;
 
	class Poo
    {

        public Sprite s_pooImage;

        public Vector2 pooPos;
        public Vector2 pooStart;

        public int pooNumber;

        public float pooScale;


        public Rectangle touchRec;
        public Rectangle bagRec;

        public Random randX;
        public Random randY;

        public Boolean grabbedPoo;

        

        public Poo(int pooNumber)
        {
            this.pooNumber = pooNumber;

            
            this.grabbedPoo = false;
            
            this.randX = new Random();
            this.randY = new Random();

            this.pooPos = new Vector2((float)(randX.nextInt((int)((float)Game1.screenWidth * .82)) + (int)(Game1.screenHeight * .07)), (float)(randY.nextInt((int)((float)Game1.screenHeight * .46)) + (int)(Game1.screenHeight * .35)));
            
            this.pooStart = new Vector2(this.pooPos.x, this.pooPos.y);
            

            this.pooScale = this.pooPos.y / 400.0f;

            this.bagRec = new Rectangle((int)((float)Game1.screenWidth * .80), (int)((float)Game1.screenHeight * .75), Game1.pooBag.getWidth(), Game1.pooBag.getHeight(), Game1.VBOM);
        
           
            
            switch(pooNumber)
            {
            case 1:
            	this.s_pooImage = new Sprite(this.pooPos.x, this.pooPos.y, Game1.pooImage1, Game1.VBOM);
            	break;
            	
            case 2:
            	this.s_pooImage = new Sprite(this.pooPos.x, this.pooPos.y, Game1.pooImage2, Game1.VBOM);
            	break;
            }
            
            this.s_pooImage.setVisible(true);
            this.s_pooImage.setZIndex(-59);
            this.s_pooImage.setScale(this.pooScale);
            Game1.mScene.attachChild(this.s_pooImage);
        }


        public void Update(float gameTime)
        {
        	this.touchRec = new Rectangle((int)pooPos.x, (int)pooPos.y, Game1.pooImage1.getWidth(), Game1.pooImage1.getHeight(), Game1.VBOM);
        	

            if (Game1.mouse.isActionDown()  && touchRec.contains((int)Game1.mouse.getX(), (int)Game1.mouse.getY()))
            {
            	this.grabbedPoo = true;

            }
            else if (Game1.mouse.isActionUp())
            {

            	this.grabbedPoo = false;

                if ( (bagRec.contains((int)Game1.mouse.getX(), (int)Game1.mouse.getY())  ) && (bagRec.contains((int)pooPos.x, (int)pooPos.y) )  )
                {
                    Game1.dog.statHygiene += 0.3f;
                    this.pooPos.x = -250;
                    this.s_pooImage.setPosition(-250, this.s_pooImage.getY());
                    Game1.appDJ.playBag();

                    Game1.dog.vibrate();

                }
                else
                {
                	this.s_pooImage.setPosition(this.pooStart.x, this.pooStart.y);
                    this.pooPos.x = this.pooStart.x;
                    this.pooPos.y = this.pooStart.y;
                    
                    
                }

            }

            if (this.grabbedPoo == true)
            {
            	this.s_pooImage.setPosition(Game1.mouse.getX() - (Game1.pooImage1.getWidth() / 2), Game1.mouse.getY()  - (Game1.pooImage1.getHeight() / 2));
            	this.pooPos.x = this.s_pooImage.getX();
            	this.pooPos.y = this.s_pooImage.getY();
            }
            

        }

    }
