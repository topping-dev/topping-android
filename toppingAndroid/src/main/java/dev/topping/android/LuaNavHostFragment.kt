package dev.topping.android

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LGView
import android.widget.LinearLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import dev.topping.android.backend.LuaInterface
import dev.topping.android.luagui.LuaContext
import dev.topping.android.luagui.LuaRef
import dev.topping.android.luagui.LuaViewInflator
import java.util.HashMap

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
            methodName = "Create",
            self = LuaNavHostFragment::class,
            arguments = [LuaContext::class, LuaRef::class]
        )
        fun Create(lc: LuaContext, luaId: LuaRef?): LuaNavHostFragment {
            return LuaNavHostFragment(lc.GetContext(), luaId)
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
            methodName = "CreateWithUI",
            self = LuaNavHostFragment::class,
            arguments = [LuaContext::class, LuaRef::class, LuaRef::class]
        )
        fun CreateWithUI(
            lc: LuaContext,
            luaId: LuaRef?,
            ui: LuaRef?
        ): LuaNavHostFragment {
            return LuaNavHostFragment(lc.GetContext(), luaId, ui)
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
        luaContext!!.SetContext(c)
        this.luaId = c!!.resources.getResourceEntryName(luaId!!.ref)
    }

    /**
     * (Ignore)
     */
    constructor(c: Context?, luaId: LuaRef?, ui: LuaRef?) {
        luaContext = LuaContext()
        luaContext!!.SetContext(c)
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
        luaContext = LuaContext.CreateLuaContext(inflater.context)
        if (ui.compareTo("") == 0) {
            LuaEvent.OnUIEvent(this, LuaEvent.UI_EVENT_CREATE, luaContext)
        } else {
            val inflaterL = LuaViewInflator(luaContext)
            view = inflaterL.ParseFile(ui, null)
        }
        return if (view != null) view!!.GetView() else {
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
        LuaEvent.OnUIEvent(this, LuaEvent.UI_EVENT_RESUME, luaContext)
    }

    /**
     * (Ignore)
     */
    override fun onPause() {
        super.onPause()
        LuaEvent.OnUIEvent(this, LuaEvent.UI_EVENT_PAUSE, luaContext)
    }

    /**
     * (Ignore)
     */
    override fun onDestroy() {
        super.onDestroy()
        LuaEvent.OnUIEvent(this, LuaEvent.UI_EVENT_DESTROY, luaContext)
    }

    /**
     * (Ignore)
     */
    override fun GetId(): String {
        val idS = requireContext().resources.getResourceEntryName(super.getId())
        return idS ?: luaId ?: "LuaNavHostFragment"
    }
}
