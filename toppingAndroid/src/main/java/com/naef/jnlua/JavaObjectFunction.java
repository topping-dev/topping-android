package com.naef.jnlua;

public abstract class JavaObjectFunction implements JavaFunction
{
    protected Object obj;

    public JavaObjectFunction(Object obj)
    {
        this.obj = obj;
    }
}
