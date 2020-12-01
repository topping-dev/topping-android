package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * Button
 */
@LuaClass(className = "LGButton")
public class LGButton extends LGTextView implements LuaInterface
{
	/**
	 * Creates LGButton Object From Lua.
	 * @param lc
	 * @return LGButton
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGButton.class)
	public static LGButton Create(LuaContext lc)
	{
		return new LGButton(lc.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public LGButton(Context context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGButton(Context context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGButton(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = new Button(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = new Button(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = new Button(context, attrs, defStyle);
	}
}
