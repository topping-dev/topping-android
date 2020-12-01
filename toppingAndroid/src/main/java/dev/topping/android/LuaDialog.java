package dev.topping.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.text.format.DateFormat;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.backend.LuaStaticVariable;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;
import dev.topping.android.osspecific.CDatePickerDialog;
import dev.topping.android.osspecific.CTimePickerDialog;

import java.util.Calendar;
import java.util.Locale;

/**
 * Lua dialog class.
 * This class is used to create dialogs and manupilate it from lua.
 * There are five types of dialogs.
 * DIALOG_TYPE_NORMAL
 * DIALOG_TYPE_PROGRESS
 * DIALOG_TYPE_PROGRESS_INDETERMINATE
 * DIALOG_TYPE_DATEPICKER
 * DIALOG_TYPE_TIMEPICKER
 */
@LuaClass(className = "LuaDialog")
public class LuaDialog implements LuaInterface
{
	AlertDialog.Builder builder = null;
	AlertDialog dialog = null;
	private int dialogType = DIALOG_TYPE_NORMAL;
	private LuaTranslator ltDateSelected;
	private LuaTranslator ltTimeSelected;
	/**
	 * for creating normal dialog
	 */
	@LuaStaticVariable
	public final static int DIALOG_TYPE_NORMAL = 0x01;
	/**
	 * for creating progress dialog
	 */
	@LuaStaticVariable
	public final static int DIALOG_TYPE_PROGRESS = 0x02;
	/**
	 * for creating indeterminate progress dialog
	 */
	@LuaStaticVariable
	public final static int DIALOG_TYPE_PROGRESS_INDETERMINATE = LuaDialog.DIALOG_TYPE_PROGRESS | 0x04;
	/**
	 * for creating date picker dialog
	 */
	@LuaStaticVariable
	public final static int DIALOG_TYPE_DATEPICKER = 0x08;
	/**
	 * for creating time picker dialog
	 */
	@LuaStaticVariable
	public final static int DIALOG_TYPE_TIMEPICKER = 0x10;

	/**
	 * Shows a messagebox
	 * @param context lua context value
	 * @param title title text
	 * @param content content text
	 */
	@LuaFunction(manual = false, methodName = "MessageBox", arguments = { LuaContext.class, String.class, String.class }, self = LuaDialog.class)
	public static void MessageBox(LuaContext context, String title, String content)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context.GetContext());
		builder.setPositiveButton("Ok", null);
		builder.setMessage(content);
		builder.setTitle(title);
		builder.show();
	}

	/**
	 * Creates LuaDialog for build
	 * @param context
	 * @param dialogType +"LuaDialog.DIALOG_TYPE_NORMAL" | "LuaDialog.DIALOG_TYPE_PROGRESS" | "LuaDialog.DIALOG_TYPE_NORMAL" | "LuaDialog.DIALOG_TYPE_PROGRESS_INDETERMINATE" | "LuaDialog.DIALOG_TYPE_DATEPICKER" | "LuaDialog.DIALOG_TYPE_TIMEPICKER"
	 * @return LuaDialog
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class, Integer.class }, self = LuaDialog.class)
	public static LuaDialog Create(LuaContext context, int dialogType)
	{
		LuaDialog ld = new LuaDialog();
		ld.dialogType = dialogType;
		switch(dialogType)
		{
		case DIALOG_TYPE_NORMAL:
		{
			ld.builder = new AlertDialog.Builder(context.GetContext());
		} break;
		case DIALOG_TYPE_PROGRESS:
		{
			ld.dialog = new ProgressDialog(context.GetContext());
			((ProgressDialog)ld.dialog).setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			((ProgressDialog)ld.dialog).setIndeterminate(false);
		} break;
		case DIALOG_TYPE_PROGRESS_INDETERMINATE:
		{
			ld.dialog = new ProgressDialog(context.GetContext());
		} break;
		case DIALOG_TYPE_DATEPICKER:
		{
			Calendar c = Calendar.getInstance(Locale.getDefault());
			ld.dialog = new CDatePickerDialog(context.GetContext(), null, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		} break;
		case DIALOG_TYPE_TIMEPICKER:
		{
			Calendar c = Calendar.getInstance(Locale.getDefault());
			ld.dialog = new CTimePickerDialog(context.GetContext(), null, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), DateFormat.is24HourFormat(context.GetContext()));
		} break;
		default:
		{
			ld.dialogType = DIALOG_TYPE_NORMAL;
			ld.builder = new AlertDialog.Builder(context.GetContext());
		} break;
		}
		return ld;
	}

	/**
	 * Sets the positive button of LuaDialog
	 * @param title title of the button
	 * @param action action to do when button is pressed
	 */
	@LuaFunction(manual = false, methodName = "SetPositiveButton", arguments = { String.class, LuaTranslator.class })
	public void SetPositiveButton(String title, final LuaTranslator action)
	{
		if(dialogType == DIALOG_TYPE_NORMAL)
		{
			builder.setPositiveButton(title, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					action.CallIn();
				}
			});
		}
	}

	/**
	 * Sets the negative button of LuaDialog
	 * @param title title of the button
	 * @param action action to do when button is pressed
	 */
	@LuaFunction(manual = false, methodName = "SetNegativeButton", arguments = { String.class, LuaTranslator.class })
	public void SetNegativeButton(String title, final LuaTranslator action)
	{
		if(dialogType == DIALOG_TYPE_NORMAL)
		{
			builder.setNegativeButton(title, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					action.CallIn();
				}
			});
		}
	}

	/**
	 * Sets the title of the LuaDialog
	 * @param title
	 */
	@LuaFunction(manual = false, methodName = "SetTitle", arguments = { String.class })
	public void SetTitle(String title)
	{
		if(dialogType == DIALOG_TYPE_NORMAL)
			builder.setTitle(title);
		else
			dialog.setTitle(title);
	}

	/**
	 * Sets the title of the LuaDialog
	 * @param titleRef
	 */
	@LuaFunction(manual = false, methodName = "SetTitleRef", arguments = { String.class })
	public void SetTitleRef(LuaRef titleRef)
	{
		if(dialogType == DIALOG_TYPE_NORMAL)
			builder.setTitle(titleRef.getRef());
		else
			dialog.setTitle(titleRef.getRef());
	}

	/**
	 * Sets the message of the LuaDialog
	 * @param message
	 */
	@LuaFunction(manual = false, methodName = "SetMessage", arguments = { String.class })
	public void SetMessage(String message)
	{
		if(dialogType == DIALOG_TYPE_NORMAL)
			builder.setMessage(message);
		else
			dialog.setMessage(message);
	}

	/**
	 * Sets the message of the LuaDialog
	 * @param messageRef
	 */
	@LuaFunction(manual = false, methodName = "SetMessageRef", arguments = { String.class })
	public void SetMessage(LuaRef messageRef)
	{
		if(dialogType == DIALOG_TYPE_NORMAL)
			builder.setMessage(messageRef.getRef());
		else
			dialog.setMessage(dialog.getContext().getResources().getString(messageRef.getRef()));
	}

	/**
	 * Sets the value of the progress bar
	 * (progress bar is needed otherwise it wont effect anything)
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "SetProgress", arguments = { Integer.class })
	public void SetProgress(Integer value)
	{
		if((dialogType & DIALOG_TYPE_PROGRESS) > 0)
			((ProgressDialog)dialog).setProgress(value);
	}

	/**
	 * Sets the maximum value of the progress bar
	 * (progress bar is needed otherwise it wont effect anything)
	 * @param value
	 */
	@LuaFunction(manual = false, methodName = "SetMax", arguments = { Integer.class })
	public void SetMax(Integer value)
	{
		if((dialogType & DIALOG_TYPE_PROGRESS) > 0)
			((ProgressDialog)dialog).setMax(value);
	}

	/**
	 * Sets the date of the date picker
	 * (date picker dialog is needed otherwise it wort effect anything)
	 * @param date
	 */
	@LuaFunction(manual = false, methodName = "SetDate", arguments = { LuaDate.class })
	public void SetDate(LuaDate date)
	{
		if(dialogType == DIALOG_TYPE_DATEPICKER)
		{
			((CDatePickerDialog)dialog).updateDate(date.GetYear(), date.GetMonth(), date.GetDay());
		}
	}

	/**
	 * Sets the date of the date picker
	 * (date picker dialog is needed otherwise it wort effect anything)
	 * @param day
	 * @param month
	 * @param year
	 */
	@LuaFunction(manual = false, methodName = "SetDateManual", arguments = { Integer.class, Integer.class, Integer.class })
	public void SetDateManual(int day, int month, int year)
	{
		if(dialogType == DIALOG_TYPE_DATEPICKER)
			((CDatePickerDialog)dialog).updateDate(year, month - 1, day);
	}

	/**
	 * Sets the time of the time picker
	 * (time picker dialog is needed otherwise it wort effect anything)
	 * @param date
	 */
	@LuaFunction(manual = false, methodName = "SetTime", arguments = { LuaDate.class })
	public void SetTime(LuaDate date)
	{
		if(dialogType == DIALOG_TYPE_TIMEPICKER)
		{
			((CTimePickerDialog)dialog).updateTime(date.GetHour(), date.GetMinute());
		}
	}

	/**
	 * Sets the time of the time picker
	 * (time picker dialog is needed otherwise it wort effect anything)
	 * @param hour
	 * @param minute
	 */
	@LuaFunction(manual = false, methodName = "SetTimeManual", arguments = { Integer.class, Integer.class })
	public void SetTimeManual(int hour, int minute)
	{
		if(dialogType == DIALOG_TYPE_TIMEPICKER)
			((CTimePickerDialog)dialog).updateTime(hour, minute);
	}

	/**
	 * Shows the created dialog of LuaDialog
	 */
	@LuaFunction(manual = false, methodName = "Show")
	public void Show()
	{
		if(dialogType == DIALOG_TYPE_NORMAL)
			dialog = builder.show();
		else
			dialog.show();
	}

	/**
	 * Dismiss the created dialog
	 */
	@LuaFunction(manual = false, methodName = "Dismiss")
	public void Dismiss()
	{
		if(dialog != null)
			dialog.dismiss();
	}

	/**
	 * Sets combo changed listener
	 * @param lt +fun(datePicker: LuaDialog, context: LuaContext, day: number, month: number, year: number):void
	 */
	@LuaFunction(manual = false, methodName = "SetDateSelectedListener", arguments = { LuaTranslator.class })
	public void SetDateSelectedListener(final LuaTranslator lt)
	{
		if(dialogType == DIALOG_TYPE_DATEPICKER)
			((CDatePickerDialog)dialog).SetLuaData(this, lt);
	}

	/**
	 * Sets combo changed listener
	 * @param lt +fun(timePicker: LuaDialog, context: LuaContext, hour: number, minute: number):void
	 */
	@LuaFunction(manual = false, methodName = "SetTimeSelectedListener", arguments = { LuaTranslator.class })
	public void SetTimeSelectedListener(final LuaTranslator lt)
	{
		if(dialogType == DIALOG_TYPE_TIMEPICKER)
			((CTimePickerDialog)dialog).SetLuaData(this, lt);
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaDialog";
	}
}
