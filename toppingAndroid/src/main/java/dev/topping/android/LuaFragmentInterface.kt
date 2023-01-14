package dev.topping.android

import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@LuaClass(className = "LuaFragmentInterface", isKotlin = true)
class LuaFragmentInterface {
    lateinit var ltOnCreate: LuaTranslator
    lateinit var ltOnCreateView: LuaTranslator
    lateinit var ltOnViewCreated: LuaTranslator
    lateinit var ltOnResume: LuaTranslator
    lateinit var ltOnPause: LuaTranslator
    lateinit var ltOnDestroy: LuaTranslator
}