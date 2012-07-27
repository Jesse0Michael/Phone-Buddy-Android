package org.phonebuddy;
import org.andengine.engine.handler.IUpdateHandler;

import android.util.Log;


public class TimeSpan {
	
	private float mInterval;
	private float mSecondsElapsed;
	private boolean mPause;
	
	public TimeSpan(float interval, boolean pause)
	{
		this.mInterval = interval;
		this.mPause = pause;
	}
	
	public void setInterval(final float pInterval)
	{
		this.mInterval = pInterval;
	}
	
	
	public void onUpdate(float pSecondsElapsed)
	{
		//Log.d("PhoneBuddy", "" + pSecondsElapsed);
		if(!this.mPause)
		{
			this.mSecondsElapsed += pSecondsElapsed;
			if(this.mSecondsElapsed >= this.mInterval)
			{
			
				this.mSecondsElapsed = 0;
				onTick();
			
			}
		}
	}
	
	public void reset()
	{
		this.mSecondsElapsed = 0;
		
		
	}
	
	public void pause()
	{
		this.mPause = true;
	}
	
	public void start()
	{
		
		if(this.mPause)
		{
			this.mPause = false;
		}
	}
	
	public void onTick()
	{
		
	}
}
