package dev.topping.android;

import com.google.android.gms.maps.model.Polyline;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

/**
 * Class that is used to create polylines on map.
 */
@LuaClass(className = "LuaMapPolyline")
public class LuaMapPolyline implements LuaInterface
{
	private Polyline polyline;

	/**
	 * (Ignore) 
	 */	
	public LuaMapPolyline(Polyline polyline)
	{
		this.polyline = polyline;
	}
	
	/**
	 * Sets the stroke color
	 * @param color
	 */
	@LuaFunction(manual = false, methodName = "SetColor", arguments = { LuaColor.class })
	public void SetColor(LuaColor color)
	{
		polyline.setColor(color.GetColorValue());
	}
	
	/**
	 * Sets the stroke width
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "SetWidth", arguments = { Float.class })
	public void SetWidth(float value)
	{
		polyline.setWidth(value);
	}
	
	/**
	 * Sets the visibility
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "SetVisible", arguments = { Boolean.class })
	public void SetVisible(boolean value)
	{
		polyline.setVisible(value);
	}
	
	/**
	 * Sets the z-index
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "SetZIndex", arguments = { Float.class })
	public void SetZIndex(float value)
	{
		polyline.setZIndex(value);
	}

	/**
	 * (Ignore) 
	 */	
	@Override
	public String GetId()
	{
		return "LuaMapPolyline";
	}

}
