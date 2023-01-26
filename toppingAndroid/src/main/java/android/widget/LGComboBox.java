package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.osspecific.utils.ComboData;
import dev.topping.android.osspecific.utils.Defaults;

/**
 * ComboBox
 */
@LuaClass(className = "LGComboBox")
public class LGComboBox extends LGEditText implements LuaInterface
{
	ArrayAdapter<ComboData> mAdapter;
	private String mCustom = "";
	private String mDelete = "";
	ComboData selectedData = null;

	/**
	 * Creates LGComboBox Object From Lua.
	 * @param lc
	 * @return LGComboBox
	 */
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGComboBox.class)
	public static LGComboBox create(LuaContext lc)
	{
		return new LGComboBox(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGComboBox(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGComboBox(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGComboBox(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGComboBox(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.getLayoutInflater().createView(context, "Spinner");
		if(view == null)
			view = new Spinner(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.getLayoutInflater().createView(context, "Spinner", attrs);
		if(view == null)
			view = new Spinner(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.getLayoutInflater().createView(context, "Spinner", attrs);
		if(view == null)
			view = new Spinner(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	@Override
	public void AfterSetup(Context context)
	{
		super.AfterSetup(context);
		mAdapter = new ArrayAdapter<>(context,
				android.R.layout.simple_spinner_dropdown_item, new ArrayList<ComboData>());
		((Spinner)view).setAdapter(mAdapter);
	}

	/**
	 * Add combo item to combobox
	 * @param id of combobox
	 * @param tag
	 */
	@LuaFunction(manual = false, methodName = "addItem", arguments = { String.class, Object.class })
	public void addItem(String id, Object tag)
	{
		ComboData cd = new ComboData();
		cd.name = id;
		cd.tag = tag;
		mAdapter.add(cd);
	}

	/**
	 * Set items
	 * @param values
	 */
	@LuaFunction(manual = false, methodName = "setItems", arguments = { Object.class })
	public void setItems(Object values)
	{
		if(values instanceof HashMap)
		{
			for(Map.Entry<Object, Object> entry : ((HashMap<Object, Object>)values).entrySet())
			{
				addItem((String) entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * Show custom button
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "showCustom", arguments = { Integer.class })
	public void showCustom(Integer value)
	{
		if(value.intValue() == 1)
		{
			ComboData cd = new ComboData();
			cd.name = mCustom;
			cd.tag = null;
			cd.type = -1;
			mAdapter.insert(cd, 0);
			Defaults.RunOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					mAdapter.notifyDataSetChanged();
				}
			});
		}
		else
		{
			ComboData cdToRemove = null;
			for(int i = 0; i < mAdapter.getCount(); i++)
			{
				ComboData cd = mAdapter.getItem(i);
				if(cd.type == -1)
				{
					cdToRemove = cd;
					break;
				}
			}

			if(cdToRemove != null)
			{
				mAdapter.remove(cdToRemove);
				Defaults.RunOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						mAdapter.notifyDataSetChanged();
					}
				});
			}
		}
	}

	/**
	 * Show delete button
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "showDelete", arguments = { Integer.class })
	public void showDelete(Integer value)
	{
		if(value.intValue() == 1)
		{
			ComboData cd = new ComboData();
			cd.name = mDelete;
			cd.tag = null;
			cd.type = -2;
			mAdapter.insert(cd, 0);
			Defaults.RunOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					mAdapter.notifyDataSetChanged();
				}
			});
		}
		else
		{
			ComboData cdToRemove = null;
			for(int i = 0; i < mAdapter.getCount(); i++)
			{
				ComboData cd = mAdapter.getItem(i);
				if(cd.type == -2)
				{
					cdToRemove = cd;
					break;
				}
			}

			if(cdToRemove != null)
			{
				mAdapter.remove(cdToRemove);
				Defaults.RunOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						mAdapter.notifyDataSetChanged();
					}
				});
			}
		}
	}

	/**
	 * Set combobox editable
	 * @param value (1 or 0)
	 */
    /*@LuaFunction(manual = false, methodName = "setEditable", arguments = { Integer.class })
    public void setEditable(Integer value)
    {
    	((ComboBox)view).editable = value.intValue() == 1 ? true : false;
    	if(value == 0)
    		((ComboBox)view).clearFocus();
    }*/

	/**
	 * Sets the selected value
	 */
	@LuaFunction(manual = false, methodName = "setSelectedIndex", arguments = { Integer.class })
	public void setSelectedIndex(int index)
	{
		((Spinner)view).setSelection(index);
	}

	/**
	 * Gets the selected name
	 */
	@LuaFunction(manual = false, methodName = "getSelectedName")
	public String getSelectedName()
	{
		if(selectedData != null)
			return selectedData.name;
		return null;
	}

	/**
	 * Gets the selected tag
	 * @return tag value
	 */
	@LuaFunction(manual = false, methodName = "getSelectedTag")
	public Object getSelectedTag()
	{
		if(selectedData != null)
			return selectedData.tag;

		return null;
	}

	/**
	 * Sets combo changed listener
	 * @param lt +fun(comboBox: LGComboBox, context: LuaContext, name: string, tag: userdata):void
	 */
	@LuaFunction(manual = false, methodName = "setOnComboChangedListener", arguments = { LuaTranslator.class })
	public void setOnComboChangedListener(final LuaTranslator lt)
	{
		if(lt == null)
		{
			((Spinner)view).setOnItemClickListener(null);
			return;
		}

		((Spinner)view).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
			{
				selectedData = mAdapter.getItem(i);
				lt.callIn(lc, selectedData.name, selectedData.tag);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView)
			{

			}
		});
	}
}
