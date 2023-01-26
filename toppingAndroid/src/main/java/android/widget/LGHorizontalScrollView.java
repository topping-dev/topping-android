package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * HorizontalScrollView
 */
@LuaClass(className = "LGHorizontalScrollView")
public class LGHorizontalScrollView extends LGFrameLayout implements LuaInterface
{
	/**
	 * Creates LGHorizontalScrollView Object From Lua.
	 * @param lc
	 * @return LGHorizontalScrollView
	 */
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGHorizontalScrollView.class)
	public static LGHorizontalScrollView create(LuaContext lc)
	{
		return new LGHorizontalScrollView(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGHorizontalScrollView(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGHorizontalScrollView(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGHorizontalScrollView(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGHorizontalScrollView(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.getLayoutInflater().createView(context, "HorizontalScrollView");
		if(view == null)
			view = new HorizontalScrollView(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.getLayoutInflater().createView(context, "HorizontalScrollView", attrs);
		if(view == null)
			view = new HorizontalScrollView(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.getLayoutInflater().createView(context, "HorizontalScrollView", attrs);
		if(view == null)
			view = new HorizontalScrollView(context, attrs, defStyle);
	}
}
