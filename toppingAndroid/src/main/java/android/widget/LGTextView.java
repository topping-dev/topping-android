package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;

@LuaClass(className = "LGTextView")
public class LGTextView extends LGView implements LuaInterface
{
	/**
	 * Creates LGTextView Object From Lua.
	 * @param lc
	 * @return LGTextView
	 */
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGTextView.class)
	public static LGTextView create(LuaContext lc)
	{
		LGTextView tv = new LGTextView(lc);
		tv.view.setTag(tv);
		return tv;
	}

	/**
	 * (Ignore)
	 */
	public LGTextView(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGTextView(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGTextView(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGTextView(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.getLayoutInflater().createView(context, "TextView");
		if(view == null)
			view = new TextView(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.getLayoutInflater().createView(context, "TextView", attrs);
		if(view == null)
			view = new TextView(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.getLayoutInflater().createView(context, "TextView", attrs);
		if(view == null)
			view = new TextView(context, attrs, defStyle);
	}

	/**
	 * Sets the text
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "setText", arguments = { String.class })
	public void setTextInternal(String val)
	{
		((TextView)view).setText(val);
	}

	/**
	 * Sets the text from ref
	 * @param ref
	 */
	@LuaFunction(manual = false, methodName = "setTextRef", arguments = { LuaRef.class })
	public void setText(LuaRef ref) {
		((TextView)view).setText(ref.getRef());
	}

	/**
	 * Gets the text
	 * @return String
	 */
	@LuaFunction(manual = false, methodName = "getText")
	public String getText()
	{
		return ((TextView)view).getText().toString();
	}

	/**
	 * Sets the text color
	 * @param colorRef
	 */
	@LuaFunction(manual = false, methodName = "setTextColor", arguments = { String.class })
	public void setTextColor(LuaRef colorRef)
	{
		((TextView)view).setTextColor(lc.getContext().getResources().getColor(colorRef.getRef()));
	}
}
