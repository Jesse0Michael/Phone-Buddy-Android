package org.phonebuddy;
import java.util.Random;

import org.andengine.entity.primitive.*;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
 
	class Poo
    {

        ITextureRegion pooImage;
        public Sprite s_pooImage;

        public Vector2 pooPos;
        public Vector2 pooStart;

        public int pooNumber;

        public float pooZ;
        public float pooScale;


        public Rectangle pooRec;
        public Rectangle touchRec;
        public Rectangle bagRec;

        public Random randX;
        public Random randY;

        public Boolean grabbedPoo;

        public int screenWidth;
        public int screenHeight;
        

        public Poo(int pooNumber)
        {
            this.pooNumber = pooNumber;

            
            grabbedPoo = false;
            
            randX = new Random();
            randY = new Random();

            pooPos = new Vector2((float)(randX.nextInt((int)((float)screenWidth * .83)) + .07), (float)(randY.nextInt((int)((float)screenHeight * .59)) + .35));
            pooStart = pooPos;

            pooScale = pooPos.y / 500.0f;
            pooZ = 0.6f - pooScale / 100.0f;

            //pooImage = Content.Load<ITextureRegion>("Textures/actPoo" + pooNumber);

            pooRec = new Rectangle(0, 0, pooImage.getWidth(), pooImage.getHeight(), Game1.VBOM);
            bagRec = new Rectangle((int)((float)screenWidth * .80), (int)((float)screenHeight * .75), pooImage.getWidth() + (int)((float)screenWidth * .80), pooImage.getHeight() + (int)((float)screenWidth * .75), Game1.VBOM);
        
           
            
            switch(pooNumber)
            {
            case 1:
            	s_pooImage = new Sprite(pooPos.x, pooPos.y, Game1.pooImage1, Game1.VBOM);
            	break;
            	
            case 2:
            	s_pooImage = new Sprite(pooPos.x, pooPos.y, Game1.pooImage2, Game1.VBOM);
            	break;
            }
            
            s_pooImage.setVisible(true);
            s_pooImage.setZIndex(59);
            Game1.mScene.attachChild(s_pooImage);
        }


        public void Update(float gameTime)
        {
            touchRec = new Rectangle((int)pooPos.x - pooImage.getWidth() / 2, (int)pooPos.y - pooImage.getHeight() / 2, pooImage.getWidth(), pooImage.getHeight(), Game1.VBOM);
            

            if (Game1.mouse.isActionDown() && touchRec.contains((int)Game1.mouse.getX(), (int)Game1.mouse.getY()))
            {
                grabbedPoo = true;

            }
            else if (Game1.mouse.isActionUp())
            {

                grabbedPoo = false;

                if (bagRec.contains((int)Game1.mouse.getX(), (int)Game1.mouse.getY()) && bagRec.contains((int)pooPos.x, (int)pooPos.y))
                {
                    Game1.dog.statHygiene += 0.3f;
                    pooPos.x = -250;
                    s_pooImage.setPosition(-250, s_pooImage.getY());
                    Game1.appDJ.playBag();

                    Game1.dog.vibrate();

                }
                else
                {
                    pooPos = pooStart;

                    s_pooImage.setPosition(pooStart.x, pooStart.y);
                }

            }

            if (grabbedPoo == true)
            {
                s_pooImage.setPosition(Game1.mouse.getX(), Game1.mouse.getY());
            }
            

        }

        public void Draw()
        {
            //spriteBatch.Draw(pooImage, pooPos, pooRec, Color.White, 0.0f, new Vector2(pooImage.Width / 2, pooImage.Height / 2), pooScale, SpriteEffects.None, pooZ);

        }
    }
