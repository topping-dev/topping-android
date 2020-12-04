package dev.topping.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.LGView;
import android.widget.TabHost.TabSpec;

import java.io.InputStream;

import androidx.fragment.app.FragmentTabHost;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.osspecific.utils.Common;

/**
 * This class is used to create tab controls
 */
@LuaClass(className = "LuaTabForm")
public class LuaTabForm implements LuaInterface
{
	LuaContext luaContext;
	String luaId;
	FragmentTabHost mTabHost = null;
	public View lastTabView = null;
	public String lastTabId = "";
	
	/**
	 * Creates LuaTabForm Object From Lua.
	 * @param lc
	 * @param luaId
	 * @return LuaTabForm
	 */
	@LuaFunction(manual = false, methodName = "Create", self = LuaTabForm.class, arguments = { LuaContext.class, String.class })
	public static Object Create(LuaContext lc, String luaId)
	{
		LuaTabForm ltf = new LuaTabForm();
		ltf.mTabHost = new FragmentTabHost(lc.GetContext());
		ltf.luaId = luaId;
		ltf.luaContext = lc;
		return ltf;
	}
	
	/**
	 * Add tab to tabform
	 * @param form tabform value
	 * @param title title of the tab
	 * @param image image of the tab
	 * @param ui xml file of tab
	 */
	@LuaFunction(manual = false, methodName = "AddTab", arguments = { Object.class, String.class, LuaStream.class, String.class })
	public void AddTab(Object form, String title, LuaStream image, String ui)
	{
		TabSpec tabToAdd = mTabHost.newTabSpec(title);
	    //backupTab.setIndicator(title, new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.liste)));
		Intent intent = ((Intent)form);
		intent.putExtra("LUA_VIEW_RUED", ui);
	    tabToAdd.setContent(intent);
	    mTabHost.addTab(tabToAdd);
	    mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height = Common.GetDPSize(45);
	}
	
	/**
	 * 
	 * @param form
	 * @param title
	 * @param image
	 * @param ui
	 */
	@LuaFunction(manual = false, methodName = "AddTabStream", arguments = { Object.class, String.class, LuaStream.class, LGView.class })
	public void AddTabStream(Object form, String title, LuaStream image, LGView ui)
	{
		TabSpec tabToAdd = mTabHost.newTabSpec(title);
	    //backupTab.setIndicator(title, new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.liste)));
		Intent intent = ((Intent)form);
		intent.putExtra("LUA_VIEW_RUED", ui);
	    tabToAdd.setContent(intent);
	    mTabHost.addTab(tabToAdd);
	    mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height = Common.GetDPSize(45);
	}
	
	@LuaFunction(manual = false, methodName = "AddTabSrc", arguments = { Object.class, String.class, String.class, String.class, String.class })
	public void AddTabSrc(Object form, String title, String path, String image, String ui)
	{
		TabSpec tabToAdd = mTabHost.newTabSpec(title);
		tabToAdd.setIndicator(title, new BitmapDrawable(luaContext.GetContext().getResources(), (InputStream)LuaResource.GetResource(path, image).GetStreamInternal()));
		Intent intent = ((Intent)form);
		intent.putExtra("LUA_VIEW_RUED", ui);
	    tabToAdd.setContent(intent);
	    mTabHost.addTab(tabToAdd);
	    mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height = Common.GetDPSize(45);
	}
	
	@LuaFunction(manual = false, methodName = "AddTabSrcStream", arguments = { Object.class, String.class, String.class, String.class, LGView.class }) 
	public void AddTabSrcStream(Object form, String title, String path, String image, LGView ui)
	{
		TabSpec tabToAdd = mTabHost.newTabSpec(title);
		tabToAdd.setIndicator(title, new BitmapDrawable(luaContext.GetContext().getResources(), (InputStream)LuaResource.GetResource(path, image).GetStreamInternal()));
		Intent intent = ((Intent)form);
		intent.putExtra("LUA_VIEW_RUED", ui);
	    tabToAdd.setContent(intent);
	    mTabHost.addTab(tabToAdd);
	    mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height = Common.GetDPSize(45);
	}
	
	@LuaFunction(manual = false, methodName = "Setup", arguments = { Object.class })
	public void Setup(Object form)
	{
		((Activity)form).setContentView(mTabHost);
	}

	@Override
	public String GetId()
	{
		if(luaId != null)
			return luaId;
		return "LuaTabForm";
	}
	
}
