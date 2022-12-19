package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import dev.topping.android.LuaResource;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;
import dev.topping.android.luagui.LuaViewInflator;

@LuaClass(className = "LGTextView")
public class LGTextView extends LGView implements LuaInterface
{
	/**
	 * Creates LGTextView Object From Lua.
	 * @param lc
	 * @return LGTextView
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGTextView.class)
	public static LGTextView Create(LuaContext lc)
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
		view = lc.GetLayoutInflater().createView(context, "TextView");
		if(view == null)
			view = new TextView(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.GetLayoutInflater().createView(context, "TextView", attrs);
		if(view == null)
			view = new TextView(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.GetLayoutInflater().createView(context, "TextView", attrs);
		if(view == null)
			view = new TextView(context, attrs, defStyle);
	}

	/**
	 * Sets the text
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "SetText", arguments = { String.class })
	public void SetTextInternal(String val)
	{
		((TextView)view).setText(val);
	}

	/**
	 * Sets the text from ref
	 * @param ref
	 */
	@LuaFunction(manual = false, methodName = "SetTextRef", arguments = { LuaRef.class })
	public void SetText(LuaRef ref) {
		((TextView)view).setText(ref.getRef());
	}

	/**
	 * Gets the text
	 * @return String
	 */
	@LuaFunction(manual = false, methodName = "GetText")
	public String GetText()
	{
		return ((TextView)view).getText().toString();
	}

	/**
	 * Sets the text color
	 * @param colorRef
	 */
	@LuaFunction(manual = false, methodName = "SetTextColor", arguments = { String.class })
	public void SetTextColor(LuaRef colorRef)
	{
		((TextView)view).setTextColor(lc.GetContext().getResources().getColor(colorRef.getRef()));
	}
}
