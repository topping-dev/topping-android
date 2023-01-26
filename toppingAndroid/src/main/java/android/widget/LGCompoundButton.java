package android.widget;

import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * Compound Button
 */
@LuaClass(className = "LGCompoundButton")
public class LGCompoundButton extends LGButton implements LuaInterface
{
	/**
	 * Creates LGCompoundButton Object From Lua.
	 * @param lc
	 * @return LGCompoundButton
	 */
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGCompoundButton.class)
	public static LGCompoundButton create(LuaContext lc)
	{
		return new LGCompoundButton(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGCompoundButton(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGCompoundButton(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGCompoundButton(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGCompoundButton(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
}
