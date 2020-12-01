package dev.topping.android.osspecific.utils;

public class Common
{
	public static String pack = "";
	public static float scale = 0;
	public static synchronized int GetDPSize(float size) { return (int)(size * scale + 0.5f); }
}
