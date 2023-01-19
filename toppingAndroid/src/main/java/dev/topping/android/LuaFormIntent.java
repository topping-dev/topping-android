package dev.topping.android;

import android.content.Intent;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

/**
 * Form configuration intent
 */
@LuaClass(className = "LuaFormIntent")
public class LuaFormIntent implements LuaInterface
{
	/**
	 * (Ignore)
	 */
	Intent intent;

	/**
	 * (Ignore)
	 */
	public LuaFormIntent() {

	}

	/**
	 * (Ignore)
	 */
	public LuaFormIntent(Intent intent) {
		this.intent = intent;
		this.intent.putExtra("", "");
	}

	/**
	 * (Ignore)
	 */
	public Intent getIntent() {
		return intent;
	}

	/**
	 * Get package bundle
	 * @return
	 */
	@LuaFunction(methodName = "getBundle")
	public LuaBundle getBundle(){
		return new LuaBundle(intent.getExtras());
	}

	/**
	 * Set flags
	 * @param flags
	 */
	@LuaFunction(methodName = "setFlags", arguments = { Integer.class })
	public void setFlags(int flags) {
		intent.setFlags(flags);
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaFormIntent";
	}
}
