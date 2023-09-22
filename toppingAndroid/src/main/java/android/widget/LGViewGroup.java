package android.widget;

import android.util.AttributeSet;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;

@LuaClass(className = "LGViewGroup")
public class LGViewGroup extends LGView implements LuaInterface
{
	protected ArrayList<LGView> subviews = new ArrayList<LGView>();
	HashMap<String, LGView> subviewMap = new HashMap<>();

	/**
	 * Creates LGViewGroup Object From Lua.
	 * Do not create this class directly.
	 * @param lc
	 * @return LGViewGroup
	 */
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGViewGroup.class)
	public static LGViewGroup create(LuaContext lc)
	{
		return new LGViewGroup(lc);
	}

	/**
	 * Gets view bindings
	 * @return table
	 */
	@LuaFunction(manual = false, methodName = "getBindings")
	public HashMap<String, LGView> getBindings()
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
	public void printDescription(String last) {
		super.printDescription(last);
		for(LGView w : subviews)
		{
			w.printDescription(last + "--");
		}
	}

	/**
	 * Get view by id
	 * @param lId
	 * @return LGView
	 */
	@LuaFunction(manual = false, methodName = "getViewById", arguments = { LuaRef.class })
	public LGView getViewById(LuaRef lId) {
		String resourceName = view.getContext().getResources().getResourceEntryName(lId.getRef());
		if(this.GetId() != null && this.GetId().compareTo(resourceName) == 0)
			return this;
		else
		{
			for(LGView w : subviews)
			{
				if(w instanceof LGViewGroup) {
					return ((LGViewGroup)w).getViewById(lId);
				} else {
					resourceName = w.view.getContext().getResources().getResourceEntryName(lId.getRef());
					if(this.GetId() != null && this.GetId().compareTo(resourceName) == 0)
						return w;
				}
			}
		}
		return null;
	}

	/**
	 * Add view to group
	 * @param view
	 */
	@LuaFunction(manual = false, methodName = "addView", arguments = { LGView.class })
	public void addSubview(LGView view)
	{
		view.parent = this;
		subviews.add(view);
		((ViewGroup)view.view).addView(view.view);
		if(view.GetId() != null)
			subviewMap.put(view.GetId(), view);
	}

	/**
	 * Remove view from group
	 * @param view
	 */
	@LuaFunction(manual = false, methodName = "removeView", arguments = { LGView.class })
	public void removeSubview(LGView view) {
		view.parent = null;
		subviews.remove(view);
		((ViewGroup)view.view).removeView(view.view);
		if(view.GetId() != null)
			subviewMap.remove(view.GetId());
	}
}
