package org.phonebuddy;
import java.util.Random;




    public class AppDJ 
    {
        public boolean volOn;

        public boolean runningOn;
        public boolean foodOn;
        public boolean drinkOn;

        public float runVol;

        private Random rand;
        private TimeSpan barkSpan;


        

        public AppDJ()
        {
            volOn = true;
            runningOn = false;
            drinkOn = false;
            foodOn = false;
            runVol = 1.0f;

            rand = new Random();
            barkSpan = new TimeSpan(1, true)
            {
            	@Override
            	public void onTick()
            	{
            		
            		int bark = rand.nextInt(5) + 1;
                    switch (bark)
                    {
                        case 1:
                        	Game1.ibark1.play();
                            break;

                        case 2:
                        	Game1.ibark2.play();
                            break;

                        case 3:
                        	Game1.ibark3.play();
                            break;

                        case 4:
                        	Game1.ibark4.play();
                            break;

                        case 5:
                        	Game1.ibark5.play();
                            break;

                    }

                    barkSpan.setInterval(rand.nextInt(5) + 2);
                    barkSpan.reset();
                
            		
            		
            	}
            };
            
        }

        public void LoadContent()
        {

            

            Game1.ifood.setLooping(true);
            Game1.idrink.setLooping(true);

        }

        
        public void Update(float gameTime)
        {
            if (volOn)
            {
                if (runningOn)
                {
                    barkSpan.start();
                    

                }
                else
                {
                	barkSpan.pause();
                	
                }

                if (foodOn)
                {
                    
                	Game1.ifood.play();

                }
                else
                {
                	Game1.ifood.stop();
                }

                if (drinkOn)
                {
                    
                	Game1.idrink.play();
                }
                else
                {
                	Game1.idrink.stop();
                }
            }

        }

        public void playThud(float dist)
        {
            if (volOn)
            {
            	Game1.ithud.setVolume(dist);
            	Game1.ithud.play();

            }
        }

        public void playBird()
        {
            if (volOn)
            {

                int bird = rand.nextInt(5) + 1;
                switch (bird)
                {
                    case 1:
                    	Game1.ibird1.setVolume(0.4f);
                    	Game1.ibird1.play();
                        break;

                    case 2:
                    	Game1.ibird2.setVolume(0.4f);
                    	Game1.ibird2.play();
                        break;

                    case 3:
                    	Game1.ibird3.setVolume(0.4f);
                    	Game1.ibird3.play();
                        break;

                    case 4:
                    	Game1.ibird4.setVolume(0.4f);
                    	Game1.ibird4.play();
                        break;

                    case 5:
                    	Game1.ibird5.setVolume(0.4f);
                    	Game1.ibird5.play();
                        break;

                }
            }
        }

        public void playGrowl()
        {
            if (volOn)
            {
                if (growlplaying() == false)
                {
                    int growl = rand.nextInt(6) + 1;

                    switch (growl)
                    {
                        case 1:
                        	Game1.igrowl1.play();
                            break;

                        case 2:
                        	Game1.igrowl2.play();
                            break;

                        case 3:
                        	Game1.igrowl3.play();
                            break;

                        case 4:
                        	Game1.igrowl4.play();
                            break;

                        case 5:
                            break;

                        case 6:
                            break;
                    }
                }
            }
        }

        public boolean growlplaying()
        {

            if (Game1.igrowl1.isPlaying() == true)
            {
                return true;
            }
            else if (Game1.igrowl2.isPlaying() == true)
            {
                return true;
            }
            else if (Game1.igrowl3.isPlaying() == true)
            {
                return true;
            }
            else if (Game1.igrowl4.isPlaying() == true)
            {
                return true;
            }
            else
            {
                return false;
            }

        }

        public void playBag()
        {
            if (volOn)
            {
            	Game1.ibag.setVolume(0.77f);
            	Game1.ibag.play();



            }
        }

        public void stopEverything()
        {

        	Game1.ibark1.stop();
        	Game1.ibark2.stop();
        	Game1.ibark3.stop();
        	Game1.ibark4.stop();
        	Game1.ibark5.stop();
        	Game1.ibird1.stop();
        	Game1.ibird2.stop();
        	Game1.ibird3.stop();
        	Game1.ibird4.stop();
        	Game1.ibird5.stop();
        	Game1.idrink.stop();
        	Game1.ifood.stop();
        	Game1.igrowl1.stop();
        	Game1.igrowl2.stop();
        	Game1.igrowl3.stop();
        	Game1.igrowl4.stop();
        	Game1.ithud.stop();
        	Game1.ibag.stop();

        }
    }
