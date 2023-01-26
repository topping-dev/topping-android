package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * Linear Layout
 */
@LuaClass(className = "LGLinearLayout")
public class LGLinearLayout extends LGViewGroup implements LuaInterface
{
	/**
	 * Creates LGLinearLayout Object From Lua.
	 * @param lc
	 * @return LGLinearLayout
	 */
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGLinearLayout.class)
	public static LGLinearLayout create(LuaContext lc)
	{
		return new LGLinearLayout(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGLinearLayout(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGLinearLayout(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGLinearLayout(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.getLayoutInflater().createView(context, "LinearLayout");
		if(view == null)
			view = new LinearLayout(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.getLayoutInflater().createView(context, "LinearLayout", attrs);
		if(view == null)
			view = new LinearLayout(context, attrs);
	}
}
