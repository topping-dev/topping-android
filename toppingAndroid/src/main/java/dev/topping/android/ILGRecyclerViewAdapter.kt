package dev.topping.android

import dev.topping.android.backend.LuaClass

@LuaClass(className = "ILGRecyclerViewAdapter", isKotlin = true)
class ILGRecyclerViewAdapter {
    lateinit var ltGetItemCount: LuaTranslator
    lateinit var ltOnCreateViewHolder: LuaTranslator
    lateinit var ltOnBindViewHolder: LuaTranslator
    lateinit var ltGetItemViewType: LuaTranslator
}