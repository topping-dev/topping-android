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
	@LuaFunction(manual = false, methodName = "create", self = LuaRect.class)
	public static LuaRect create()
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
	@LuaFunction(manual = false, methodName = "createPar", self = LuaRect.class, arguments = { Float.class, Float.class ,Float.class, Float.class })
	public static LuaRect createPar(Float left, Float top, Float right, Float bottom)
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
	@LuaFunction(manual = false, methodName = "set", arguments = { Float.class, Float.class ,Float.class, Float.class })
	public void set(Float left, Float top, Float right, Float bottom)
	{
		super.set(left, top, right, bottom);
	}
	
	/**
	 * Gets Left value
	 * @return float
	 */
	@LuaFunction(manual = false, methodName = "getLeft")
	public Float getLeft() { return left; }
	
	/**
	 * Gets Right value
	 * @return float
	 */
	@LuaFunction(manual = false, methodName = "getRight")
	public Float getRight() { return right; }
	
	/**
	 * Gets Top value
	 * @return float
	 */
	@LuaFunction(manual = false, methodName = "getTop")
	public Float getTop() { return top; }
	
	/**
	 * Gets Bottom value
	 * @return float
	 */
	@LuaFunction(manual = false, methodName = "getBottom")
	public Float getBottom() { return bottom; }

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaRect";
	}
}
