package dev.topping.android.osspecific;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import dev.topping.android.LuaDialog;
import dev.topping.android.LuaTranslator;

public class CDatePickerDialog extends DatePickerDialog
{
	private LuaDialog dialog;
	private LuaTranslator ltOnDateChanged;
	
	public CDatePickerDialog(Context context, OnDateSetListener callBack,
			int year, int monthOfYear, int dayOfMonth)
	{
		super(context, callBack, year, monthOfYear, dayOfMonth);
	}
	
	public void SetLuaData(LuaDialog dialog, LuaTranslator onDateChanged)
	{
		this.dialog = dialog;
		ltOnDateChanged = onDateChanged;
	}
	
	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day)
	{
		super.onDateChanged(view, year, month, day);
		if(ltOnDateChanged != null)
			ltOnDateChanged.CallIn(dialog, day, month + 1, year);
	}
	
}
