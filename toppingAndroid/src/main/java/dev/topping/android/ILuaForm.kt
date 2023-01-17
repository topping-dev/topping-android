package dev.topping.android

import dev.topping.android.backend.LuaClass

@LuaClass(className = "ILuaForm", isKotlin = true)
class ILuaForm {
    lateinit var ltOnCreate: LuaTranslator
    lateinit var ltOnResume: LuaTranslator
    lateinit var ltOnPause: LuaTranslator
    lateinit var ltOnDestroy: LuaTranslator
}