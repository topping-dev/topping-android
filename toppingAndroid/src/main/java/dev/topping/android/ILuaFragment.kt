package dev.topping.android

import dev.topping.android.backend.LuaClass

@LuaClass(className = "ILuaFragment", isKotlin = true)
class ILuaFragment {
    lateinit var ltOnCreate: LuaTranslator
    lateinit var ltOnCreateView: LuaTranslator
    lateinit var ltOnViewCreated: LuaTranslator
    lateinit var ltOnResume: LuaTranslator
    lateinit var ltOnPause: LuaTranslator
    lateinit var ltOnDestroy: LuaTranslator
}