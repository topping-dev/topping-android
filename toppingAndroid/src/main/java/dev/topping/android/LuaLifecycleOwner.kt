package dev.topping.android

import androidx.lifecycle.LifecycleOwner
import dev.topping.android.backend.LuaClass

@LuaClass(className = "LuaLifecycleOwner")
interface LuaLifecycleOwner {
    fun getLifecycleOwner(): LifecycleOwner
}