package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * RadioGroup
 */
@LuaClass(className = "LGRadioGroup")
public class LGRadioGroup extends LGLinearLayout implements LuaInterface
{
	/**
	 * Creates LGRadioGroup Object From Lua.
	 * @param lc
	 * @return LGRadioGroup
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGRadioGroup.class)
	public static LGRadioGroup Create(LuaContext lc)
	{
		return new LGRadioGroup(lc.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public LGRadioGroup(Context context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGRadioGroup(Context context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGRadioGroup(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = new RadioGroup(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = new RadioGroup(context, attrs);
	}

	/**
	 * Sets group checked changed listener
	 * @param lt +fun(radioGroup: LGRadioGroup, context: LuaContext, isChecked: bool):void
	 */
	@LuaFunction(manual = false, methodName = "SetOnCheckedChangedListener", arguments = { LuaTranslator.class })
	public void SetOnCheckedChangedListener(LuaTranslator lt)
	{
		if(lt == null)
		{
			((RadioGroup)view).setOnCheckedChangeListener(null);
			return;
		}

		((RadioGroup)view).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i)
			{
				lt.CallIn(LGRadioGroup.this, i);
			}
		});
	}
}
