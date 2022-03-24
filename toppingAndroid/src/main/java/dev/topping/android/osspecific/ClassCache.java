package dev.topping.android.osspecific;

import java.util.HashMap;
import java.util.Map;

public class ClassCache {
    private static Map<String, Class> cache = new HashMap<>();

    public static Class forName(String clzName) throws ClassNotFoundException {
        Class clz = cache.get(clzName);
        if (clz != null) return clz;
        clz = Class.forName(clzName);
        cache.put(clzName, clz);
        return clz;
    }
}
