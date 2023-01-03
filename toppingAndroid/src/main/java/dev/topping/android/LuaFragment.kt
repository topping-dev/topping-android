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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import dev.topping.android.backend.LuaInterface
import dev.topping.android.luagui.LuaContext
import dev.topping.android.luagui.LuaRef
import dev.topping.android.luagui.LuaViewInflator
import dev.topping.android.osspecific.utils.toBundle

/**
 * User interface fragment
 */
@LuaClass(className = "LuaFragment", isKotlin = true)
open class LuaFragment : Fragment, LuaInterface {
    protected var luaContext: LuaContext? = null
    protected var luaId: String? = null
    protected var ui: LuaRef? = null
    protected var view: LGView? = null
    protected var rootView: LinearLayout? = null

    companion object {
        /**
         * Creates LuaFragment Object From Lua.
         * Fragment that created will be sent on FRAGMENT_EVENT_CREATE event.
         * @param lc
         * @param luaId
         * @return LuaFragment
         */
        @LuaFunction(
            manual = false,
            methodName = "Create",
            self = LuaFragment::class,
            arguments = [LuaContext::class, LuaRef::class]
        )
        fun Create(lc: LuaContext, luaId: LuaRef?): LuaFragment {
            return LuaFragment(lc.GetContext(), luaId)
        }

        /**
         * Creates LuaFragment Object From Lua.
         * Fragment that created will be sent on FRAGMENT_EVENT_CREATE event.
         * @param lc
         * @param luaId
         * @param args
         * @return LuaFragment
         */
        @LuaFunction(
            manual = false,
            methodName = "Create",
            self = LuaFragment::class,
            arguments = [LuaContext::class, LuaRef::class, Map::class]
        )
        fun Create(lc: LuaContext, luaId: LuaRef?, args: Map<String, Any>?): LuaFragment {
            return LuaFragment(lc.GetContext(), luaId, null, args)
        }

        /**
         * Creates LuaFragment Object From Lua with ui.
         * Fragment that created will be sent on FRAGMENT_EVENT_CREATE event.
         * @param lc
         * @param luaId
         * @param ui
         * @return LuaFragment
         */
        @LuaFunction(
            manual = false,
            methodName = "CreateWithUI",
            self = LuaFragment::class,
            arguments = [LuaContext::class, LuaRef::class, LuaRef::class, Map::class]
        )
        fun CreateWithUI(
            lc: LuaContext,
            luaId: LuaRef?,
            ui: LuaRef?,
            args: Map<String, Any>?
        ): LuaFragment {
            return LuaFragment(lc.GetContext(), luaId, ui, args)
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
    constructor(c: Context?, luaId: LuaRef?) {
        luaContext = LuaContext()
        luaContext!!.SetContext(c)
        this.luaId = c!!.resources.getResourceEntryName(luaId!!.ref)
    }

    /**
     * (Ignore)
     */
    constructor(c: Context?, luaId: LuaRef?, ui: LuaRef?, args: Map<String, Any>?) {
        luaContext = LuaContext()
        luaContext!!.SetContext(c)
        this.luaId = c!!.resources.getResourceEntryName(luaId?.ref!!)
        this.ui = ui
        args?.let {
            arguments = it.toBundle(Bundle())
        }
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
    @LuaFunction(manual = false, methodName = "SetViewXML", arguments = [LuaRef::class])
    fun SetViewXML(xml: LuaRef?) {
        val inflater = LuaViewInflator(luaContext)
        view = inflater.Inflate(xml, null)
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
    fun getNavController(): LuaNavController
    {
        return LuaNavController(findNavController())
    }

    /**
     * (Ignore)
     */
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        luaContext = LuaContext.CreateLuaContext(inflater.context)
        if (ui == null) {
            view = LuaEvent.OnUIEvent(this, LuaEvent.UI_EVENT_FRAGMENT_CREATE_VIEW, luaContext, LuaViewInflator(luaContext), null, LuaBundle(savedInstanceState)) as LGView?
        } else {
            val inflaterL = LuaViewInflator(luaContext)
            view = inflaterL.Inflate(ui, null)
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LuaEvent.OnUIEvent(this, LuaEvent.UI_EVENT_FRAGMENT_VIEW_CREATED, luaContext)
    }

    /**
     * (Ignore)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LuaEvent.OnUIEvent(this, LuaEvent.UI_EVENT_CREATE, luaContext)
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
        var idS: String? = null
        getNavController().navController.currentDestination?.let {
            idS = requireContext().resources.getResourceEntryName(it.id)
        }
        if(idS == null)
            idS = requireContext().resources.getResourceEntryName(id)
        return idS ?: luaId ?: "LuaFragment"
    }
}
