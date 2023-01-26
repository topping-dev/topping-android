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
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGRelativeLayout.class)
	public static LGRelativeLayout create(LuaContext lc)
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
		view = lc.getLayoutInflater().createView(context, "RelativeLayout");
		if(view == null)
			view = new RelativeLayout(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.getLayoutInflater().createView(context, "RelativeLayout", attrs);
		if(view == null)
			view = new RelativeLayout(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.getLayoutInflater().createView(context, "RelativeLayout", attrs);
		if(view == null)
			view = new RelativeLayout(context, attrs, defStyle);
	}
}
