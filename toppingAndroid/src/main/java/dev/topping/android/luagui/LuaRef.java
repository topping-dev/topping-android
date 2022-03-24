package dev.topping.android.luagui;

import android.content.Context;

import com.naef.jnlua.IDelegate;
import com.naef.jnlua.LuaState;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import dalvik.system.DexFile;
import dev.topping.android.ToppingEngine;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.osspecific.ClassCache;

import static com.naef.jnlua.Lua.LUA_ENVIRONINDEX;
import static com.naef.jnlua.Lua.LUA_GLOBALSINDEX;
import static com.naef.jnlua.Lua.LUA_MULTRET;
import static com.naef.jnlua.Lua.lua_CFunction;
import static com.naef.jnlua.Lua.lua_call;
import static com.naef.jnlua.Lua.lua_createtable;
import static com.naef.jnlua.Lua.lua_equal;
import static com.naef.jnlua.Lua.lua_getfield;
import static com.naef.jnlua.Lua.lua_gettable;
import static com.naef.jnlua.Lua.lua_gettop;
import static com.naef.jnlua.Lua.lua_insert;
import static com.naef.jnlua.Lua.lua_newtable;
import static com.naef.jnlua.Lua.lua_pop;
import static com.naef.jnlua.Lua.lua_pushboolean;
import static com.naef.jnlua.Lua.lua_pushcclosure;
import static com.naef.jnlua.Lua.lua_pushcfunction;
import static com.naef.jnlua.Lua.lua_pushliteral;
import static com.naef.jnlua.Lua.lua_pushnumber;
import static com.naef.jnlua.Lua.lua_pushvalue;
import static com.naef.jnlua.Lua.lua_rawgeti;
import static com.naef.jnlua.Lua.lua_rawset;
import static com.naef.jnlua.Lua.lua_rawseti;
import static com.naef.jnlua.Lua.lua_remove;
import static com.naef.jnlua.Lua.lua_setfield;
import static com.naef.jnlua.Lua.lua_settable;
import static com.naef.jnlua.Lua.lua_settop;
import static com.naef.jnlua.Lua.lua_toboolean;
import static com.naef.jnlua.Lua.lua_upvalueindex;

@LuaClass(className = "LuaRef")
public class LuaRef implements LuaInterface
{
    private int ref;

    /**
     * (Ignore)
     */
    private LuaRef(int ref)
    {
        this.ref = ref;
    }

    /**
     * (Ignore)
     */
    public int getRef()
    {
        return ref;
    }

    /* pushes new closure table onto the stack, using closure table at
     * given index as its parent */
    private static void lc_newclosuretable(LuaState L, int idx)
    {
        lua_newtable(L);
        lua_pushvalue(L,idx);
        lua_rawseti(L,-2,0);
    }

    /* gets upvalue with ID varid by consulting upvalue table at index
     * tidx for the upvalue table at given nesting level. */
    static void lc_getupvalue(LuaState L, int tidx, int level, int varid) {
        if (level == 0) {
            lua_rawgeti(L,tidx,varid);
        }
        else {
            lua_pushvalue(L,tidx);
            while (--level >= 0) {
                lua_rawgeti(L,tidx,0); /* 0 links to parent table */
                lua_remove(L,-2);
                tidx = -1;
            }
            lua_rawgeti(L,-1,varid);
            lua_remove(L,-2);
        }
    }

    private static IDelegate lcf6 = lua_CFunction.build(LuaRef.class, "lcf6");

    /* function(t,k) */
    public static int lcf6 (LuaState L) {
        int lc_nformalargs = 2;
        lua_settop(L,2);

        /* if type(store[k]) == "table" then */
        int lc2 = 2;
        lua_getfield(L,LUA_ENVIRONINDEX,"type");
        lc_getupvalue(L,lua_upvalueindex(1),0,1);
        lua_pushvalue(L,2);
        lua_gettable(L,-2);
        lua_remove(L,-2);
        lua_call(L,1,1);
        lua_pushliteral(L,"table");
        final int lc3 = lua_equal(L,-2,-1);
        lua_pop(L,2);
        lua_pushboolean(L, lc3 != 0);
        final int lc4 = lua_toboolean(L,-1);
        lua_pop(L,1);
        if (lc4 > 0) {

            /* return store[k] */
            lc_getupvalue(L,lua_upvalueindex(1),0,1);
            lua_pushvalue(L,2);
            lua_gettable(L,-2);
            lua_remove(L,-2);
            return 1;
        }
        else {

            /* else
             * return LuaRef.WithValue(store[k]) */
            final int lc5 = lua_gettop(L);
            lua_getfield(L,LUA_ENVIRONINDEX,"LuaRef");
            lua_pushliteral(L,"WithValue");
            lua_gettable(L,-2);
            lua_remove(L,-2);
            lc_getupvalue(L,lua_upvalueindex(1),0,1);
            lua_pushvalue(L,2);
            lua_gettable(L,-2);
            lua_remove(L,-2);
            lua_call(L,1,LUA_MULTRET);
            return (lua_gettop(L) - lc5);
        }
    }

    private static IDelegate lcf7 = lua_CFunction.build(LuaRef.class, "lcf7");

    /* function (t,k,v) */
    public static int lcf7 (LuaState L) {
        int lc_nformalargs = 3;
        lua_settop(L,3);

        /* error("attempt to update a read-only table", 2) */
        lua_getfield(L,LUA_ENVIRONINDEX,"error");
        lua_pushliteral(L,"attempt to update a read-only table");
        lua_pushnumber(L,2);
        lua_call(L,2,0);
        return 0;
    }

    private static IDelegate readOnlyTable = lua_CFunction.build(LuaRef.class, "readOnlyTable");

    /* name: readOnlyTable
     * function(t) */
    public static int readOnlyTable (LuaState L) {
        int lc_nformalargs = 1;
        lua_settop(L,1);

        /* local proxy = {} */
        lua_newtable(L);

        /* local store = t */
        lc_newclosuretable(L,lua_upvalueindex(1));
        int lc1 = 3;
        lua_pushvalue(L,1);
        lua_rawseti(L,lc1,1);

        /* local mt = {       -- create metatable
         * 		__index = function(t,k)
         * 		    if type(store[k]) == "table" then
         * 		        return store[k]
         * 		    else
         * 		        return LuaRef.WithValue(store[k])
         * 		    end
         * 	    end,
         * 		__newindex = function (t,k,v)
         * 		  error("attempt to update a read-only table", 2)
         * 		end
         * 	} */
        lua_createtable(L,0,2);
        lua_pushliteral(L,"__index");
        lua_pushvalue(L,lc1);
        lua_pushcclosure(L, lcf6,1);
        lua_rawset(L,-3);
        lua_pushliteral(L,"__newindex");
        lua_pushcfunction(L, lcf7);
        lua_rawset(L,-3);

        /* setmetatable(proxy, mt) */
        lua_getfield(L,LUA_GLOBALSINDEX,"setmetatable");
        lua_pushvalue(L,2);
        lua_pushvalue(L,4);
        lua_call(L,2,0);

        /* return proxy */
        lua_pushvalue(L,2);
        return 1;
    }

    /**
     * (Ignore)
     */
    public static void ResourceLoader(Context ctx)
    {
        /* function readOnlyTable (t)
         * 	local proxy = {}
         * 	local store = t
         * 	local mt = {       -- create metatable
         * 		__index = function(t,k)
         * 		    if type(store[k]) == "table" then
         * 		        return store[k]
         * 		    else
         * 		        return LuaRef.WithValue(store[k])
         * 		    end
         * 	    end,
         * 		__newindex = function (t,k,v)
         * 		  error("attempt to update a read-only table", 2)
         * 		end
         * 	}
         * 	setmetatable(proxy, mt)
         * 	return proxy
         * end */
        LuaState L = ToppingEngine.getInstance().GetLuaState();
        lua_pushcfunction(L, readOnlyTable);
        lua_setfield(L,LUA_GLOBALSINDEX,"readOnlyTable");

        Class rClass = null;
        try
        {
            DexFile df = new DexFile(ctx.getPackageCodePath());
            for (Enumeration<String> iter = df.entries(); iter.hasMoreElements(); )
            {
                String s = iter.nextElement();
                if (s.startsWith(ctx.getPackageName()) && s.endsWith("R"))
                {
                    try
                    {
                        rClass = ClassCache.forName(s);
                        break;
                    }
                    catch (ClassNotFoundException e)
                    {

                    }
                }
            }
        }
        catch (IOException e)
        {
            return;
        }

        if(rClass == null)
            return;

        Class<?>[] declaredClasses = rClass.getDeclaredClasses();

        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        HashSet<String> reservedKeywordSet = new HashSet<>();
        reservedKeywordSet.add("and");
        reservedKeywordSet.add("end");
        reservedKeywordSet.add("in");
        reservedKeywordSet.add("repeat");
        reservedKeywordSet.add("break");
        reservedKeywordSet.add("do");
        reservedKeywordSet.add("else");
        reservedKeywordSet.add("false");
        reservedKeywordSet.add("for");
        reservedKeywordSet.add("function");
        reservedKeywordSet.add("elseif");
        reservedKeywordSet.add("if");
        reservedKeywordSet.add("not");
        reservedKeywordSet.add("local");
        reservedKeywordSet.add("nil");
        reservedKeywordSet.add("or");
        reservedKeywordSet.add("return");
        reservedKeywordSet.add("then");
        reservedKeywordSet.add("true");
        reservedKeywordSet.add("until");
        reservedKeywordSet.add("while");

        ArrayList<String> classArr = new ArrayList<>();
        for (int d = 0; d < declaredClasses.length; d++)
        {
            Field[] declaredFields = declaredClasses[d].getDeclaredFields();

            StringBuilder innerBuilder = new StringBuilder();
            int arrayCount = 0;
            int primitiveCount = 0;
            HashMap<String, Object> map = new HashMap<>();
            /* vXXXX = { abc=12345678, ... } */
            for (int i = 0; i < declaredFields.length; i++)
            {
                String name = declaredFields[i].getName();
                //ALT=2131165184,CTRL=2131165185,FUNCTION=2131165186,META=2131165187,SHIFT=2131165188,SYM=2131165189
                if(name.equalsIgnoreCase("ALT")
                || name.equalsIgnoreCase("CTRL")
                || name.equalsIgnoreCase("FUNCTION")
                || name.equalsIgnoreCase("META")
                || name.equalsIgnoreCase("SHIFT")
                || name.equalsIgnoreCase("SYM"))
                    continue;

                if(reservedKeywordSet.contains(name))
                    name = name.toUpperCase(Locale.US);

                Object o = null;
                try
                {
                    o = declaredFields[i].get(null);
                }
                catch (IllegalAccessException e)
                {

                }

                Type type = declaredFields[i].getType();

                if (type instanceof Class && ((Class) type).isArray())
                {
                    int len = Array.getLength(o);
                    if(len == 0)
                        continue;

                    arrayCount++;

                    ArrayList<Object> arr = new ArrayList<>();
                    for(int k = 0; k < len; ++k)
                    {
                        arr.add(Array.get(o, k));
                    }
                    map.put(name, arr);
                }
                else
                {
                    primitiveCount++;
                    map.put(name, o);
                }
            }

            lua_createtable(L, arrayCount, primitiveCount);
            for(Map.Entry<String, Object> kvp : map.entrySet())
            {
                lua_pushliteral(L,kvp.getKey());
                if(kvp.getValue() instanceof ArrayList)
                {
                    ArrayList lst = (ArrayList) kvp.getValue();
                    lua_createtable(L, lst.size() - 1, 1);
                    lua_pushnumber(L,0);
                    lua_pushnumber(L, (int) lst.get(0));
                    lua_rawset(L,-3);
                    for(int i = 1; i < lst.size(); i++)
                    {
                        lua_pushnumber(L, (int) lst.get(i));
                        lua_rawseti(L,-2,i);
                    }
                }
                else
                {
                    lua_pushnumber(L, (int) kvp.getValue());
                }
                lua_rawset(L,-3);
            }
            lua_setfield(L,LUA_GLOBALSINDEX,"v" + declaredClasses[d].getSimpleName());

            /* tXXXX= readOnlyTable(vXXXX) */
            lua_getfield(L,LUA_GLOBALSINDEX,"readOnlyTable");
            lua_getfield(L,LUA_GLOBALSINDEX,"v" + declaredClasses[d].getSimpleName());
            lua_call(L,1,1);
            lua_setfield(L,LUA_GLOBALSINDEX,"t" + declaredClasses[d].getSimpleName());

            classArr.add(declaredClasses[d].getSimpleName());
        }

        /* tLR = { XXXX=tXXXX,YYYY=tYYYY } */
        lua_createtable(L, 0, classArr.size());
        for(int i = 0; i < classArr.size(); i++)
        {
            lua_pushliteral(L, classArr.get(i));
            lua_getfield(L,LUA_GLOBALSINDEX,"t" + classArr.get(i));
            lua_rawset(L,-3);
        }

        lua_setfield(L,LUA_GLOBALSINDEX,"tLR");

        /* _G['LR'] = readOnlyTable(tLR) */
        lua_getfield(L,LUA_GLOBALSINDEX,"readOnlyTable");
        lua_getfield(L,LUA_GLOBALSINDEX,"tLR");
        lua_call(L,1,1);
        lua_getfield(L,LUA_GLOBALSINDEX,"_G");
        lua_insert(L,-2);
        lua_pushliteral(L,"LR");
        lua_insert(L,-2);
        lua_settable(L,-3);
        lua_pop(L,1);
    }

    /**
     * (Ignore)
     */
    @LuaFunction(manual = false, methodName = "WithValue", self = LuaRef.class, arguments = { Integer.class })
    public static LuaRef WithValue(int val)
    {
        return new LuaRef(val);
    }

    /**
     * Returns LuaRef from resource string
     * @param ctx
     * @param id
     * @return LuaRef
     */
    @LuaFunction(manual = false, methodName = "GetRef", self = LuaRef.class, arguments = { LuaContext.class, String.class })
    public static LuaRef GetRef(LuaContext ctx, String id)
    {
        return new LuaRef(ctx.GetContext().getResources().getIdentifier(id, null, null));
    }

    @Override
    public String GetId()
    {
        return "LuaRef";
    }
}
