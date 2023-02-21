package dev.topping.android

import androidx.navigation.NavOptions
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import dev.topping.android.luagui.LuaRef

/**
 * Navigation options
 */
@LuaClass(className = "LuaNavOptions", isKotlin = true)
class LuaNavOptions(val navOptions: NavOptions) {
    companion object {
        /**
         * Create Nav Options
         * @param singleTop
         * @param popUpTo
         * @param popUpToInclusive
         * @param enterAnim
         * @param exitAnim
         * @param popEnterAnim
         * @param popExitAnim
         * @return LuaNavOptions
         */
        @LuaFunction(
            manual = false,
            methodName = "create",
            arguments = [Boolean::class, LuaRef::class, Boolean::class, LuaRef::class, LuaRef::class, LuaRef::class, LuaRef::class],
            self = LuaNavOptions::class
        )
        fun create(
            singleTop: Boolean, popUpTo: LuaRef, popUpToInclusive: Boolean,
            enterAnim: LuaRef, exitAnim: LuaRef,
            popEnterAnim: LuaRef, popExitAnim: LuaRef
        ): LuaNavOptions {
            return LuaNavOptions(
                NavOptions.Builder().setLaunchSingleTop(singleTop)
                    .setPopUpTo(popUpTo.ref, popUpToInclusive)
                    .setEnterAnim(enterAnim.ref).setExitAnim(exitAnim.ref)
                    .setPopEnterAnim(popEnterAnim.ref).setPopExitAnim(popExitAnim.ref).build()
            )
        }
    }
}