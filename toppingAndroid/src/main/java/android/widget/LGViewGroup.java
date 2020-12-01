package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGViewGroup")
public class LGViewGroup extends LGView implements LuaInterface
{
	/**
	 * Creates LGViewGroup Object From Lua.
	 * Do not create this class directly.
	 * @param lc
	 * @return LGViewGroup
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGViewGroup.class)
	public static LGViewGroup Create(LuaContext lc)
	{
		return new LGViewGroup(lc.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public LGViewGroup(Context context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGViewGroup(Context context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGViewGroup(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGViewGroup(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
}
