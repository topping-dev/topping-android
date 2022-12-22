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
import dev.topping.android.luagui.LuaViewInflator;

@LuaClass(className = "LGView")
public class LGView extends Object implements LuaInterface, Serializable
{
	/**
	 *
	 */
	protected boolean loaded = false;
	protected AttributeSet attrs;
	public View view;
	public LuaContext lc;
	public String luaId = null;
	protected ArrayList<LGView> subviews = new ArrayList<LGView>();
	public String internalName = "";

	/**
	 * Creates LGView Object From Lua.
	 * @param lc
	 * @return LGView
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGView.class)
	public static LGView Create(LuaContext lc)
	{
		return new LGView(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGView(LuaContext context)
	{
		this.lc = context;
		BeforeSetup(context.GetContext());
		Setup(context.GetContext());
		AfterSetup(context.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public LGView(LuaContext context, String luaId)
	{
		this.lc = context;
		this.luaId = luaId;
		BeforeSetup(context.GetContext());
		Setup(context.GetContext());
		AfterSetup(context.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public LGView(LuaContext context, AttributeSet attrs)
	{
		this.lc = context;
		this.attrs = attrs;
		BeforeSetup(context.GetContext());
		Setup(context.GetContext(), attrs);
		AfterSetup(context.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public LGView(LuaContext context, AttributeSet attrs, int defStyle)
	{
		this.lc = context;
		this.attrs = attrs;
		BeforeSetup(context.GetContext());
		Setup(context.GetContext(), attrs);
		AfterSetup(context.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.GetLayoutInflater().createView(context, "View");
		if(view == null)
			view = new View(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.GetLayoutInflater().createView(context, "View", attrs);
		if(view == null)
			view = new View(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.GetLayoutInflater().createView(context, "View", attrs);
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
	public void onCreate()
	{
		for(LGView w : subviews)
			w.onCreate();
	}

	/**
	 * (Ignore)
	 */
	public void onResume()
	{
		for(LGView w : subviews)
			w.onResume();
	}

	/**
	 * (Ignore)
	 */
	public void onPause()
	{
		for(LGView w : subviews)
			w.onPause();
	}

	/**
	 * (Ignore)
	 */
	public void onDestroy()
	{
		for(LGView w : subviews)
			w.onDestroy();
	}

	/**
	 * (Ignore)
	 */
	public View GetView() { return view; }

	/**
	 * (Ignore)
	 */
	public void PrintDescription(String last)
	{
		ViewGroup.LayoutParams lps = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);//getLayoutParams();
		ViewGroup.LayoutParams lpsv = view.getLayoutParams();
		Log.e("PrintDescription", last + toString() + " Width: " + lps.width + " Height: " + lps.height);
		Log.e("PrintDescriptionView", last + view.toString() + " Width: " + lpsv.width + " Height: " + lpsv.height);
		for(LGView w : subviews)
		{
			w.PrintDescription(last + "--");
		}
	}

	/**
	 * Get view by id
	 * @param lId
	 * @return LGView
	 */
	@LuaFunction(manual = false, methodName = "GetViewById", arguments = { LuaRef.class })
	public LGView GetViewById(LuaRef lId)
	{
		String resourceName = view.getContext().getResources().getResourceEntryName(lId.getRef());
		if(this.GetId() != null && this.GetId().compareTo(resourceName) == 0)
			return this;
		else
		{
			for(LGView w : subviews)
			{
				LGView wFound = w.GetViewById(lId);
				if(wFound != null)
					return wFound;
			}
		}
		return null;
	}

	/**
	 * Set enabled
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "SetEnabled", arguments = { Boolean.class })
	public void SetEnabled(Boolean value)
	{
		view.setEnabled(value);
	}

	/**
	 * Set focusable
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "SetFocusable", arguments = { Boolean.class })
	public void SetFocusable(Boolean value)
	{
		view.setFocusable(value);
	}

	/**
	 * Set background ref
	 * @param backgroundRef
	 */
	@LuaFunction(manual = false, methodName = "SetBackground", arguments = { LuaRef.class })
	public void SetBackground(LuaRef backgroundRef)
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
	@LuaFunction(manual = false, methodName = "SetOnClickListener", arguments = { LuaTranslator.class })
	public void SetOnClickListener(LuaTranslator lt)
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
				lt.CallIn(lc);
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
	@Override
	public String GetId()
	{
		if(luaId != null)
			return luaId;
		String idS = null;
		try {
			idS = lc.GetContext().getResources().getResourceEntryName(view.getId());
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
	public void SetLuaId(String val)
	{
		luaId = val;
	}

	/**
	 * (Ignore)
	 */
	public boolean IsLoaded()
	{
		return loaded;
	}

	/**
	 * (Ignore)
	 */
	public void SetLoaded(boolean loaded)
	{
		this.loaded = loaded;
	}

	/**
	 * (Ignore)
	 */
	public void AddSubview(LGView view)
	{
		this.subviews.add(view);
	}
}
