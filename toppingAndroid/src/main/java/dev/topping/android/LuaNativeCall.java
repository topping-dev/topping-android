package dev.topping.android;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaHelper;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.osspecific.ClassCache;

/**
 * Call native functions from lua
 */
@LuaClass(className = "LuaNativeCall")
public class LuaNativeCall implements LuaInterface
{
	/**
	 * Call native function on underlying system
	 * @param obj
	 * @param func
	 * @param params
	 * @return LuaObjectStore
	 */
	@LuaFunction(manual = false, methodName = "call", arguments = { Object.class, String.class, ArrayList.class })
	public static LuaObjectStore call(Object obj, String func, ArrayList<Object> params)
	{
		Method[] methods = obj.getClass().getMethods();
		LuaObjectStore los = new LuaObjectStore();
		for(Method m : methods)
		{
			if(m.getName().compareTo(func) == 0)
			{
				try
				{
					los.obj = m.invoke(obj, params.toArray());
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return los;
	}

	/**
	 * Call native function on underlying system
	 * @param cls
	 * @param func
	 * @param params
	 * @return LuaObjectStore
	 */
	@LuaFunction(manual = false, methodName = "callClass", arguments = { String.class, String.class, ArrayList.class })
	public static LuaObjectStore callClass(String cls, String func, ArrayList<Object> params)
	{
		Class self = null;
		try
		{
			self = ClassCache.forName(cls);
		}
		catch (ClassNotFoundException e)
		{
			return null;
		}
		Method[] methods = self.getMethods();
		LuaObjectStore los = new LuaObjectStore();
		for(Method m : methods)
		{
			if(m.getName().compareTo(func) == 0)
			{
				try
				{
					los.obj = m.invoke(null, params.toArray());
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return los;
	}
	
	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaNativeCall";
	}

}
