package dev.topping.android;

import android.os.Handler;
import android.os.Looper;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

/**
 * Threading implementation
 */
@LuaClass(className = "LuaThread")
public class LuaThread implements LuaInterface
{
    private LuaTranslator lt;
    private Thread thread;

    /**
     * Run function on ui thread
     * @param lt +fun():void
     */
    @LuaFunction(manual = false, methodName = "RunOnUIThread", arguments = { LuaTranslator.class }, self = LuaThread.class)
    public static void RunOnUIThread(LuaTranslator lt)
    {
        if(Looper.myLooper() == Looper.getMainLooper())
            lt.CallIn();
        else
        {
            new Handler(Looper.getMainLooper()).post(new Runnable()
            {
                @Override
                public void run()
                {
                    lt.CallIn();
                }
            });
        }
    }

    /**
     * Run function on background thread
     * @param lt +fun(thread: LuaThread):void
     */
    @LuaFunction(manual = false, methodName = "RunOnBackground", arguments = { LuaTranslator.class }, self = LuaThread.class)
    public static void RunOnBackground(LuaTranslator lt)
    {
        LuaThread thread = new LuaThread();
        thread.lt = lt;
        thread.Run();
    }

    /**
     * Create new thread
     * @param lt +fun(thread: LuaThread):void
     * @return LuaThread
     */
    @LuaFunction(manual = false, methodName = "New", arguments = { LuaTranslator.class }, self = LuaThread.class)
    public static LuaThread New(LuaTranslator lt)
    {
        LuaThread thread = new LuaThread();
        thread.lt = lt;
        return thread;
    }

    /**
     * Run the thread
     */
    @LuaFunction(manual = false, methodName = "Run")
    public void Run()
    {
        thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                lt.CallInSelf(LuaThread.this);
            }
        });
        thread.start();
    }

    /**
     * Wait thread
     * @param milliseconds
     */
    @LuaFunction(manual = false, methodName = "Wait", arguments = { Long.class })
    public void Wait(long milliseconds)
    {
        try
        {
            thread.wait(milliseconds);
        }
        catch (InterruptedException e)
        {

        }
    }

    /**
     * Notify thread
     */
    @LuaFunction(manual = false, methodName = "Notify")
    public void Notify()
    {
        thread.notify();
    }

    /**
     * Interrupt thread
     */
    @LuaFunction(manual = false, methodName = "Interrupt")
    public void Interrupt()
    {
        thread.interrupt();
    }

    /**
     * Sleep thread
     * @param milliseconds
     */
    @LuaFunction(manual = false, methodName = "Sleep", arguments = { Long.class })
    public void Sleep(long milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException e)
        {

        }
    }

    /**
     * (Ignore)
     */
    @Override
    public String GetId()
    {
        return "LuaThread";
    }
}
