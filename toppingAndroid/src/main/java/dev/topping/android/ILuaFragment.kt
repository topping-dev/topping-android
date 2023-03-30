package dev.topping.android

import android.widget.LGView
import dev.topping.android.backend.LuaClass
import dev.topping.android.luagui.LuaContext
import dev.topping.android.luagui.LuaViewInflator

/**
 * Interface that you can override in kotlin to approach ILuaFragment more objective way
 */
@LuaClass(className = "ILuaFragment", isKotlin = true)
class ILuaFragment {
    /**
     * (Ignore)
     */
    lateinit var ltOnCreate: LuaTranslator

    /**
     * (Ignore)
     */
    lateinit var ltOnCreateView: LuaTranslator

    /**
     * (Ignore)
     */
    lateinit var ltOnViewCreated: LuaTranslator

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
     * @param savedInstanceState
     */
    fun onCreate(savedInstanceState: LuaBundle?) {}

    /**
     * onCreateView
     * @param luacontext
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    fun onCreateView(
        luacontext: LuaContext,
        inflater: LuaViewInflator,
        container: LGView?,
        savedInstanceState: LuaBundle?
    ): LGView {
        return LGView(LuaContext())
    }

    /**
     * onViewCreated
     * @param view
     * @param savedInstanceState
     */
    fun onViewCreated(
        view: LGView,
        savedInstanceState: LuaBundle?
    ) {
    }

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
     * Get Fragment object
     */
    fun getFragment(): LuaFragment {
        return LuaFragment()
    }
}