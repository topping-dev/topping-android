package dev.topping.android

import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaStaticVariable

@LuaClass(className = "LuaDispatchers", isKotlin = true)
class LuaDispatchers {
    companion object {
        /**
         * Dispatchers Default
         */
        @LuaStaticVariable
        var DEFAULT = 0

        /**
         * Dispatchers Main
         */
        @LuaStaticVariable
        var MAIN = 1

        /**
         * Dispatchers Unconfined
         */
        @LuaStaticVariable
        var UNCONFINED = 2

        /**
         * Dispatchers Io
         */
        @LuaStaticVariable
        var IO = 3
    }
}