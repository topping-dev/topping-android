package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * Frame layout
 */
@LuaClass(className = "LGFrameLayout")
public class LGFrameLayout extends LGViewGroup implements LuaInterface
{
	/**
	 * Creates LGFrameLayout Object From Lua.
	 * @param lc
	 * @return LGFrameLayout
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGFrameLayout.class)
	public static LGFrameLayout Create(LuaContext lc)
	{
		return new LGFrameLayout(lc.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public LGFrameLayout(Context context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGFrameLayout(Context context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGFrameLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGFrameLayout(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = new FrameLayout(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = new FrameLayout(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = new FrameLayout(context, attrs, defStyle);
	}
}
