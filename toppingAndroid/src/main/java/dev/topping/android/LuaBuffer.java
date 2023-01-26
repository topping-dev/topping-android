package dev.topping.android;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

/**
 * Class that is used in buffer operations
 */
@LuaClass(className = "LuaBuffer")
public class LuaBuffer implements LuaInterface
{
	public byte[] buffer;
	
	/**
	 * Creates a buffer
	 * @param capacity
	 * @return LuaBuffer
	 */
	public static LuaBuffer create(int capacity)
	{
		LuaBuffer lb = new LuaBuffer();
		lb.buffer = new byte[capacity];
		return lb;
	}
	
	/**
	 * Gets byte from index
	 * @param index
	 * @return int
	 */
	@LuaFunction(manual = false, methodName = "getByte", arguments = { Integer.class })
	public Integer getByte(Integer index)
	{
		return (int) buffer[index];
	}
	
	/**
	 * Set Byte at index
	 * @param index
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "setByte", arguments = { Integer.class, Integer.class })
	public void setByte(Integer index, Integer value)
	{
		buffer[index] = (byte)value.intValue();
	}
	
	/**
	 * (Ignore)
	 */
	public byte[] getBuffer() { return buffer; }

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId() 
	{
		return "LuaBuffer";
	}
}
