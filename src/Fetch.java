import org.andengine.entity.primitive.*;
import org.andengine.opengl.texture.region.ITextureRegion;

    class Fetch
    {
        
        ITextureRegion ball;
        ITextureRegion shadow;

        public int ballPosX;
        public int ballPosY;
        public int bounceCount;

        public float Magnitude;
        public float ballScale;
        public float ballRot;
        public float ballPosZ;
        public float initialVel;
        public float ballStartWidth;
        public float bounceY;
        public float bounceSpeed;
        public float xFactor;
        public float yFactor;
        public float sFactor;
        public float shadowScale;
        public float oldPoint;
        public float oldOldPoint;
        public float nowPoint;

        public Rectangle ballRec;
        public Rectangle touchRec;

        public Vector2 vanishingPoint;
        public Vector2 farthestBack;
        public Vector2 released;
        public Vector2 ballOrigin;
        public Vector2 ballPos;
        public Vector2 linePos;
        public Vector2 dogLine;
        public Vector2 shadowLine;

        public TimeSpan reactionTime;
        public TimeSpan idleTime;
        public TimeSpan backTime;
        public TimeSpan releaseTime;

        

        public enum ballState
        {
            ballIdle,
            ballHolding,
            ballReleased,
            ballWaiting,
            ballReturning
        };

        public ballState state;

        
        public Fetch()
        {

            restart();
            
        }

        public void restart()
        {
            ballPosX = (int)((float)Game1.screenWidth * .86);
            ballPosY = (int)((float)Game1.screenHeight * .80);
            ballPosZ = 0.4f;
            ballScale = 1.0f;
            ballRot = 0.0f;
            initialVel = 0.0f;
            bounceCount = 0;
            Magnitude = 250.0f;
            bounceY = 0.0f;
            bounceSpeed = 0.0f;

            shadowScale = 1.0f;
            xFactor = 1.0f;
            yFactor = 2.0f;
            sFactor = 3.0f;
            oldPoint = 0.0f;
            oldOldPoint = 0.0f;
            nowPoint = 0.0f;

            state = ballState.ballIdle;

            vanishingPoint = new Vector2((int)((float)Game1.screenWidth * .55), (int)((float)Game1.screenHeight * .23));
            farthestBack = new Vector2(0, 0);
            released = new Vector2(0, 0);
            ballOrigin = new Vector2(ballPosX, ballPosY);
            ballPos = new Vector2(ballPosX, ballPosY);
            linePos = new Vector2(ballPosX, ballPosY);
            dogLine = new Vector2(Game1.dog.dogPos.x, Game1.dog.dogPos.y);
            shadowLine = new Vector2(ballPosX, (int)((float)Game1.screenHeight * .88));
            reactionTime = new TimeSpan(0, 0, 0, 0, 250);
            idleTime = new TimeSpan(0, 0, 30);
            backTime = new TimeSpan(0, 0, 0, 0, 0);
            releaseTime = new TimeSpan(0, 0, 0, 0, 0);
            

            
        }

        public void LoadContent()
        {
            //ball = Content.Load<ITextureRegion>("Textures/actFetch");
            //shadow = Content.Load<ITextureRegion>("Textures/ballShadow");

            ballRec = new Rectangle(0, 0, ball.getWidth(), ball.getHeight(), Game1.VBOM);
            touchRec = new Rectangle(ballPosX, ballPosY, ball.getWidth(), ball.getHeight(), Game1.VBOM);
            ballStartWidth = ball.getWidth();
            
        }


        public void Update(float gameTime)
        {
            touchRec = new Rectangle((int)ballPos.x - ball.getWidth()/2, (int)ballPos.y-ball.getHeight()/2, ball.getWidth(), ball.getHeight(), Game1.VBOM);
            

            switch (state)
            {
                case ballIdle:
                    idleing(gameTime);

                    break;

                case ballHolding:
                    holding(gameTime);

                    break;

                case ballReleased:
                    releasing(gameTime);

                    break;


                case ballReturning:
                    returning(gameTime);

                    break;


            }

        }

        public void idleing(float gameTime)
        {

            if (Game1.mouse.isActionDown() && touchRec.contains(Game1.mouse.getX(), Game1.mouse.getY()))
            {
                state = ballState.ballHolding;

            }

        }

        public void holding(float gameTime)
        {
            
            if (Game1.mouse.isActionDown())
            {
                ballPos.x = (int)Game1.mouse.getX();
                ballPos.y = (int)Game1.mouse.getY();


                if (Game1.mouse.getY() >= farthestBack.y)
                {
                    farthestBack.x = Game1.mouse.getX();
                    farthestBack.y = Game1.mouse.getY();
                    backTime = gameTime.TotalGameTime;
                }

            }
            else if (Game1.mouse.isActionUp())
            {
                released.x = Game1.mouse.getX();
                released.y = Game1.mouse.getY();
                releaseTime = gameTime.TotalGameTime;
                initialVel = (float)((Math.sqrt(Math.pow((released.x - farthestBack.x), 2) + Math.pow((released.y - farthestBack.y), 2)))/(releaseTime.TotalMilliseconds - backTime.TotalMilliseconds));
                
                linePos = new Vector2(ballPos.x, ballPos.y);
                
                if (initialVel <= 0.45f)
                {


                    restart();
                }
                else if(initialVel <= 1.0f)
                {
                    initialVel = 1.0f;
                    state = ballState.ballReleased;
                }
                else if (initialVel >= 1.5f)
                {
                    initialVel = 1.5f;
                
                    state = ballState.ballReleased;
                }

            }
        }

        public void releasing(float gameTime)
        {
            if (reactionTime >= TimeSpan.Zero)
            {
                reactionTime -= gameTime.ElapsedGameTime;
            }
            else
            {
                waiting();

            }


            if (bounceCount < 4)
            {
                ballRot -= (float)Math.PI / (8 * initialVel);

                bounceSpeed += initialVel / 50.0f;
                bounceY = (float)Math.abs(Math.sin(bounceSpeed)) * ((Magnitude * initialVel));

                ballScale = 1 / ((bounceSpeed) + 1);

                oldOldPoint = oldPoint;
                oldPoint = nowPoint;
                nowPoint = ((float)Math.abs(Math.sin(bounceSpeed)));

                if (oldPoint < oldOldPoint && oldPoint < nowPoint)
                {
                    Game1.dog.vibrate();
                    Game1.appDJ.playThud(1.0f - (float)(bounceCount/10));
                    Magnitude /= 2.0f;
                    bounceCount++;
                }

                if (linePos.y != vanishingPoint.y)
                {
                    if (linePos.y >= vanishingPoint.y - (4 / (bounceSpeed + 1)) && linePos.y <= vanishingPoint.y + (4 / (bounceSpeed + 1)))
                    {
                        linePos.y = vanishingPoint.y;
                    }
                    else if (linePos.y >= vanishingPoint.y)
                    {
                        linePos.y -= 2 / (bounceSpeed + 1); 
                    }
                    else if (linePos.y <= vanishingPoint.y)
                    {
                        linePos.y += 10 / (bounceSpeed + 1); 
                    }

                }

                linePos.x -= 2.5f / (bounceSpeed + 1); 

            }

            ballPos.x = linePos.x;
            ballPos.y = linePos.y - bounceY;


            shadowScale = ballScale / ((float)Math.abs(Math.sin(bounceSpeed)) + 1);

            if (shadowLine.x >= linePos.x - (2 / bounceSpeed + 1) && shadowLine.x <= linePos.x + (2 / bounceSpeed + 1))
            {
                shadowLine.x = linePos.x;
            }
            else if (shadowLine.x >= linePos.x)
            {
                shadowLine.x -= (2 / bounceSpeed + 1);
            }
            else if (shadowLine.x <= linePos.x)
            {
                shadowLine.x += (2 / bounceSpeed + 1);
            }

            if (shadowLine.y >= linePos.y + (50.0f * ballScale) - (2 / bounceSpeed + 1) && shadowLine.y <= linePos.y + (50.0f * ballScale) + (2 / bounceSpeed + 1))
            {
                shadowLine.y = linePos.y + (50.0f * ballScale); 
            }
            else if (shadowLine.y >= linePos.y + (50.0f * ballScale))
            {
                shadowLine.y -= (2 / bounceSpeed + 1);
            }
            else if (shadowLine.y <= linePos.y + (50.0f * ballScale))
            {
                shadowLine.y += (2 / bounceSpeed + 1);
            }
            


            

        }

        public void waiting()
        {
            ballPosZ = 0.6f;


            if (Game1.dog.dogPos.y != linePos.y || Game1.dog.dogPos.x != ballPos.x)
            {
                Game1.dog.myAnimate = Dog.animate.dogRunAway;
                Game1.appDJ.runningOn = true;
                if (Game1.dog.dogPos.y >= linePos.y - (Game1.dog.returnSpeedY / yFactor) && Game1.dog.dogPos.y <= linePos.y + (Game1.dog.returnSpeedY / yFactor))
                {
                    Game1.dog.dogPos.y = linePos.y;
                }
                else if (Game1.dog.dogPos.y >= linePos.y)
                {
                    Game1.dog.dogPos.y -= (Game1.dog.returnSpeedY / yFactor);

                }
                else if (Game1.dog.dogPos.y <= linePos.y)
                {
                    Game1.dog.dogPos.y += (Game1.dog.returnSpeedY / yFactor);

                }

                if (Game1.dog.dogPos.x >= ballPos.x - (Game1.dog.returnSpeedX / xFactor) && Game1.dog.dogPos.x <= ballPos.x + (Game1.dog.returnSpeedX / xFactor))
                {
                    Game1.dog.dogPos.x = ballPos.x;

                }
                else if (Game1.dog.dogPos.x >= ballPos.x)
                {
                    Game1.dog.dogPos.x -= (Game1.dog.returnSpeedX / xFactor);
                }
                else if (Game1.dog.dogPos.x <= ballPos.x)
                {
                    Game1.dog.dogPos.x += (Game1.dog.returnSpeedX / xFactor);
                }

                if (Game1.dog.dogScale >= ballScale - (Game1.dog.returnSpeedS / sFactor) && Game1.dog.dogScale <= ballScale + (Game1.dog.returnSpeedS / sFactor) && ballScale >= 0.1f)
                {
                    Game1.dog.dogScale = ballScale;
                }
                else if (Game1.dog.dogScale >= ballScale && ballScale >= 0.1f)
                {
                    Game1.dog.dogScale -= (Game1.dog.returnSpeedS / sFactor);
                }
                else if (Game1.dog.dogScale <= ballScale && ballScale >= 0.1f)
                {
                    Game1.dog.dogScale += (Game1.dog.returnSpeedS / sFactor);
                }
                

            }
            else
            {
                shadowLine.y = -100;
                state = ballState.ballReturning;
                Game1.appDJ.runningOn = false;
            }



        }

        public void returning(float gameTime)
        {
            ballPosZ = 0.4f;
            if (Game1.dog.dogPos.y != Game1.dog.origin.y || Game1.dog.dogPos.x != Game1.dog.origin.x || Game1.dog.dogScale < 1.0f)
            {
                Game1.dog.myAnimate = Dog.animate.dogRunTowards;

                if (Game1.dog.dogPos.y >= Game1.dog.origin.y - (Game1.dog.returnSpeedY / yFactor) && Game1.dog.dogPos.y <= Game1.dog.origin.y + (Game1.dog.returnSpeedY / yFactor))
                {
                    Game1.dog.dogPos.y = Game1.dog.origin.y;
                    ballPos.y = Game1.dog.origin.y;
                }
                else if (Game1.dog.dogPos.y <= Game1.dog.origin.y)
                {
                    Game1.dog.dogPos.y += (Game1.dog.returnSpeedY / yFactor);
                    ballPos.y += (Game1.dog.returnSpeedY / yFactor);

                }
                else if (Game1.dog.dogPos.y >= Game1.dog.origin.y)
                {
                    Game1.dog.dogPos.y -= (Game1.dog.returnSpeedY / yFactor);
                    ballPos.y -= (Game1.dog.returnSpeedY / yFactor);

                }


                if (Game1.dog.dogPos.x >= Game1.dog.origin.x - (Game1.dog.returnSpeedX / xFactor) && Game1.dog.dogPos.x <= Game1.dog.origin.x + (Game1.dog.returnSpeedX / xFactor))
                {
                    Game1.dog.dogPos.x = Game1.dog.origin.x;
                    ballPos.x = Game1.dog.origin.x;

                }
                else if (Game1.dog.dogPos.x >= Game1.dog.origin.x)
                {
                    Game1.dog.dogPos.x -= (Game1.dog.returnSpeedX / xFactor);
                    ballPos.x -= (Game1.dog.returnSpeedX / xFactor);
                }
                else if (Game1.dog.dogPos.x <= Game1.dog.origin.x)
                {
                    Game1.dog.dogPos.x += (Game1.dog.returnSpeedX / xFactor);
                    ballPos.x += (Game1.dog.returnSpeedX / xFactor);
                }

                if (Game1.dog.dogScale >= 1.0f - (Game1.dog.returnSpeedS / sFactor) && Game1.dog.dogScale <= 1.0f + (Game1.dog.returnSpeedS / sFactor))
                {
                    Game1.dog.dogScale = 1.0f;

                }
                else if (Game1.dog.dogScale >= 1.0f)
                {
                    Game1.dog.dogScale -= (Game1.dog.returnSpeedS / sFactor);
                    ballScale -= (Game1.dog.returnSpeedS / sFactor);

                }
                else if (Game1.dog.dogScale <= 1.0f)
                {
                    Game1.dog.dogScale += (Game1.dog.returnSpeedS / sFactor);
                    ballScale += (Game1.dog.returnSpeedS / (sFactor * 2));
                }

            }
            else
            {
                Game1.dog.statEntertainment += 0.4f;
                Game1.dog.myAnimate = Dog.animate.dogSitting;
                restart();
            }


        }

        public void Draw()
        {
            spriteBatch.Draw(ball, ballPos, ballRec, Color.White, ballRot, new Vector2(ball.Width/2, ball.Height/2), ballScale, SpriteEffects.None, ballPosZ);
            spriteBatch.Draw(shadow, shadowLine, ballRec, Color.White, 0.0f, new Vector2(ball.Width / 2, (ball.Height / 2)), shadowScale, SpriteEffects.None, ballPosZ + .05f);
        }
    }
