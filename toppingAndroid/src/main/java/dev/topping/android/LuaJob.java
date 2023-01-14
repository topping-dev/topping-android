package dev.topping.android;

import android.content.Context;

import java.util.concurrent.CancellationException;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/**
 * Lua Job
 */
@LuaClass(className = "LuaJob")
public class LuaJob implements LuaInterface
{
	private final Job job;

	LuaJob(Job job) {
		this.job = job;
	}

	/**
	 * cancel jobs
	 */
	@LuaFunction(
		manual = false,
		methodName = "cancel"
	)
	public void cancel() {
		job.cancel(new CancellationException());
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaJob";
	}

}
