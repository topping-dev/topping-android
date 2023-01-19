package dev.topping.android.luagui;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.CustomLayoutInflater;

import dev.topping.android.LuaForm;
import dev.topping.android.LuaFormIntent;
import dev.topping.android.LuaNativeObject;
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
	 * Gets binded form.
	 * @return LuaForm
	 */
	@LuaFunction(manual = false, methodName = "GetForm")
	public LuaForm GetForm() {
		if(context instanceof LuaForm)
			return (LuaForm) context;
		return null;
	}

	/**
	 * Starts form from created form intent
	 * @see dev.topping.android.LuaForm Create
	 */
	@LuaFunction(manual = false, methodName = "StartForm", arguments = { LuaFormIntent.class })
	public void StartForm(LuaFormIntent formIntent) {
		context.startActivity(formIntent.getIntent());
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
