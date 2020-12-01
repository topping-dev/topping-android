package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * ProgressBar
 */
@LuaClass(className = "LGProgressBar")
public class LGProgressBar extends LGView implements LuaInterface
{
	/**
	 * Creates LGProgressBar Object From Lua.
	 * @param lc
	 * @return LGProgressBar
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGProgressBar.class)
	public static LGProgressBar Create(LuaContext lc)
	{
		return new LGProgressBar(lc.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public LGProgressBar(Context context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGProgressBar(Context context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGProgressBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGProgressBar(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = new ProgressBar(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = new ProgressBar(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = new ProgressBar(context, attrs, defStyle);
	}

	/**
	 * Sets the progress
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "SetProgress", arguments = { Integer.class })
	public void SetProgress(Integer val)
	{
		((ProgressBar)view).setProgress(val);
	}

	/**
	 * Sets the max progress
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "SetMax", arguments = { Integer.class })
	public void SetMax(Integer val)
	{
		((ProgressBar)view).setMax(val);
	}

	/**
	 * Sets the indeterminate
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "SetIndeterminate", arguments = { Boolean.class })
	public void SetIndeterminate(Boolean val)
	{
		((ProgressBar)view).setIndeterminate(val);
	}
}
