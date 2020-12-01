package com.naef.jnlua;

import android.content.Context;

import dev.topping.android.osspecific.Defines;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class Lua
{
    public static final int LUA_MULTRET = LuaState.MULTRET;
    public static final int LUA_REGISTRYINDEX = LuaState.REGISTRYINDEX;
    public static final int LUA_ENVIRONINDEX = LuaState.ENVIRONINDEX;
    public static final int LUA_GLOBALSINDEX = LuaState.GLOBALSINDEX;

    public static final int LUA_TNONE = (-1);

    public static final int LUA_TNIL = 0;
    public static final int LUA_TBOOLEAN = 1;
    public static final int LUA_TLIGHTUSERDATA = 2;
    public static final int LUA_TNUMBER = 3;
    public static final int LUA_TSTRING = 4;
    public static final int LUA_TTABLE = 5;
    public static final int LUA_TFUNCTION = 6;
    public static final int LUA_TUSERDATA = 7;
    public static final int LUA_TTHREAD = 8;

    public static String scriptsRoot = "";
    public static int primaryLoad = 0;

    public static Delegator lua_CFunction = new Delegator(
            new Class[] { LuaState.class }, Integer.TYPE);

    public static LuaState lua_open()
    {
        LuaState l = new LuaState();
        l.setJavaReflector(new JavaReflector()
        {
            @Override
            public JavaFunction getMetamethod(Metamethod metamethod)
            {
                return new JavaFunction()
                {
                    @Override
                    public int invoke(LuaState luaState)
                    {
                        return 0;
                    }
                };
            }
        });
        return l;
    }

    public static void luaL_openlibs(LuaState l)
    {
        l.openLibs();
    }

    public static int luaL_loadstring(LuaState l, String toString)
    {
        try
        {
            l.load(toString, toString);
            return 0;
        }
        catch (Exception e)
        {
            return 1;
        }
    }

    public static String lua_tostring(LuaState l, int i)
    {
        return l.toString(i);
    }

    public static void lua_insert(LuaState l, int i)
    {
        l.insert(i);
    }

    public static void lua_pop(LuaState l, int i)
    {
        l.pop(i);
    }

    public static int lua_equal(LuaState l, int i1, int i2)
    {
        return l.equal(i1, i2) ? 1 : 0;
    }

    public static void lua_getglobal(LuaState l, String func)
    {
        l.getGlobal(func);
    }

    public static void lua_getfield(LuaState l, int i, String token)
    {
        l.getField(i, token);
    }

    public static boolean lua_isfunction(LuaState l, int i)
    {
        return l.isFunction(i);
    }

    public static boolean lua_iscfunction(LuaState l, int i)
    {
        return l.isCFunction(i);
    }

    public static void lua_replace(LuaState l, int i)
    {
        l.replace(i);
    }

    public static void lua_pushvalue(LuaState l, int i)
    {
        l.pushValue(i);
    }

    public static void lua_settop(LuaState l, int i)
    {
        l.setTop(i);
    }

    public static boolean lua_istable(LuaState l, int i)
    {
        return l.isTable(i);
    }

    public static boolean lua_isnoneornil(LuaState l, int i)
    {
        return l.isNoneOrNil(i);
    }

    public static boolean lua_isboolean(LuaState l, int i)
    {
        return l.isBoolean(i);
    }

    public static int lua_toboolean(LuaState l, int i)
    {
        return l.toBoolean(i) ? 1 : 0;
    }

    public static int lua_isnumber(LuaState l, int i)
    {
        return l.isNumber(i) ? 1 : 0;
    }

    public static double lua_tonumber(LuaState l, int i)
    {
        return l.toNumber(i);
    }

    public static int lua_isstring(LuaState l, int i)
    {
        return l.isString(i) ? 1 : 0;
    }

    public static Object lua_getuserdata(LuaState l, int i)
    {
        return l.toJavaObjectRaw(i);
    }

    public static Object lua_touserdata(LuaState l, int i)
    {
        return l.toUserdata(i);
    }

    public static Object lua_touserdataRaw(LuaState l, int i)
    {
        return l.toLightJavaObjectRaw(i);
    }

    public static long lua_topointer(LuaState l, int i)
    {
        return l.toPointer(i);
    }

    public static int lua_pcall(LuaState l, int i, int ret, int i1)
    {
        try
        {
            l.call(i, ret);
            return 0;
        }
        catch (Exception e)
        {
            return 1;
        }
    }

    public static void lua_rawgeti(LuaState l, int i, int key)
    {
        l.rawGet(i, key);
    }

    public static void lua_rawset(LuaState l, int i)
    {
        l.rawSet(i);
    }

    public static void lua_rawseti(LuaState l, int i, int key)
    {
        l.rawSet(i, key);
    }

    public static boolean lua_isnil(LuaState l, int i)
    {
        return l.isNil(i);
    }

    public static int lua_tointeger(LuaState l, int i)
    {
        return l.toInteger(i);
    }

    public static void lua_pushstring(LuaState l, String str)
    {
        l.pushString(str);
    }

    public static void lua_call(LuaState l, int argCount, int returnCount)
    {
        l.call(argCount, returnCount);
    }

    public static int luaL_loadfile(LuaState l, Context context, String filename)
    {
        if(filename.startsWith("./"))
        {
            scriptsRoot = "";
            filename = filename.substring(2, filename.length());
        }
        InputStream istr = null;
        switch(primaryLoad)
        {
            case 1:
            {
                istr = Defines.GetResourceSdAsset(scriptsRoot + "/", filename);
            }
            case 2:
            {
                istr = Defines.GetResourceInternalAsset(scriptsRoot + "/", filename);
            }
            case 3:
            {
                istr = Defines.GetResourceAsset(scriptsRoot + "/", filename);
            }
            default:
            {
                istr = Defines.GetResourceAsset(scriptsRoot + "/", filename);
            }
        }
        if(istr == null)
            return 1;

        try
        {
            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader
                    (istr, Charset.forName("UTF-8"))))
            {
                int c = 0;
                while ((c = reader.read()) != -1)
                {
                    textBuilder.append((char) c);
                }
            }

            l.load(textBuilder.toString(), filename);
            return 0;
        }
        catch (Exception ex)
        {
            return 1;
        }
    }

    public static void lua_newtable(LuaState l)
    {
        l.newTable();
    }

    public static int lua_gettop(LuaState l)
    {
        return l.getTop();
    }

    public static int luaL_newmetatable(LuaState l, String tname)
    {
        l.getField(LUA_REGISTRYINDEX, tname);
        if(!l.isNil(-1))
            return 0;
        l.pop(1);
        l.newTable();
        l.pushValue(-1);
        l.setField(LUA_REGISTRYINDEX, tname);
        return 1;
    }

    public static void lua_setfield(LuaState l, int i, String str)
    {
        l.setField(i, str);
    }

    public static void lua_pushcfunction(LuaState l, IDelegate tostring_t)
    {
        l.pushJavaFunction(new JavaFunction()
        {
            @Override
            public int invoke(LuaState luaState)
            {
                tostring_t.invoke(luaState);
                return 0;
            }
        });
    }

    public static void lua_setmetatable(LuaState l, int i)
    {
        l.setMetatable(i);
    }

    public static void lua_pushlightuserdata(LuaState l, Object obj)
    {
        l.pushLightUserData(obj);
    }

    public static void lua_pushuserdata(LuaState l, Object obj)
    {
        l.pushJavaObject(obj);
    }

    public static void lua_pushcclosure(LuaState l, IDelegate del, int i)
    {
        l.pushJavaClosure(new JavaFunction()
        {
            @Override
            public int invoke(LuaState luaState)
            {
                del.invoke(luaState);
                return 0;
            }
        }, i);
    }

    public static void lua_gettable(LuaState l, int i)
    {
        l.getTable(i);
    }

    public static void lua_settable(LuaState l, int i)
    {
        l.setTable(i);
    }

    public static void lua_pushliteral(LuaState l, String str)
    {
        l.pushString(str);
    }

    public static void lua_setglobal(LuaState l, String str)
    {
        l.setGlobal(str);
    }

    public static void lua_pushinteger(LuaState l, int val)
    {
        l.pushInteger(val);
    }

    public static void lua_pushnumber(LuaState l, double val)
    {
        l.pushNumber(val);
    }

    public static void lua_pushnil(LuaState l)
    {
        l.pushNil();
    }

    public static void luaL_getmetatable(LuaState l, String name)
    {
        //Check this when update, this is definition
        l.getField(LUA_REGISTRYINDEX, name);
    }

    public static void luaL_error(LuaState l, String s)
    {
        l.lError(s);
    }

    public static void lua_newuserdata(LuaState l, Object object)
    {
        l.newJavaObject(object);
    }

    public static void lua_pushboolean(LuaState l, boolean b)
    {
        l.pushBoolean(b);
    }

    public static void lua_remove(LuaState l, int i)
    {
        l.remove(i);
    }

    public static int lua_upvalueindex(int i)
    {
        return (LUA_GLOBALSINDEX-(i));
    }

    public static int luaL_checkinteger(LuaState l, int i)
    {
        return l.checkInteger(i);
    }

    public static long luaL_checklong(LuaState l, int i)
    {
        return (long) l.checkNumber(i);
    }

    public static double luaL_checknumber(LuaState l, int i)
    {
        return l.checkNumber(i);
    }

    public static String luaL_checkstring(LuaState l, int i)
    {
        return l.checkString(i);
    }

    public static int luaL_ref(LuaState l, int i)
    {
        return l.ref(i);
    }

    public static void lua_lock(LuaState l)
    {

    }

    public static void lua_createtable(LuaState l, int i, int size)
    {
        l.newTable(i, size);
    }

    public static void lua_unlock(LuaState l)
    {

    }

    public static void lua_close(LuaState l)
    {
        l.close();
    }

    public static void lua_register(LuaState l, String name, IDelegate iDelegate)
    {
        l.register(new NamedJavaFunction()
        {
            @Override
            public String getName()
            {
                return name;
            }

            @Override
            public int invoke(LuaState luaState)
            {
                iDelegate.invoke(luaState);
                return 0;
            }
        });
    }

    public static long hvalue(LuaState l, long valTValue)
    {
        return l.hvalue(valTValue);
    }

    public static int sizenode(LuaState l, long valTable)
    {
        return l.sizenode(valTable);
    }

    public static long gnode(LuaState l, long valTable, int index)
    {
        return l.gnode(valTable, index);
    }

    public static long key2tval(LuaState l, long valNode)
    {
        return l.key2tval(valNode);
    }

    public static int ttype(LuaState l, long valTValue)
    {
        return l.ttype(valTValue);
    }

    public static String svalue(LuaState l, long valTValue)
    {
        return l.svalue(valTValue);
    }

    public static double nvalue(LuaState l, long valTValue)
    {
        return l.nvalue(valTValue);
    }

    public static long pvalue(LuaState l, long valTValue)
    {
        return l.pvalue(valTValue);
    }

    public static long luaH_get(LuaState l, long valTable, long valTValue)
    {
        return l.luaH_get(valTable, valTValue);
    }
}
