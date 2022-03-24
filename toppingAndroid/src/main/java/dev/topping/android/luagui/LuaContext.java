package dev.topping.android.luagui;

import android.content.Context;

import androidx.appcompat.app.CustomLayoutInflater;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

@LuaClass(className = "LuaContext")
public class LuaContext implements LuaInterface
{
	private Context context;
	private final CustomLayoutInflater layoutInflater = new CustomLayoutInflater();
	
	/**
	 * Creates LuaContext Object From Lua.
	 * @return LuaContext
	 */
	@LuaFunction(manual = false, methodName = "CreateLuaContext", self = LuaContext.class, arguments = { Object.class })
	public static LuaContext CreateLuaContext(Object context)
	{
		LuaContext lc = new LuaContext();
		lc.SetContext(context);
		return lc;
	}

	/**
	 * (Ignore)
	 */
	public Context GetContext() { return context; }
	
	/**
	 * (Ignore)
	 */
	public void SetContext(Object val) {
		context = (Context) val;
	}

	/**
	 * (Ignore)
	 */
	public CustomLayoutInflater GetLayoutInflater() {
		return layoutInflater;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaContext";
	}	
}
