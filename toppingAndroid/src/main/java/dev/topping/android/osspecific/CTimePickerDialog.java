package dev.topping.android.osspecific;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import dev.topping.android.LuaDialog;
import dev.topping.android.LuaTranslator;

public class CTimePickerDialog extends TimePickerDialog
{
	private LuaDialog dialog;
	private LuaTranslator ltOnTimeChanged;
	
	public CTimePickerDialog(Context context, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView)
	{
		super(context, callBack, hourOfDay, minute, is24HourView);
	}
	
	public void SetLuaData(LuaDialog dialog, LuaTranslator onDateChanged)
	{
		this.dialog = dialog;
		ltOnTimeChanged = onDateChanged;
	}
	
	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
	{
		super.onTimeChanged(view, hourOfDay, minute);
		if(ltOnTimeChanged != null)
			ltOnTimeChanged.callIn(dialog, hourOfDay, minute);
	}

}
