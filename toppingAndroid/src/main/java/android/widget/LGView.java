package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;

import dev.topping.android.LuaTranslator;
import dev.topping.android.R;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
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
	public ArrayList<LGView> subviews = new ArrayList<LGView>();

	/**
	 * Creates LGView Object From Lua.
	 * @param lc
	 * @return LGView
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGView.class)
	public static LGView Create(LuaContext lc)
	{
		return new LGView(lc.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public LGView(Context context)
	{
		//super(context);
		Setup(context);
		AfterSetup(context);
	}

	/**
	 * (Ignore)
	 */
	public LGView(Context context, String luaId)
	{
		//super(context);
		this.luaId = luaId;
		Setup(context);
		AfterSetup(context);
	}

	/**
	 * (Ignore)
	 */
	public LGView(Context context, AttributeSet attrs)
	{
		//super(context, attrs);
		this.attrs = attrs;
		Setup(context, attrs);
		AfterSetup(context);
	}

	/**
	 * (Ignore)
	 */
	public LGView(Context context, AttributeSet attrs, int defStyle)
	{
		//super(context, attrs);
		this.attrs = attrs;
		Setup(context, attrs);
		AfterSetup(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = new View(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = new View(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = new View(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void AfterSetup(Context context)
	{
		lc = LuaContext.CreateLuaContext(context);
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
	@LuaFunction(manual = false, methodName = "GetViewById", arguments = { String.class })
	public LGView GetViewById(String lId)
	{
		if(this.GetId() != null && this.GetId().compareTo(lId) == 0)
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
	 * Set background
	 * @param background
	 */
	@LuaFunction(manual = false, methodName = "SetBackground", arguments = { String.class })
	public void SetBackground(String background)
	{
		int res = lc.GetContext().getResources().getIdentifier(background, (String)null, lc.GetContext().getPackageName());
		if(res == 0)
		{
			int backVal = LuaViewInflator.parseColor(lc, background);
			if (backVal != Integer.MAX_VALUE)
				view.setBackgroundColor(backVal);
		}
	}

	/**
	 * Set background ref
	 * @param backgroundRef
	 */
	@LuaFunction(manual = false, methodName = "SetBackgroundRef", arguments = { String.class })
	public void SetBackgroundRef(String backgroundRef)
	{
		int backVal = LuaViewInflator.parseColor(lc, backgroundRef);
		if(backVal != Integer.MAX_VALUE)
			view.setBackgroundColor(backVal);
		else
			view.setBackgroundResource(lc.GetContext().getResources().getIdentifier(backgroundRef, (String)null, lc.GetContext().getPackageName()));
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
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		if(luaId != null)
			return luaId;
		String customId = (String) view.getTag(-1);
		if(customId == null)
		{
			//TODO:Check this
			TypedArray a = view.getContext().obtainStyledAttributes(attrs, R.styleable.lua, 0, 0);
			String str = a.getString(R.styleable.lua_id);
			a.recycle();
			return str;
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
}
