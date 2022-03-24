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
 * Checkbox
 */
@LuaClass(className = "LGCheckBox")
public class LGCheckBox extends LGCompoundButton implements LuaInterface
{
	/**
	 * Creates LGCheckbox Object From Lua.
	 * @param lc
	 * @return LGCheckBox
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGCheckBox.class)
	public static LGCheckBox Create(LuaContext lc)
	{
		return new LGCheckBox(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGCheckBox(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGCheckBox(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGCheckBox(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGCheckBox(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.GetLayoutInflater().createView(context, "CheckBox");
		if(view == null)
			view = new CheckBox(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.GetLayoutInflater().createView(context, "CheckBox", attrs);
		if(view == null)
			view = new CheckBox(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.GetLayoutInflater().createView(context, "CheckBox", attrs);
		if(view == null)
			view = new CheckBox(context, attrs, defStyle);
	}

	/**
	 * Sets on checked changed listener
	 * @param lt +fun(checkbox: LGCheckBox, context: LuaContext, isChecked: bool):void
	 */
	@LuaFunction(manual = false, methodName = "SetOnCheckedChangedListener", arguments = { LuaTranslator.class })
	public void SetOnCheckedChangedListener(LuaTranslator lt)
	{
		if(lt == null)
		{
			((CheckBox)view).setOnCheckedChangeListener(null);
		}

		((CheckBox)view).setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
			{
				lt.Call(LGCheckBox.this.lc, isChecked);
			}
		});
	}
}
