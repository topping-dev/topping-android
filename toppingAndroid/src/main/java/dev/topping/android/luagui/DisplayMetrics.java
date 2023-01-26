package dev.topping.android.luagui;

import android.content.Context;
import android.content.res.Configuration;
import android.view.Surface;
import android.view.ViewGroup;

public class DisplayMetrics {
	public static float density=1.0f;
	public static float scaledDensity=1.0f;
	public static float xdpi=160;
	public static float ydpi=160;

	public static final float MM_TO_IN = 0.0393700787f;
	public static final float PT_TO_IN = 1/72.0f;
	
	public static boolean isTablet = false;
	
	public static synchronized int getRotation(Context context)
	{
		/*WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	    Display display = wm.getDefaultDisplay();
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO)
			return display.getOrientation();
		else
			return display.getRotation();*/
		int orientation = context.getResources().getConfiguration().orientation;
		if(orientation == Configuration.ORIENTATION_PORTRAIT)
			return Surface.ROTATION_0;
		else
			return Surface.ROTATION_90;
	}

	public static int readSize(String sz)
	{
		if (sz == null)
			return -1;
		if ("wrap_content".compareTo(sz) == 0) {
			return ViewGroup.LayoutParams.WRAP_CONTENT;
		}
		else if ("fill_parent".compareTo(sz) == 0) {
			return ViewGroup.LayoutParams.MATCH_PARENT;
		}
		else if ("match_parent".compareTo(sz) == 0) {
			return ViewGroup.LayoutParams.MATCH_PARENT;
		}
		try {
			float size;
			if (sz.endsWith("dip"))
				size = Float.parseFloat(sz.substring(0, sz.length()-3));
			else if(sz.endsWith("dp"))
				size = Float.parseFloat(sz.substring(0, sz.length()-2));
			else
				size = Float.parseFloat(sz);
			
			if (sz.endsWith("px")) {
				return (int)size;
			}
			else if (sz.endsWith("in")) {
				return (int)(size*xdpi);
			}
			else if (sz.endsWith("mm")) {
				return (int)(size*MM_TO_IN*xdpi);
			}
			else if (sz.endsWith("pt")) {
				return (int)(size*PT_TO_IN*xdpi);
			}
			else if (sz.endsWith("dp") || sz.endsWith("dip")) {
				return (int)(size*density);
			}
			else if (sz.endsWith("sp")) {
				return (int)(size*scaledDensity);
			}
			else {
				return Integer.parseInt(sz);
			}
		} catch (NumberFormatException ex) {
			return -1;
		}
	}
}
