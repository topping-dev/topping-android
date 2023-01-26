package dev.topping.android.luagui;

import android.content.Context;

import androidx.appcompat.app.CustomLayoutInflater;

import dev.topping.android.LuaForm;
import dev.topping.android.LuaFormIntent;
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
	@LuaFunction(manual = false, methodName = "createLuaContext", self = LuaContext.class, arguments = { Object.class })
	public static LuaContext createLuaContext(Object context)
	{
		LuaContext lc = new LuaContext();
		lc.setContext(context);
		return lc;
	}

	/**
	 * Gets binded form.
	 * @return LuaForm
	 */
	@LuaFunction(manual = false, methodName = "getForm")
	public LuaForm getForm() {
		if(context instanceof LuaForm)
			return (LuaForm) context;
		return null;
	}

	/**
	 * Starts form from created form intent
	 * @see dev.topping.android.LuaForm Create
	 */
	@LuaFunction(manual = false, methodName = "startForm", arguments = { LuaFormIntent.class })
	public void startForm(LuaFormIntent formIntent) {
		context.startActivity(formIntent.getIntent());
	}

	/**
	 * (Ignore)
	 */
	public Context getContext() { return context; }
	
	/**
	 * (Ignore)
	 */
	public void setContext(Object val) {
		context = (Context) val;
	}

	/**
	 * (Ignore)
	 */
	public CustomLayoutInflater getLayoutInflater() {
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
