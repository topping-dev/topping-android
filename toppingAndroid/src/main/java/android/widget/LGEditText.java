package android.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * EditText
 */
@LuaClass(className = "LGEditText")
public class LGEditText extends LGTextView implements LuaInterface
{
	private LuaTranslator ltTextChanged;
	private LuaTranslator ltBeforeTextChanged;
	private LuaTranslator ltAfterTextChanged;
	
	/**
	 * Creates LGEditText Object From Lua.
	 * @param lc
	 * @return LGEditText
	 */
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGEditText.class)
	public static LGEditText create(LuaContext lc)
	{
		return new LGEditText(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGEditText(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGEditText(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGEditText(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGEditText(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.getLayoutInflater().createView(context, "EditText");
		if(view == null)
			view = new EditText(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.getLayoutInflater().createView(context, "EditText", attrs);
		if(view == null)
			view = new EditText(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.getLayoutInflater().createView(context, "EditText", attrs);
		if(view == null)
			view = new EditText(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	protected void initEvents()
	{
		((TextView)view).addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				if(ltTextChanged != null)
					ltTextChanged.callIn(LGEditText.this, s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after)
			{
				if(ltBeforeTextChanged != null)
					ltBeforeTextChanged.callIn(LGEditText.this, s);
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				if(ltAfterTextChanged != null)
					ltAfterTextChanged.callIn(LGEditText.this, s);
			}
		});
	}
	
	/**
	 * Sets text changed listener
	 * @param lt +fun(textView: LGTextView, view: LGView, s: string):void
	 */
	@LuaFunction(manual = false, methodName = "setTextChangedListener", arguments = { LuaTranslator.class })
	public void setTextChangedListener(LuaTranslator lt)
	{
		ltTextChanged = lt;
		initEvents();
	}

	/**
	 * Sets before text changed listener
	 * @param lt +fun(textView: LGTextView, view: LGView, s: string):void
	 */
	@LuaFunction(manual = false, methodName = "setBeforeTextChangedListener", arguments = { LuaTranslator.class })
	public void setBeforeTextChangedListener(LuaTranslator lt)
	{
		ltBeforeTextChanged = lt;
		initEvents();
	}

	/**
	 * Sets after text changed listener
	 * @param lt +fun(textView: LGTextView, view: LGView, s: string):void
	 */
	@LuaFunction(manual = false, methodName = "setAfterTextChangedListener", arguments = { LuaTranslator.class })
	public void setAfterTextChangedListener(LuaTranslator lt)
	{
		ltAfterTextChanged = lt;
		initEvents();
	}
}
