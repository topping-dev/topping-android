package dev.topping.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.naef.jnlua.Lua;
import com.naef.jnlua.LuaState;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaGlobalManual;
import dev.topping.android.backend.LuaInterface;

/**
 * General store for storing string and numbers in dictionary
 * Keys must be string value
 */
@LuaClass(className = "LuaStore")
@LuaGlobalManual(name = "STORE")
public class LuaStore implements LuaInterface
{
	private static final String PREFS_NAME = "LUA_STORE_FILE_RUED";
	
	/**
	 * Sets the string value to store
	 * @param key 
	 * @param value
	 */
	public static void setString(String key, String value)
	{
		Context ctx = LuaForm.Companion.getActiveForm().getContext().getContext();
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString(key, value);
	    editor.commit();
	}
	
	/**
	 * Sets the number value to store
	 * @param key
	 * @param value
	 */
	public static void setNumber(String key, double value)
	{
		Context ctx = LuaForm.Companion.getActiveForm().getContext().getContext();
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putFloat(key, (float) value);
	    editor.commit();
	}
	
	/**
	 * Gets value stored at key
	 * @param key
	 * @return
	 */
	public static Object get(String key)
	{
		Context ctx = LuaForm.Companion.getActiveForm().getContext().getContext();
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		try
		{
			return settings.getString(key, null);
		}
		catch (Exception e) 
		{
			return (double) settings.getFloat(key, -1.0f);
		}
	}
	
	/**
	 * Gets string value stored at key
	 * @param key
	 * @return String
	 */
	public static String getString(String key)
	{
		Context ctx = LuaForm.Companion.getActiveForm().getContext().getContext();
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		try
		{
			return settings.getString(key, null);
		}
		catch (Exception e) 
		{
			return null;
		}
	}
	
	/**
	 * Gets number value stored at key
	 * @param key
	 * @return double
	 */
	public static Double getNumber(String key)
	{
		Context ctx = LuaForm.Companion.getActiveForm().getContext().getContext();
		SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
		return (double) settings.getFloat(key, -1);
	}
	
	/**
	 * (Ignore)
	 */
	public static int Lua_Index(LuaState L)
	{
		String key = Lua.lua_tostring(L, 2).toString();
		Object val = LuaStore.get(key);
		if(val == null)
			ToppingEngine.getInstance().pushNIL();
		else if(val instanceof String)
			ToppingEngine.getInstance().pushString((String) val);
		else
			ToppingEngine.getInstance().pushDouble((Double)val);
		return 1;
	}

	/**
	 * (Ignore)
	 */
	public static int Lua_NewIndex(LuaState L)
	{
		String key = Lua.lua_tostring(L, 2).toString();
		if(Lua.lua_isstring(L, 3) != 0)
		{
			String val = Lua.lua_tostring(L, 3).toString();
			LuaStore.setString(key, val);
		}
		else if(Lua.lua_isnumber(L, 3) != 0)
		{
			double val = Lua.lua_tonumber(L, 3);
			LuaStore.setNumber(key, val);
		}
		return 1;
	}

	/**
	 * (Ignore)
	 */
	public static int Lua_GC(LuaState L)
	{
		/*int ptr = 0;
	    Object obj = check(L, 1);
	    if(obj == null)
		    return 0;
	    Lua.lua_getfield(L, Lua.LUA_REGISTRYINDEX, "DO NOT TRASH");
	    if(Lua.lua_istable(L, -1))
	    {
	    	String name = RemoveChar(obj.getClass().getName(), '.');
            Lua.lua_getfield(L, -1, name);
		    if(Lua.lua_isnil(L,-1))
		    {
			    obj = null;
		    }
	    }*/
	    //Lua.lua_pop(L, 3);
	    return 0;
	}

	/**
	 * (Ignore)
	 */
	public static int Lua_ToString(LuaState L)
	{
		Lua.lua_pushstring(L, "STORE");
		return 0;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId() 
	{
		return "LuaStore";
	}
}
