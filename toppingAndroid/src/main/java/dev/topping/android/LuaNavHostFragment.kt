package dev.topping.android

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.NavHostFragment
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import dev.topping.android.backend.LuaInterface
import dev.topping.android.luagui.LuaContext
import dev.topping.android.luagui.LuaRef
import dev.topping.android.luagui.LuaViewInflator

/**
 * User interface fragment
 */
@LuaClass(className = "LuaNavHostFragment", isKotlin = true)
open class LuaNavHostFragment : LuaFragment, LuaInterface {
    var navHostFragment: NavHostFragment? = null

    companion object {
        /**
         * Creates LuaNavHostFragment Object From Lua.
         * Fragment that created will be sent on FRAGMENT_EVENT_CREATE event.
         * @param lc
         * @param luaId
         * @return LuaNavHostFragment
         */
        @LuaFunction(
            manual = false,
            methodName = "create",
            self = LuaNavHostFragment::class,
            arguments = [LuaContext::class, LuaRef::class]
        )
        fun create(lc: LuaContext, luaId: LuaRef?): LuaNavHostFragment {
            return LuaNavHostFragment(lc.getContext(), luaId)
        }

        /**
         * Creates LuaNavHostFragment Object From Lua with ui.
         * Fragment that created will be sent on FRAGMENT_EVENT_CREATE event.
         * @param lc
         * @param luaId
         * @param ui
         * @return LuaNavHostFragment
         */
        @LuaFunction(
            manual = false,
            methodName = "createWithUI",
            self = LuaNavHostFragment::class,
            arguments = [LuaContext::class, LuaRef::class, LuaRef::class]
        )
        fun createWithUI(
            lc: LuaContext,
            luaId: LuaRef?,
            ui: LuaRef?
        ): LuaNavHostFragment {
            return LuaNavHostFragment(lc.getContext(), luaId, ui)
        }
    }

    /**
     * (Ignore)
     */
    constructor() {}

    /**
     * (Ignore)
     */
    constructor(c: Context?, luaId: LuaRef?) {
        luaContext = LuaContext()
        luaContext!!.setContext(c)
        this.luaId = c!!.resources.getResourceEntryName(luaId!!.ref)
    }

    /**
     * (Ignore)
     */
    constructor(c: Context?, luaId: LuaRef?, ui: LuaRef?) {
        luaContext = LuaContext()
        luaContext!!.setContext(c)
        this.luaId = c!!.resources.getResourceEntryName(luaId!!.ref)
        this.ui = ui
    }

    /**
     * Get Nav Controller
     */
    @LuaFunction(manual = false, methodName = "getNavController")
    fun getNavControllerInternal(): LuaNavController
    {
        return LuaNavController(navHostFragment?.navController)
    }

    /**
     *
     */

    /**
     * (Ignore)
     */
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.luaId = "LuaNavHostFragment"
        val ui = ""
        this.luaId = luaId
        luaContext = LuaContext.createLuaContext(inflater.context)
        if (ui.compareTo("") == 0) {
            LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_CREATE, luaContext)
        } else {
            val inflaterL = LuaViewInflator(luaContext)
            view = inflaterL.parseFile(ui, null)
        }
        return if (view != null) view!!.getView() else {
            rootView = LinearLayout(inflater.context)
            rootView!!.layoutParams =
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            rootView!!.setBackgroundColor(Color.WHITE)
            rootView
        }
    }

    /**
     * (Ignore)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * (Ignore)
     */
    override fun onResume() {
        super.onResume()
        LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_RESUME, luaContext)
    }

    /**
     * (Ignore)
     */
    override fun onPause() {
        super.onPause()
        LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_PAUSE, luaContext)
    }

    /**
     * (Ignore)
     */
    override fun onDestroy() {
        super.onDestroy()
        LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_DESTROY, luaContext)
    }

    /**
     * (Ignore)
     */
    override fun GetId(): String {
        val idS = requireContext().resources.getResourceEntryName(super.getId())
        return idS ?: luaId ?: "LuaNavHostFragment"
    }
}
