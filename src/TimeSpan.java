import org.andengine.engine.handler.IUpdateHandler;


public class TimeSpan implements IUpdateHandler {
	
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
	
	@Override
	public void onUpdate(float pSecondsElapsed)
	{
		this.mSecondsElapsed += pSecondsElapsed;
		if(this.mSecondsElapsed >= this.mInterval)
		{
			if(!this.mPause)
			{
				this.mSecondsElapsed -= this.mInterval;
				onTick();
			}
		}
	}
	
	@Override
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
