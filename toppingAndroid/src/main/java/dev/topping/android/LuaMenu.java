package dev.topping.android;

import android.graphics.drawable.Drawable;

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
	LuaRef idVal;
	String title;
	LuaRef iconRes;
	Drawable iconDrawable;

	/**
	 * (Ignore)
	 */
	public LuaMenu(LuaContext context, LuaRef idVal) {
		this.context = context;
		this.idVal = idVal;
	}

	/**
	 * Create menu item
	 * @param context
	 * @param idVal
	 * @return
	 */
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class, LuaRef.class }, self = LuaMenu.class)
	public static LuaMenu create(LuaContext context, LuaRef idVal) {
		return new LuaMenu(context, idVal);
	}

	/**
	 * Get item id
	 * @return id
	 */
	@LuaFunction(manual = false, methodName = "getItemId", arguments = { String.class })
	public LuaRef getItemId() {
		return idVal;
	}

	/**
	 * Set title
	 * @param text
	 */
	@LuaFunction(manual = false, methodName = "setTitle", arguments = { String.class })
	public void setTitle(String text) {
		title = text;
	}

	/**
	 * Set title
	 * @param text LuaRef
	 */
	@LuaFunction(manual = false, methodName = "setTitleRef", arguments = { LuaRef.class })
	public void setTitleRef(LuaRef text) {
		title = context.getContext().getResources().getResourceEntryName(text.getRef());
	}

	/**
	 * Set Icon
	 * @param icon
	 */
	@LuaFunction(manual = false, methodName = "setIcon", arguments = { LuaRef.class })
	public void setIcon(LuaRef icon) {
		iconRes = icon;
	}

	/**
	 * (Ignore)
	 */
	public void setIcon(Drawable icon) {
		iconDrawable = icon;
	}

	/**
	 * (Ignore)
	 */
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
