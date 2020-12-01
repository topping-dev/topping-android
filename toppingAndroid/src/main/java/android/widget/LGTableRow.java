package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGTableRow")
public class LGTableRow extends LGLinearLayout implements LuaInterface
{
	/**
	 * Creates LGTableRow Object From Lua.
	 * @param lc
	 * @return LGTableRow
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGTableRow.class)
	public static LGTableRow Create(LuaContext lc)
	{
		return new LGTableRow(lc.GetContext());
	}
	
	/**
	 * (Ignore)
	 */
	public LGTableRow(Context context)
	{
		super(context);		
	}
	
	/**
	 * (Ignore)
	 */
	public LGTableRow(Context context, String luaId)
	{
		super(context, luaId);		
	}
	
	/**
	 * (Ignore)
	 */
	public LGTableRow(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
    {
		view = new TableRow(context);
    }
	
	/**
	 * (Ignore)
	 */
    public void Setup(Context context, AttributeSet attrs)
    {
    	view = new TableRow(context, attrs);
    }
}
