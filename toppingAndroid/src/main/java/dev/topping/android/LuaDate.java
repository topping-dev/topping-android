package dev.topping.android;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

/**
 * LuaDate class.
 * LuaDate class is used to manipulate date values.
 */
@LuaClass(className = "LuaDate")
public class LuaDate implements LuaInterface
{
	Calendar dateStore = null;
	
	/**
	 * Returns the current LuaDate
	 * @return LuaDate
	 */
	@LuaFunction(manual = false, methodName = "now", self = LuaDate.class)
	public static LuaDate now()
	{
		LuaDate ret = new LuaDate();
		ret.dateStore = Calendar.getInstance(Locale.getDefault());
		return ret;
	}
	
	/**
	 * Creates LuaDate with given parameters
	 * @param day
	 * @param month
	 * @param year
	 * @return LuaDate
	 */
	@LuaFunction(manual = false, methodName = "createDate", self = LuaDate.class, arguments = { Integer.class, Integer.class, Integer.class })
	public static LuaDate createDate(int day, int month, int year)
	{
		LuaDate ret = new LuaDate();
		ret.dateStore = Calendar.getInstance(Locale.getDefault());
		ret.dateStore.set(year, month - 1, day, 0, 0, 0);
		return ret;
	}
	
	/**
	 * Creates LuaDate with given parameters
	 * @param day
	 * @param month
	 * @param year
	 * @param hour
	 * @param minute
	 * @param second
	 * @return LuaDate
	 */
	@LuaFunction(manual = false, methodName = "createDateWithTime", self = LuaDate.class, arguments = { Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class })
	public static LuaDate createDateWithTime(int day, int month, int year, int hour, int minute, int second)
	{
		LuaDate ret = new LuaDate();
		ret.dateStore = Calendar.getInstance(Locale.getDefault());
		ret.dateStore.set(year, month - 1, day, hour, minute, second);
		return ret;
	}
	
	/**
	 * Gets the day of month
	 * @return int
	 */
	@LuaFunction(manual = false, methodName = "getDay")
	public Integer getDay()
	{
		return dateStore.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Sets the day of month
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "setDay", arguments = { Integer.class })
	public void setDay(Integer val)
	{
		dateStore.set(Calendar.DAY_OF_MONTH, val);
	}
	
	/**
	 * Gets the month
	 * @return int
	 */
	@LuaFunction(manual = false, methodName = "getMonth")
	public Integer getMonth()
	{
		return dateStore.get(Calendar.MONTH);
	}
	
	/**
	 * Sets the month
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "setMonth", arguments = { Integer.class })
	public void setMonth(Integer val)
	{
		dateStore.set(Calendar.MONTH, val);
	}
	
	/**
	 * Gets the year
	 * @return int
	 */
	@LuaFunction(manual = false, methodName = "getYear")
	public Integer getYear()
	{
		return dateStore.get(Calendar.YEAR);
	}
	
	/**
	 * Sets the year
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "setYear", arguments = { Integer.class })
	public void setYear(Integer val)
	{
		dateStore.set(Calendar.YEAR, val);
	}
	
	/**
	 * Gets the hour of the day (24)
	 * @return int
	 */
	@LuaFunction(manual = false, methodName = "getHour")
	public Integer getHour()
	{
		return dateStore.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * Sets the hour of the day (24)
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "setHour", arguments = { Integer.class })
	public void setHour(Integer val)
	{
		dateStore.set(Calendar.HOUR_OF_DAY, val);
	}	
	
	/**
	 * Gets the minute
	 * @return int
	 */
	@LuaFunction(manual = false, methodName = "getMinute")
	public Integer getMinute()
	{
		return dateStore.get(Calendar.MINUTE);
	}
	
	/**
	 * Set the minute
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "setMinute", arguments = { Integer.class })
	public void setMinute(Integer val)
	{
		dateStore.set(Calendar.MINUTE, val);
	}	
	
	/**
	 * Gets the Second
	 * @return int
	 */
	@LuaFunction(manual = false, methodName = "getSecond")
	public Integer getSecond()
	{
		return dateStore.get(Calendar.SECOND);
	}
	
	/**
	 * Sets the second
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "setSecond", arguments = { Integer.class })
	public void setSecond(Integer val)
	{
		dateStore.set(Calendar.SECOND, val);
	}
	
	/**
	 * Gets the millisecond
	 * @return int
	 */
	@LuaFunction(manual = false, methodName = "getMilliSecond")
	public Integer getMilliSecond()
	{
		return dateStore.get(Calendar.MILLISECOND);
	}
	
	/**
	 * Sets the millisecond
	 * @param val
	 */
	@LuaFunction(manual = false, methodName = "setMilliSecond", arguments = { Integer.class })
	public void setMilliSecond(Integer val)
	{
		dateStore.set(Calendar.MILLISECOND, val);
	}
	
	/**
	 * Gets the string representation of date
	 * 
	 * D 	day in year 	(Number) 	189
	 * E 	day of week 	(Text) 	Tuesday
	 * F 	day of week in month 	(Number) 	2 (2nd Wed in July)
	 * G 	era designator 	(Text) 	AD
	 * H 	hour in day (0-23) 	(Number) 	0
	 * K 	hour in am/pm (0-11) 	(Number) 	0
	 * L 	stand-alone month 	(Text/Number) 	July / 07
	 * M 	month in year 	(Text/Number) 	July / 07
	 * S 	fractional seconds 	(Number) 	978
	 * W 	week in month 	(Number) 	2
	 * Z 	time zone (RFC 822) 	(Timezone) 	-0800
	 * a 	am/pm marker 	(Text) 	PM
	 * c 	stand-alone day of week 	(Text/Number) 	Tuesday / 2
	 * d 	day in month 	(Number) 	10
	 * h 	hour in am/pm (1-12) 	(Number) 	12
	 * k 	hour in day (1-24) 	(Number) 	24
	 * m 	minute in hour 	(Number) 	30
	 * s 	second in minute 	(Number) 	55
	 * w 	week in year 	(Number) 	27
	 * y 	year 	(Number) 	2010
	 * z 	time zone 	(Timezone) 	Pacific Standard Time
	 * ' 	escape for text 	(Delimiter) 	'Date='
	 * '' 	single quote 	(Literal) 	'o''clock'
	 * @param frmt
	 * @return String
	 */
	public String toString(String frmt)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(frmt);
		return sdf.format(dateStore.getTime());
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId() 
	{
		return "LuaDate";
	}
}
