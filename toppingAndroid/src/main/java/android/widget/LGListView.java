package android.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Surface;

import dev.topping.android.LuaFragment;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.DisplayMetrics;
import dev.topping.android.luagui.LuaContext;

/**
 * ListView
 */
@LuaClass(className = "LGListView")
public class LGListView extends LGAbsListView implements LuaInterface
{
	private boolean useTabletModeIfNecessary = true;
	private int selectionColor;
	private LGListViewFragment llvf;
	public LuaFragment lf;

	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGListView.class)
	public static LGListView Create(LuaContext lc)
	{
		return new LGListView(lc.GetContext());
	}

	/**
	 * (Ignore)
	 */
	public LGListView(Context context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGListView(Context context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
//    	selectionColor = Color.TRANSPARENT;
//    	FragmentTracker ft = FragmentTracker.GetInstance(LuaForm.GetActiveForm().getSupportFragmentManager());
//		int rotation = DisplayMetrics.GetRotation(getContext());
//		int id = Defines.generateViewId();
//		int idD = Defines.generateViewId();
//		if(DisplayMetrics.isTablet && (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270))
//		{
//			LinearLayout general = new LinearLayout(context);
//			general.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//
//			FrameLayout fll = new FrameLayout(context);
//			fll.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.3f));
//			fll.setId(id);
//			general.addView(fll);
//
//			FrameLayout fld = new FrameLayout(context);
//			fld.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.7f));
//			fld.setId(idD);
//			general.addView(fld);
//
//			llvf = new LGListViewFragment();
//			lf = new LuaFragment();
//			ft.ReplaceFragment(/*com.dk.Circler.Helpers.R.id.listfragment*/ id, llvf, false);
//			ft.ReplaceFragment(/*com.dk.Circler.Helpers.R.id.detailfragment*/ idD, lf, false);
//			view = general;
//		}
//		else
		view = new ListView(context);//ft.ReplaceFragment(R.id.generalDataListViewLinearLayout, gdlf, false);

	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		selectionColor = Color.TRANSPARENT;
//    	FragmentTracker ft = FragmentTracker.GetInstance(LuaForm.GetActiveForm().getSupportFragmentManager());
//		int rotation = DisplayMetrics.GetRotation(getContext());
//		int id = Defines.generateViewId();
//		int idD = Defines.generateViewId();
//    	if(DisplayMetrics.isTablet && (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270))
//		{
//    		LinearLayout general = new LinearLayout(context);
//			general.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//
//			FrameLayout fll = new FrameLayout(context);
//			fll.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.3f));
//			fll.setId(id);
//			general.addView(fll);
//
//			FrameLayout fld = new FrameLayout(context);
//			fld.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.7f));
//			fld.setId(idD);
//			general.addView(fld);
//
//			llvf = new LGListViewFragment();
//			lf = new LuaFragment();
//			ft.ReplaceFragment(/*com.dk.Circler.Helpers.R.id.listfragment*/ id, llvf, false);
//			ft.ReplaceFragment(/*com.dk.Circler.Helpers.R.id.detailfragment*/ idD, lf, false);
//			view = general;
//		}
//		else
		view = new ListView(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		selectionColor = Color.TRANSPARENT;
//    	FragmentTracker ft = FragmentTracker.GetInstance(LuaForm.GetActiveForm().getSupportFragmentManager());
//		int rotation = DisplayMetrics.GetRotation(getContext());
//		int id = Defines.generateViewId();
//		int idD = Defines.generateViewId();
//    	if(DisplayMetrics.isTablet && (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270))
//		{
//    		LinearLayout general = new LinearLayout(context);
//			general.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//
//			FrameLayout fll = new FrameLayout(context);
//			fll.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.3f));
//			fll.setId(id);
//			general.addView(fll);
//
//			FrameLayout fld = new FrameLayout(context);
//			fld.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.7f));
//			fld.setId(idD);
//			general.addView(fld);
//
//			llvf = new LGListViewFragment();
//			lf = new LuaFragment();
//			ft.ReplaceFragment(/*com.dk.Circler.Helpers.R.id.listfragment*/ id, llvf, false);
//			ft.ReplaceFragment(/*com.dk.Circler.Helpers.R.id.detailfragment*/ idD, lf, false);
//			view = general;
//		}
//		else
		view = new ListView(context, attrs, defStyle);
	}

	/**
	 * Gets the LGAdapterView of listview
	 * @return LGAdapterView
	 */
	@LuaFunction(manual = false, methodName = "GetAdapter", arguments = { })
	public LGAdapterView GetAdapter()
	{
		int rotation = view.getContext().getResources().getConfiguration().orientation;
		if(DisplayMetrics.isTablet && (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) && llvf != null)
			return (LGAdapterView) llvf.getListAdapter();
		else
			return (LGAdapterView) ((ListView)view).getAdapter();
	}

	/**
	 * Sets the LGAdapterView of listview
	 * @param adapter
	 */
	@LuaFunction(manual = false, methodName = "SetAdapter", arguments = { LGAdapterView.class })
	public void SetAdapter(LGAdapterView adapter)
	{
		int rotation = view.getContext().getResources().getConfiguration().orientation;
		if(DisplayMetrics.isTablet && (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) && llvf != null)
			llvf.setListAdapter(adapter);
		else
			((ListView)view).setAdapter(adapter);
		adapter.par = this;
	}

	/**
	 * Gets the tablet mode status
	 * @return boolean
	 */
	@LuaFunction(manual = false, methodName = "IsUseTabletModeIfNecessaryEnabled", arguments = { })
	public boolean IsUseTabletModeIfNecessaryEnabled()
	{
		return useTabletModeIfNecessary == true;
	}

	/**
	 * Sets the tablet mode if tablet is present.
	 * If this value is set false, classic mode will be used.
	 * @param tabletMode
	 */
	@LuaFunction(manual = false, methodName = "SetUseTabletModeIfNecessary", arguments = { Boolean.class })
	public void SetUseTabletModeIfNecessary(boolean tabletMode)
	{
		useTabletModeIfNecessary = tabletMode;
	}

	/**
	 * Returns the selected cell color
	 * @return int
	 */
	@LuaFunction(manual = false, methodName = "GetSelectedCellColor")
	public Integer GetSelectedCellColor()
	{
		return selectionColor;
	}

	/**
	 * Sets the selected cell color.
	 * @param color
	 */
	@LuaFunction(manual = false, methodName = "SetSelectedCellColor", arguments = { Integer.class })
	public void SetSelectedCellColor(int color)
	{
		selectionColor = color;
	}

	/**
	 * (Ignore)
	 */
	public LuaFragment GetDetailFragment()
	{
		return lf;
	}
}
