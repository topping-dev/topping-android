package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * AutoCompleteTextView
 */
@LuaClass(className = "LGAutoCompleteTextView")
public class LGAutoCompleteTextView extends LGEditText implements LuaInterface
{

	/**
	 * Creates LGAutoCompleteTextView Object From Lua.
	 * @param lc
	 * @return LGAutoCompleteTextView
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGAutoCompleteTextView.class)
	public static LGAutoCompleteTextView Create(LuaContext lc)
	{
		return new LGAutoCompleteTextView(lc.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public LGAutoCompleteTextView(Context context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGAutoCompleteTextView(Context context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGAutoCompleteTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = new AutoCompleteTextView(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = new AutoCompleteTextView(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = new AutoCompleteTextView(context, attrs, defStyle);
	}
}
