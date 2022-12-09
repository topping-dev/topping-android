package dev.topping.android

import androidx.lifecycle.ViewModelProvider
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction

@LuaClass(className = "LuaViewModelProvider", isKotlin = true)
class LuaViewModelProvider private constructor(private val provider: ViewModelProvider){
    companion object {
        /**
         * Get LuaViewModelProvider of LuaFragment
         * @param fragment
         * @return LuaViewModelProvider
         */
        @LuaFunction(
            manual = false,
            methodName = "OfFragment",
            arguments = [LuaFragment::class],
            self = LuaViewModelProvider::class
        )
        fun OfFragment(fragment: LuaFragment): LuaViewModelProvider {
            return LuaViewModelProvider(ViewModelProvider(fragment))
        }

        /**
         * Get LuaViewModelProvider of LuaForm
         * @param form
         * @return LuaViewModelProvider
         */
        @LuaFunction(
            manual = false,
            methodName = "OfForm",
            arguments = [LuaForm::class],
            self = LuaViewModelProvider::class
        )
        fun OfForm(form: LuaForm): LuaViewModelProvider {
            return LuaViewModelProvider(ViewModelProvider(form))
        }
    }

    /**
     * Get view model
     * @param tag
     * @return LuaViewModel
     */
    @LuaFunction(
        manual = false,
        methodName = "Get",
        arguments = [String::class]
    )
    fun Get(tag: String): LuaViewModel {
        return provider.get(tag, LuaViewModel::class.java)
    }
}