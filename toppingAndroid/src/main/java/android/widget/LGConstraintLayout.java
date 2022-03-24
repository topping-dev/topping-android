package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * Constraint Layout
 */
@LuaClass(className = "LGConstraintLayout")
public class LGConstraintLayout extends LGViewGroup implements LuaInterface
{
	/**
	 * Creates LGConstraintLayout Object From Lua.
	 * @param lc
	 * @return LGConstraintLayout
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGConstraintLayout.class)
	public static LGConstraintLayout Create(LuaContext lc)
	{
		return new LGConstraintLayout(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGConstraintLayout(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGConstraintLayout(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGConstraintLayout(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.GetLayoutInflater().createView(context, "ConstraintLayout");
		if(view == null)
			view = new ConstraintLayout(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.GetLayoutInflater().createView(context, "ConstraintLayout", attrs);
		if(view == null)
			view = new ConstraintLayout(context, attrs);
	}
}
