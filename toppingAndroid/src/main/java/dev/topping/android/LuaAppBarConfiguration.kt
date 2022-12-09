package dev.topping.android

import androidx.navigation.ui.AppBarConfiguration
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import dev.topping.android.luagui.LuaRef

@LuaClass(className = "LuaAppBarConfiguration", isKotlin = true)
class LuaAppBarConfiguration(var appBarConfiguration: AppBarConfiguration) {
    companion object {
        @LuaFunction(
            manual = false,
            methodName = "create",
            arguments = [],
            self = LuaAppBarConfiguration::class
        )
        fun create(
            singleTop: Boolean, popUpTo: LuaRef, popUpToInclusive: Boolean,
            enterAnim: LuaRef, exitAnim: LuaRef,
            popEnterAnim: LuaRef, popExitAnim: LuaRef
        ): LuaAppBarConfiguration {
            return LuaAppBarConfiguration(
                AppBarConfiguration.Builder().build()
            )
        }
    }

    @LuaFunction(
        manual = false,
        methodName = "setTopLevelDestinations",
        arguments = [Array::class],
    )
    fun setTopLevelDestinations(ids: Array<LuaRef>) {
        appBarConfiguration = AppBarConfiguration.Builder(*ids.map {
            it.ref
        }.toIntArray()).build()
    }
}