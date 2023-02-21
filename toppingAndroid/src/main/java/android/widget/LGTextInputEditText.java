package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * Material EditText
 */
@LuaClass(className = "LGTextInputEditText")
public class LGTextInputEditText extends LGEditText implements LuaInterface
{
	/**
	 * Creates LGTextInputEditText Object From Lua.
	 * @param lc
	 * @return LGTextInputEditText
	 */
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGTextInputEditText.class)
	public static LGTextInputEditText create(LuaContext lc)
	{
		return new LGTextInputEditText(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGTextInputEditText(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGTextInputEditText(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGTextInputEditText(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGTextInputEditText(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.getLayoutInflater().createView(context, "TextInputEditText");
		if(view == null)
			view = new TextInputEditText(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.getLayoutInflater().createView(context, "TextInputEditText", attrs);
		if(view == null)
			view = new TextInputEditText(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.getLayoutInflater().createView(context, "TextInputEditText", attrs);
		if(view == null)
			view = new TextInputEditText(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId() {
		return "LGTextInputEditText";
	}
}
