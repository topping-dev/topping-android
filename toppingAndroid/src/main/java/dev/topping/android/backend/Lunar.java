package dev.topping.android.backend;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.naef.jnlua.IDelegate;
import com.naef.jnlua.JavaObjectFunction;
import com.naef.jnlua.Lua;
import com.naef.jnlua.LuaException;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import dev.topping.android.LuaJavaFunction;
import dev.topping.android.LuaNativeObject;
import dev.topping.android.LuaTranslator;
import dev.topping.android.ToppingEngine;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty1;

public class Lunar
{
    private Lunar()
    {

    }

    public static String RemoveChar(String from, char w)
    {
        char[] arr = from.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : arr)
        {
            if (c != w)
                sb.append(c);
        }
        return sb.toString();
    }

    public static byte[] sizeof(Object obj)
    {
    	try
    	{
    	    ByteArrayOutputStream byteObject = new ByteArrayOutputStream();
    	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteObject);
    	    objectOutputStream.writeObject(obj);
    	    objectOutputStream.flush();
    	    objectOutputStream.close();
    	    byteObject.close();

    	    return byteObject.toByteArray();
    	}
    	catch(Exception e)
    	{
    		return new byte[0];
    	}

    }

    public static class KotlinCompanionObject
	{
		public KotlinCompanionObject(Object instance, KFunction[] functions, KProperty[] properties) {
			this.instance = instance;
			this.functions = functions;
			this.properties = properties;
		}
		Object instance;
		KFunction[] functions;
		KProperty[] properties;
	}

    static IDelegate tostring_T = Lua.lua_CFunction.build(Lunar.class, "tostring_T");
    static IDelegate gc_T = Lua.lua_CFunction.build(Lunar.class, "gc_T");
    public static HashMap<Class<?>, Method[]> methodMap = new HashMap<>();
	public static HashMap<Class<?>, Field[]> fieldMap = new HashMap<>();
	public static HashMap<Class<?>, KotlinCompanionObject> kotlinCompanionMap = new HashMap<>();
	public static void Register(LuaState L, Class<?> cls) {
		Register(L, cls, false);
	}
    public static void Register(LuaState L, Class<?> cls, boolean loadAll)
    {
        //String name = RemoveChar(cls.getName(), '.');

    	LuaClass lc = cls.getAnnotation(LuaClass.class);
    	String name = "";
    	if(lc == null)
    	{
    		Log.e("LuaEngine", "No class annotation on " + cls.getName());
    		String[] arr = cls.getName().split("\\.");
    		name = arr[arr.length - 1];
    		//return;
    	}
    	else
    		name = lc.className();

	    Lua.lua_newtable(L);
	    int methods = Lua.lua_gettop(L);

        Lua.luaL_newmetatable(L, name);
	    int metatable = Lua.lua_gettop(L);

	    Lua.luaL_newmetatable(L, "DO NOT TRASH");
	    Lua.lua_pop(L, 1);

	    // store method table in globals so that
	    // scripts can add functions written in Lua.
	    Lua.lua_pushvalue(L, methods);
        Lua.lua_setfield(L, Lua.LUA_GLOBALSINDEX, name);

	    // hide metatable from Lua getmetatable()
	    Lua.lua_pushvalue(L, methods);
	    Lua.lua_setfield(L, metatable, "__metatable");

	    Lua.lua_pushvalue(L, methods);
	    Lua.lua_setfield(L, metatable, "__index");

	    Lua.lua_pushcfunction(L, tostring_T);
	    Lua.lua_setfield(L, metatable, "__tostring");

	    Lua.lua_pushcfunction(L, gc_T);
	    Lua.lua_setfield(L, metatable, "__gc");

	    Lua.lua_newtable(L);                // mt for method table
	    Lua.lua_setmetatable(L, methods);

	    //Method [] methodInfos = cls.getMethods();
		Method[] methodInfos = methodMap.get(cls);
		if(methodInfos != null)
		{
			for (Method m : methodInfos)
			{
				if (loadAll)
				{
					Lua.lua_pushstring(L, m.getName());
					LuaJavaFunction lf = new LuaJavaFunction(false, cls, m.getName(), m.getParameterTypes(), m);
					//Lua.lua_pushlightuserdata(L, lf);
					//Lua.lua_pushuserdata(L, lf);
					if ((m.getModifiers() & Modifier.STATIC) > 0)
					{
						L.pushJavaFunction(new JavaObjectFunction(lf)
						{
							@Override
							public int invoke(LuaState luaState)
							{
								return Sthunk(luaState, obj);
							}
						});
					}
					else
					{
						L.pushJavaFunction(new JavaObjectFunction(lf)
						{
							@Override
							public int invoke(LuaState luaState)
							{
								return thunk(luaState, obj);
							}
						});
					}
					Lua.lua_settable(L, methods);
				}
				else
				{
					LuaFunction lf = m.getAnnotation(LuaFunction.class);
					if (lf != null)
					{
 						Lua.lua_pushstring(L, lf.methodName() != null ? lf.methodName() : m.getName());
						//Lua.lua_pushuserdata(L, lf);
						//Lua.lua_pushlightuserdata(L, new LuaJavaFunction(lf.manual(), lf.self(), lf.methodName(), lf.arguments(), m));
						LuaJavaFunction ljf = new LuaJavaFunction(lf.manual(), lf.self(), lf.methodName(), lf.arguments(), m);
						if ((m.getModifiers() & Modifier.STATIC) > 0)
						{
							L.pushJavaFunction(new JavaObjectFunction(ljf)
							{
								@Override
								public int invoke(LuaState luaState)
								{
									return Sthunk(luaState, obj);
								}
							});
						}
						else
						{
							L.pushJavaFunction(new JavaObjectFunction(ljf)
							{
								@Override
								public int invoke(LuaState luaState)
								{
									return thunk(luaState, obj);
								}
							});
						}
						Lua.lua_settable(L, methods);
					}
				}
			}
		}
		else
		{
			Log.e("Lunar", "Empty method info for " + name + ", used tasker?");
		}

		KotlinCompanionObject kotlinCompanionObject = kotlinCompanionMap.get(cls);
		if(kotlinCompanionObject != null)
		{
			for (KFunction kf : kotlinCompanionObject.functions) {
				if (loadAll) {
					Lua.lua_pushstring(L, kf.getName());
					LuaJavaFunction lf = new LuaJavaFunction(false, cls, kf.getName(), kf.getParameters(), kf, kotlinCompanionObject.instance);
					//Lua.lua_pushlightuserdata(L, lf);
					//Lua.lua_pushuserdata(L, lf);
					//if ((kf.getModifiers() & Modifier.STATIC) > 0) {
						L.pushJavaFunction(new JavaObjectFunction(lf) {
							@Override
							public int invoke(LuaState luaState) {
								return Sthunk(luaState, obj);
							}
						});
					/*} else {
						L.pushJavaFunction(new JavaObjectFunction(lf) {
							@Override
							public int invoke(LuaState luaState) {
								return thunk(luaState, obj);
							}
						});
					}*/
					Lua.lua_settable(L, methods);
				} else {
					LuaFunction lf = null;
					for(Annotation a : kf.getAnnotations()) {
						if (a instanceof LuaFunction) {
							lf = (LuaFunction) a;
							break;
						}
					}
					if (lf != null) {
						Lua.lua_pushstring(L, lf.methodName() != null ? lf.methodName() : kf.getName());
						//Lua.lua_pushuserdata(L, lf);
						//Lua.lua_pushlightuserdata(L, new LuaJavaFunction(lf.manual(), lf.self(), lf.methodName(), lf.arguments(), m));
						LuaJavaFunction ljf = new LuaJavaFunction(lf.manual(), lf.self(), lf.methodName(), lf.arguments(), kf, kotlinCompanionObject.instance);
						//if ((m.getModifiers() & Modifier.STATIC) > 0) {
							L.pushJavaFunction(new JavaObjectFunction(ljf) {
								@Override
								public int invoke(LuaState luaState) {
									return Sthunk(luaState, obj);
								}
							});
						/*} else {
							L.pushJavaFunction(new JavaObjectFunction(ljf) {
								@Override
								public int invoke(LuaState luaState) {
									return thunk(luaState, obj);
								}
							});
						}*/
						Lua.lua_settable(L, methods);
					}
				}
			}
			for (KProperty kp : kotlinCompanionObject.properties)
			{
				LuaStaticVariable a = null;
				for(Annotation ann : kp.getAnnotations()) {
					if (ann instanceof LuaStaticVariable) {
						a = (LuaStaticVariable) ann;
						break;
					}
				}
				if(a != null)
				{
					Lua.lua_pushstring(L, kp.getName());
					try
					{
						if(kp instanceof KProperty1)
							ToppingEngine.getInstance().FillVariable(((KProperty1)kp).get(kotlinCompanionObject.instance));
					}
					catch (Exception e)
					{
						Log.e("Lunar", "Fields exposed to LUA must be public and static " + cls.getSimpleName());
						continue;
					}
					Lua.lua_settable(L, methods);
				}
			}
		}

		Field[] fieldInfos = fieldMap.get(cls);
		if(fieldInfos != null)
		{
			for (Field f : fieldInfos)
			{
				Annotation a = f.getAnnotation(LuaStaticVariable.class);
				if(a != null)
				{
					Lua.lua_pushstring(L, f.getName());
					try
					{
						ToppingEngine.getInstance().FillVariable(f.get(null));
					}
					catch (IllegalAccessException e)
					{
						Log.e("Lunar", "Fields exposed to LUA must be public and static " + cls.getSimpleName());
						continue;
					}
					Lua.lua_settable(L, methods);
				}
			}
		}

		LuaGlobalString lgs = cls.getAnnotation(LuaGlobalString.class);
		if(lgs != null)
		{
			String[] keys = lgs.keys();
			String[] vals = lgs.vals();
			for(int i = 0; i < keys.length; i++)
			{
				Lua.lua_pushliteral(L, vals[i]);
				Lua.lua_setglobal(L, keys[i]);
			}
		}

		LuaGlobalInt lgi = cls.getAnnotation(LuaGlobalInt.class);
		if(lgi != null)
		{
			String[] keys = lgi.keys();
			int[] vals = lgi.vals();
			for(int i = 0; i < keys.length; i++)
			{
				Lua.lua_pushinteger(L, vals[i]);
				Lua.lua_setglobal(L, keys[i]);
			}
		}

		LuaGlobalNumber lgn = cls.getAnnotation(LuaGlobalNumber.class);
		if(lgn != null)
		{
			String[] keys = lgn.keys();
			double[] vals = lgn.vals();
			for(int i = 0; i < keys.length; i++)
			{
				Lua.lua_pushnumber(L, vals[i]);
				Lua.lua_setglobal(L, keys[i]);
			}
		}

		Lua.lua_pop(L, 2);  // drop metatable and method table

		LuaGlobalManual lgm = cls.getAnnotation(LuaGlobalManual.class);
		if(lgm != null)
		{
			String namea = lgm.name();
			Lua.lua_newtable(L);
			int methodsa = Lua.lua_gettop(L);

			Lua.luaL_newmetatable(L, namea);
			int metatablea = Lua.lua_gettop(L);

			Lua.luaL_newmetatable(L, "DO NOT TRASH");
			Lua.lua_pop(L, 1);

			// store method table in globals so that
			// scripts can add functions written in Lua.
			Lua.lua_pushvalue(L, methodsa);
			Lua.lua_setfield(L, Lua.LUA_GLOBALSINDEX, namea);

			// hide metatable from Lua getmetatable()
			Lua.lua_pushvalue(L, methodsa);
			Lua.lua_setfield(L, metatablea, "__metatable");

			Lua.lua_pushvalue(L, methodsa);
			Lua.lua_setfield(L, metatablea, "__index");

			Lua.lua_pushcfunction(L, Lua.lua_CFunction.build(cls, "Lua_ToString"));
			Lua.lua_setfield(L, metatablea, "__tostring");

			Lua.lua_pushcfunction(L, Lua.lua_CFunction.build(cls, "Lua_GC"));
			Lua.lua_setfield(L, metatablea, "__gc");

			Lua.lua_newtable(L);                // mt for method table
			int mt = Lua.lua_gettop(L);

			Lua.lua_pushcfunction(L, Lua.lua_CFunction.build(cls, "Lua_Index"));
			Lua.lua_setfield(L, mt, "__index");

			Lua.lua_pushcfunction(L, Lua.lua_CFunction.build(cls, "Lua_NewIndex"));
			Lua.lua_setfield(L, mt, "__newindex");

			Lua.lua_setmetatable(L, methodsa);
			Lua.lua_pop(L, 2);
		}
    }

// push onto the Lua stack a userdata containing a pointer to T object
    @SuppressWarnings("unchecked")
	public static int push(LuaState L, Object obj, boolean gc, boolean lib)
    {
    	if(!lib)
		{
			Lua.lua_pushuserdata(L, obj);
			return 0;
		}

        if (obj == null)
        {
            Lua.lua_pushnil(L);
            return Lua.lua_gettop(L);
        }

        //String name = RemoveChar(obj.getClass().getName(), '.');
        Class<?> cls = obj.getClass();
    	LuaClass lc = cls.getAnnotation(LuaClass.class);
    	String name = "";
    	Object objectToAdd = obj;
    	if(lc == null)
    	{
    		Log.e("Lunar", "No LuaClass defined for " + cls.getName());
    		/*String[] arr = cls.getName().split("\\.");
    		name = arr[arr.length - 1];*/
        	LuaNativeObject lno = new LuaNativeObject();
        	lno.obj = obj;
        	objectToAdd = lno;
        	cls = objectToAdd.getClass();
        	lc = cls.getAnnotation(LuaClass.class);
        	name = lc.className();
    		//return Lua.lua_gettop(L);
    	}
    	else
    		name = lc.className();

        Lua.luaL_getmetatable(L, name);  // lookup metatable in Lua registry
        if (Lua.lua_isnil(L, -1))
            Lua.luaL_error(L, name + " missing metatable");

        int mt = Lua.lua_gettop(L);
        //Lua.lua_pushlightuserdata(L, obj);
        //Object ptr = Lua.lua_newuserdata(L, obj, sizeof(obj).length);
        /*Object ptr = Lua.lua_newuserdata(L, new LuaObject<>());
        ((LuaObject)ptr).PushObject(objectToAdd);*/
        //Lua.lua_pushuserdata(L, objectToAdd);
		Lua.lua_newuserdata(L, objectToAdd);
        int ud = Lua.lua_gettop(L);
        {
            Lua.lua_pushvalue(L, mt);
            Lua.lua_setmetatable(L, -2);
            Lua.lua_getfield(L, Lua.LUA_REGISTRYINDEX, "DO NOT TRASH");
            if (Lua.lua_isnil(L, -1))
            {
                Lua.luaL_newmetatable(L, "DO NOT TRASH");
                Lua.lua_pop(L, 1);
            }
            Lua.lua_getfield(L, Lua.LUA_REGISTRYINDEX, "DO NOT TRASH");
            if (gc == false)
            {
                Lua.lua_pushboolean(L, true);
                Lua.lua_setfield(L, -2, name);
            }
            Lua.lua_pop(L, 1);
        }

        Lua.lua_settop(L, ud);
        Lua.lua_replace(L, mt);
        Lua.lua_settop(L, mt);
        return mt;  // index of userdata containing pointer to T object
    }

    // get userdata from Lua stack and return pointer to T object
    private static Object check(LuaState L, int narg)
    {
        //Object obj = Lua.lua_getuserdata(L, narg);
		Object obj = Lua.lua_touserdata(L, narg);
        //T obj = (T)Lua.lua_touserdata(L, narg);
        if (obj == null)
		    return null;
	    return obj;
    }

    public static Object ParseTable(long valTValue)
    {
    	LuaState l = ToppingEngine.getInstance().GetLuaState();
    	HashMap<Object, Object> map = new HashMap<Object, Object>();
    	long ot = Lua.hvalue(l, valTValue);
    	int size = Lua.sizenode(l, ot);
		for(int i = 0; i < size; i++)
		{
			long node = Lua.gnode(l, ot, i);
			long key = Lua.key2tval(l, node);
			Object keyObject = null;
			switch(Lua.ttype(l, key))
			{
				case Lua.LUA_TNIL:
					break;
				case Lua.LUA_TSTRING:
				{
					keyObject = Lua.svalue(l, key);
				}break;
				case Lua.LUA_TNUMBER:
					keyObject = Lua.nvalue(l, key);
					break;
				default:
					break;
			}

			long val = Lua.luaH_get(l, ot, key);
			Object valObject = null;
			switch(Lua.ttype(l, val))
			{
				case Lua.LUA_TNIL:
					break;
				case Lua.LUA_TSTRING:
				{
					valObject = Lua.svalue(l, val);
				}break;
				case Lua.LUA_TNUMBER:
					valObject = Lua.nvalue(l, val);
					break;
				case Lua.LUA_TTABLE:
					valObject = ParseTable(val);
					break;
				case Lua.LUA_TUSERDATA:
				{
					/*valObject = (Lua.rawuvalue(val).user_data);
					if(valObject != null)
					{
						if(valObject.getClass().getName().startsWith("dev.topping.android.LuaObject"))
	                    {
	            			Object objA = ((LuaObject<?>)valObject).obj;
	            			if(objA == null)
	            			{
	            				Log.e("Lunar Push", "Cannot get lua object property static thunk");
	                            return 0;
	            			}

	                        valObject = objA;
	                    }
					}*/
				}break;
				case Lua.LUA_TLIGHTUSERDATA:
				{
					valObject = Lua.pvalue(l, val);
					if(valObject != null)
					{
						if(valObject.getClass().getName().startsWith("dev.topping.android.LuaObject"))
	                    {
	            			Object objA = ((LuaObject<?>)valObject).obj;
	            			if(objA == null)
	            			{
	            				Log.e("Lunar Push", "Cannot get lua object property static thunk");
	                            return 0;
	            			}

	                        valObject = objA;
	                    }
					}
				}break;

			}
			map.put(keyObject, valObject);
		}
		return map;
    }

    // member function dispatcher
    @SuppressWarnings("rawtypes")
	public static int thunk(LuaState L, Object lao)
    {
    	try
    	{
	    	// stack has userdata, followed by method args
		    Object lobj = check(L, 1);  // get 'self', or if you prefer, 'this'
		    if(lobj == null)
		    	return 0;
			Object obj = lobj;
		    Lua.lua_remove(L, 1);
			LuaJavaFunction la = (LuaJavaFunction) lao;
		    Method m = la.method();

	        if (la.manual())
	        {
				try
				{
					m.invoke(obj, new Object[] { L });
				}
				catch (IllegalArgumentException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				catch (IllegalAccessException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				catch (InvocationTargetException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
			else
	        {
	            int count = 1;
	            ArrayList<Object> argList = new ArrayList<Object>();
	            Class<?>[] argTypeList = la.arguments();
	            for (Class<?> c : argTypeList)
	            {
	            	String name = c.getName();
	            	if(name.compareTo("java.lang.Boolean") == 0
						|| c == boolean.class)
	            		argList.add((Lua.luaL_checkinteger(L, count)) != 0);
	            	else if(name.compareTo("java.lang.Byte") == 0
							|| c == byte.class)
	            		argList.add(Lua.luaL_checkinteger(L, count));
	            	else if(name.compareTo("java.lang.Short") == 0
							|| c == short.class)
	            		argList.add((short)(Lua.luaL_checkinteger(L, count)));
	            	else if(name.compareTo("java.lang.Integer") == 0
							|| c == int.class)
	            		argList.add(Lua.luaL_checkinteger(L, count));
	            	else if(name.compareTo("java.lang.Long") == 0
							|| c == long.class)
	            		argList.add((Long)Lua.luaL_checklong(L, count));
	            	else if(name.compareTo("java.lang.Float") == 0
							|| c == float.class)
	            		argList.add((float) (Lua.luaL_checknumber(L, count)));
	            	else if(name.compareTo("java.lang.Double") == 0
							|| c == double.class)
	            		argList.add((Double)Lua.luaL_checknumber(L, count));
	            	else if(name.compareTo("java.lang.Char") == 0
							|| c == char.class)
	            	{
	            		String val = Lua.luaL_checkstring(L, count).toString();
	            		argList.add(val.charAt(0));
	            	}
	            	else if(name.compareTo("java.lang.String") == 0)
	            		argList.add(Lua.luaL_checkstring(L, count).toString());
					else if(c == LuaTranslator.class && Lua.lua_isfunction(L, count))
					{
						int fref = Lua.luaL_ref(L, Lua.LUA_REGISTRYINDEX);
						LuaTranslator lt = new LuaTranslator(obj, fref);
						argList.add(lt);
					}
	            	else
	            	{
	            		Object objudata = Lua.lua_getuserdata(L, count);
	            		if(objudata == null && L.type(count) == LuaType.USERDATA)
						{
							objudata = Lua.lua_touserdata(L, count);
						}
	            		if(objudata != null)
	            		{
		            		if(objudata.getClass() == dev.topping.android.backend.LuaObject.class)
		                    {
		            			Object objA = ((LuaObject<?>)objudata).obj;
		            			if(objA == null)
		            			{
		            				Log.e("Lunar Push", "Cannot get lua object property thunk");
		                            return 0;
		            			}

		                        argList.add(objA);
		                    }
		            		else if(objudata.getClass() == LuaNativeObject.class)
		            		{
		            			Object objNative = ((LuaNativeObject)objudata).obj;
		            			if(objNative == null)
		            			{
		            				Log.e("Lunar Push", "Cannot get lua native object property thunk");
		            				return 0;
		            			}

		            			argList.add(objNative);
		            		}
		                    else
		                        argList.add(objudata);
	            		}
	            		else
	            		{
							long o = Lua.lua_topointer(L, count);
							if(o == 0)
							{
								if(Lua.lua_isboolean(L, count))
									argList.add(Lua.lua_toboolean(L, count));
								else if(Lua.lua_isnumber(L, count) > 0)
									argList.add(Lua.lua_tonumber(L, count));
								else if(Lua.lua_isstring(L, count) > 0)
									argList.add(Lua.lua_tostring(L, count).toString());
								else if(Lua.lua_isnoneornil(L, count))
									argList.add(null);
							}
							else
							{
								long ot = (long)o; //table
								HashMap<Object, Object> map = new HashMap<Object, Object>();
								LuaState l = ToppingEngine.getInstance().GetLuaState();
								int size = Lua.sizenode(l, ot);
								for(int i = 0; i < size; i++)
								{
									long node = Lua.gnode(l, ot, i);
									long key = Lua.key2tval(l, node);
									Object keyObject = null;
									switch (Lua.ttype(l, key))
									{
									case Lua.LUA_TNIL:
										break;
									case Lua.LUA_TSTRING:
									{
										keyObject = Lua.svalue(l, key);
									}break;
									case Lua.LUA_TNUMBER:
										keyObject = Lua.nvalue(l, key);
										break;
									}

									long val = Lua.luaH_get(l, ot, key);
									Object valObject = null;
									switch (Lua.ttype(l, val))
									{
										case Lua.LUA_TNIL:
											break;
										case Lua.LUA_TSTRING:
										{
											valObject = Lua.svalue(l, val);
										}break;
										case Lua.LUA_TNUMBER:
											valObject = Lua.nvalue(l, val);
											break;
										case Lua.LUA_TTABLE:
											valObject = ParseTable(val);
											break;
										case Lua.LUA_TUSERDATA:
										{
											/*valObject = (Lua.rawuvalue(val).user_data);
											if(valObject != null)
											{
												if(valObject.getClass().getName().startsWith("dev.topping.android.LuaObject"))
												{
													Object objA = ((LuaObject<?>)valObject).obj;
													if(objA == null)
													{
														Log.e("Lunar Push", "Cannot get lua object property static thunk");
														return 0;
													}

													valObject = objA;
												}
											}*/
										}break;
										case Lua.LUA_TLIGHTUSERDATA:
										{
											valObject = Lua.pvalue(l, val);
											if(valObject != null)
											{
												if(valObject.getClass().getName().startsWith("dev.topping.android.LuaObject"))
												{
													Object objA = ((LuaObject<?>)valObject).obj;
													if(objA == null)
													{
														Log.e("Lunar Push", "Cannot get lua object property static thunk");
														return 0;
													}

													valObject = objA;
												}
											}
										}break;
									}

									if(valObject != null)
										map.put(keyObject, valObject);
								}
								argList.add(map);
	            			}
	            		}
	            	}


	                ++count;
	            }

	            Object retval = null;
				try
				{
					retval = m.invoke(obj, argList.toArray());
				} catch (IllegalArgumentException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            if (retval == null)
	                ((ToppingEngine) ToppingEngine.getInstance()).PushNIL();
	            else
	            {
	            	String retName = retval.getClass().getName();
	            	if(retName.compareTo("java.lang.Boolean") == 0
							|| retval.getClass() == boolean.class)
	            		((ToppingEngine) ToppingEngine.getInstance()).PushBool((Boolean)retval);
	            	else if(retName.compareTo("java.lang.Byte") == 0
							|| retval.getClass() == byte.class
	            		|| retName.compareTo("java.lang.Short") == 0
							|| retval.getClass() == short.class
	            		|| retName.compareTo("java.lang.Integer") == 0
							|| retval.getClass() == int.class
	            		|| retName.compareTo("java.lang.Long") == 0
							|| retval.getClass() == long.class)
	            		((ToppingEngine) ToppingEngine.getInstance()).PushInt((Integer)retval);
	            	else if(retName.compareTo("java.lang.Float") == 0
							|| retval.getClass() == float.class)
	            		((ToppingEngine) ToppingEngine.getInstance()).PushFloat((Float)retval);
	            	else if(retName.compareTo("java.lang.Double") == 0
							|| retval.getClass() == double.class)
	            		((ToppingEngine) ToppingEngine.getInstance()).PushDouble((Double)retval);
	            	else if(retName.compareTo("java.lang.Char") == 0
							|| retval.getClass() == char.class
	            			|| retName.compareTo("java.lang.String") == 0)
	            		((ToppingEngine) ToppingEngine.getInstance()).PushString((String)retval);
	            	else if(retName.compareTo("java.lang.Void") == 0
						|| retval.getClass() == void.class
						|| retval.getClass() == kotlin.Unit.class)
	            		((ToppingEngine) ToppingEngine.getInstance()).PushInt(0);
	            	else if(retName.compareTo("java.util.HashMap") == 0)
	            	{
	            		((ToppingEngine) ToppingEngine.getInstance()).PushTable((HashMap<Object, Object>)retval);
	            	}
	            	else
	            	{
	                	ToppingEngine l = (ToppingEngine) ToppingEngine.getInstance();
	                	Lunar.push(l.GetLuaState(), retval, false, true);
	            	}
	            }
	        }

	        return 1;
	    }
		catch (LuaException e)
		{
//			String s = Lua.lua_tostring(L, -1).toString();
//			if(s == null || e.c == null)
//				Log.e("Lunar", "Exception on thunk, possible solution use : for object variables");
//			else
//				Log.e("Lunar", "Exception on thunk, " + s);
//			Log.e("Lunar", e.getMessage());
//			Tools.LogException(e.L, "Lunar", e);
			Log.e("Lunar", "Exception on thunk " + e.getMessage());
			return 1;
		}
    }

    public static int Sthunk(LuaState L, Object lao)
    {
    	try
    	{
			LuaJavaFunction la = (LuaJavaFunction) lao;
		    Class<?> selfClass = la.self();
		    Method m = la.method();
		    KFunction kf = la.kotlinMethod();

	        if (la.manual())
	        {
				try {
					m.invoke(null, new Object[] { L });
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
			else
	        {
	            int count = 1;
	            ArrayList<Object> argList = new ArrayList<Object>();
	            Class<?>[] argTypeList = la.arguments();
	            //if(argTypeList.length == Lua.luaL_ar)
	            for (Class<?> c : argTypeList)
	            {
	            	String name = c.getName();
	            	if(name.compareTo("java.lang.Boolean") == 0
						|| c == boolean.class)
	            		argList.add((Lua.luaL_checkinteger(L, count)) != 0);
	            	else if(name.compareTo("java.lang.Byte") == 0
							|| c == byte.class)
	            		argList.add(Lua.luaL_checkinteger(L, count));
	            	else if(name.compareTo("java.lang.Short") == 0
							|| c == short.class)
	            		argList.add((short)(Lua.luaL_checkinteger(L, count)));
	            	else if(name.compareTo("java.lang.Integer") == 0
							|| c == int.class)
	            		argList.add(Lua.luaL_checkinteger(L, count));
	            	else if(name.compareTo("java.lang.Long") == 0
							|| c == long.class)
	            		argList.add((Long)Lua.luaL_checklong(L, count));
	            	else if(name.compareTo("java.lang.Float") == 0
							|| c == float.class)
	            		argList.add(new Float((Lua.luaL_checknumber(L, count))));
	            	else if(name.compareTo("java.lang.Double") == 0
							|| c == double.class)
	            		argList.add((Double)Lua.luaL_checknumber(L, count));
	            	else if(name.compareTo("java.lang.Character") == 0
							|| c == char.class)
	            	{
	            		String val = Lua.luaL_checkstring(L, count).toString();
	            		argList.add(val.charAt(0));
	            	}
	            	else if(name.compareTo("java.lang.String") == 0)
	            		argList.add(Lua.luaL_checkstring(L, count).toString());
	            	else if(c == LuaTranslator.class && Lua.lua_isfunction(L, count))
					{
						int fref = Lua.luaL_ref(L, Lua.LUA_REGISTRYINDEX);
						LuaTranslator lt = new LuaTranslator(null, fref);
						argList.add(lt);
					}
	            	else
	            	{
						Object objudata = Lua.lua_getuserdata(L, count);
						if(objudata == null && L.type(count) == LuaType.USERDATA)
						{
							objudata = Lua.lua_touserdata(L, count);
						}
						if(objudata != null)
						{
							if(objudata.getClass() == dev.topping.android.backend.LuaObject.class)
							{
								Object objA = ((LuaObject<?>)objudata).obj;
								if(objA == null)
								{
									Log.e("Lunar Push", "Cannot get lua object property thunk");
									return 0;
								}

								argList.add(objA);
							}
							else if(objudata.getClass() == LuaNativeObject.class)
							{
								Object objNative = ((LuaNativeObject)objudata).obj;
								if(objNative == null)
								{
									Log.e("Lunar Push", "Cannot get lua native object property thunk");
									return 0;
								}

								argList.add(objNative);
							}
							else
								argList.add(objudata);
						}
						else
						{
							long o = Lua.lua_topointer(L, count);
							if(o == 0)
							{
								if(Lua.lua_isboolean(L, count))
									argList.add(Lua.lua_toboolean(L, count));
								else if(Lua.lua_isnumber(L, count) > 0)
									argList.add(Lua.lua_tonumber(L, count));
								else if(Lua.lua_isstring(L, count) > 0)
									argList.add(Lua.lua_tostring(L, count).toString());
								else if(Lua.lua_isnoneornil(L, count))
									argList.add(null);
							}
							else
							{
								long ot = (long)o; //table
								HashMap<Object, Object> map = new HashMap<Object, Object>();
								LuaState l = ToppingEngine.getInstance().GetLuaState();
								int size = Lua.sizenode(l, ot);
								for(int i = 0; i < size; i++)
								{
									long node = Lua.gnode(l, ot, i);
									long key = Lua.key2tval(l, node);
									Object keyObject = null;
									switch (Lua.ttype(l, key))
									{
									case Lua.LUA_TNIL:
										break;
									case Lua.LUA_TSTRING:
									{
										keyObject = Lua.svalue(l, key);
									}break;
									case Lua.LUA_TNUMBER:
										keyObject = Lua.nvalue(l, key);
										break;
									}

									long val = Lua.luaH_get(l, ot, key);
									Object valObject = null;
									switch (Lua.ttype(l, val))
									{
									case Lua.LUA_TNIL:
										break;
									case Lua.LUA_TSTRING:
									{
										valObject = Lua.svalue(l, val);
									}break;
									case Lua.LUA_TNUMBER:
										valObject = Lua.nvalue(l, val);
										break;
									case Lua.LUA_TTABLE:
										valObject = ParseTable(val);
										break;
									case Lua.LUA_TUSERDATA:
									{
											/*valObject = (Lua.rawuvalue(val).user_data);
											if(valObject != null)
											{
												if(valObject.getClass().getName().startsWith("dev.topping.android.LuaObject"))
												{
													Object objA = ((LuaObject<?>)valObject).obj;
													if(objA == null)
													{
														Log.e("Lunar Push", "Cannot get lua object property static thunk");
														return 0;
													}

													valObject = objA;
												}
											}*/
									}break;
									case Lua.LUA_TLIGHTUSERDATA:
									{
										valObject = Lua.pvalue(l, val);
										if(valObject != null)
										{
											if(valObject.getClass().getName().startsWith("dev.topping.android.LuaObject"))
											{
												Object objA = ((LuaObject<?>)valObject).obj;
												if(objA == null)
												{
													Log.e("Lunar Push", "Cannot get lua object property static thunk");
													return 0;
												}

												valObject = objA;
											}
										}
									}break;
									}

									if(valObject != null)
										map.put(keyObject, valObject);
								}
								argList.add(map);
							}
						}
	            	}

	                ++count;
	            }

	            Object retval = null;
				try
				{
					if(m != null)
						retval = m.invoke(null, argList.toArray());
					else {
						List<KParameter> paramList = kf.getParameters();
						HashMap<KParameter, Object> valueMap = new HashMap<>();
						int index = 0;
						for(KParameter kParameter : paramList)
						{
							if(index == 0)
								valueMap.put(kParameter, la.kotlinObject());
							else
								valueMap.put(kParameter, argList.get(index - 1));
							index++;
						}
						retval = kf.callBy(valueMap);
					}

				}
				catch (Exception e)
				{
					if(m != null)
						Log.e("Lunar", "Exception occured at static thunk, " + e.getMessage() + "\n" + m.getName());
					else
						Log.e("Lunar", "Exception occured at static thunk, " + e.getMessage() + "\n" + kf.getName());
				}


	            if (retval == null)
	                ((ToppingEngine) ToppingEngine.getInstance()).PushNIL();
	            else
	            {
	            	String retName = retval.getClass().getName();
	            	if(retName.compareTo("java.lang.Boolean") == 0
							|| retval.getClass() == boolean.class)
	            		((ToppingEngine) ToppingEngine.getInstance()).PushBool((Boolean)retval);
	            	else if(retName.compareTo("java.lang.Byte") == 0
							|| retval.getClass() == byte.class
	            			|| retName.compareTo("java.lang.Short") == 0
							|| retval.getClass() == short.class
	            			|| retName.compareTo("java.lang.Integer") == 0
							|| retval.getClass() == int.class
	            			|| retName.compareTo("java.lang.Long") == 0
							|| retval.getClass() == long.class)
	            		((ToppingEngine) ToppingEngine.getInstance()).PushInt((Integer)retval);
	            	else if(retName.compareTo("java.lang.Float") == 0
							|| retval.getClass() == float.class)
	            		((ToppingEngine) ToppingEngine.getInstance()).PushFloat((Float)retval);
	            	else if(retName.compareTo("java.lang.Double") == 0
							|| retval.getClass() == double.class)
	            		((ToppingEngine) ToppingEngine.getInstance()).PushDouble((Double)retval);
	            	else if(retName.compareTo("java.lang.Char") == 0
							|| retval.getClass() == char.class
	            			|| retName.compareTo("java.lang.String") == 0)
	            		((ToppingEngine) ToppingEngine.getInstance()).PushString((String)retval);
	            	else if(retName.compareTo("java.lang.Void") == 0
							|| retval.getClass() == void.class
							|| retval.getClass() == kotlin.Unit.class)
	            		((ToppingEngine) ToppingEngine.getInstance()).PushInt(0);
	            	else if(retName.compareTo("java.util.HashMap") == 0)
	            	{
	            		((ToppingEngine) ToppingEngine.getInstance()).PushTable((HashMap<Object, Object>)retval);
	            	}
	            	else
	            	{
	                	ToppingEngine l = (ToppingEngine) ToppingEngine.getInstance();
	                	Lunar.push(l.GetLuaState(), retval, false, true);
	            	}
	            }
	        }
	        return 1;
    	}
    	catch (LuaException e)
		{
////    		String s = Lua.lua_tostring(L, -1).toString();
////    		if(s == null)
//    			Log.e("Lunar", "Exception on sThunk, possible solution use . for static variables");
//    		else
//    			Log.e("Lunar", "Exception on sThunk, " + s);
//			Log.e("Lunar", e.getMessage());
//			Tools.LogException(e.L, "Lunar", e);
			Log.e("Lunar", "Exception on sThunk " + e.getMessage());
			return 1;
		}
    }

    // garbage collection metamethod	
	public static int gc_T(LuaState L)
	{
        int ptr = 0;
	    Object obj = check(L, 1);
	    if(obj == null)
		    return 0;
	    Lua.lua_getfield(L, Lua.LUA_REGISTRYINDEX, "DO NOT TRASH");
	    if(Lua.lua_istable(L, -1))
	    {
	    	String name = RemoveChar(obj.getClass().getName(), '.');
            Lua.lua_getfield(L, -1, name);
		    if(Lua.lua_isnil(L,-1))
		    {
			    obj = null;
		    }
	    }
	    Lua.lua_pop(L, 3);
	    return 0;
	}

	public static int tostring_T(LuaState L)
	{
		Object ptrHold = (Lua.lua_touserdata(L, 1));
		if(ptrHold == null)
			return 0;
		if(ptrHold.getClass() == LuaObject.class)
			ptrHold = ((LuaObject<?>)ptrHold).obj;
		if(ptrHold == null)
			return 0;
		String name = ptrHold.getClass().getSimpleName();
		Lua.lua_pushstring(L, name);
		return 1;
	}

	public static void putBundle(Bundle b, String key, Object value)
	{
		Class c = value.getClass();
		String name = c.getName();
		if(name.compareTo("java.lang.Boolean") == 0
				|| c == boolean.class)
			b.putBoolean(key, (Boolean) value);
		else if(name.compareTo("java.lang.Byte") == 0
				|| c == byte.class)
			b.putByte(key, (Byte) value);
		else if(name.compareTo("java.lang.Short") == 0
				|| c == short.class)
			b.putShort(key, (Short) value);
		else if(name.compareTo("java.lang.Integer") == 0
				|| c == int.class)
			b.putInt(key, (Integer) value);
		else if(name.compareTo("java.lang.Long") == 0
				|| c == long.class)
			b.putLong(key, (Long) value);
		else if(name.compareTo("java.lang.Float") == 0
				|| c == float.class)
			b.putFloat(key, (Float) value);
		else if(name.compareTo("java.lang.Double") == 0
				|| c == double.class)
			b.putDouble(key, (Double) value);
		else if(name.compareTo("java.lang.Char") == 0
				|| c == char.class)
		{
			b.putChar(key, (Character) value);
		}
		else if(name.compareTo("java.lang.String") == 0)
			b.putString(key, (String) value);
		else if(c.isArray())
		{
			Class component = c.getComponentType();
			String componentName = component.getName();
			if(componentName.compareTo("java.lang.Boolean") == 0
					|| component == boolean.class)
				b.putBooleanArray(key, (boolean[]) value);
			else if(componentName.compareTo("java.lang.Byte") == 0
					|| component == byte.class)
				b.putByteArray(key, (byte[]) value);
			else if(componentName.compareTo("java.lang.Short") == 0
					|| component == short.class)
				b.putShortArray(key, (short[]) value);
			else if(componentName.compareTo("java.lang.Integer") == 0
					|| component == int.class)
				b.putIntArray(key, (int[]) value);
			else if(componentName.compareTo("java.lang.Long") == 0
					|| component == long.class)
				b.putLongArray(key, (long[]) value);
			else if(componentName.compareTo("java.lang.Float") == 0
					|| component == float.class)
				b.putFloatArray(key, (float[]) value);
			else if(componentName.compareTo("java.lang.Double") == 0
					|| component == double.class)
				b.putDoubleArray(key, (double[]) value);
			else if(componentName.compareTo("java.lang.Char") == 0
					|| component == char.class)
			{
				b.putCharArray(key, (char[]) value);
			}
			else if(componentName.compareTo("java.lang.String") == 0)
				b.putStringArray(key, (String[]) value);
			else {
				Class[] cListComponent = component.getClasses();
				boolean parcelableFoundComponent = false;
				for (Class cInComponent : cListComponent) {
					if (cInComponent == Parcelable.class) {
						parcelableFoundComponent = true;
						break;
					}
				}
				if (parcelableFoundComponent)
					b.putParcelableArray(key, (Parcelable[]) value);
			}
		}
		else {
			Class[] cList = c.getClasses();
			boolean parcelableFound = false;
			for(Class cIn : cList)
			{
				if(cIn == Parcelable.class) {
					parcelableFound = true;
					break;
				}
			}
			if(parcelableFound)
				b.putParcelable(key, (Parcelable) value);
			else
			{
				Log.e("Lunar", "Cannot add parameter");
			}
		}
	}
}
