package dev.topping.android;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import com.google.android.gms.maps.model.Polygon;

/**
 * Class that is used to create polygons on map.
 */
@LuaClass(className = "LuaMapPolygon")
public class LuaMapPolygon implements LuaInterface
{
	private Polygon polygon;

	/**
	 * (Ignore) 
	 */	
	public LuaMapPolygon(Polygon polygon)
	{
		this.polygon = polygon;
	}
	
	/**
	 * Sets the fill color
	 * @param color
	 */
	@LuaFunction(manual = false, methodName = "SetFillColor", arguments = { LuaColor.class })
	public void SetFillColor(LuaColor color)
	{
		polygon.setFillColor(color.GetColorValue());
	}
	
	/**
	 * Sets the stroke color
	 * @param color
	 */
	@LuaFunction(manual = false, methodName = "SetStrokeColor", arguments = { LuaColor.class })
	public void SetStrokeColor(LuaColor color)
	{
		polygon.setStrokeColor(color.GetColorValue());
	}
	
	/**
	 * Sets the stroke width
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "SetStrokeWidth", arguments = { Float.class })
	public void SetStrokeWidth(float value)
	{
		polygon.setStrokeWidth(value);
	}
	
	/**
	 * Sets the visibility
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "SetVisible", arguments = { Boolean.class })
	public void SetVisible(boolean value)
	{
		polygon.setVisible(value);
	}
	
	/**
	 * Sets the z-index
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "SetZIndex", arguments = { Float.class })
	public void SetZIndex(float value)
	{
		polygon.setZIndex(value);
	}

	/**
	 * (Ignore) 
	 */	
	@Override
	public String GetId()
	{
		return "LuaMapPolygon";
	}

}
