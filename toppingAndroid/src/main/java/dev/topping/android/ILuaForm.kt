package dev.topping.android

import dev.topping.android.backend.LuaClass

/**
 * Interface that you can override in kotlin to approach ILuaForm more objective way
 */
@LuaClass(className = "ILuaForm", isKotlin = true)
class ILuaForm {
    /**
     * (Ignore)
     */
    lateinit var ltOnCreate: LuaTranslator

    /**
     * (Ignore)
     */
    lateinit var ltOnResume: LuaTranslator

    /**
     * (Ignore)
     */
    lateinit var ltOnPause: LuaTranslator

    /**
     * (Ignore)
     */
    lateinit var ltOnDestroy: LuaTranslator

    /**
     * onCreate
     */
    fun onCreate() {}

    /**
     * onResume
     */
    fun onResume() {}

    /**
     * onPause
     */
    fun onPause() {}

    /**
     * onDestroy
     */
    fun onDestroy() {}

    /**
     * Get form object
     * @return LuaForm
     */
    fun getForm(): LuaForm {
        return LuaForm()
    }
}