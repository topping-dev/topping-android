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
    @LuaFunction(manual = false, methodName = "V", self = LuaLog.class, arguments = { String.class, String.class })
    public static void V(String tag, String message)
    {
        Log.v(tag, message);
    }

    /**
     * Logs debug
     * @param tag
     * @param message
     */
    @LuaFunction(manual = false, methodName = "D", self = LuaLog.class, arguments = { String.class, String.class })
    public static void D(String tag, String message)
    {
        Log.d(tag, message);
    }

    /**
     * Logs info
     * @param tag
     * @param message
     */
    @LuaFunction(manual = false, methodName = "I", self = LuaLog.class, arguments = { String.class, String.class })
    public static void I(String tag, String message)
    {
        Log.i(tag, message);
    }

    /**
     * Logs warn
     * @param tag
     * @param message
     */
    @LuaFunction(manual = false, methodName = "W", self = LuaLog.class, arguments = { String.class, String.class })
    public static void W(String tag, String message)
    {
        Log.w(tag, message);
    }

    /**
     * Logs error
     * @param tag
     * @param message
     */
    @LuaFunction(manual = false, methodName = "E", self = LuaLog.class, arguments = { String.class, String.class })
    public static void E(String tag, String message)
    {
        Log.e(tag, message);
    }

    @Override
    public String GetId()
    {
        return "LuaLog";
    }
}
