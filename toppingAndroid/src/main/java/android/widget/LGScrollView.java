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
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGScrollView.class)
	public static LGScrollView create(LuaContext lc)
	{
		return new LGScrollView(lc);
	}
	
	/**
	 * (Ignore)
	 */	
	public LGScrollView(LuaContext context)
	{
		super(context);
	}
	
	/**
	 * (Ignore)
	 */
	public LGScrollView(LuaContext context, String luaId)
	{
		super(context, luaId);
	}
	
	/**
	 * (Ignore)
	 */
	public LGScrollView(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	/**
	 * (Ignore)
	 */
    public LGScrollView(LuaContext context, AttributeSet attrs, int defStyle)
    {
    	super(context, attrs, defStyle);
    }
	
	/**
	 * (Ignore)
	 */
    public void Setup(Context context)
    {
		view = lc.getLayoutInflater().createView(context, "ScrollView");
		if(view == null)
			view = new ScrollView(context);
    }
	
	/**
	 * (Ignore)
	 */
    public void Setup(Context context, AttributeSet attrs)
    {
		view = lc.getLayoutInflater().createView(context, "ScrollView", attrs);
		if(view == null)
			view = new ScrollView(context, attrs);
    }
	
	/**
	 * (Ignore)
	 */
    public void Setup(Context context, AttributeSet attrs, int defStyle)
    {
		view = lc.getLayoutInflater().createView(context, "ScrollView", attrs);
		if(view == null)
			view = new ScrollView(context, attrs, defStyle);
    }
}
