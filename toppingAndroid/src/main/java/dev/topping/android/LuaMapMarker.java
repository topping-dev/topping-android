package dev.topping.android;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Class that is used to create markers on map.
 */
@LuaClass(className = "LuaMapMarker")
public class LuaMapMarker implements LuaInterface
{
	private Marker marker;
	
	/**
	 * (Ignore) 
	 */	
	public LuaMapMarker(Marker marker)
	{
		this.marker = marker;
	}
	
	/**
	 * Set marker draggable
	 * @param draggable
	 */
	@LuaFunction(manual = false, methodName="SetDraggable", arguments = { Boolean.class })
	public void SetDraggable(boolean draggable)
	{
		marker.setDraggable(draggable);
	}
	
	/**
	 * Set marker position 
	 * @param point
	 */	
	@LuaFunction(manual = false, methodName = "SetPosition", arguments = { LuaPoint.class })
	public void SetPosition(LuaPoint point)
	{
		marker.setPosition(new LatLng(point.x, point.y));
	}
	
	/**
	 * Set marker position
	 * @param x
	 * @param y
	 */	
	@LuaFunction(manual = false, methodName = "SetPositionEx", arguments = { Double.class, Double.class })
	public void SetPositionEx(double x, double y)
	{
		marker.setPosition(new LatLng(x, y));
	}
	
	/**
	 * Set marker snippet 
	 * @param value
	 */	
	@LuaFunction(manual = false, methodName = "SetSnippet", arguments = { String.class })
	public void SetSnippet(String value)
	{
		marker.setSnippet(value);
	}
	
	/**
	 * Set marker title
	 * @param value 
	 */	
	@LuaFunction(manual = false, methodName = "SetTitle", arguments = { String.class })
	public void SetTitle(String value)
	{
		marker.setTitle(value);
	}
	
	/**
	 * Set marker visibility 
	 * @param value
	 */	
	@LuaFunction(manual = false, methodName = "SetVisible", arguments = { Boolean.class })
	public void SetVisible(boolean value)
	{
		marker.setVisible(value);
	}

	/**
	 * (Ignore) 
	 */	
	@Override
	public String GetId()
	{
		return "LuaMapMarker";
	}

}
