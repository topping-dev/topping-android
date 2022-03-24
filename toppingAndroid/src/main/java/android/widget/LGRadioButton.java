package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * RadioButton
 */
@LuaClass(className = "LGRadioButton")
public class LGRadioButton extends LGCompoundButton implements LuaInterface
{
	/**
	 * Creates LGRadioButton Object From Lua.
	 * @param lc
	 * @return LGRadioButton
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGRadioButton.class)
	public static LGRadioButton Create(LuaContext lc)
	{
		return new LGRadioButton(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGRadioButton(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGRadioButton(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGRadioButton(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGRadioButton(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.GetLayoutInflater().createView(context, "RadioButton");
		if(view == null)
			view = new RadioButton(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.GetLayoutInflater().createView(context, "RadioButton", attrs);
		if(view == null)
			view = new RadioButton(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.GetLayoutInflater().createView(context, "RadioButton", attrs);
		if(view == null)
			view = new RadioButton(context, attrs, defStyle);
	}
}
