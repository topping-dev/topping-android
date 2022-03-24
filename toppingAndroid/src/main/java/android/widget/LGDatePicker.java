package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.DatePicker.OnDateChangedListener;

import java.util.Calendar;
import java.util.Locale;

import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * DatePicker
 */
@LuaClass(className = "LGDatePicker")
public class LGDatePicker extends LGFrameLayout implements LuaInterface
{
	LuaTranslator onDateChanged = null;
	int startDay = -1;
	int startMonth = -1;
	int startYear = -1;

	/**
	 * Creates LGDatePicker Object From Lua.
	 * @param lc
	 * @return LGDatePicker
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGDatePicker.class)
	public static LGDatePicker Create(LuaContext lc)
	{
		return new LGDatePicker(lc);
	}
	
	/**
	 * (Ignore)
	 */
	public LGDatePicker(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGDatePicker(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGDatePicker(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGDatePicker(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.GetLayoutInflater().createView(context, "DatePicker");
		if(view == null)
			view = new DatePicker(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.GetLayoutInflater().createView(context, "DatePicker", attrs);
		if(view == null)
			view = new DatePicker(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.GetLayoutInflater().createView(context, "DatePicker", attrs);
		if(view == null)
			view = new DatePicker(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	@Override
	public void AfterSetup(Context context)
	{
		super.AfterSetup(context);
		if(startDay == -1)
		{
			Calendar now = Calendar.getInstance(Locale.getDefault());
			startDay = now.get(Calendar.DAY_OF_MONTH);
			startMonth = now.get(Calendar.MONTH);
			startYear = now.get(Calendar.YEAR);
		}

		final LGDatePicker self = this;
		((DatePicker)view).init(startYear, startMonth, startDay, new OnDateChangedListener()
		{
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
			{
				if(onDateChanged != null)
					onDateChanged.CallIn(self, Integer.valueOf(dayOfMonth), Integer.valueOf(monthOfYear), Integer.valueOf(year));
			}
		});
	}

	/**
	 * Gets the day value
	 * @return int day
	 */
	@LuaFunction(manual = false, methodName = "GetDay")
	public Integer GetDay()
	{
		return ((DatePicker)view).getDayOfMonth();
	}

	/**
	 * Gets the month value
	 * @return int month
	 */
	@LuaFunction(manual = false, methodName = "GetMonth")
	public Integer GetMonth()
	{
		return ((DatePicker)view).getMonth();
	}

	/**
	 * Gets the year value
	 * @return int year
	 */
	@LuaFunction(manual = false, methodName = "GetYear")
	public Integer GetYear()
	{
		return ((DatePicker)view).getYear();
	}

	/**
	 * Update the date value of picker
	 * @param day
	 * @param month
	 * @param year
	 */
	@LuaFunction(manual = false, methodName = "GetYear", arguments = { Integer.class, Integer.class, Integer.class })
	public void UpdateDate(Integer day, Integer month, Integer year)
	{
		((DatePicker)view).updateDate(year, month, day);
	}

	/**
	 * Sets on date changed listener
	 * @param lt +fun(datePicker: LGDatePicker, day: number, month: number, year: number):void
	 */
	@LuaFunction(manual = false, methodName = "SetOnDateChangedListener", arguments = { LuaTranslator.class })
	public void SetOnDateChangedListener(LuaTranslator lt)
	{
		onDateChanged = lt;
	}
}
