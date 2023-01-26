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
@LuaClass(className = "LuaJSONArray")
public class LuaJSONArray implements LuaInterface
{
	public JSONArray jsa;

	/**
	 * Get array count.
	 * @return int count of array.
	 */
	@LuaFunction(manual = false, methodName = "count", arguments = { })
	public int count()
	{
		return jsa.length();
	}
	
	/**
	 * Get object value at index.
	 * @param index value.
	 * @return LuaJSONObject
	 */
	@LuaFunction(manual = false, methodName = "getJSONObject", arguments = { Integer.class })
	public LuaJSONObject getJSONObject(Integer index)
	{
		LuaJSONObject lso = new LuaJSONObject();
		try
		{
			lso.jso = jsa.getJSONObject(index);
		}
		catch(JSONException e)
		{
			lso.jso = new JSONObject();
		}
		return lso;
	}
	
	/**
	 * Get array value at index.
	 * @param index value.
	 * @return LuaJSONArray
	 */
	@LuaFunction(manual = false, methodName = "getJSONArray", arguments = { Integer.class })
	public LuaJSONArray getJSONArray(Integer index)
	{
		LuaJSONArray lsa = new LuaJSONArray();
		try
		{
			lsa.jsa = jsa.getJSONArray(index);
		}
		catch(JSONException e)
		{
			lsa.jsa = new JSONArray();
		}
		return lsa;
	}
	
	/**
	 * Get string value at name.
	 * @param index value.
	 * @return String value.
	 */
	@LuaFunction(manual = false, methodName = "getString", arguments = { Integer.class })
	public String getString(Integer index)
	{
		try
		{
			return jsa.getString(index);
		}
		catch(JSONException e)
		{
			return "error" + e.getMessage() != null ? e.getMessage() : "";
		}
	}
	
	/**
	 * Get int value at name.
	 * @param index value.
	 * @return int value.
	 */
	@LuaFunction(manual = false, methodName = "getInt", arguments = { Integer.class })
	public Integer getInt(Integer index)
	{
		try
		{
			return jsa.getInt(index);
		}
		catch(JSONException e)
		{
			return -Integer.MAX_VALUE;
		}
	}
	
	/**
	 * Get double value at name.
	 * @param index value.
	 * @return double value.
	 */
	@LuaFunction(manual = false, methodName = "getDouble", arguments = { Integer.class })
	public Double getDouble(Integer index)
	{
		try
		{
			return jsa.getDouble(index);
		}
		catch(JSONException e)
		{
			return (double)-Integer.MAX_VALUE;
		}
	}
	
	/**
	 * Get float value at name.
	 * @param index value.
	 * @return float value.
	 */
	@LuaFunction(manual = false, methodName = "getFloat", arguments = { Integer.class })
	public Float getFloat(Integer index)
	{
		try
		{
			return (float)jsa.getDouble(index);
		}
		catch(JSONException e)
		{
			return (float)-Integer.MAX_VALUE;
		}
	}
	
	/**
	 * Get boolean value at name.
	 * @param index value.
	 * @return boolean value.
	 */
	@LuaFunction(manual = false, methodName = "getBool", arguments = { Integer.class })
	public Boolean getBool(Integer index)
	{
		try
		{
			return jsa.getBoolean(index);
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
		return "LuaJSONArray";
	}

}
