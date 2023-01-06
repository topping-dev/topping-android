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

	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LuaMenu.class)
	public static LuaMenu Create(LuaContext context) {
		return new LuaMenu(context);
	}

	@LuaFunction(manual = false, methodName = "SetTitle", arguments = { String.class })
	public void SetTitle(String text) {
		title = text;
	}

	@LuaFunction(manual = false, methodName = "SetTitleRef", arguments = { LuaRef.class })
	public void SetTitleRef(LuaRef text) {
		title = context.GetContext().getResources().getResourceEntryName(text.getRef());
	}

	@LuaFunction(manual = false, methodName = "SetIcon", arguments = { LuaRef.class })
	public void SetIcon(LuaRef icon) {
		iconRes = icon;
	}

	public void SetIntent(LuaTranslator lt) {

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
