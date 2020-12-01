package dev.topping.android;

import android.graphics.Color;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.backend.LuaStaticVariable;

/**
 * Color
 */
@LuaClass(className = "LuaColor")
public class LuaColor implements LuaInterface
{
	private int colorValue;

	/**
	 * black color
	 */
	@LuaStaticVariable
	public static final int BLACK = 0xff000000;
	/**
	 * blue color
	 */
	@LuaStaticVariable
	public static final int BLUE = 0xff0000ff;
	/**
	 * cyan color
	 */
	@LuaStaticVariable
	public static final int CYAN = 0xff00ffff;
	/**
	 * dark gray color
	 */
	@LuaStaticVariable
	public static final int DKGRAY = 0xff444444;
	/**
	 * gray color
	 */
	@LuaStaticVariable
	public static final int GRAY = 0xff888888;
	/**
	 * green color
	 */
	@LuaStaticVariable
	public static final int GREEN = 0xff00ff00;
	/**
	 * light gray color
	 */
	@LuaStaticVariable
	public static final int LTGRAY = 0xffcccccc;
	/**
	 * magenta color
	 */
	@LuaStaticVariable
	public static final int MAGENTA = 0xffff00ff;
	/**
	 * red color
	 */
	@LuaStaticVariable
	public static final int RED = 0xffff0000;
	/**
	 * transparent color
	 */
	@LuaStaticVariable
	public static final int TRANSPARENT = 0x00000000;
	/**
	 * white color
	 */
	@LuaStaticVariable
	public static final int WHITE = 0xffffffff;
	/**
	 * yellow color
	 */
	@LuaStaticVariable
	public static final int YELLOW = 0xffffff00;

	/**
	 * Returns LuaColor from string value.
	 * Example #ffffffff or #ffffff
	 * @param colorStr
	 * @return LuaColor
	 */
	@LuaFunction(manual = false, methodName = "FromString", self = LuaColor.class, arguments = { String.class })
	public static LuaColor FromString(String colorStr)
	{
		LuaColor color = new LuaColor();
		color.colorValue = Color.parseColor(colorStr);
		return color;
	}

	/**
	 * Returns LuaColor from argb.
	 * @param alpha
	 * @param red
	 * @param green
	 * @param blue
	 * @return LuaColor
	 */
	@LuaFunction(manual = false, methodName = "CreateFromARGB", self = LuaColor.class, arguments = { Integer.class, Integer.class, Integer.class, Integer.class })
	public static LuaColor CreateFromARGB(int alpha, int red, int green, int blue)
	{
		LuaColor color = new LuaColor();
		color.colorValue = Color.argb(alpha, red, green, blue);
		return color;
	}

	/**
	 * Returns LuaColor from rgb.
	 * @param red
	 * @param green
	 * @param blue
	 * @return LuaColor
	 */
	@LuaFunction(manual = false, methodName = "CreateFromRGB", self = LuaColor.class, arguments = { Integer.class, Integer.class, Integer.class })
	public static LuaColor CreateFromRGB(int red, int green, int blue)
	{
		LuaColor color = new LuaColor();
		color.colorValue = Color.rgb(red, green, blue);
		return color;
	}

	/**
	 * Returns the integer color value
	 * @return int
	 */
	@LuaFunction(manual = false, methodName = "GetColorValue")
	public int GetColorValue()
	{
		return colorValue;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaColor";
	}
}
