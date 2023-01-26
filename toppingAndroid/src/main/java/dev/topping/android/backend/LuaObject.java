package dev.topping.android.backend;

public class LuaObject <T>
{
    public T obj;

    public void pushObject(T ptr)
    {
        obj = ptr;
    }
}
