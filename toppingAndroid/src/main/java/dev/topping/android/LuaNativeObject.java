package dev.topping.android;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;

/**
 * Object to store native objects that are not registered on lua engine.
 */
@LuaClass(className = "LuaNativeObject")
public class LuaNativeObject implements LuaInterface
{
	/**
	 * (Ignore)
	 */
	public LuaNativeObject() {

	}

	/**
	 * (Ignore)
	 */
	public LuaNativeObject(Object obj) {
		this.obj = obj;
	}

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
		return "LuaNativeObject";
	}
}
