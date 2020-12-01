package android.widget;

import android.content.Context;
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
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGCompoundButton.class)
	public static LGCompoundButton Create(LuaContext lc)
	{
		return new LGCompoundButton(lc.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public LGCompoundButton(Context context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGCompoundButton(Context context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGCompoundButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGCompoundButton(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
}
