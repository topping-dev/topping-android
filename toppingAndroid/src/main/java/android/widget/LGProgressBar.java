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
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGProgressBar.class)
	public static LGProgressBar create(LuaContext lc)
	{
		return new LGProgressBar(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGProgressBar(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGProgressBar(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGProgressBar(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGProgressBar(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.getLayoutInflater().createView(context, "ProgressBar");
		if(view == null)
			view = new ProgressBar(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.getLayoutInflater().createView(context, "ProgressBar", attrs);
		if(view == null)
			view = new ProgressBar(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.getLayoutInflater().createView(context, "ProgressBar", attrs);
		if(view == null)
			view = new ProgressBar(context, attrs, defStyle);
	}

	/**
	 * Sets the progress
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "setProgress", arguments = { Integer.class })
	public void setProgress(Integer val)
	{
		((ProgressBar)view).setProgress(val);
	}

	/**
	 * Sets the max progress
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "setMax", arguments = { Integer.class })
	public void setMax(Integer val)
	{
		((ProgressBar)view).setMax(val);
	}

	/**
	 * Sets the indeterminate
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "setIndeterminate", arguments = { Boolean.class })
	public void setIndeterminate(Boolean val)
	{
		((ProgressBar)view).setIndeterminate(val);
	}
}
