package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton.OnCheckedChangeListener;

import dev.topping.android.LuaTranslator;
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
		return new LGRadioButton(lc.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public LGRadioButton(Context context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGRadioButton(Context context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGRadioButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGRadioButton(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = new RadioButton(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = new RadioButton(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = new RadioButton(context, attrs, defStyle);
	}
}
