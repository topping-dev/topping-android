package dev.topping.android

import androidx.lifecycle.ViewModel
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction

@LuaClass(className = "LuaViewModel", isKotlin = true)
class LuaViewModel : ViewModel() {
    private val objectMap = mutableMapOf<String, Any?>()

    /**
     * Set LuaViewModel object
     * @param key
     * @param obj
     */
    @LuaFunction(
        manual = false,
        methodName = "SetObject",
        arguments = [String::class, Any::class]
    )
    fun SetObject(key: String, obj: Any?) {
        objectMap[key] = obj
    }

    /**
     * Get LuaViewModel object
     * @param key
     * @return object
     */
    @LuaFunction(
        manual = false,
        methodName = "GetObject",
        arguments = [String::class]
    )
    fun GetObject(key: String) : Any? {
        return objectMap[key]
    }
}