package dev.topping.android.backend;

public class LuaObject <T>
{
    public T obj;

    public void PushObject(T ptr)
    {
        obj = ptr;
    }
}
