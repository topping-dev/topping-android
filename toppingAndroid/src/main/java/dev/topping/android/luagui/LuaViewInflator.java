package dev.topping.android.luagui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.ViewGroup;
import android.widget.LGAutoCompleteTextView;
import android.widget.LGButton;
import android.widget.LGCheckBox;
import android.widget.LGComboBox;
import android.widget.LGConstraintLayout;
import android.widget.LGDatePicker;
import android.widget.LGEditText;
import androidx.fragment.app.LGFragmentContainerView;
import android.widget.LGFrameLayout;
import android.widget.LGHorizontalScrollView;
import android.widget.LGImageView;
import android.widget.LGLinearLayout;
import android.widget.LGListView;
import android.widget.LGProgressBar;
import android.widget.LGRadioButton;
import android.widget.LGRadioGroup;
import android.widget.LGRecyclerView;
import android.widget.LGRelativeLayout;
import android.widget.LGScrollView;
import android.widget.LGTabLayout;
import android.widget.LGTextView;
import android.widget.LGToolbar;
import android.widget.LGView;
import android.widget.LGViewPager;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Stack;

import dev.topping.android.LuaEvent;
import dev.topping.android.LuaForm;
import dev.topping.android.LuaTab;
import dev.topping.android.ToppingEngine;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

@LuaClass(className = "LuaViewInflator")
public class LuaViewInflator implements LuaInterface
{
	Stack<LGView> lgStack;
	Hashtable<String, Integer> ids;
	Context context;
	LuaContext lc;
	int idg;

	/**
	 * (Ignore)
	 */
	public LuaViewInflator(LuaContext lc)
	{
		this.lgStack = new Stack<>();
		this.ids = new Hashtable<>();
		this.context = lc.GetContext();
		this.lc = lc;
		this.idg = 0;
		android.util.DisplayMetrics metrics = new android.util.DisplayMetrics();
		((Activity)lc.GetContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		DisplayMetrics.density = metrics.density;
		DisplayMetrics.xdpi = metrics.xdpi;
		DisplayMetrics.ydpi = metrics.ydpi;
		DisplayMetrics.scaledDensity = metrics.scaledDensity;
	}

	/**
	 * (Ignore)
	 */
	public static int parseColor(LuaContext lc, String val)
	{
		if(val == null)
			return Color.WHITE;
		else
		{
			if(val.startsWith("#"))
				return Color.parseColor(val);
			else
			{
				int identifier = lc.GetContext().getResources().getIdentifier(val, "color", lc.GetContext().getPackageName());
				try
				{
					return lc.GetContext().getResources().getColor(identifier);
				}
				catch (Exception e)
				{
					return Integer.MAX_VALUE;
				}
			}
		}
	}

	/**
	 * Creates LuaViewInflater Object From Lua.
	 * @param lc
	 * @return LuaViewInflator
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LuaViewInflator.class)
	public static LuaViewInflator Create(LuaContext lc)
	{
		LuaViewInflator lvi = new LuaViewInflator(lc);
		return lvi;
	}

	public static int getResourceId(Context c, String pVariableName, String pResourcename, String pPackageName)
	{
		try {
			return c.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Parses xml file
	 * @param filename
	 * @param parent
	 * @return LGView
	 */
	@LuaFunction(manual = false, methodName = "ParseFile", arguments = { String.class, LGView.class })
	public LGView ParseFile(String filename, LGView parent)
	{
		XmlPullParser parse;
		try {
			String[] arr = filename.split("\\.");
			String name = arr[0].toLowerCase(Locale.US);
			parse = ToppingEngine.getInstance().GetContext().getResources().getLayout(getResourceId(ToppingEngine.getInstance().GetContext(), name, "layout", ToppingEngine.getInstance().GetContext().getPackageName()));

			ArrayList<LGView> lgRoot = new ArrayList<LGView>();
			LGView v = inflate(parse, lgRoot, parent);
			if(!v.IsLoaded())
				v.SetLoaded(LuaEvent.Companion.OnUIEvent(v, LuaEvent.UI_EVENT_VIEW_CREATE, lc));
			return v;
		}
		catch (XmlPullParserException ex) { Log.e("LuaViewInflator", ex.getMessage()); }
		catch (IOException ex) {  }
		return null;
	}

	/**
	 * Parses xml file
	 * @param id
	 * @param parent
	 * @return LGView
	 */
	@LuaFunction(manual = false, methodName = "Inflate", arguments = { LuaRef.class, LGView.class })
	public LGView Inflate(LuaRef id, LGView parent)
	{
		XmlPullParser parse;
		try {
			parse = ToppingEngine.getInstance().GetContext().getResources().getLayout(id.getRef());

			ArrayList<LGView> lgRoot = new ArrayList<LGView>();
			LGView v = inflate(parse, lgRoot, parent);
			if(!v.IsLoaded())
				v.SetLoaded(LuaEvent.Companion.OnUIEvent(v, LuaEvent.UI_EVENT_VIEW_CREATE, lc));
			return v;
		}
		catch (XmlPullParserException ex) { Log.e("LuaViewInflator", ex.getMessage()); }
		catch (IOException ex) {  }
		return null;
	}

	/**
	 * (Ignore)
	 */
	public LGView inflate(XmlPullParser parse, ArrayList<LGView> lgroot, LGView parent)
			throws XmlPullParserException, IOException
	{
		ids.clear();

		Stack<StringBuffer> data = new Stack<StringBuffer>();
		int evt = parse.getEventType();
		LGView root = null;
		while (evt != XmlPullParser.END_DOCUMENT) {
			switch (evt) {
			case XmlPullParser.START_DOCUMENT:
				data.clear();
				break;
			case XmlPullParser.START_TAG:
				data.push(new StringBuffer());
				LGView v = createView(parse, parent);

				if (v == null)
				{
					evt = parse.next();
					continue;
				}

				if(lgStack.size() > 0)
				{
					LGView vParent = lgStack.peek();
					vParent.AddSubview(v);
					((ViewGroup)vParent.view).addView(v.view);
				}
				else
				{
					lgroot.add(v);
				}

				if(v.view instanceof ViewGroup)
				{
					lgStack.push(v);
				}
				else
				{
					v.SetLoaded(LuaEvent.Companion.OnUIEvent(v, LuaEvent.UI_EVENT_VIEW_CREATE, lc));
				}

				if (root == null) {
					root = v;
				}
				break;
			case XmlPullParser.TEXT:
				data.peek().append(parse.getText());
				break;
			case XmlPullParser.END_TAG:
				data.pop();
				if(lgStack.size() > 0 && lgStack.peek().internalName.equalsIgnoreCase(parse.getName()))
				{
					lgStack.peek().SetLoaded(LuaEvent.Companion.OnUIEvent(lgStack.peek(), LuaEvent.UI_EVENT_VIEW_CREATE, lc));
					lgStack.pop();
				}
			}
			evt = parse.next();
		}
		return root;
	}

	private Class<LGView> ContainsPluginView(ArrayList<Class<?>> plugins, String name)
	{
		for(Class cls : plugins)
		{
			if(name.contains(((LGView)(cls.cast(LGView.class))).GetId()))
			{
				return (Class<LGView>)cls;
			}
		}

		return null;
	}

	protected LGView createView(XmlPullParser parse, LGView parent)
	{
		String name = parse.getName();
		LGView lgresult = null;
		Class<LGView> pluginView = null;
		AttributeSet atts = Xml.asAttributeSet(parse);
		String luaId = findAttribute(atts, "lua:id");
		if(name.equals("LinearLayout") ||
				name.equals("LGLinearLayout")){
			lgresult = new LGLinearLayout(lc, atts);
		}
		else if (name.equals("RadioGroup")
				|| name.equals("LGRadioGroup")) {
			lgresult = new LGRadioGroup(lc, atts);
		}
		else if (name.equals("RelativeLayout")
				|| name.equals("LGRelativeLayout")) {
			lgresult = new LGRelativeLayout(lc, atts);
		}
		else if (name.equals("ScrollView")
				|| name.equals("LGScrollView")) {
			lgresult = new LGScrollView(lc, atts);
		}
		else if (name.equals("FrameLayout")
				|| name.equals("LGFrameLayout")) {
			lgresult = new LGFrameLayout(lc, atts);
		}
		else if (name.equals("TextView")
				|| name.equals("MaterialTextView")
				|| name.equals("LGTextView")) {
			lgresult = new LGTextView(lc, atts);
		}
		else if (name.equals("AutoCompleteTextView")
				|| name.equals("LGAutoCompleteTextView")) {
			lgresult = new LGAutoCompleteTextView(lc, atts);
		}
		/*else if (name.equals("AnalogClock")) {
			result = new LGAnalogClock(context, atts);
		}*/
		else if (name.equals("Button")
				|| name.equals("MaterialButton")
				|| name.equals("LGButton")) {
			lgresult = new LGButton(lc, atts);
		}
		else if (name.equals("CheckBox")
				|| name.equals("MaterialCheckbox")
				|| name.equals("LGCheckBox")) {
			lgresult = new LGCheckBox(lc, atts);
		}
		else if (name.equals("Spinner")
				|| name.equals("LGComboBox")) {
			lgresult = new LGComboBox(lc, atts);
		}
		else if (name.equals("DatePicker")
				|| name.equals("MaterialDatePicker")
				|| name.equals("LGDatePicker")) {
			lgresult = new LGDatePicker(lc, atts);
		}
		/*else if (name.equals("DigitalClock")) {
			result = new LGDigitalClock(context, luaId);
		}*/
		else if (name.equals("EditText")
				|| name.equals("LGEditText")) {
			lgresult = new LGEditText(lc, atts);
		}
		else if (name.equals("ProgressBar")
				|| name.equals("LGProgressBar")) {
			lgresult = new LGProgressBar(lc, atts);
		}
		else if (name.equals("RadioButton")
				|| name.equals("LGRadioButton")) {
			lgresult = new LGRadioButton(lc, atts);
		}
		else if (name.equals("ListView")
				|| name.equals("LGListView")) {
			lgresult = new LGListView(lc, atts);
		}
		else if (name.equals("ImageView")
				|| name.equals("LGImageView"))
		{
			lgresult = new LGImageView(lc, atts);
		}
		else if(name.equals("LGHorizontalScrollView")
				|| name.equals("HorizontalScrollView"))
		{
			lgresult = new LGHorizontalScrollView(lc, atts);
		}
		else if (name.equals("LGView")
				|| name.equals("View"))
		{
			lgresult = new LGView(lc, atts);
		}
		else if(name.equals("LGRecyclerView")
				|| name.equals("androidx.recyclerview.widget.RecyclerView"))
		{
			lgresult = new LGRecyclerView(lc, atts);
		}
		else if(name.equals("LGToolbar")
				|| name.equals("androidx.appcompat.widget.Toolbar")
				|| name.equals("com.google.android.material.appbar.MaterialToolbar"))
		{
			lgresult = new LGToolbar(lc, atts);
		}
		else if(name.equals("androidx.constraintlayout.widget.ConstraintLayout")
			|| name.equals("LGConstraintLayout")) {
			lgresult = new LGConstraintLayout(lc, atts);
		}
		else if(name.equals("androidx.fragment.app.FragmentContainerView")
		|| name.equals("FragmentContainerView")
		|| name.equals("LGFragmentContainerView")) {
			lgresult = new LGFragmentContainerView(lc, atts);
		}
		else if(name.equals("com.google.android.material.tabs.TabLayout")
				|| name.equals("LGTabLayout")) {
			lgresult = new LGTabLayout(lc, atts);
		}
		else if(name.equals("com.google.android.material.tabs.TabItem")
				|| name.equals("LuaTab")) {
			lgresult = LuaTab.Create();
		}
		else if(name.equals("androidx.viewpager2.widget.ViewPager2")
				|| name.equals("android.support.v4.view.ViewPager")
				|| name.equals("LGViewPager")) {
			lgresult = new LGViewPager(lc, atts);
		}
		else if((pluginView = ContainsPluginView(ToppingEngine.GetViewPlugins(), name)) != null)
		{
			try
			{
				lgresult = pluginView.newInstance();
			}
			catch (Exception e)
			{

			}
		}
		else {
			Toast.makeText(context , "Unhandled tag:"+name,
					Toast.LENGTH_SHORT).show();
		}

		if (lgresult == null)
			return null;

		lgresult.internalName = name;
		lgresult.view.setTag(lgresult);
		lgresult.SetLuaId(luaId);

		if (lgStack.size() > 0)
		{
			ViewGroup.LayoutParams lps = ((ViewGroup)lgStack.peek().view).generateLayoutParams(atts);
			lgresult.view.setLayoutParams(lps);
		}
		else if(parent != null && parent.view instanceof ViewGroup)
		{
			lgresult.view.setLayoutParams(((ViewGroup)parent.view).generateLayoutParams(atts));
		}
		else
		{
			lgresult.view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		}

		lgresult.onCreate();

		return lgresult;
	}

	/**
	 * (Ignore)
	 */
	protected String findAttribute(AttributeSet atts, String id) {
		for (int i=0;i<atts.getAttributeCount();i++) {
			if (atts.getAttributeName(i).equals(id)) {
				return atts.getAttributeValue(i);
			}
		}
		int ix = id.indexOf(":");
		String[] attsNs = id.split(":");
		if (ix != -1) {
			if(attsNs[0].compareTo("lua") == 0)
				return atts.getAttributeValue("http://schemas.android.com/apk/res-auto", id.substring(ix + 1));
			else
				return atts.getAttributeValue("http://schemas.android.com/apk/res/android", id.substring(ix+1));
		}
		else {
			return null;
		}
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaViewInflator";
	}
}
