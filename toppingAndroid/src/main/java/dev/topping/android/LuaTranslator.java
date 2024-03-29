package dev.topping.android;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import kotlin.reflect.KCallable;

/**
 * Translates Lua Functions to native functions
 */
@LuaClass(className = "LuaTranslator")
public class LuaTranslator implements LuaInterface
{
    Object obj = null;
    String function = null;
    Integer nobj = null;
    public KCallable<Object> kObj = null;

    /**
     * Creates LuaTranslator Object From Lua.
     * @param obj
     * @param functionName
     * @return LuaTranslator
     */
    @LuaFunction(manual = false, methodName = "register", self = LuaTranslator.class, arguments = { Object.class, String.class })
    public static LuaTranslator register(Object obj, String functionName)
    {
        return new LuaTranslator(obj, functionName);
    }

    /**
     * (Ignore)
     */
    public LuaTranslator(Object objP, KCallable<Object> kObj)
    {
        this.obj = objP;
        this.kObj = kObj;
    }

    /**
     * (Ignore)
     */
    public LuaTranslator(Object objP, String functionP)
    {
        function = functionP;
        obj = objP;
    }

    /**
     * (Ignore)
     */
    public LuaTranslator(Object objP, Integer nObjP)
    {
        nobj = nObjP;
        obj = objP;
    }

    /**
     * (Ignore)
     */
    public Object callIn(Object ... args)
    {
        if(kObj != null)
        {
            if(obj == null)
                return kObj.call(args);
            else
                return kObj.call(obj, args);
        }
        if(nobj != null)
            return ToppingEngine.getInstance().onNativeEvent(obj, nobj, args);
        return ToppingEngine.getInstance().onGuiEvent(obj, function, args);
    }

    /**
     * (Ignore)
     */
    public Object callInSelf(Object self, Object ... args)
    {
        if(kObj != null)
        {
            Object[] newArgs = new Object[args.length + 1];
            newArgs[0] = self;
            for(int i = 0; i < args.length; i++)
                newArgs[i + 1] = args[i];
            return kObj.call(obj, newArgs);
        }
        if(nobj != null)
            return ToppingEngine.getInstance().onNativeEvent(self, nobj, args);
        return ToppingEngine.getInstance().onGuiEvent(self, function, args);
    }

    /**
     * (Ignore)
     */
    public Object call(Object a, Object b)
    {
        return callIn(a, b);
    }

    /**
     * (Ignore)
     */
    @Override
    public String GetId()
    {
        return "LuaTranslator";
    }
}
