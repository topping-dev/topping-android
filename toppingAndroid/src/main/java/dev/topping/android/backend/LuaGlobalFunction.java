package dev.topping.android.backend;

public interface LuaGlobalFunction
{
	/**
	 * (Ignore)
	 */
	public int Lua_Index(Long L);
	/**
	 * (Ignore)
	 */
	public int Lua_NewIndex(Long L);
	/**
	 * (Ignore)
	 */
	public int Lua_GC(Long L);
	/**
	 * (Ignore)
	 */
	public int Lua_ToString(Long L);
}