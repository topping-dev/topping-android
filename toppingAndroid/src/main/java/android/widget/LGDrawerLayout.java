package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;

import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * Drawer Layout
 */
@LuaClass(className = "LGDrawerLayout")
public class LGDrawerLayout extends LGViewGroup implements LuaInterface
{
	private ArrayList<LuaTranslator> ltOnDrawerSlide = new ArrayList<>();
	private ArrayList<LuaTranslator> ltOnDrawerOpened = new ArrayList<>();
	private ArrayList<LuaTranslator> ltOnDrawerClosed = new ArrayList<>();
	private ArrayList<LuaTranslator> ltOnDrawerStateChanged = new ArrayList<>();
	/**
	 * Creates LGDrawerLayout Object From Lua.
	 * @param lc
	 * @return LGDrawerLayout
	 */
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGDrawerLayout.class)
	public static LGDrawerLayout create(LuaContext lc)
	{
		return new LGDrawerLayout(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGDrawerLayout(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGDrawerLayout(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGDrawerLayout(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGDrawerLayout(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.getLayoutInflater().createView(context, "androidx.drawerlayout.widget.DrawerLayout");
		if(view == null)
			view = new DrawerLayout(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.getLayoutInflater().createView(context, "androidx.drawerlayout.widget.DrawerLayout", attrs);
		if(view == null)
			view = new DrawerLayout(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.getLayoutInflater().createView(context, "androidx.drawerlayout.widget.DrawerLayout", attrs);
		if(view == null)
			view = new DrawerLayout(context, attrs, defStyle);
	}

	@Override
	public void AfterSetup(Context context) {
		super.AfterSetup(context);
		((DrawerLayout)view).addDrawerListener(new DrawerLayout.DrawerListener() {
			@Override
			public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
				for(LuaTranslator lt : ltOnDrawerSlide)
					lt.callIn(slideOffset);
			}

			@Override
			public void onDrawerOpened(@NonNull View drawerView) {
				for(LuaTranslator lt : ltOnDrawerOpened)
					lt.callIn();
			}

			@Override
			public void onDrawerClosed(@NonNull View drawerView) {
				for(LuaTranslator lt : ltOnDrawerClosed)
					lt.callIn();
			}

			@Override
			public void onDrawerStateChanged(int newState) {
				for(LuaTranslator lt : ltOnDrawerStateChanged)
					lt.callIn(newState);
			}
		});
	}

	/**
	 * Add drawer slide changed listener
	 * @param lt
	 */
	@LuaFunction(manual = false, methodName = "addOnDrawerSlide", arguments = { LuaTranslator.class })
	public void addOnDrawerSlide(LuaTranslator lt) {
		ltOnDrawerSlide.add(lt);
	}

	/**
	 * Add drawer opened listener
	 * @param lt
	 */
	@LuaFunction(manual = false, methodName = "addOnDrawerOpened", arguments = { LuaTranslator.class })
	public void addOnDrawerOpened(LuaTranslator lt) {
		ltOnDrawerOpened.add(lt);
	}

	/**
	 * Add drawer closed listener
	 * @param lt
	 */
	@LuaFunction(manual = false, methodName = "addOnDrawerClosed", arguments = { LuaTranslator.class })
	public void addOnDrawerClosed(LuaTranslator lt) {
		ltOnDrawerClosed.add(lt);
	}

	/**
	 * Add drawer state changed listener
	 * @param lt
	 */
	@LuaFunction(manual = false, methodName = "addOnDrawerStateChanged", arguments = { LuaTranslator.class })
	public void addOnDrawerStateChanged(LuaTranslator lt) {
		ltOnDrawerStateChanged.add(lt);
	}

	/**
	 * Remove drawer slide changed listener
	 * @param lt
	 */
	@LuaFunction(manual = false, methodName = "removeOnDrawerSlide", arguments = { LuaTranslator.class })
	public void removeOnDrawerSlide(LuaTranslator lt) {
		ltOnDrawerSlide.remove(lt);
	}

	/**
	 * Remove drawer opened listener
	 * @param lt
	 */
	@LuaFunction(manual = false, methodName = "removeOnDrawerOpened", arguments = { LuaTranslator.class })
	public void removeOnDrawerOpened(LuaTranslator lt) {
		ltOnDrawerOpened.remove(lt);
	}

	/**
	 * Remove drawer closed listener
	 * @param lt
	 */
	@LuaFunction(manual = false, methodName = "removeOnDrawerClosed", arguments = { LuaTranslator.class })
	public void removeOnDrawerClosed(LuaTranslator lt) {
		ltOnDrawerClosed.remove(lt);
	}

	/**
	 * Remove drawer state changed listener
	 * @param lt
	 */
	@LuaFunction(manual = false, methodName = "removeOnDrawerStateChanged", arguments = { LuaTranslator.class })
	public void removeOnDrawerStateChanged(LuaTranslator lt) {
		ltOnDrawerStateChanged.remove(lt);
	}
}
