package android.widget;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.DisplayMetrics;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;

/**
 * AdapterView
 */
@LuaClass(className = "LGAdapterView")
public class LGAdapterView extends BaseAdapter implements Filterable, SpinnerAdapter, SectionIndexer, LuaInterface
{
	public Map<String, Adapter> sections = new LinkedHashMap<String,Adapter>();
	public final ArrayAdapter<String> headers;
	public ArrayList<String> sectionsValues = new ArrayList<String>();
	public ArrayList<Object> values = new ArrayList<>();
	public final static int TYPE_SECTION_HEADER = 0;
	public Object parent;

	private LuaContext mLc;
	private String id;
	private LuaTranslator ltItemChanged = null;
	public LGListView par = null;
	private LGView lastSelectedView = null;
	private int lastPosition = -1;
	private LuaTranslator ltOnAdapterView;

	private LayoutInflater mInflater;
	private final Object mLock = new Object();
	private ArrayList<Object> mOriginalValues = new ArrayList<>();
	private ArrayFilter mFilter;
	private int mDropDownResource = android.R.layout.simple_dropdown_item_1line;

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
			return (Object) values.get(position);
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
			if(v instanceof ViewGroup)
				((ViewGroup)v).removeAllViews();

			LGView vF;
			if(ltOnAdapterView != null) {
				vF = (LGView) ltOnAdapterView.CallIn(par, position, getItem(position), v, mLc);
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
			}
			else
			{
				vF = LGView.Create(mLc);
				vF.view = getDropDownView(position, convertView, parent);
			}
			v = vF.view;
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
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if(mInflater == null)
			mInflater = LayoutInflater.from(mLc.GetContext());
		return createViewFromResource(mInflater, position, convertView, parent, mDropDownResource);
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
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "AddValue", arguments = { Object.class })
	public void AddValue(Object value)
	{
		values.add(value);
		mOriginalValues.add(value);
	}

	/**
	 * Remove Value from adapter
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "RemoveValue", arguments = { Object.class })
	public void RemoveValue(Object value)
	{
		values.remove(value);
		mOriginalValues.remove(value);
	}

	/**
	 * Set Values of adapter
	 * @param values
	 */
	@LuaFunction(manual = false, methodName = "SetValues", arguments = { Object.class })
	public void SetValues(Object values) {
		if(values instanceof ArrayList)
		{
			for(Object entry : ((ArrayList<Object>)values))
			{
				AddValue(entry);
			}
		}
	}

	/**
	 * Remove all values from adapter
	 */
	@LuaFunction(manual = false, methodName = "Clear")
	public void Clear()
	{
		values.clear();
		mOriginalValues.clear();
	}

	/**
	 * Notify data set changed
	 */
	@LuaFunction(manual = false, methodName = "Notify")
	public void Notify()
	{
		notifyDataSetChanged();
	}

	@LuaFunction(manual = false, methodName = "SetDropdownResource", arguments = { LuaRef.class })
	public void SetDropdownResource(LuaRef ref)
	{
		mDropDownResource = ref.getRef();
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
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new ArrayFilter();
		}
		return mFilter;
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

	/**
	 * (Ignore)
	 */
	private @NonNull View createViewFromResource(@NonNull LayoutInflater inflater, int position,
								@Nullable View convertView, @NonNull ViewGroup parent, int resource) {
		final View view;
		final TextView text;
		if (convertView == null) {
			view = inflater.inflate(resource, parent, false);
		} else {
			view = convertView;
		}
		text = (TextView) view;
		final Object item = getItem(position);
		if (item instanceof CharSequence) {
			text.setText((CharSequence) item);
		} else {
			text.setText(item.toString());
		}
		return view;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public CharSequence[] getAutofillOptions() {
		// First check if app developer explicitly set them.
		final CharSequence[] explicitOptions = super.getAutofillOptions();
		if (explicitOptions != null) {
			return explicitOptions;
		}
		// Otherwise, only return options that came from static resources.
		if (values == null || values.isEmpty()) {
			return null;
		}
		final int size = values.size();
		final CharSequence[] options = new CharSequence[size];
		values.toArray(options);
		return options;
	}

	/**
	 * (Ignore)
	 */
	private class ArrayFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			final FilterResults results = new FilterResults();
			if (mOriginalValues == null) {
				synchronized (mLock) {
					mOriginalValues = new ArrayList<>(values);
				}
			}
			if (prefix == null || prefix.length() == 0) {
				final ArrayList<Object> list;
				synchronized (mLock) {
					list = new ArrayList<>(mOriginalValues);
				}
				results.values = list;
				results.count = list.size();
			} else {
				final String prefixString = prefix.toString().toLowerCase();
				final ArrayList<Object> values;
				synchronized (mLock) {
					values = new ArrayList<>(mOriginalValues);
				}
				final int count = values.size();
				final ArrayList<Object> newValues = new ArrayList<>();
				for (int i = 0; i < count; i++) {
					final Object value = values.get(i);
					final String valueText = value.toString().toLowerCase();
					// First match against the whole, non-splitted value
					if (valueText.startsWith(prefixString)) {
						newValues.add(value);
					} else {
						final String[] words = valueText.split(" ");
						for (String word : words) {
							if (word.startsWith(prefixString)) {
								newValues.add(value);
								break;
							}
						}
					}
				}
				results.values = newValues;
				results.count = newValues.size();
			}
			return results;
		}
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			//noinspection unchecked
			values = (ArrayList<Object>) results.values;
			if (results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}
	}
}
