package dev.topping.android.osspecific.utils;

import android.os.Handler;
import android.os.Looper;

public class Defaults
{
    public static void RunOnUiThread(Runnable uiRunnable)
    {
        if(Looper.myLooper() == Looper.getMainLooper())
            uiRunnable.run();
        else
            new Handler(Looper.getMainLooper()).post(uiRunnable);
    }
}
