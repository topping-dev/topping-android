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
    @LuaFunction(manual = false, methodName = "runOnUIThread", arguments = { LuaTranslator.class }, self = LuaThread.class)
    public static void runOnUIThread(LuaTranslator lt)
    {
        if(Looper.myLooper() == Looper.getMainLooper())
            lt.callIn();
        else
        {
            new Handler(Looper.getMainLooper()).post(new Runnable()
            {
                @Override
                public void run()
                {
                    lt.callIn();
                }
            });
        }
    }

    /**
     * Run function on background thread
     * @param lt +fun(thread: LuaThread):void
     */
    @LuaFunction(manual = false, methodName = "runOnBackground", arguments = { LuaTranslator.class }, self = LuaThread.class)
    public static void runOnBackground(LuaTranslator lt)
    {
        LuaThread thread = new LuaThread();
        thread.lt = lt;
        thread.start();
    }

    /**
     * Create new thread
     * @param lt +fun(thread: LuaThread):void
     * @return LuaThread
     */
    @LuaFunction(manual = false, methodName = "create", arguments = { LuaTranslator.class }, self = LuaThread.class)
    public static LuaThread create(LuaTranslator lt)
    {
        LuaThread thread = new LuaThread();
        thread.lt = lt;
        return thread;
    }

    /**
     * Run the thread
     */
    @LuaFunction(manual = false, methodName = "start")
    public void start()
    {
        thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                lt.callInSelf(LuaThread.this);
            }
        });
        thread.start();
    }

    /**
     * Interrupt thread
     */
    @LuaFunction(manual = false, methodName = "interrupt")
    public void interrupt()
    {
        thread.interrupt();
    }

    /**
     * Sleep thread
     * @param milliseconds
     */
    @LuaFunction(manual = false, methodName = "sleep", arguments = { Long.class })
    public void sleep(long milliseconds)
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
