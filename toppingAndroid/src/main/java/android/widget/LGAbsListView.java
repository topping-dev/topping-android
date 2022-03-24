package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * AbsListView
 */
@LuaClass(className = "LGAbsListView")
public class LGAbsListView extends LGViewGroup implements LuaInterface
{
	private boolean loaded = false;

	/**
	 * Creates LGAbsListView Object From Lua.
	 * Do not use this class directly
	 * @param lc
	 * @return LGAbsListView
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGAbsListView.class)
	public static LGAbsListView Create(LuaContext lc)
	{
		return new LGAbsListView(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGAbsListView(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGAbsListView(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGAbsListView(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGAbsListView(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
}
