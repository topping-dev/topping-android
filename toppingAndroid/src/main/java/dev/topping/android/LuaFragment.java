package dev.topping.android;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LGView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaViewInflator;

/**
 * User interface fragment
 */
@LuaClass(className = "LuaFragment")
public class LuaFragment extends Fragment implements LuaInterface
{
	protected LuaContext luaContext;
	protected String luaId;
	protected String ui;
	protected LGView view;
	protected LinearLayout rootView;
	
	/**
	 * Creates LuaForm Object From Lua.
	 * Form that created will be sent on GUI_EVENT_CREATE event.
	 * @param lc
	 * @param luaId
	 * @return LuaFragment
	 */
	@LuaFunction(manual = false, methodName = "Create", self = LuaForm.class, arguments = { LuaContext.class, String.class })
	public static LuaFragment Create(LuaContext lc, String luaId)
	{
		LuaFragment lf = new LuaFragment(lc.GetContext(), luaId);
		return lf;
	}
	
	/**
	 * Creates LuaForm Object From Lua with ui.
	 * Form that created will be sent on GUI_EVENT_CREATE event.
	 * @param lc
	 * @param luaId
	 * @param ui
	 * @return LuaFragment
	 */
	@LuaFunction(manual = false, methodName = "CreateWithUI", self = LuaForm.class, arguments = { LuaContext.class, String.class, String.class })
	public static LuaFragment CreateWithUI(LuaContext lc, String luaId, String ui)
	{
		LuaFragment lf = new LuaFragment(lc.GetContext(), luaId, ui);
		return lf;
	}
	
	/**
	 * Gets LuaContext value of fragment
	 * @return LuaContext
	 */
	@LuaFunction(manual = false, methodName = "GetContext")
	public LuaContext GetContext()
	{
		return luaContext;
	}
	
	/**
	 * (Ignore)
	 */
	public LuaFragment()
	{
	}
	
	/**
	 * (Ignore)
	 */
	public LuaFragment(Context c, String luaId)
	{
		luaContext = new LuaContext();
		luaContext.SetContext(c);
		
		this.luaId = luaId;
	}
	
	/**
	 * (Ignore)
	 */
	public LuaFragment(Context c, String luaId, String ui)
	{
		luaContext = new LuaContext();
		luaContext.SetContext(c);
		
		this.luaId = luaId;
	}

	/**
	 * Checks that fragment is initialized or not.
	 * @return boolean
	 */
	@LuaFunction(manual = false, methodName = "IsInitialized")
	public boolean IsInitialized()
	{
		return view != null;
	}
	
	/**
	 * Gets the view by id
	 * @param lId
	 * @return LGView
	 */
	@LuaFunction(manual = false, methodName = "GetViewById", arguments = { String.class })
	public LGView GetViewById(String lId)
	{
		return this.view.GetViewById(lId);
	}
	
	/**
	 * Gets the view of fragment.
	 * @return LGView
	 */
	@LuaFunction(manual = false, methodName = "GetView")
	public LGView GetView()
	{
		return view;
	}
	
	/**
	 * Sets the view to render.
	 * @param v
	 */
	@LuaFunction(manual = false, methodName = "SetView", arguments = { LGView.class })
	public void SetView(LGView v)
	{
		view = v;
		rootView.removeAllViews();
		rootView.addView(v.GetView());
	}
	
	/**
	 * Sets the xml file of the view to render.
	 * @param xml
	 */
	@LuaFunction(manual = false, methodName = "SetViewXML", arguments = { String.class })
	public void SetViewXML(String xml)
	{
		LuaViewInflator inflater = new LuaViewInflator(luaContext);
		view = inflater.ParseFile(xml, null);
		rootView.removeAllViews();
		rootView.addView(view.GetView());
	}
	
	/**
	 * Sets the luaid of the view to render.
	 * @param luaId
	 */
	@LuaFunction(manual = false, methodName = "SetViewId", arguments = { String.class })
	public void SetViewId(String luaId)
	{
		this.luaId = luaId;
	}
	
	
	/**
	 * Sets the title of the screen.
	 * @param str
	 */
	@LuaFunction(manual = false, methodName = "SetTitle", arguments = { String.class })
	public void SetTitle(String str)
	{
		getActivity().setTitle(str);
	}
	
	/**
	 * Closes the form
	 */
	@LuaFunction(manual = false, methodName = "Close")
	public void Close()
	{
		getActivity().finishActivity(-1);
	}
	
	/**
	 * (Ignore)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		String luaId = "LuaFragment";
		String ui = "";
		
		this.luaId = luaId;
		luaContext = LuaContext.CreateLuaContext(inflater.getContext());
		if(ui.compareTo("") == 0)
		{
			LuaForm.OnFormEvent(this, LuaForm.FORM_EVENT_CREATE, luaContext);
		}
		else
		{
			LuaViewInflator inflaterL = new LuaViewInflator(luaContext);
			this.view = inflaterL.ParseFile(ui, null);
		}
		if(view != null)
			return view.GetView();
		else
		{
			rootView = new LinearLayout(inflater.getContext());
			rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			rootView.setBackgroundColor(Color.WHITE);
			return rootView;
		}
	}
	
	/**
	 * (Ignore)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{			
		super.onCreate(savedInstanceState);
	}
	
	/**
	 * (Ignore)
	 */
	@Override
	public void onResume()
	{
		super.onResume();
		LuaForm.OnFormEvent(this, LuaForm.FORM_EVENT_RESUME, luaContext);
	}
	
	/**
	 * (Ignore)
	 */
	@Override
	public void onPause()
	{
		super.onPause();
		LuaForm.OnFormEvent(this, LuaForm.FORM_EVENT_PAUSE, luaContext);
	}
	
	/**
	 * (Ignore)
	 */
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		LuaForm.OnFormEvent(this, LuaForm.FORM_EVENT_DESTROY, luaContext);
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId() 
	{
		if(luaId != null)
			return luaId;
		return "LuaFragment";
	}
}
