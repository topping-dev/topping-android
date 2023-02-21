package dev.topping.android

import androidx.navigation.ui.AppBarConfiguration
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import dev.topping.android.luagui.LuaRef

/**
 * App Bar Configuration
 */
@LuaClass(className = "LuaAppBarConfiguration", isKotlin = true)
class LuaAppBarConfiguration(var appBarConfiguration: AppBarConfiguration) {
    companion object {
        /**
         * Create app bar configuration
         * @param singleTop boolean
         * @param popUpTo LuaRef
         * @param popUpToInclusive LuaRef
         * @param enterAnim LuaRef
         * @param exitAnim LuaRef
         * @param popEnterAnim LuaRef
         * @param popExitAnim LuaRef
         * @return LuaAppBarConfiguration
         */
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

    /**
     * Set top level destinations
     * @param ids array
     */
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