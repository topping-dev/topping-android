package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import dev.topping.android.LuaMenu;
import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;

/**
 * Navigation View
 */
@LuaClass(className = "LGNavigationView")
public class LGNavigationView extends LGLinearLayout implements LuaInterface
{
	protected LuaTranslator ltNavigationSelectListener;

	/**
	 * Creates LGNavigationView Object From Lua.
	 * @param lc
	 * @return LGNavigationView
	 */
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGNavigationView.class)
	public static LGNavigationView create(LuaContext lc)
	{
		return new LGNavigationView(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGNavigationView(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGNavigationView(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGNavigationView(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.getLayoutInflater().createView(context, "com.google.android.material.navigation.NavigationView");
		if(view == null)
			view = new NavigationView(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.getLayoutInflater().createView(context, "com.google.android.material.navigation.NavigationView", attrs);
		if(view == null)
			view = new NavigationView(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.getLayoutInflater().createView(context, "com.google.android.material.navigation.NavigationView", attrs);
		if(view == null)
			view = new NavigationView(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	@Override
	public void AfterSetup(Context context) {
		super.AfterSetup(context);
		((NavigationView)view).setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				if(ltNavigationSelectListener == null)
					return false;
				else {
					LuaMenu menu = new LuaMenu(lc, LuaRef.withValue(item.getItemId()));
					menu.setTitle(item.getTitle().toString());
					menu.setIcon(item.getIcon());
					return (boolean) ltNavigationSelectListener.callIn(menu);
				}
			}
		});
	}

	/**
	 * Set navigation item select listener
	 * @param lt +fun(navigationView: LGNavigationView, item: LuaMenu):bool
	 */
	@LuaFunction(manual = false, methodName = "setNavigationItemSelectListener", arguments = { LuaTranslator.class })
	public void setNavigationItemSelectListener(LuaTranslator lt) {
		ltNavigationSelectListener = lt;
	}
}
