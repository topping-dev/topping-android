package dev.topping.android

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ResolveInfo
import android.nfc.FormatException
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.LGView
import android.widget.LGViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import dev.topping.android.backend.LuaInterface
import dev.topping.android.backend.LuaStaticVariable
import dev.topping.android.luagui.LuaContext
import dev.topping.android.luagui.LuaRef
import dev.topping.android.luagui.LuaViewInflator
import dev.topping.android.osspecific.Defines
import dev.topping.android.osspecific.utils.Common
import org.ndeftools.Message
import org.ndeftools.wellknown.SmartPosterRecord
import org.ndeftools.wellknown.UriRecord
import java.util.*


/**
 * User interface form
 */
@LuaClass(className = "LuaForm", isKotlin = true)
open class LuaForm : AppCompatActivity(), LuaInterface, LuaLifecycleOwner {
    protected var luaContext: LuaContext? = null
    protected var luaId: String? = "LuaForm"
    protected var ui: String? = ""
    protected var view: LGView? = null

    //Nfc
    var mNfcAdapter: NfcAdapter? = null
    var mNfcPendingIntent: PendingIntent? = null

    companion object {
        private var activeForm: LuaForm? = null
        private val eventMap = HashMap<String, LuaTranslator>()

        /**
         * Fires when user interface is created
         */
        @LuaStaticVariable
        @JvmField
        var FORM_EVENT_CREATE = 0

        /**
         * Fires when user interface resumed
         */
        @LuaStaticVariable
        @JvmField
        var FORM_EVENT_RESUME = 1

        /**
         * Fires when user interface paused
         */
        @LuaStaticVariable
        @JvmField
        var FORM_EVENT_PAUSE = 2

        /**
         * Fires when user interface destroyed
         */
        @LuaStaticVariable
        @JvmField
        var FORM_EVENT_DESTROY = 3

        /**
         * Fires when user interfaces updated
         */
        @LuaStaticVariable
        @JvmField
        var FORM_EVENT_UPDATE = 4

        /**
         * Fires when user interface paint called
         */
        @LuaStaticVariable
        @JvmField
        var FORM_EVENT_PAINT = 5

        /**
         * Fires when user interface tapped
         */
        @LuaStaticVariable
        @JvmField
        var FORM_EVENT_MOUSEDOWN = 6

        /**
         * Fires when user interface tap dropped
         */
        @LuaStaticVariable
        @JvmField
        var FORM_EVENT_MOUSEUP = 7

        /**
         * Fires when user touches and moves
         */
        @LuaStaticVariable
        @JvmField
        var FORM_EVENT_MOUSEMOVE = 8

        /**
         * Fires when keystroke happened
         */
        @LuaStaticVariable
        @JvmField
        var FORM_EVENT_KEYDOWN = 9

        /**
         * Fires when keystoke dropped
         */
        @LuaStaticVariable
        @JvmField
        var FORM_EVENT_KEYUP = 10

        /**
         * Fires when nfc event happened
         */
        @LuaStaticVariable
        @JvmField
        var FORM_EVENT_NFC = 11

        fun OnFormEvent(self: LuaInterface, event: Int, lc: LuaContext?, vararg args: Any?): Boolean {
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
         * @param event +"LuaForm.FORM_EVENT_CREATE" | "LuaForm.FORM_EVENT_RESUME" | "LuaForm.FORM_EVENT_RESUME" | "LuaForm.FORM_EVENT_PAUSE" | "LuaForm.FORM_EVENT_DESTROY" | "LuaForm.FORM_EVENT_UPDATE" | "LuaForm.FORM_EVENT_PAINT" | "LuaForm.FORM_EVENT_MOUSEDOWN" | "LuaForm.FORM_EVENT_MOUSEUP" | "LuaForm.FORM_EVENT_MOUSEMOVE" | "LuaForm.FORM_EVENT_KEYDOWN" | "LuaForm.FORM_EVENT_KEYUP"
         * @param lt +fun(form: LuaForm, context: LuaContext):void
         */
        @LuaFunction(
            manual = false,
            methodName = "RegisterFormEvent",
            self = LuaForm::class,
            arguments = [String::class, Int::class, LuaTranslator::class]
        )
        fun RegisterFormEvent(luaId: String, event: Int, lt: LuaTranslator) {
            eventMap[luaId + event] = lt
        }

        /**
         * Creates LuaForm Object From Lua.
         * Form that created will be sent on FORM_EVENT_CREATE event.
         * @param lc
         * @param luaId
         */
        @LuaFunction(
            manual = false,
            methodName = "Create",
            self = LuaForm::class,
            arguments = [LuaContext::class, String::class]
        )
        fun Create(lc: LuaContext, luaId: String?) {
            val intent = Intent(lc.GetContext(), LuaForm::class.java)
            intent.putExtra("LUA_ID_RUED", luaId)
            lc.GetContext().startActivity(intent)
        }

        /**
         * Creates LuaForm Object From Lua with ui.
         * Form that created will be sent on FORM_EVENT_CREATE event.
         * @param lc
         * @param luaId
         * @param ui
         */
        @LuaFunction(
            manual = false,
            methodName = "CreateWithUI",
            self = LuaForm::class,
            arguments = [LuaContext::class, String::class, String::class]
        )
        fun CreateWithUI(lc: LuaContext, luaId: String?, ui: String?) {
            val intent = Intent(lc.GetContext(), LuaForm::class.java)
            intent.putExtra("LUA_ID_RUED", luaId)
            intent.putExtra("LUA_UI_RUED", ui)
            lc.GetContext().startActivity(intent)
        }

        /**
         * Creates LuaForm Object From Lua for tabs.
         * @param lc
         * @param luaId
         * @return NativeObject
         */
        @LuaFunction(
            manual = false,
            methodName = "CreateForTab",
            self = LuaForm::class,
            arguments = [LuaContext::class, String::class]
        )
        fun CreateForTab(lc: LuaContext, luaId: String?): Any {
            val intent = Intent(lc.GetContext(), LuaForm::class.java)
            intent.putExtra("LUA_ID_RUED", luaId)
            return intent
        }

        /**
         * Gets Active LuaForm
         * @return LuaForm
         */
        @LuaFunction(manual = false, methodName = "GetActiveForm", self = LuaForm::class)
        fun GetActiveForm(): LuaForm? {
            return activeForm
        }

        fun SetActiveForm(form: LuaForm?) {
            activeForm = form
        }
    }

    /**
     * Gets LuaContext value of form
     * @return LuaContext
     */
    @LuaFunction(manual = false, methodName = "GetContext")
    fun GetContext(): LuaContext? {
        return luaContext
    }

    /**
     * Gets the view of form.
     * @return LGView
     */
    @LuaFunction(manual = false, methodName = "GetViewById", arguments = [String::class])
    fun GetViewById(lId: String?): LGView {
        return view!!.GetViewById(lId)
    }

    /**
     * Gets the view of form.
     * @return LGView
     */
    @LuaFunction(manual = false, methodName = "GetViewByIdRef", arguments = [LuaRef::class])
    fun GetViewByIdRef(ref: LuaRef): LGView {
        val resourceName = luaContext!!.GetContext().resources.getResourceEntryName(ref.ref)
        return view!!.GetViewById(resourceName)
    }

    /**
     * Gets the view bindings
     * @return HashMap
     */
    @LuaFunction(manual = false, methodName = "GetBindings", arguments = [])
    fun GetBindings(): HashMap<String, LGView>? {
        return if (view is LGViewGroup) (view as LGViewGroup).GetBindings() else null
    }

    /**
     * Gets the view.
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
    fun SetView(v: LGView?) {
        view = v
    }

    /**
     * Sets the xml file of the view to render.
     * @param xml
     */
    @LuaFunction(manual = false, methodName = "SetViewXML", arguments = [String::class])
    fun SetViewXML(xml: String?) {
        val inflater = LuaViewInflator(luaContext)
        view = inflater.ParseFile(xml, null)
        setContentView(view?.view)
    }

    /**
     * Sets the title of the screen.
     * @param str
     */
    @LuaFunction(manual = false, methodName = "SetTitle", arguments = [String::class])
    fun SetTitle(str: String?) {
        title = str
    }

    /**
     * Sets the title of the screen.
     * @param ref
     */
    @LuaFunction(manual = false, methodName = "SetTitleRef", arguments = [String::class])
    fun SetTitleRef(ref: LuaRef) {
        setTitle(ref.ref)
    }

    /**
     * Closes the form
     */
    @LuaFunction(manual = false, methodName = "Close")
    fun Close() {
        finishActivity(-1)
    }

    /**
     * Get lifecycle
     */
    @LuaFunction(manual = false, methodName = "GetLifecycle")
    fun GetLifecycle() : LuaLifecycle {
        return LuaLifecycle.Create(this)
    }

    /**
     * Get Fragment Manager
     */
    @LuaFunction(manual = false, methodName = "getFragmentManager")
    fun getFragmentManagerInner() : LuaFragmentManager {
        return LuaFragmentManager(supportFragmentManager)
    }

    /**
     * (Ignore)
     */
    @SuppressLint("NewApi")
    fun onCreateOverload(savedInstanceState: Bundle?) {
        if (Defines.CheckPermission(this, Manifest.permission.NFC)) {
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

            // Create a generic PendingIntent that will be deliver to this activity. The NFC stack
            // will fill in the intent with the details of the discovered tag before delivering to
            // this activity.
            mNfcPendingIntent = PendingIntent.getActivity(
                this, 0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
            )
        }
        Common.pack = applicationContext.packageName
        Common.scale = resources.displayMetrics.density
        super.onCreate(savedInstanceState)
    }

    /**
     * (Ignore)
     */
    @SuppressLint("NewApi")
    fun SetNfc(adapter: NfcAdapter?, pi: PendingIntent?) {
        if (Defines.CheckPermission(this, Manifest.permission.NFC)) {
            if (mNfcAdapter != null) mNfcAdapter!!.disableForegroundDispatch(this)
            mNfcAdapter = adapter
            mNfcPendingIntent = pi
            if (mNfcAdapter != null) mNfcAdapter!!.enableForegroundDispatch(
                this,
                mNfcPendingIntent,
                null,
                null
            )
        }
    }

    /**
     * (Ignore)
     */
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        if (Defines.CheckPermission(this, Manifest.permission.NFC)) {
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

            // Create a generic PendingIntent that will be deliver to this activity. The NFC stack
            // will fill in the intent with the details of the discovered tag before delivering to
            // this activity.
            mNfcPendingIntent = PendingIntent.getActivity(
                this, 0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
            )
        }
        Common.pack = applicationContext.packageName
        Common.scale = resources.displayMetrics.density
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        /*val pm = packageManager
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val appList = pm.queryIntentActivities(mainIntent, 0)
        Collections.sort(appList, ResolveInfo.DisplayNameComparator(pm))
        for (temp in appList) {
            Log.d("Lua Engine", "Launcher package and activity name = "
                        + temp.activityInfo.packageName + "    "
                        + temp.activityInfo.name
            )
            if (javaClass.simpleName.compareTo(temp.activityInfo.name) == 0) {
                return
            }
        }*/
        /*if(this.getClass() == MainActivity.class)
			return;*/
        activeForm = this
        var luaId: String? = "LuaForm"
        var extras: Bundle? = null
        if (savedInstanceState == null) {
            extras = intent.extras
            if (extras == null) {
                luaId = "LuaForm"
                ui = ""
            } else {
                luaId = extras.getString("LUA_ID_RUED", "LuaForm")
                ui = extras.getString("LUA_UI_RUED", "")
            }
        } else {
            luaId = savedInstanceState.getString("LUA_ID_RUED", "LuaForm")
            ui = savedInstanceState.getString("LUA_UI_RUED", "")
        }
        this.luaId = luaId
        luaContext = LuaContext.CreateLuaContext(this)
        if (ui == null || ui!!.compareTo("") == 0) {
            OnFormEvent(this, FORM_EVENT_CREATE, luaContext)
        } else {
            val inflater = LuaViewInflator(luaContext)
            view = inflater.ParseFile(ui, null)
            setContentView(view?.view)
        }
    }

    /**
     * (Ignore)
     */
    @SuppressLint("NewApi")
    override fun onResume() {
        super.onResume()
        activeForm = this
        if (Defines.CheckPermission(this, Manifest.permission.NFC)) {
            if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action || NfcAdapter.ACTION_TAG_DISCOVERED == intent.action || NfcAdapter.ACTION_TECH_DISCOVERED == intent.action) {
                val arr = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                for (p in arr!!) {
                    if (p is NdefMessage) {
                        try {
                            /*TODO: Burda hashmap uzerinden halledecez,
							 * mesela smartposterrecord icin hashmap<Integer, Hashmap<String, string>
							 * uzerinde ic hashmapte
							 * "type"->"spr" (SmartPosterRecord)
							 * "title"->"title degeri"
							 * "uri"->"uri degeri"
							 * gibi ayarlanıp gönderilecek,
							 * su an icin sadece urli text olarak parse icin gonderiyoruz.
							 */
                            val highLevel = Message(p)
                            for (r in highLevel) {
                                if (r is UriRecord) {
                                    OnFormEvent(this, FORM_EVENT_NFC, luaContext, r.uri.toString())
                                }
                            }
                        } catch (e: FormatException) {
                        }
                    }
                }
            }
        }
        OnFormEvent(this, FORM_EVENT_RESUME, luaContext)
        if (Defines.CheckPermission(this, Manifest.permission.NFC)) {
            if (mNfcAdapter != null) mNfcAdapter!!.enableForegroundDispatch(
                this,
                mNfcPendingIntent,
                null,
                null
            )
        }
        if (view != null) {
            view!!.onResume()
        }
    }

    /**
     * (Ignore)
     */
    @SuppressLint("NewApi")
    override fun onPause() {
        super.onPause()
        OnFormEvent(this, FORM_EVENT_PAUSE, luaContext)
        if (Defines.CheckPermission(this, Manifest.permission.NFC)) {
            if (mNfcAdapter != null) mNfcAdapter!!.disableForegroundDispatch(this)
        }
        if (view != null) {
            view!!.onPause()
        }
    }

    /**
     * (Ignore)
     */
    override fun onDestroy() {
        super.onDestroy()
        OnFormEvent(this, FORM_EVENT_DESTROY, luaContext)
        //Move destroy event to subviews
        if (view != null) {
            view!!.onDestroy()
        }
    }

    /**
     * (Ignore)
     */
    @SuppressLint("NewApi")
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (Defines.CheckPermission(this, Manifest.permission.NFC)) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            for (tech in tag!!.techList) {
                if (tech == Ndef::class.java.name) {
                    val arr = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                    for (p in arr!!) {
                        if (p is NdefMessage) {
                            try {
                                /*TODO: Burda hashmap uzerinden halledecez,
								 * mesela smartposterrecord icin hashmap<Integer, Hashmap<String, string>
								 * uzerinde ic hashmapte
								 * "type"->"spr" (SmartPosterRecord)
								 * "title"->"title degeri"
								 * "uri"->"uri degeri"
								 * gibi ayarlanıp gönderilecek,
								 * su an icin sadece urli text olarak parse icin gonderiyoruz.
								 */
                                var count = 0
                                val nfcData = HashMap<Int, HashMap<String, String>>()
                                val highLevel = Message(p)
                                for (r in highLevel) {
                                    val record = HashMap<String, String>()
                                    if (r is UriRecord) {
                                        record["type"] = "uri"
                                        record["uri"] = r.uri.toString()
                                        nfcData[count++] = record
                                    }
                                    if (r is SmartPosterRecord) {
                                        val spr = r
                                        record["type"] = "spr"
                                        record["title"] = spr.title.toString()
                                        record["uri"] = spr.uri.toString()
                                        nfcData[count++] = record
                                    }
                                }
                                OnFormEvent(this, FORM_EVENT_NFC, luaContext, nfcData)
                            } catch (e: FormatException) {
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * (Ignore)
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        luaId = savedInstanceState.getString("LUA_ID_RUED")
        ui = savedInstanceState.getString("LUA_UI_RUED")
    }

    /**
     * (Ignore)
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("LUA_ID_RUED", luaId)
        outState.putString("LUA_UI_RUED", ui)
    }

    /**
     * (Ignore)
     */
    override fun getLifecycleOwner(): LifecycleOwner {
        return this
    }

    /**
     * (Ignore)
     */
    override fun GetId(): String {
        return luaId ?: "LuaForm"
    }
}