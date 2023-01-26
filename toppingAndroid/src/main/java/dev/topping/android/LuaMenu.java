package dev.topping.android;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;

/**
 * Menu Item for menu resources
 */
@LuaClass(className = "LuaMenu")
public class LuaMenu implements LuaInterface
{
	private final LuaContext context;
	int idVal;
	String title;
	LuaRef iconRes;

	public LuaMenu(LuaContext context) {
		this.context = context;
	}

	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LuaMenu.class)
	public static LuaMenu create(LuaContext context) {
		return new LuaMenu(context);
	}

	@LuaFunction(manual = false, methodName = "setTitle", arguments = { String.class })
	public void setTitle(String text) {
		title = text;
	}

	@LuaFunction(manual = false, methodName = "setTitleRef", arguments = { LuaRef.class })
	public void setTitleRef(LuaRef text) {
		title = context.getContext().getResources().getResourceEntryName(text.getRef());
	}

	@LuaFunction(manual = false, methodName = "setIcon", arguments = { LuaRef.class })
	public void setIcon(LuaRef icon) {
		iconRes = icon;
	}

	public void setIntent(LuaTranslator lt) {

	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaMenu";
	}
}
