package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import java.util.HashMap;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGViewGroup")
public class LGViewGroup extends LGView implements LuaInterface
{
	HashMap<String, LGView> subviewMap = new HashMap<>();

	/**
	 * Creates LGViewGroup Object From Lua.
	 * Do not create this class directly.
	 * @param lc
	 * @return LGViewGroup
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGViewGroup.class)
	public static LGViewGroup Create(LuaContext lc)
	{
		return new LGViewGroup(lc);
	}

	/**
	 * Gets view bindings
	 * @return table
	 */
	@LuaFunction(manual = false, methodName = "GetBindings", arguments = { })
	public HashMap<String, LGView> GetBindings()
	{
		return subviewMap;
	}

	/**
	 * (Ignore)
	 */
	public LGViewGroup(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGViewGroup(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGViewGroup(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGViewGroup(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	@Override
	public void AddSubview(LGView view)
	{
		super.AddSubview(view);

		if(view.GetId() != null)
			subviewMap.put(view.GetId(), view);
	}
}
