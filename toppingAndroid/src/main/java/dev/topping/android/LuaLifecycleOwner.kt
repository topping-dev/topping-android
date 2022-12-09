package dev.topping.android

import androidx.lifecycle.LifecycleOwner

interface LuaLifecycleOwner {
    fun getLifecycleOwner(): LifecycleOwner
}