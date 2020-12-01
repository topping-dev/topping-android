package dev.topping.android.backend;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import dev.topping.android.ToppingEngine;

public abstract class LuaLoadHandler extends Handler
{
	public static final int INIT_MESSAGE = 1;
	public static final int LOAD_MESSAGE = 2;
	public static final int FINISH_MESSAGE = 3;
	
	private Activity ctx;
	private ProgressDialog mDialog;
	
	public LuaLoadHandler(Activity ctx, Looper looper)
	{
		super(looper);
		this.ctx = ctx;
	}
	
	@Override
	public void handleMessage(Message msg)
	{
		super.handleMessage(msg);
		switch(msg.what)
		{
			case INIT_MESSAGE:
			{
				ctx.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						mDialog = new ProgressDialog(ctx);
						mDialog.setIndeterminate(false);
						mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
						mDialog.setMax(100);
				        mDialog.setMessage("Please wait...");
				        mDialog.setCancelable(false);
				        ToppingEngine.getInstance().loadDialog = mDialog;
				        mDialog.show();
					}
				});
		        sendEmptyMessage(LOAD_MESSAGE);
			}break;
			case LOAD_MESSAGE:
			{
				ToppingEngine.getInstance().Startup(ctx);
				sendEmptyMessage(FINISH_MESSAGE);
				
			}break;
			case FINISH_MESSAGE:
				ctx.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						mDialog.dismiss();
						OnFinished();
					}
				});
				break;
		}
	}
	
	public abstract void OnFinished();
}
