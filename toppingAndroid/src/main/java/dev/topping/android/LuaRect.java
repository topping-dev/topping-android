package dev.topping.android;

import android.graphics.RectF;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

@LuaClass(className = "LuaRect")
public class LuaRect extends RectF implements LuaInterface
{
	/**
	 * Creates LuaRect
	 * @return LuaRect
	 */
	@LuaFunction(manual = false, methodName = "CreateRect", self = LuaRect.class)
	public static LuaRect CreateRect()
	{
		return new LuaRect();
	}
	
	/**
	 * Create LuaRect with parameters
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return LuaRect
	 */
	@LuaFunction(manual = false, methodName = "CreateRectPar", self = LuaRect.class, arguments = { Float.class, Float.class ,Float.class, Float.class })
	public static LuaRect CreateRectPar(Float left, Float top, Float right, Float bottom)
	{
		LuaRect lr = new LuaRect();
		lr.set(left.floatValue(), top.floatValue(), right.floatValue(), bottom.floatValue());
		return lr;
	}
	
	/**
	 * Sets the parameters of rectangle
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	@LuaFunction(manual = false, methodName = "Set", arguments = { Float.class, Float.class ,Float.class, Float.class })
	public void Set(Float left, Float top, Float right, Float bottom)
	{
		// TODO Auto-generated method stub
		super.set(left.floatValue(), top.floatValue(), right.floatValue(), bottom.floatValue());
	}
	
	/**
	 * Gets Left value
	 * @return float
	 */
	@LuaFunction(manual = false, methodName = "GetLeft")
	public Float GetLeft() { return Float.valueOf(left); }
	
	/**
	 * Gets Right value
	 * @return float
	 */
	@LuaFunction(manual = false, methodName = "GetRight")
	public Float GetRight() { return Float.valueOf(right); }
	
	/**
	 * Gets Top value
	 * @return float
	 */
	@LuaFunction(manual = false, methodName = "GetTop")
	public Float GetTop() { return Float.valueOf(top); }
	
	/**
	 * Gets Bottom value
	 * @return float
	 */
	@LuaFunction(manual = false, methodName = "GetBottom")
	public Float GetBottom() { return Float.valueOf(bottom); }

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaRect";
	}
}
