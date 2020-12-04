package android.widget;

import android.graphics.Color;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.DisplayMetrics;
import dev.topping.android.luagui.LuaContext;

/**
 * AdapterView
 */
@LuaClass(className = "LGAdapterView")
public class LGAdapterView extends BaseAdapter implements SectionIndexer, LuaInterface
{
	public Map<String, Adapter> sections = new LinkedHashMap<String,Adapter>();
	public final ArrayAdapter<String> headers;
	public ArrayList<String> sectionsValues = new ArrayList<String>();
	public LinkedHashMap<Integer, Object> values = new LinkedHashMap<Integer, Object>();
	public final static int TYPE_SECTION_HEADER = 0;

	private LuaContext mLc;
	private String id;
	private LuaTranslator ltItemChanged = null;
	public LGListView par = null;
	private LGView lastSelectedView = null;
	private int lastPosition = -1;
	private LuaTranslator ltOnAdapterView;

	/**
	 * Creates LGAdapterView Object From Lua.
	 * @return LGAdapterView
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class, String.class }, self = LGAdapterView.class)
	public static LGAdapterView Create(LuaContext lc, String id)
	{
		LGAdapterView lgav = new LGAdapterView(lc, id);
		return lgav;
	}

	/**
	 * (Ignore)
	 */
	public LGAdapterView(LuaContext lc, String id)
	{
		//mInflater = (LayoutInflater)lc.GetContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLc = lc;
		this.id = id;
		this.headers = null;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public int getCount()
	{
		if(sections.size() > 0)
		{
			int total = 0;
			for(Adapter adapter : this.sections.values())
				total += adapter.getCount() + 1;
			return total;
		}
		else
			return values.size();
	}

	/**
	 * (Ignore)
	 */
	@Override
	public Object getItem(int position)
	{
		if(sections.size() > 0)
		{
			for(Object section : this.sections.keySet())
			{
				Adapter adapter = sections.get(section);
				int size = adapter.getCount() + 1;

				// check if position inside this section
				if(position == 0) return section;
				if(position < size) return adapter.getItem(position - 1);

				// otherwise jump into next section
				position -= size;
			}
		}
		else
			return (Object) values.values().toArray()[position];
		return null;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public long getItemId(int position)
	{
		return position;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(sections.size() > 0)
		{
			int sectionnum = 0;
			for(Object section : this.sections.keySet())
			{
				Adapter adapter = sections.get(section);
				int size = adapter.getCount() + 1;

				// check if position inside this section
				if(position == 0) return headers.getView(sectionnum, convertView, parent);
				if(position < size) return adapter.getView(position - 1, convertView, parent);

				// otherwise jump into next section
				position -= size;
				sectionnum++;
			}
		}
		else
		{
			View v = convertView;
			if(v != null && v instanceof ViewGroup)
				((ViewGroup)v).removeAllViews();

			LGView vF = (LGView) ltOnAdapterView.CallIn(par, position, getItem(position), v, mLc);
			v = vF.view;

			int rot = mLc.GetContext().getResources().getConfiguration().orientation;
			if(DisplayMetrics.isTablet && rot == Surface.ROTATION_90 && position == lastPosition)
			{
				if(lastSelectedView == null)
					lastSelectedView  = vF;
				v.setBackgroundColor(par.GetSelectedCellColor());
			}
			else
				v.setBackgroundColor(Color.TRANSPARENT);

			LayoutParams lps = vF.view.getLayoutParams();
			v.setLayoutParams(new AbsListView.LayoutParams(lps.width, lps.height));
			//v.view.setLayoutParams(new LinearLayout.LayoutParams(lps.width, lps.height));
			/*v.addView(v.view);
			v.PrintDescription("");
	        final int pos = position;
	        final LGListView par = this.par;
	        par.setClickable(true);
	        //Fix for nonvisib
	        final LGView vf = v;
	        v.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					int rot = DisplayMetrics.GetRotation(mLc.GetContext());
					if(DisplayMetrics.isTablet && rot == Surface.ROTATION_90)
					{
						if(lastSelectedView != null)
							lastSelectedView.setBackgroundColor(Color.TRANSPARENT);
						v.setBackgroundColor(par.GetSelectedCellColor());
						lastSelectedView = vf;
						lastPosition = pos;
					}

					if(ltItemChanged != null)
						ltItemChanged.CallIn(par, par.GetDetailFragment(), pos, getItem(pos));

				}
			});*/
			return v;
		}
		return null;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public int getPositionForSection(int section)
	{
		if(section > sections.size())
			return 0;

		int total = 1;
		int sectionCount = 0;
		for(Adapter adapter : this.sections.values())
		{
			sectionCount++;
			total += adapter.getCount() + 1;
			if(sectionCount >= section)
				return total;
		}
		return sectionCount;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public int getSectionForPosition(int position)
	{
		int total = 1;
		int sectionCount = 0;
		for(Adapter adapter : this.sections.values())
		{
			if(position <= total)
				return sectionCount;
			total += adapter.getCount() + 1;
			sectionCount++;
		}
		return sectionCount;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public Object[] getSections()
	{
		return sectionsValues.toArray();
	}

	/**
	 * (Ignore)
	 */
	public void DoExternalClick(int pos, View view)
	{
		if(ltItemChanged != null)
		{
			ltItemChanged.CallIn(par, pos, getItem(pos));
		}
	}

	/**
	 * Add section
	 * @param header of section
	 * @param id of LGAdapterView
	 */
	@LuaFunction(manual = false, methodName = "AddSection", arguments = { String.class, String.class })
	public LGAdapterView AddSection(String header, String id)
	{
		LGAdapterView lgav = new LGAdapterView(mLc, id);
		sectionsValues.add(header);
		sections.put(header, lgav);
		return lgav;
	}

	/**
	 * Remove section
	 * @param header value
	 */
	@LuaFunction(manual = false, methodName = "RemoveSection", arguments = { String.class })
	public void RemoveSection(String header)
	{
		sectionsValues.remove(header);
		sections.remove(header);
	}

	/**
	 * Add Value to adapter
	 * @param id of value
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "AddValue", arguments = { Integer.class, Object.class })
	public void AddValue(Integer id, Object value)
	{
		values.put(id, value);
		notifyDataSetChanged();
	}

	/**
	 * Remove Value from adapter
	 * @param id of value
	 */
	@LuaFunction(manual = false, methodName = "RemoveValue", arguments = { Integer.class })
	public void RemoveValue(Integer id)
	{
		values.remove(id);
		notifyDataSetChanged();
	}

	/**
	 * Remove all values from adapter
	 */
	@LuaFunction(manual = false, methodName = "Clear")
	public void Clear()
	{
		values.clear();
		notifyDataSetChanged();
	}

	/**
	 * Used to set adapterview function
	 * @param lt +fun(adapter: LGAdapterView, parent: LGView, position: number, object: userdata):void
	 */
	@LuaFunction(manual = false, methodName = "SetOnAdapterView", arguments = { LuaTranslator.class })
	public void SetOnAdapterView(LuaTranslator lt)
	{
		ltOnAdapterView = lt;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		if(id == null)
			return "LGAdapterView";
		return id;
	}
}
