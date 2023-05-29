package dev.topping.android

import androidx.lifecycle.LifecycleOwner
import dev.topping.android.backend.LuaClass

/**
 * Lifecycle owner
 */
@LuaClass(className = "LuaLifecycleOwner")
open class LuaLifecycleOwner(owner: LifecycleOwner) {
    private var lifecycleOwner: LifecycleOwner = owner

    /**
     * (Ignore)
     */
    open fun getLifecycleOwner(): LifecycleOwner {
        return lifecycleOwner
    }
}