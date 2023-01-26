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
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGAutoCompleteTextView.class)
	public static LGAutoCompleteTextView create(LuaContext lc)
	{
		return new LGAutoCompleteTextView(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGAutoCompleteTextView(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGAutoCompleteTextView(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGAutoCompleteTextView(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGAutoCompleteTextView(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.getLayoutInflater().createView(context, "AutoCompleteTextView");
		if(view == null)
			view = new AutoCompleteTextView(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.getLayoutInflater().createView(context, "AutoCompleteTextView", attrs);
		if(view == null)
			view = new AutoCompleteTextView(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.getLayoutInflater().createView(context, "AutoCompleteTextView", attrs);
		if(view == null)
			view = new AutoCompleteTextView(context, attrs, defStyle);
	}

	/**
	 * Gets the LGRecyclerViewAdapter of recyclerview
	 * @return LGRecyclerViewAdapter
	 */
	@LuaFunction(manual = false, methodName = "getAdapter", arguments = { })
	public LGAdapterView getAdapter()
	{
		return (LGAdapterView) ((AutoCompleteTextView)view).getAdapter();
	}

	/**
	 * Sets the LGRecyclerViewAdapter of listview
	 * @param adapter
	 */
	@LuaFunction(manual = false, methodName = "setAdapter", arguments = { LGAdapterView.class })
	public void setAdapter(LGAdapterView adapter)
	{
		((AutoCompleteTextView)view).setAdapter(adapter);
		adapter.parent = this;
	}
}
