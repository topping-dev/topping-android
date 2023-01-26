package dev.topping.android;

import android.util.Log;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

@LuaClass(className = "LuaLog")
public class LuaLog implements LuaInterface
{
    /**
     * Logs verbose
     * @param tag
     * @param message
     */
    @LuaFunction(manual = false, methodName = "v", self = LuaLog.class, arguments = { String.class, String.class })
    public static void v(String tag, String message)
    {
        Log.v(tag, message);
    }

    /**
     * Logs debug
     * @param tag
     * @param message
     */
    @LuaFunction(manual = false, methodName = "d", self = LuaLog.class, arguments = { String.class, String.class })
    public static void d(String tag, String message)
    {
        Log.d(tag, message);
    }

    /**
     * Logs info
     * @param tag
     * @param message
     */
    @LuaFunction(manual = false, methodName = "i", self = LuaLog.class, arguments = { String.class, String.class })
    public static void i(String tag, String message)
    {
        Log.i(tag, message);
    }

    /**
     * Logs warn
     * @param tag
     * @param message
     */
    @LuaFunction(manual = false, methodName = "w", self = LuaLog.class, arguments = { String.class, String.class })
    public static void w(String tag, String message)
    {
        Log.w(tag, message);
    }

    /**
     * Logs error
     * @param tag
     * @param message
     */
    @LuaFunction(manual = false, methodName = "e", self = LuaLog.class, arguments = { String.class, String.class })
    public static void e(String tag, String message)
    {
        Log.e(tag, message);
    }

    @Override
    public String GetId()
    {
        return "LuaLog";
    }
}
