package dev.topping.android;

import android.graphics.PointF;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

/**
 * Class used to store point
 */
@LuaClass(className = "LuaPoint")
public class LuaPoint extends PointF implements LuaInterface
{
	/**
	 * Creates LuaPoint
	 * @return LuaPoint
	 */
	@LuaFunction(manual = false, methodName = "CreatePoint", self = LuaPoint.class)
	public static LuaPoint CreatePoint()
	{
		return new LuaPoint();
	}

	/**
	 * Creates LuaPoint with parameters
	 * @param x
	 * @param y
	 * @return LuaPoint
	 */
	@LuaFunction(manual = false, methodName = "CreatePointPar", self = LuaPoint.class, arguments = { Float.class, Float.class })
	public static LuaPoint CreatePointPar(Float x, Float y)
	{
		LuaPoint lp = new LuaPoint();
		lp.Set(x, y);
		return lp;
	}

	/**
	 * Sets the parameters of point
	 * @param x
	 * @param y
	 */
	@LuaFunction(manual = false, methodName = "Set", arguments = { Float.class, Float.class })
	public void Set(Float x, Float y)
	{
		super.set(x.floatValue(), y.floatValue());
	}

	/**
	 * Gets the x value
	 * @return float
	 */
	@LuaFunction(manual = false, methodName = "GetX")
	public Float GetX() { return Float.valueOf(x); }

	/**
	 * Gets the y value
	 * @return float
	 */
	@LuaFunction(manual = false, methodName = "GetY")
	public Float GetY() { return Float.valueOf(y); }

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaPoint";
	}

}
