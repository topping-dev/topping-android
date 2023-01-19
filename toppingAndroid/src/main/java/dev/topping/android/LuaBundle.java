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
	@LuaFunction(methodName = "getString", arguments = { String.class })
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
	@LuaFunction(methodName = "getStringDef", arguments = { String.class, String.class })
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
	@LuaFunction(methodName = "putString", arguments = { String.class, String.class })
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
	@LuaFunction(methodName = "getByte", arguments = { String.class })
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
	@LuaFunction(methodName = "getByteDef", arguments = { String.class, Byte.class })
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
	@LuaFunction(methodName = "putByte", arguments = { String.class, Byte.class })
	public void putByte(String key, byte value) {
		if(bundle != null) {
			bundle.putByte(key, value);
		}
	}

	/**
	 * Get char
	 * @param key
	 * @return
	 */
	@LuaFunction(methodName = "getChar", arguments = { String.class })
	public char getChar(String key) {
		if(bundle != null) {
			bundle.getChar(key);
		}
		return 0;
	}

	/**
	 * Get char
	 * @param key
	 * @param def
	 * @return
	 */
	@LuaFunction(methodName = "getCharDef", arguments = { String.class, Character.class })
	public char getChar(String key, char def) {
		if(bundle != null) {
			bundle.getChar(key, def);
		}
		return def;
	}

	/**
	 * Put char
	 * @param key
	 * @param value
	 */
	@LuaFunction(methodName = "putChar", arguments = { String.class, Character.class })
	public void putChar(String key, char value) {
		if(bundle != null) {
			bundle.putChar(key, value);
		}
	}

	/**
	 * Get short
	 * @param key
	 * @return
	 */
	@LuaFunction(methodName = "getShort", arguments = { String.class })
	public short getShort(String key) {
		if(bundle != null) {
			bundle.getShort(key);
		}
		return 0;
	}

	/**
	 * Get short
	 * @param key
	 * @param def
	 * @return
	 */
	@LuaFunction(methodName = "getShortDef", arguments = { String.class, Short.class })
	public short getShort(String key, short def) {
		if(bundle != null) {
			bundle.getShort(key, def);
		}
		return def;
	}

	/**
	 * Put short
	 * @param key
	 * @param value
	 */
	@LuaFunction(methodName = "putShort", arguments = { String.class, Short.class })
	public void putShort(String key, short value) {
		if(bundle != null) {
			bundle.putShort(key, value);
		}
	}

	/**
	 * Get int
	 * @param key
	 * @return
	 */
	@LuaFunction(methodName = "getInt", arguments = { String.class })
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
	@LuaFunction(methodName = "getIntDef", arguments = { String.class, Integer.class })
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
	@LuaFunction(methodName = "putInt", arguments = { String.class, Integer.class })
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
	@LuaFunction(methodName = "getLong", arguments = { String.class })
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
	@LuaFunction(methodName = "getLongDef", arguments = { String.class, Long.class })
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
	@LuaFunction(methodName = "putLong", arguments = { String.class, Long.class })
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
	@LuaFunction(methodName = "getFloat", arguments = { String.class })
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
	@LuaFunction(methodName = "getFloatDef", arguments = { String.class, Float.class })
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
	@LuaFunction(methodName = "putFloat", arguments = { String.class, Float.class })
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
	@LuaFunction(methodName = "getDouble", arguments = { String.class })
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
	@LuaFunction(methodName = "getDoubleDef", arguments = { String.class, Double.class })
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
	@LuaFunction(methodName = "putDouble", arguments = { String.class, Double.class })
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
