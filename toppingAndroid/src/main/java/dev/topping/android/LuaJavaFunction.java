package dev.topping.android;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;

/**
 * Used to define java functions in lua
 */
@LuaClass(className = "LuaJavaFunction")
public class LuaJavaFunction implements LuaFunction
{
	boolean manual = false;
	Class<?> self = Void.class;
	String methodName;
	Class<?>[] arguments;
	List<KParameter> kotlinArguments;
	Method mi;
	KFunction kf;
	Object kfObject;

	public LuaJavaFunction(boolean manualP, Class<?> selfP, String methodNameP, Class<?>[] argumentsP, Method miP)
	{
		manual = manualP;
		self = selfP;
		methodName = methodNameP;
		arguments = argumentsP;
		mi = miP;
	}

	public LuaJavaFunction(boolean manualP, Class<?> selfP, String methodNameP, Class<?>[] argumentsP, KFunction kfP)
	{
		this(manualP, selfP, methodNameP, argumentsP, kfP, null);
	}

	public LuaJavaFunction(boolean manualP, Class<?> selfP, String methodNameP, Class<?>[] argumentsP, KFunction kfP, Object kfObjectP)
	{
		manual = manualP;
		self = selfP;
		methodName = methodNameP;
		arguments = argumentsP;
		kf = kfP;
		kfObject = kfObjectP;
	}

	public LuaJavaFunction(boolean manualP, Class<?> selfP, String methodNameP, List<KParameter> argumentsP, KFunction kfP)
	{
		this(manualP, selfP, methodNameP, argumentsP, kfP, null);
	}

	public LuaJavaFunction(boolean manualP, Class<?> selfP, String methodNameP, List<KParameter> argumentsP, KFunction kfP, Object kfObjectP)
	{
		manual = manualP;
		self = selfP;
		methodName = methodNameP;
		kotlinArguments = argumentsP;
		kf = kfP;
		kfObject = kfObjectP;
	}

	@Override
	public Class<? extends Annotation> annotationType() {
		return null;
	}

	@Override
	public boolean manual() {
		return manual;
	}

	@Override
	public Class<?> self() {
		return self;
	}

	@Override
	public String methodName() {
		return methodName;
	}

	@Override
	public Class<?>[] arguments() {
		return arguments;
	}

	public Method method()
	{
		return mi;
	}

	public KFunction kotlinMethod()
	{
		return kf;
	}

	public Object kotlinObject()
	{
		return kfObject;
	}
}
