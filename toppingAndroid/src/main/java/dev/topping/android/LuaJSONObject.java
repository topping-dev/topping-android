package dev.topping.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

/**
 * Class that handles JSON Object.
 */
@LuaClass(className = "LuaJSONObject")
public class LuaJSONObject implements LuaInterface
{
	public JSONObject jso;
	/**
	 * Creates LuaJSON from json string.
	 * @param str
	 * return LuaJSONObject
	 */
	@LuaFunction(manual = false, methodName = "createJSOFromString", self = LuaJSONObject.class, arguments = { String.class })
	public static LuaJSONObject createJSOFromString(String str)
	{
		LuaJSONObject lso = new LuaJSONObject();
		try
		{
			lso.jso = new JSONObject(str);
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		return lso;
	}
	
	/**
	 * Get object value at name.
	 * @param name Name value.
	 * @return LuaJSONObject
	 */
	@LuaFunction(manual = false, methodName = "getJSONObject", arguments = { String.class })
	public LuaJSONObject getJSONObject(String name)
	{
		LuaJSONObject lso = new LuaJSONObject();
		try
		{
			lso.jso = jso.getJSONObject(name);
		}
		catch(JSONException e)
		{
			lso.jso = new JSONObject();
		}
		return lso;
	}
	
	/**
	 * Get array value at name.
	 * @param name Name value.
	 * @return LuaJSONArray
	 */
	@LuaFunction(manual = false, methodName = "getJSONArray", arguments = { String.class })
	public LuaJSONArray getJSONArray(String name)
	{
		LuaJSONArray lsa = new LuaJSONArray();
		try
		{
			lsa.jsa = jso.getJSONArray(name);
		}
		catch(JSONException e)
		{
			lsa.jsa = new JSONArray();
		}
		return lsa;
	}
	
	/**
	 * Get string value at name.
	 * @param name Name value.
	 * @return String value.
	 */
	@LuaFunction(manual = false, methodName = "getString", arguments = { String.class })
	public String getString(String name)
	{
		try
		{
			return jso.getString(name);
		}
		catch(JSONException e)
		{
			return "error" + e.getMessage() != null ? e.getMessage() : "";
		}
	}
	
	/**
	 * Get int value at name.
	 * @param name Name value.
	 * @return int value.
	 */
	@LuaFunction(manual = false, methodName = "getInt", arguments = { String.class })
	public Integer getInt(String name)
	{
		try
		{
			return jso.getInt(name);
		}
		catch(JSONException e)
		{
			return -Integer.MAX_VALUE;
		}
	}
	
	/**
	 * Get double value at name.
	 * @param name Name value.
	 * @return double value.
	 */
	@LuaFunction(manual = false, methodName = "getDouble", arguments = { String.class })
	public Double getDouble(String name)
	{
		try
		{
			return jso.getDouble(name);
		}
		catch(JSONException e)
		{
			return (double)-Integer.MAX_VALUE;
		}
	}
	
	/**
	 * Get float value at name.
	 * @param name Name value.
	 * @return float value.
	 */
	@LuaFunction(manual = false, methodName = "getFloat", arguments = { String.class })
	public Float getFloat(String name)
	{
		try
		{
			return (float)jso.getDouble(name);
		}
		catch(JSONException e)
		{
			return (float)-Integer.MAX_VALUE;
		}
	}
	
	/**
	 * Get boolean value at name.
	 * @param name Name value.
	 * @return boolean value.
	 */
	@LuaFunction(manual = false, methodName = "getBool", arguments = { String.class })
	public Boolean getBool(String name)
	{
		try
		{
			return jso.getBoolean(name);
		}
		catch(JSONException e)
		{
			return false;
		}
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaJSONObject";
	}

}
