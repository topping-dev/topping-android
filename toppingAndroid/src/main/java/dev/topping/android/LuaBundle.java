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
	 * Get String
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		if(bundle != null) {
			bundle.getString(key);
		}
		return null;
	}

	/**
	 * Get String
	 * @param key
	 * @param def
	 * @return
	 */
	public String getString(String key, String def) {
		if(bundle != null) {
			bundle.getString(key, def);
		}
		return def;
	}

	/**
	 * Put String
	 * @param key
	 * @param value
	 */
	public void putString(String key, String value) {
		if(bundle != null) {
			bundle.putString(key, value);
		}
	}

	/**
	 * Get byte
	 * @param key
	 * @return
	 */
	public byte getByte(String key) {
		if(bundle != null) {
			bundle.getByte(key);
		}
		return 0;
	}

	/**
	 * Get byte
	 * @param key
	 * @param def
	 * @return
	 */
	public byte getByte(String key, byte def) {
		if(bundle != null) {
			bundle.getByte(key, def);
		}
		return def;
	}

	/**
	 * Put byte
	 * @param key
	 * @param value
	 */
	public void putByte(String key, byte value) {
		if(bundle != null) {
			bundle.putByte(key, value);
		}
	}

	/**
	 * Get int
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		if(bundle != null) {
			bundle.getInt(key);
		}
		return 0;
	}

	/**
	 * Get int
	 * @param key
	 * @param def
	 * @return
	 */
	public int getInt(String key, int def) {
		if(bundle != null) {
			bundle.getInt(key, def);
		}
		return def;
	}

	/**
	 * Put int
	 * @param key
	 * @param value
	 */
	public void putInt(String key, int value) {
		if(bundle != null) {
			bundle.putInt(key, value);
		}
	}

	/**
	 * Get long
	 * @param key
	 * @return
	 */
	public long getLong(String key) {
		if(bundle != null) {
			bundle.getInt(key);
		}
		return 0;
	}

	/**
	 * Get long
	 * @param key
	 * @param def
	 * @return
	 */
	public long getLong(String key, long def) {
		if(bundle != null) {
			bundle.getLong(key, def);
		}
		return def;
	}

	/**
	 * Put long
	 * @param key
	 * @param value
	 */
	public void putLong(String key, long value) {
		if(bundle != null) {
			bundle.putLong(key, value);
		}
	}

	/**
	 * Get float
	 * @param key
	 * @return
	 */
	public float getFloat(String key) {
		if(bundle != null) {
			bundle.getFloat(key);
		}
		return 0;
	}

	/**
	 * Get float
	 * @param key
	 * @param def
	 * @return
	 */
	public float getFloat(String key, float def) {
		if(bundle != null) {
			bundle.getFloat(key, def);
		}
		return def;
	}

	/**
	 * Put float
	 * @param key
	 * @param value
	 */
	public void putFloat(String key, float value) {
		if(bundle != null) {
			bundle.putFloat(key, value);
		}
	}

	/**
	 * Get double
	 * @param key
	 * @return
	 */
	public double getDouble(String key) {
		if(bundle != null) {
			bundle.getDouble(key);
		}
		return 0;
	}

	/**
	 * Get double
	 * @param key
	 * @param def
	 * @return
	 */
	public double getDouble(String key, double def) {
		if(bundle != null) {
			bundle.getDouble(key, def);
		}
		return def;
	}

	/**
	 * Put double
	 * @param key
	 * @param value
	 */
	public void putDouble(String key, double value) {
		if(bundle != null) {
			bundle.putDouble(key, value);
		}
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
