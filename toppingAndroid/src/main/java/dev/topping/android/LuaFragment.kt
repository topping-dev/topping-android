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
    private var kotlinInterface: ILuaFragment? = null

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
            methodName = "create",
            self = LuaFragment::class,
            arguments = [LuaContext::class, LuaRef::class]
        )
        fun create(lc: LuaContext, luaId: LuaRef?): LuaFragment {
            return LuaFragment(lc.getContext(), luaId)
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
            methodName = "create",
            self = LuaFragment::class,
            arguments = [LuaContext::class, LuaRef::class, Map::class]
        )
        fun create(lc: LuaContext, luaId: LuaRef?, args: Map<String, Any>?): LuaFragment {
            return LuaFragment(lc.getContext(), luaId, null, args)
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
            methodName = "createWithUI",
            self = LuaFragment::class,
            arguments = [LuaContext::class, LuaRef::class, LuaRef::class, Map::class]
        )
        fun createWithUI(
            lc: LuaContext,
            luaId: LuaRef?,
            ui: LuaRef?,
            args: Map<String, Any>?
        ): LuaFragment {
            return LuaFragment(lc.getContext(), luaId, ui, args)
        }
    }

    /**
     * Gets LuaContext value of fragment
     * @return LuaContext
     */
    @JvmName("getLuaContextJava")
    @LuaFunction(manual = false, methodName = "getLuaContext")
    fun getLuaContext(): LuaContext? {
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
        luaContext!!.setContext(c)
        this.luaId = c!!.resources.getResourceEntryName(luaId!!.ref)
    }

    /**
     * (Ignore)
     */
    constructor(c: Context?, luaId: LuaRef?, ui: LuaRef?, args: Map<String, Any>?) {
        luaContext = LuaContext()
        luaContext!!.setContext(c)
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
    @LuaFunction(manual = false, methodName = "isInitialized")
    fun isInitialized(): Boolean {
        return view != null
    }

    /**
     * Gets the view by id
     * @param lId
     * @return LGView
     */
    @SuppressLint("UseRequireInsteadOfGet")
    @LuaFunction(manual = false, methodName = "getViewById", arguments = [LuaRef::class])
    fun getViewById(lId: LuaRef): LGView {
        return view!!.getViewById(lId)
    }

    /**
     * Gets the view of fragment.
     * @return LGView
     */
    fun getLGView(): LGView? {
        return view
    }

    /**
     * Sets the view to render.
     * @param v
     */
    @LuaFunction(manual = false, methodName = "setView", arguments = [LGView::class])
    fun setLGView(v: LGView) {
        view = v
        rootView!!.removeAllViews()
        rootView!!.addView(v.getView())
    }

    /**
     * Sets the xml file of the view to render.
     * @param xml
     */
    @LuaFunction(manual = false, methodName = "setViewXML", arguments = [LuaRef::class])
    fun setViewXML(xml: LuaRef?) {
        val inflater = LuaViewInflator(luaContext)
        view = inflater.inflate(xml, null)
        rootView!!.removeAllViews()
        rootView!!.addView(view?.view)
    }

    /**
     * Sets the luaid of the view to render.
     * @param luaId
     */
    @LuaFunction(manual = false, methodName = "setViewId", arguments = [String::class])
    fun setViewId(luaId: String?) {
        this.luaId = luaId
    }

    /**
     * Sets the title of the screen.
     * @param str
     */
    @LuaFunction(manual = false, methodName = "setTitle", arguments = [String::class])
    fun setTitle(str: String?) {
        requireActivity().title = str
    }

    /**
     * Closes the form
     */
    @LuaFunction(manual = false, methodName = "close")
    fun close() {
        parentFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
    }

    /**
     * Get Arguments
     */
    @LuaFunction(manual = false, methodName = "getArguments")
    fun getArgumentsBundle(): LuaBundle
    {
        return LuaBundle(arguments)
    }

    /**
     * Get Nav Controller
     */
    @LuaFunction(manual = false, methodName = "getNavController")
    fun getNavController(): LuaNavController
    {
        return LuaNavController(findNavController())
    }

    @LuaFunction(manual = false, methodName = "getLifecycleOwner")
    fun getLifecycleOwner(): LuaLifecycleOwner {
        return LuaLifecycleOwner(viewLifecycleOwner)
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
        luaContext = LuaContext.createLuaContext(inflater.context)
        if (ui == null) {
            view = LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_FRAGMENT_CREATE_VIEW, luaContext, LuaViewInflator(luaContext), null, LuaBundle(savedInstanceState)) as LGView?
            if(view == null)
                view = kotlinInterface?.ltOnCreateView?.callIn(luaContext, LuaViewInflator(luaContext), null, LuaBundle(savedInstanceState)) as LGView?
        } else {
            val inflaterL = LuaViewInflator(luaContext)
            view = inflaterL.inflate(ui, null)
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kotlinInterface?.ltOnViewCreated?.callIn(this.view, LuaBundle(savedInstanceState))
        LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_FRAGMENT_VIEW_CREATED, luaContext)
    }

    /**
     * (Ignore)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kotlinInterface = LuaEvent.getFragmentInstance(GetId(), this)
        kotlinInterface?.ltOnCreate?.callIn(LuaBundle(null))
        LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_CREATE, luaContext)
    }

    /**
     * (Ignore)
     */
    override fun onResume() {
        super.onResume()
        kotlinInterface?.ltOnResume?.callIn()
        LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_RESUME, luaContext)
    }

    /**
     * (Ignore)
     */
    override fun onPause() {
        super.onPause()
        kotlinInterface?.ltOnPause?.callIn()
        LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_PAUSE, luaContext)
    }

    /**
     * (Ignore)
     */
    override fun onDestroy() {
        super.onDestroy()
        kotlinInterface?.ltOnDestroy?.callIn()
        LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_DESTROY, luaContext)
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
