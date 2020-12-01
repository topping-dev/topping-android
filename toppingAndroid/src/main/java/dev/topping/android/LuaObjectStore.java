package dev.topping.android;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;

/**
 * Object store to store c objects sent and received from lua engine.
 */
@LuaClass(className = "LuaObjectStore")
public class LuaObjectStore implements LuaInterface
{
	/**
	 * Object that sent and received.
	 */
	public Object obj;

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaObjectStore";
	}
}
