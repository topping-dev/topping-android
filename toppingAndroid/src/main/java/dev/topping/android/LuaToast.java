package dev.topping.android;

import android.widget.Toast;

import com.naef.jnlua.Lua;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.backend.LuaStaticVariable;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;

/**
 * Create toast messages with this class
 */
@LuaClass(className = "LuaToast")
public class LuaToast implements LuaInterface
{
	/**
	 * short length duration
	 */
	@LuaStaticVariable
	public static final int TOAST_SHORT = Toast.LENGTH_SHORT;
	/**
	 * long length duration
	 */
	@LuaStaticVariable
	public static final int TOAST_LONG = Toast.LENGTH_LONG;

	/**
	 * Show the toast
	 * @param context
	 * @param text text to show
	 * @param duration +"LuaToast.TOAST_SHORT" | "LuaToast.TOAST_LONG" | number
	 */
	@LuaFunction(manual = false, methodName = "Show", arguments = { LuaContext.class, LuaRef.class, Integer.class }, self = LuaToast.class)
	public static void Show(LuaContext context, LuaRef text, Integer duration)
	{
		Toast.makeText(context.GetContext(), context.GetContext().getResources().getResourceEntryName(text.getRef()), duration).show();
	}

	/**
	 * Show the toast
	 * @param context
	 * @param text text to show
	 * @param duration +"LuaToast.TOAST_SHORT" | "LuaToast.TOAST_LONG" | number
	 */
	@LuaFunction(manual = false, methodName = "ShowInternal", arguments = { LuaContext.class, String.class, Integer.class }, self = LuaToast.class)
	public static void ShowInternal(LuaContext context, String text, Integer duration)
	{
		Toast.makeText(context.GetContext(), text, duration).show();
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaToast";
	}
}
