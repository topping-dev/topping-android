package dev.topping.android;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

/**
 * Class that is used to create circles on map.
 */
@LuaClass(className = "LuaMapCircle")
public class LuaMapCircle implements LuaInterface
{
	Circle circle;
	
	/**
	 * (Ignore) 
	 */	
	public LuaMapCircle(Circle circle)
	{
		this.circle = circle;
	}
	
	/**
	 * Set circle center
	 * @param center
	 */
	@LuaFunction(manual = false, methodName = "SetCenter", arguments = { LuaPoint.class })
	public void SetCenter(LuaPoint center)
	{
		circle.setCenter(new LatLng(center.x, center.y));
	}
	
	/**
	 * Set circle center
	 * @param x
	 * @param y
	 */
	@LuaFunction(manual = false, methodName = "SetCenterEx", arguments = { Double.class, Double.class })
	public void SetCenterEx(double x, double y)
	{
		circle.setCenter(new LatLng(x, y));
	}
	
	/**
	 * Set circle radius
	 * @param radius
	 */
	@LuaFunction(manual = false, methodName = "SetRadius", arguments = { Double.class })
	public void SetRadius(double radius)
	{
		circle.setRadius(radius);
	}
	
	/**
	 * Set circle stroke color
	 * @param color
	 */
	@LuaFunction(manual = false, methodName = "SetStrokeColor", arguments = { LuaColor.class })
	public void SetStrokeColor(LuaColor color)
	{
		circle.setStrokeColor(color.GetColorValue());
	}
	
	/**
	 * Set circle stroke color with integer
	 * @param color
	 */
	@LuaFunction(manual = false, methodName = "SetStrokeColorEx", arguments = { Integer.class })
	public void SetStrokeColorEx(int color)
	{
		circle.setStrokeColor(color);
	}
	
	/**
	 * Set circle stroke width
	 * @param width
	 */
	@LuaFunction(manual = false, methodName = "SetStrokeWidth", arguments = { Double.class })
	public void SetStrokeWidth(double width)
	{
		circle.setStrokeWidth((float)width);
	}
	
	/**
	 * Set circle fill color
	 * @param color
	 */
	@LuaFunction(manual = false, methodName = "SetFillColor", arguments = { LuaColor.class })
	public void SetFillColor(LuaColor color)
	{
		circle.setFillColor(color.GetColorValue());
	}
	
	/**
	 * Set circle fill color with integer
	 * @param color
	 */
	@LuaFunction(manual = false, methodName = "SetFillColorEx", arguments = { Integer.class})
	public void SetFillColorEx(int color)
	{
		circle.setFillColor(color);
	}
	
	/**
	 * Set z-index of circle
	 * @param index
	 */
	@LuaFunction(manual = false, methodName = "SetZIndex", arguments = { Double.class })
	public void SetZIndex(double index)
	{
		circle.setZIndex((float) index);
	}

	/**
	 * (Ignore) 
	 */	
	@Override
	public String GetId()
	{
		return "LuaMapCircle";
	}
}
