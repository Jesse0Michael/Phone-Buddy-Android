package org.phonebuddy;
import java.util.Random;

import org.andengine.entity.primitive.*;
import org.andengine.entity.sprite.Sprite;

    class Cloud 
    {


        public int cloudNumber;
        public float cloudScale;
        public float move;
        public Random rand;
        
        public Sprite s_cloud;
        

        public Cloud(int cloudNumber, Vector2 pos, boolean direction)
        {
        

            this.cloudNumber = cloudNumber;

            rand = new Random();
            
            cloudScale = 0.8f +((float)rand.nextDouble() - 0.5f) / 10f;

            if (direction == true)
            {
                move = ((float)rand.nextDouble() / 10f);
            }
            else
            {
                move = (((float)rand.nextDouble() - 1.0f) / 10f);
            }

            
            switch(cloudNumber)
            {
            case 1:
            	s_cloud = new Sprite(pos.x, pos.y, Game1.cloudImage1, Game1.VBOM);
            	break;
            	
            case 2:
            	s_cloud = new Sprite(pos.x, pos.y, Game1.cloudImage2, Game1.VBOM);
            	break;
            	
            case 3:
            	s_cloud = new Sprite(pos.x, pos.y, Game1.cloudImage3, Game1.VBOM);
            	break;
            	
            case 4:
            	s_cloud = new Sprite(pos.x, pos.y, Game1.cloudImage4, Game1.VBOM);
            	break;
            	
            case 5:
            	s_cloud = new Sprite(pos.x, pos.y, Game1.cloudImage5, Game1.VBOM);
            	break;
            	
            	
            }
            
            
            s_cloud.setVisible(true);
            s_cloud.setZIndex(-70 - (int)Math.abs(move));
            s_cloud.setScale(cloudScale);
            Game1.mScene.attachChild(s_cloud);
            
        }

        public void Update(float gameTime)
        {
            
            s_cloud.setPosition(s_cloud.getX() + move, s_cloud.getY());
        }

    }

