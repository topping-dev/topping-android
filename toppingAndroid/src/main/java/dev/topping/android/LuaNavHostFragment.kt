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
open class LuaNavHostFragment : NavHostFragment, LuaInterface {
    protected var luaContext: LuaContext? = null
    protected var luaId: String? = null
    protected var ui: String? = null
    protected var view: LGView? = null
    protected var rootView: LinearLayout? = null

    companion object {
        private val eventMap = HashMap<String, LuaTranslator>()

        fun OnFragmentEvent(self: LuaInterface, event: Int, lc: LuaContext?, vararg args: Any?): Boolean {
            var ltToCall: LuaTranslator? = null
            ltToCall = eventMap[self.GetId() + event]
            if (ltToCall != null) {
                if (args.isNotEmpty()) ltToCall.CallInSelf(self, lc, args)
                else ltToCall.CallInSelf(self, lc)
                return true
            }
            return false
        }

        /**
         * Registers GUI event
         * @param luaId
         * @param event +"LuaFragment.FRAGMENT_EVENT_CREATE" | "LuaFragment.FRAGMENT_EVENT_CREATE_VIEW" | "LuaFragment.FRAGMENT_EVENT_VIEW_CREATED" | "LuaFragment.FRAGMENT_EVENT_RESUME" | "LuaFragment.FRAGMENT_EVENT_PAUSE" | "LuaFragment.FRAGMENT_EVENT_DESTROY"
         * @param lt +fun(fragment: LuaNavHostFragment, context: LuaContext):void
         */
        @LuaFunction(
            manual = false,
            methodName = "RegisterFragmentEvent",
            self = LuaNavHostFragment::class,
            arguments = [String::class, Int::class, LuaTranslator::class]
        )
        fun RegisterFragmentEvent(luaId: String, event: Int, lt: LuaTranslator) {
            eventMap[luaId + event] = lt
        }

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
            arguments = [LuaContext::class, String::class]
        )
        fun Create(lc: LuaContext, luaId: String?): LuaNavHostFragment {
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
            arguments = [LuaContext::class, String::class, String::class]
        )
        fun CreateWithUI(
            lc: LuaContext,
            luaId: String?,
            ui: String?
        ): LuaNavHostFragment {
            return LuaNavHostFragment(lc.GetContext(), luaId, ui)
        }
    }

    /**
     * Gets LuaContext value of fragment
     * @return LuaContext
     */
    @LuaFunction(manual = false, methodName = "GetContext")
    fun GetContext(): LuaContext? {
        return luaContext
    }

    /**
     * (Ignore)
     */
    constructor() {}

    /**
     * (Ignore)
     */
    constructor(c: Context?, luaId: String?) {
        luaContext = LuaContext()
        luaContext!!.SetContext(c)
        this.luaId = luaId
    }

    /**
     * (Ignore)
     */
    constructor(c: Context?, luaId: String?, ui: String?) {
        luaContext = LuaContext()
        luaContext!!.SetContext(c)
        this.luaId = luaId
    }

    /**
     * Checks that fragment is initialized or not.
     * @return boolean
     */
    @LuaFunction(manual = false, methodName = "IsInitialized")
    fun IsInitialized(): Boolean {
        return view != null
    }

    /**
     * Gets the view by id
     * @param lId
     * @return LGView
     */
    @SuppressLint("UseRequireInsteadOfGet")
    @LuaFunction(manual = false, methodName = "GetViewById", arguments = [LuaRef::class])
    fun GetViewById(lId: LuaRef): LGView {
        return view!!.GetViewById(lId)
    }

    /**
     * Gets the view of fragment.
     * @return LGView
     */
    @LuaFunction(manual = false, methodName = "GetView")
    fun GetView(): LGView? {
        return view
    }

    /**
     * Sets the view to render.
     * @param v
     */
    @LuaFunction(manual = false, methodName = "SetView", arguments = [LGView::class])
    fun SetView(v: LGView) {
        view = v
        rootView!!.removeAllViews()
        rootView!!.addView(v.GetView())
    }

    /**
     * Sets the xml file of the view to render.
     * @param xml
     */
    @LuaFunction(manual = false, methodName = "SetViewXML", arguments = [String::class])
    fun SetViewXML(xml: String?) {
        val inflater = LuaViewInflator(luaContext)
        view = inflater.ParseFile(xml, null)
        rootView!!.removeAllViews()
        rootView!!.addView(view?.view)
    }

    /**
     * Sets the luaid of the view to render.
     * @param luaId
     */
    @LuaFunction(manual = false, methodName = "SetViewId", arguments = [String::class])
    fun SetViewId(luaId: String?) {
        this.luaId = luaId
    }

    /**
     * Sets the title of the screen.
     * @param str
     */
    @LuaFunction(manual = false, methodName = "SetTitle", arguments = [String::class])
    fun SetTitle(str: String?) {
        requireActivity().title = str
    }

    /**
     * Closes the form
     */
    @LuaFunction(manual = false, methodName = "Close")
    fun Close() {
        parentFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
    }

    /**
     * Get Nav Controller
     */
    @LuaFunction(manual = false, methodName = "getNavController")
    fun getNavControllerInternal(): LuaNavController
    {
        return LuaNavController(navController)
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
        super.onCreateView(inflater, container, savedInstanceState)
        val luaId = "LuaNavHostFragment"
        val ui = ""
        this.luaId = luaId
        luaContext = LuaContext.CreateLuaContext(inflater.context)
        if (ui.compareTo("") == 0) {
            OnFragmentEvent(this, LuaFragment.FRAGMENT_EVENT_CREATE, luaContext)
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
        OnFragmentEvent(this, LuaFragment.FRAGMENT_EVENT_RESUME, luaContext)
    }

    /**
     * (Ignore)
     */
    override fun onPause() {
        super.onPause()
        OnFragmentEvent(this, LuaFragment.FRAGMENT_EVENT_PAUSE, luaContext)
    }

    /**
     * (Ignore)
     */
    override fun onDestroy() {
        super.onDestroy()
        OnFragmentEvent(this, LuaFragment.FRAGMENT_EVENT_DESTROY, luaContext)
    }

    /**
     * (Ignore)
     */
    override fun GetId(): String {
        return luaId ?: "LuaNavHostFragment"
    }
}
