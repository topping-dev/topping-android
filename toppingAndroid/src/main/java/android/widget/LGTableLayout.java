package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGTableLayout")
public class LGTableLayout extends LGLinearLayout implements LuaInterface
{
	/**
	 * Creates LGTableLayout Object From Lua.
	 * @param lc
	 * @return LGTableLayout
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGTableLayout.class)
	public static LGTableLayout Create(LuaContext lc)
	{
		return new LGTableLayout(lc.GetContext());
	}
	
	/**
	 * (Ignore)
	 */	
	public LGTableLayout(Context context)
	{
		super(context);
	}
	
	/**
	 * (Ignore)
	 */
	public LGTableLayout(Context context, String luaId)
	{
		super(context, luaId);
	}
	
	/**
	 * (Ignore)
	 */
	public LGTableLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
    {
		view = new TableLayout(context);
    }
	
	/**
	 * (Ignore)
	 */
    public void Setup(Context context, AttributeSet attrs)
    {
    	view = new TableLayout(context, attrs);
    }
}
