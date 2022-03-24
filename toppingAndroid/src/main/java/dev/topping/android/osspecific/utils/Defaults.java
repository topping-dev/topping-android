package dev.topping.android.osspecific.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;

public class Defaults
{
    public static void RunOnUiThread(Runnable uiRunnable)
    {
        if(Looper.myLooper() == Looper.getMainLooper())
            uiRunnable.run();
        else
            new Handler(Looper.getMainLooper()).post(uiRunnable);
    }

    public static CharSequence resolveStringAttr(Context context, int attrRes)
    {
        Resources.Theme theme = context.getTheme();
        TypedValue value = new TypedValue();

        if (!theme.resolveAttribute(attrRes, value, true)) {
            return null;
        }

        return value.string;
    }
}
