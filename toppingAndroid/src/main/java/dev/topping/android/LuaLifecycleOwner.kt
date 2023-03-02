package dev.topping.android

import androidx.lifecycle.LifecycleOwner
import dev.topping.android.backend.LuaClass

/**
 * Lifecycle owner
 */
@LuaClass(className = "LuaLifecycleOwner")
interface LuaLifecycleOwner {
    /**
     * (Ignore)
     */
    fun getLifecycleOwner(): LifecycleOwner
}