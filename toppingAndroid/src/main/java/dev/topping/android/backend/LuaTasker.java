package dev.topping.android.backend;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class LuaTasker<T> implements Runnable
{
	public static Object lock = new Object();
	private int CURRENT_COUNT = 0;
	public int MAX_THREAD_COUNT = 16;
	public int THREAD_SLEEP_TIME = 10;
	private ConcurrentLinkedQueue<T> valsToDl = new ConcurrentLinkedQueue<T>();
	private boolean exit;
	
	private void decrementCounter()
	{
		CURRENT_COUNT--;
	}

	private boolean incrementCounter()
	{
		if(CURRENT_COUNT + 1 < MAX_THREAD_COUNT)
		{
			CURRENT_COUNT++;
			return true;
		}
		return false;
	}
	
	public void addToQueue(T val)
	{
		synchronized (lock)
		{
			valsToDl.add(val);
		}
	}
	
	public void removeFromQueue(T val)
	{
		synchronized (lock)
		{
			valsToDl.remove(val);
		}
	}
	
	public boolean hasJob()
	{
		synchronized (lock)
		{
			return valsToDl.size() > 0 || CURRENT_COUNT > 0;
		}
	}
	
	public void exit()
	{
		exit = true;
	}
	
	@Override
	public void run()
	{
		while(!exit)
		{
			T valToDoJob = null;
			boolean canStart = false;
			synchronized (lock)
			{
				if(valsToDl.size() > 0)
					canStart = incrementCounter();
				if(canStart)
					valToDoJob = valsToDl.poll();				
			}
			if(valToDoJob == null)
			{
				try
				{
					Thread.sleep(THREAD_SLEEP_TIME);
				}
				catch(InterruptedException e)
				{
				}
				continue;
			}
			else
			{				
				if(canStart)
				{
					final T fValToDoJob = valToDoJob;
					Thread taskerWorker = new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							doJob(fValToDoJob);
							synchronized (lock)
							{
								decrementCounter();
							}
						}
					}, "Tasker Worker");
					taskerWorker.start();
				}
				try
				{
					Thread.sleep(THREAD_SLEEP_TIME);
				}
				catch(InterruptedException e)
				{
				}
			}
		}
	}
	
	public abstract void doJob(T val);
}
