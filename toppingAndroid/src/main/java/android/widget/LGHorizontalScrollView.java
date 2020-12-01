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
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGHorizontalScrollView.class)
	public static LGHorizontalScrollView Create(LuaContext lc)
	{
		return new LGHorizontalScrollView(lc.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public LGHorizontalScrollView(Context context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGHorizontalScrollView(Context context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGHorizontalScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGHorizontalScrollView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = new HorizontalScrollView(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = new HorizontalScrollView(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = new HorizontalScrollView(context, attrs, defStyle);
	}
}
