package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * RelativeLayout
 */
@LuaClass(className = "LGRelativeLayout")
public class LGRelativeLayout extends LGViewGroup implements LuaInterface
{
	/**
	 * Creates LGRelativeLayout Object From Lua.
	 * @param lc
	 * @return LGRelativeLayout
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGRelativeLayout.class)
	public static LGRelativeLayout Create(LuaContext lc)
	{
		return new LGRelativeLayout(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGRelativeLayout(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGRelativeLayout(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGRelativeLayout(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGRelativeLayout(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.GetLayoutInflater().createView(context, "RelativeLayout");
		if(view == null)
			view = new RelativeLayout(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.GetLayoutInflater().createView(context, "RelativeLayout", attrs);
		if(view == null)
			view = new RelativeLayout(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.GetLayoutInflater().createView(context, "RelativeLayout", attrs);
		if(view == null)
			view = new RelativeLayout(context, attrs, defStyle);
	}
}
