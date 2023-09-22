package dev.topping.android

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.nfc.FormatException
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LGView
import android.widget.LGViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.LifecycleOwner
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import dev.topping.android.backend.LuaInterface
import dev.topping.android.luagui.LuaContext
import dev.topping.android.luagui.LuaRef
import dev.topping.android.luagui.LuaViewInflator
import dev.topping.android.osspecific.Defines
import org.ndeftools.Message
import org.ndeftools.wellknown.SmartPosterRecord
import org.ndeftools.wellknown.UriRecord
import java.util.*

/**
 * User interface controller
 */
@LuaClass(className = "LuaForm", isKotlin = true)
open class LuaForm : AppCompatActivity(), LuaInterface {
    protected var luaContext: LuaContext? = null
    protected var luaId: String? = "LuaForm"
    protected var ui: LuaRef? = null
    protected var view: LGView? = null
    private var kotlinInterface: ILuaForm? = null

    //Nfc
    var mNfcAdapter: NfcAdapter? = null
    var mNfcPendingIntent: PendingIntent? = null

    companion object {
        private var activeForm: LuaForm? = null

        /**
         * Creates LuaForm Object From Lua.
         * Form that created will be sent on FORM_EVENT_CREATE event.
         * @param lc
         * @param luaId
         * @return LuaNativeObject
         */
        @LuaFunction(
            manual = false,
            methodName = "create",
            self = LuaForm::class,
            arguments = [LuaContext::class, LuaRef::class]
        )
        fun create(lc: LuaContext, luaId: LuaRef?) : LuaFormIntent {
            val intent = Intent(lc.getContext(), LuaForm::class.java)
            intent.putExtra("LUA_ID_RUED", luaId)
            return LuaFormIntent(intent)
        }

        /**
         * Creates LuaForm Object From Lua with ui.
         * Form that created will be sent on FORM_EVENT_CREATE event.
         * @param lc
         * @param luaId
         * @param ui
         * @return LuaNativeObject
         */
        @LuaFunction(
            manual = false,
            methodName = "createWithUI",
            self = LuaForm::class,
            arguments = [LuaContext::class, LuaRef::class, LuaRef::class]
        )
        fun createWithUI(lc: LuaContext, luaId: LuaRef?, ui: LuaRef?) : LuaFormIntent {
            val intent = Intent(lc.getContext(), LuaForm::class.java)
            intent.putExtra("LUA_ID_RUED", luaId)
            intent.putExtra("LUA_UI_RUED", ui)
            return LuaFormIntent(intent)
        }

        /**
         * Gets Active LuaForm
         * @return LuaForm
         */
        @LuaFunction(manual = false, methodName = "getActiveForm", self = LuaForm::class)
        fun getActiveForm(): LuaForm? {
            return activeForm
        }

        /**
         * (Ignore)
         */
        fun setActiveForm(form: LuaForm?) {
            activeForm = form
        }
    }

    /**
     * Gets LuaContext value of form
     * @return LuaContext
     */
    @LuaFunction(manual = false, methodName = "getContext")
    fun getContext(): LuaContext? {
        return luaContext
    }

    /**
     * Gets the view of form.
     * @return LGView
     */
    @LuaFunction(manual = false, methodName = "getViewById", arguments = [LuaRef::class])
    fun getViewById(lId: LuaRef): LGView? {
        if(view is LGViewGroup)
            return (view as LGViewGroup).getViewById(lId)
        else
            return null
    }

    /**
     * Gets the view bindings
     * @return HashMap
     */
    @LuaFunction(manual = false, methodName = "getBindings", arguments = [])
    fun getBindings(): HashMap<String, LGView>? {
        return if (view is LGViewGroup) (view as LGViewGroup).getBindings() else null
    }

    /**
     * Gets the view.
     * @return LGView
     */
    @JvmName("getViewIn")
    @LuaFunction(manual = false, methodName = "getView")
    fun getView(): LGView? {
        return view
    }

    /**
     * Sets the view to render.
     * @param v
     */
    @JvmName("setViewIn")
    @LuaFunction(manual = false, methodName = "setView", arguments = [LGView::class])
    fun setView(v: LGView?) {
        view = v
    }

    /**
     * Sets the xml file of the view to render.
     * @param xml
     */
    @LuaFunction(manual = false, methodName = "setViewXML", arguments = [LuaRef::class])
    fun setViewXML(xml: LuaRef?) {
        val inflater = LuaViewInflator(luaContext)
        view = inflater.inflate(xml, null)
        setContentView(view?.view)
    }

    /**
     * Sets the title of the screen.
     * @param str
     */
    @LuaFunction(manual = false, methodName = "setTitle", arguments = [String::class])
    fun setTitle(str: String?) {
        title = str
    }

    /**
     * Sets the title of the screen.
     * @param ref
     */
    @LuaFunction(manual = false, methodName = "setTitleRef", arguments = [String::class])
    fun setTitleRef(ref: LuaRef) {
        setTitle(ref.ref)
    }

    /**
     * Closes the form
     */
    @LuaFunction(manual = false, methodName = "close")
    fun close() {
        finishActivity(-1)
    }

    /**
     * Get lifecycle
     */
    @LuaFunction(manual = false, methodName = "getLifecycle")
    fun getLuaLifecycle() : LuaLifecycle {
        return LuaLifecycle.create(this)
    }

    /**
     * Get Fragment Manager
     */
    @LuaFunction(manual = false, methodName = "getFragmentManager")
    fun getFragmentManagerInner() : LuaFragmentManager {
        return LuaFragmentManager(supportFragmentManager)
    }

    /**
     * Get Lifecycle Owner
     */
    @LuaFunction(manual = false, methodName = "getLifecycleOwner")
    fun getLifecycleOwner(): LuaLifecycleOwner {
        return LuaLifecycleOwner(this)
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
        super.onCreate(savedInstanceState)
    }

    /**
     * (Ignore)
     */
    @SuppressLint("NewApi")
    fun setNfc(adapter: NfcAdapter?, pi: PendingIntent?) {
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
        supportFragmentManager.fragmentFactory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                try {
                    return super.instantiate(classLoader, className)
                } catch (ex: Exception)
                {
                    val res = resources.getIdentifier(className, "id", packageName)
                    return LuaFragment.create(this@LuaForm.luaContext!!, LuaRef.withValue(res), null)
                }
            }
        }
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
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        activeForm = this
        var luaId: LuaRef? = null
        var extras: Bundle? = null
        if (savedInstanceState == null) {
            extras = intent.extras
            if (extras == null) {
                luaId = LuaRef.withValue(View.NO_ID)
                ui = LuaRef.withValue(View.NO_ID)
            } else {
                luaId = extras.getSerializable("LUA_ID_RUED") as LuaRef?
                ui = extras.getSerializable("LUA_UI_RUED") as LuaRef?
            }
        } else {
            this.luaId = savedInstanceState.getString("LUA_ID_RUED")
            ui = savedInstanceState.getSerializable("LUA_UI_RUED") as LuaRef?
        }
        if(luaId != null)
            this.luaId = if(luaId.ref != View.NO_ID) resources.getResourceEntryName(luaId.ref) else "LuaForm"
        luaContext = LuaContext.createLuaContext(this)
        if (ui == null || ui!!.ref == View.NO_ID) {
            LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_CREATE, luaContext)
            kotlinInterface = LuaEvent.getFormInstance(GetId(), this)
            kotlinInterface?.ltOnCreate?.callIn()
        } else {
            val inflater = LuaViewInflator(luaContext)
            view = inflater.inflate(ui, null)
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
                                    LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_NFC, luaContext, r.uri.toString())
                                }
                            }
                        } catch (_: FormatException) {
                        }
                    }
                }
            }
        }
        LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_RESUME, luaContext)
        kotlinInterface?.ltOnResume?.callIn()
        if (Defines.CheckPermission(this, Manifest.permission.NFC)) {
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
    override fun onPause() {
        super.onPause()
        LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_PAUSE, luaContext)
        kotlinInterface?.ltOnPause?.callIn()
        if (Defines.CheckPermission(this, Manifest.permission.NFC)) {
            if (mNfcAdapter != null) mNfcAdapter!!.disableForegroundDispatch(this)
        }
    }

    /**
     * (Ignore)
     */
    override fun onDestroy() {
        super.onDestroy()
        LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_DESTROY, luaContext)
        kotlinInterface?.ltOnDestroy?.callIn()
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
                                LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_NFC, luaContext, nfcData)
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
        ui = savedInstanceState.getSerializable("LUA_UI_RUED") as LuaRef?
    }

    /**
     * (Ignore)
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("LUA_ID_RUED", luaId)
        outState.putSerializable("LUA_UI_RUED", ui)
    }

    /**
     * (Ignore)
     */
    fun afterInit() {
        LuaEvent.onUIEvent(this, LuaEvent.UI_EVENT_CREATE, luaContext)
        kotlinInterface = LuaEvent.getFormInstance(GetId(), this)
        kotlinInterface?.ltOnCreate?.callIn()
    }

    /**
     * (Ignore)
     */
    override fun GetId(): String {
        return luaId ?: "LuaForm"
    }
}