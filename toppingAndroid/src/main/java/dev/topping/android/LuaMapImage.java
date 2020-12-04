package dev.topping.android;

import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

/**
 * Class that is used to create images on map.
 */
@LuaClass(className = "LuaMapImage")
public class LuaMapImage implements LuaInterface
{
	GroundOverlay image;
	
	/**
	 * (Ignore)
	 */
	public LuaMapImage(GroundOverlay image)
	{
		this.image = image;
	}
	
	/**
	 * Sets the bearing of image
	 * @param bearing
	 */
	@LuaFunction(manual = false, methodName = "SetBearing", arguments = { Float.class })
	public void SetBearing(float bearing)
	{
		image.setBearing(bearing);
	}
	
	/**
	 * Sets the dimension of image, height automatically calculated
	 * @param width
	 */
	@LuaFunction(manual = false, methodName = "SetDimensions", arguments = { Float.class })
	public void SetDimensions(float width)
	{
		image.setDimensions(width);
	}
	
	/**
	 * Sets the dimesion of image
	 * @param width
	 * @param height
	 */
	@LuaFunction(manual = false, methodName = "SetDimensionsEx", arguments = { Float.class, Float.class })
	public void SetDimensionsEx(float width, float height)
	{
		image.setDimensions(width, height);
	}
	
	/**
	 * Sets the position of image
	 * @param point
	 */
	@LuaFunction(manual = false, methodName = "SetPosition", arguments = { LuaPoint.class })
	public void SetPosition(LuaPoint point)
	{
		image.setPosition(new LatLng(point.x, point.y));
	}
	
	/**
	 * Sets the position of the image
	 * @param x
	 * @param y
	 */
	@LuaFunction(manual = false, methodName = "SetPositionEx", arguments = { Float.class, Float.class })
	public void SetPositionEx(float x, float y)
	{
		image.setPosition(new LatLng(x, y));
	}
	
	/*public void SetPositionFromBound(LuaPoint point)
	{ 
	}*/
	
	/**
	 * Sets the transparency of the image
	 * @param transperency
	 */
	@LuaFunction(manual = false, methodName = "SetTransparency", arguments = { Float.class })
	public void SetTransparency(float transperency)
	{
		image.setTransparency(transperency);
	}
	
	/**
	 * Sets the visibility of the image
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "SetVisible", arguments = { Boolean.class })
	public void SetVisible(boolean value)
	{
		image.setVisible(value);
	}
	
	/**
	 * Sets the z-index of the image
	 * @param index
	 */
	@LuaFunction(manual = false, methodName = "SetZIndex", arguments = { Float.class })
	public void SetZIndex(float index)
	{
		image.setZIndex(index);
	}

	/**
	 * (Ignore) 
	 */	
	@Override
	public String GetId()
	{
		return "LuaMapImage";
	}
	
}
