package dev.topping.android;

import android.os.Bundle;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

/**
 * Lua Bundle
 */
@LuaClass(className = "LuaBundle")
public class LuaBundle implements LuaInterface
{
	public Bundle bundle;

	public LuaBundle(@Nullable Bundle bundle) {
		this.bundle = bundle;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaBundle";
	}

}
