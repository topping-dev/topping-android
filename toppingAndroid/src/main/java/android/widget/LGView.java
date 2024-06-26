package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import java.io.Serializable;
import java.util.ArrayList;

import dev.topping.android.LuaNavController;
import dev.topping.android.LuaTranslator;
import dev.topping.android.R;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;

/**
 * Base view class
 */
@LuaClass(className = "LGView")
public class LGView implements LuaInterface, Serializable
{
	/**
	 *
	 */
	protected Boolean loaded = null;
	protected AttributeSet attrs;
	public View view;
	public LuaContext lc;
	public String luaId = null;
	public String internalName = "";
	public LGView parent;

	/**
	 * Creates LGView Object From Lua.
	 * @param lc
	 * @return LGView
	 */
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGView.class)
	public static LGView create(LuaContext lc)
	{
		return new LGView(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGView(LuaContext context)
	{
		this.lc = context;
		BeforeSetup(context.getContext());
		Setup(context.getContext());
		AfterSetup(context.getContext());
	}

	/**
	 * (Ignore)
	 */
	public LGView(LuaContext context, String luaId)
	{
		this.lc = context;
		this.luaId = luaId;
		BeforeSetup(context.getContext());
		Setup(context.getContext());
		AfterSetup(context.getContext());
	}

	/**
	 * (Ignore)
	 */
	public LGView(LuaContext context, AttributeSet attrs)
	{
		this.lc = context;
		this.attrs = attrs;
		BeforeSetup(context.getContext());
		Setup(context.getContext(), attrs);
		AfterSetup(context.getContext());
	}

	/**
	 * (Ignore)
	 */
	public LGView(LuaContext context, AttributeSet attrs, int defStyle)
	{
		this.lc = context;
		this.attrs = attrs;
		BeforeSetup(context.getContext());
		Setup(context.getContext(), attrs);
		AfterSetup(context.getContext());
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.getLayoutInflater().createView(context, "View");
		if(view == null)
			view = new View(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.getLayoutInflater().createView(context, "View", attrs);
		if(view == null)
			view = new View(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.getLayoutInflater().createView(context, "View", attrs);
		if(view == null)
			view = new View(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void BeforeSetup(Context context)
	{
	}

	/**
	 * (Ignore)
	 */
	public void AfterSetup(Context context)
	{
	}

	/**
	 * (Ignore)
	 */
	public View getView() { return view; }

	/**
	 * (Ignore)
	 */
	public void printDescription(String last)
	{
		ViewGroup.LayoutParams lps = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);//getLayoutParams();
		ViewGroup.LayoutParams lpsv = view.getLayoutParams();
		Log.e("PrintDescription", last + toString() + " Width: " + lps.width + " Height: " + lps.height);
		Log.e("PrintDescriptionView", last + view.toString() + " Width: " + lpsv.width + " Height: " + lpsv.height);
	}

	/**
	 * Set enabled
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "setEnabled", arguments = { Boolean.class })
	public void setEnabled(Boolean value)
	{
		view.setEnabled(value);
	}

	/**
	 * Set focusable
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "setFocusable", arguments = { Boolean.class })
	public void setFocusable(Boolean value)
	{
		view.setFocusable(value);
	}

	/**
	 * Set background ref
	 * @param backgroundRef
	 */
	@LuaFunction(manual = false, methodName = "setBackground", arguments = { LuaRef.class })
	public void setBackground(LuaRef backgroundRef)
	{
		TypedValue value = new TypedValue();
		view.getContext().getResources().getValue(backgroundRef.getRef(), value, true); // will throw if resId doesn't exist

		if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT && value.type <= TypedValue.TYPE_LAST_COLOR_INT) {
			view.setBackgroundColor(view.getResources().getColor(backgroundRef.getRef()));
		} else if (value.type == TypedValue.TYPE_REFERENCE) {
			view.setBackgroundResource(backgroundRef.getRef());
		}
	}

	/**
	 * Adds click event to button
	 * @param lt +fun(view: LGView, context: LuaContext):void
	 */
	@LuaFunction(manual = false, methodName = "setOnClickListener", arguments = { LuaTranslator.class })
	public void setOnClickListener(LuaTranslator lt)
	{
		if(lt == null)
		{
			view.setOnClickListener(null);
			return;
		}

		view.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				lt.callIn(lc);
			}
		});
	}

	/**
	 * Gets navigation controller of view
	 * @return LuaNavController
	 */
	@LuaFunction(manual = false, methodName = "findNavController")
	public LuaNavController findNavController()
	{
		return new LuaNavController(NavHostFragment.findNavController(FragmentManager.findFragment(view)));
	}

	/**
	 * (Ignore)
	 */
	public LGView generateLGViewForName(String name, LuaContext lc, AttributeSet atts) {
		return null;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		if(luaId != null)
			return luaId;
		String idS = null;
		try {
			idS = lc.getContext().getResources().getResourceEntryName(view.getId());
		} catch (Exception ex) {}
		if(idS != null)
			return idS;
		String customId = (String) view.getTag(-1);
		if(customId == null)
		{
			TypedArray a = view.getContext().obtainStyledAttributes(attrs, R.styleable.lua, 0, 0);
			customId = a.getString(R.styleable.lua_id);
			a.recycle();
		}
		if(customId == null)
		{
			if (view.getId() != View.NO_ID)
				customId = view.getResources().getResourceEntryName(view.getId());
		}
		return customId;
	}

	/**
	 * (Ignore)
	 */
	public void setLuaId(String val)
	{
		luaId = val;
	}

	/**
	 * (Ignore)
	 */
	public boolean isLoaded()
	{
		if(loaded == null || !loaded)
			return false;
		return true;
	}

	/**
	 * (Ignore)
	 */
	public void setLoaded(Object loaded)
	{
		if(loaded == null)
			return;
		if(loaded instanceof Boolean)
			this.loaded = (Boolean) loaded;
	}
}
