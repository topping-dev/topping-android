package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGScrollView")
public class LGScrollView extends LGFrameLayout implements LuaInterface
{
	/**
	 * Creates LGScrollView Object From Lua.
	 * @param lc
	 * @return LGScrollView
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGScrollView.class)
	public static LGScrollView Create(LuaContext lc)
	{
		return new LGScrollView(lc.GetContext());
	}
	
	/**
	 * (Ignore)
	 */	
	public LGScrollView(Context context)
	{
		super(context);
	}
	
	/**
	 * (Ignore)
	 */
	public LGScrollView(Context context, String luaId)
	{
		super(context, luaId);
	}
	
	/**
	 * (Ignore)
	 */
	public LGScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	/**
	 * (Ignore)
	 */
    public LGScrollView(Context context, AttributeSet attrs, int defStyle)
    {
    	super(context, attrs, defStyle);
    }
	
	/**
	 * (Ignore)
	 */
    public void Setup(Context context)
    {
    	view = new ScrollView(context);
    }
	
	/**
	 * (Ignore)
	 */
    public void Setup(Context context, AttributeSet attrs)
    {
    	view = new ScrollView(context, attrs);
    }
	
	/**
	 * (Ignore)
	 */
    public void Setup(Context context, AttributeSet attrs, int defStyle)
    {
    	view = new ScrollView(context, attrs, defStyle);
    }
}
