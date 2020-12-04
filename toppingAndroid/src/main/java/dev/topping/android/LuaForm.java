package dev.topping.android;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LGView;

import org.ndeftools.Message;
import org.ndeftools.Record;
import org.ndeftools.wellknown.SmartPosterRecord;
import org.ndeftools.wellknown.UriRecord;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.backend.LuaStaticVariable;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaViewInflator;
import dev.topping.android.osspecific.Defines;
import dev.topping.android.osspecific.utils.Common;

/**
 * User interface form
 */
@LuaClass(className = "LuaForm")
public class LuaForm extends FragmentActivity implements LuaInterface
{
	private static LuaForm activeForm = null;
	protected LuaContext luaContext;
	protected String luaId = "LuaForm";
	protected String ui = "";
	protected LGView view;

	//Nfc
	NfcAdapter mNfcAdapter;
	PendingIntent mNfcPendingIntent;

	private static HashMap<String, LuaTranslator> eventMap = new HashMap<>();

	/**
	 * Fires when user interface is created
	 */
	@LuaStaticVariable
	public static int FORM_EVENT_CREATE = 0;
	/**
	 * Fires when user interface resumed
	 */
	@LuaStaticVariable
	public static int FORM_EVENT_RESUME = 1;
	/**
	 * Fires when user interface paused
	 */
	@LuaStaticVariable
	public static int FORM_EVENT_PAUSE = 2;
	/**
	 * Fires when user interface destroyed
	 */
	@LuaStaticVariable
	public static int FORM_EVENT_DESTROY = 3;
	/**
	 * Fires when user interfaces updated
	 */
	@LuaStaticVariable
	public static int FORM_EVENT_UPDATE = 4;
	/**
	 * Fires when user interface paint called
	 */
	@LuaStaticVariable
	public static int FORM_EVENT_PAINT = 5;
	/**
	 * Fires when user interface tapped
	 */
	@LuaStaticVariable
	public static int FORM_EVENT_MOUSEDOWN = 6;
	/**
	 * Fires when user interface tap dropped
	 */
	@LuaStaticVariable
	public static int FORM_EVENT_MOUSEUP = 7;
	/**
	 * Fires when user touches and moves
	 */
	@LuaStaticVariable
	public static int FORM_EVENT_MOUSEMOVE = 8;
	/**
	 * Fires when keystroke happened
	 */
	@LuaStaticVariable
	public static int FORM_EVENT_KEYDOWN = 9;
	/**
	 * Fires when keystoke dropped
	 */
	@LuaStaticVariable
	public static int FORM_EVENT_KEYUP = 10;
	/**
	 * Fires when nfc event happened
	 */
	@LuaStaticVariable
	public static int FORM_EVENT_NFC = 11;

	public static boolean OnFormEvent(LuaInterface self, int event, LuaContext lc, Object ... args)
	{
		LuaTranslator ltToCall = null;
		ltToCall = eventMap.get(self.GetId() + event);
		if(ltToCall != null)
		{
			if(args.length > 0)
				ltToCall.CallInSelf(self, lc, args);
			else
				ltToCall.CallInSelf(self, lc);
			return true;
		}
		return false;
	}

	/**
	 * Registers GUI event
	 * @param luaId
	 * @param event +"LuaForm.FORM_EVENT_CREATE" | "LuaForm.FORM_EVENT_RESUME" | "LuaForm.FORM_EVENT_RESUME" | "LuaForm.FORM_EVENT_PAUSE" | "LuaForm.FORM_EVENT_DESTROY" | "LuaForm.FORM_EVENT_UPDATE" | "LuaForm.FORM_EVENT_PAINT" | "LuaForm.FORM_EVENT_MOUSEDOWN" | "LuaForm.FORM_EVENT_MOUSEUP" | "LuaForm.FORM_EVENT_MOUSEMOVE" | "LuaForm.FORM_EVENT_KEYDOWN" | "LuaForm.FORM_EVENT_KEYUP"
	 * @param lt +fun(form: LuaForm, context: LuaContext):void
	 */
	@LuaFunction(manual = false, methodName = "RegisterFormEvent", self = LuaForm.class, arguments = { String.class, Integer.class, LuaTranslator.class })
	public static void RegisterFormEvent(String luaId, int event, LuaTranslator lt)
	{
		eventMap.put(luaId + event, lt);
	}

	/**
	 * Creates LuaForm Object From Lua.
	 * Form that created will be sent on FORM_EVENT_CREATE event.
	 * @param lc
	 * @param luaId
	 */
	@LuaFunction(manual = false, methodName = "Create", self = LuaForm.class, arguments = { LuaContext.class, String.class })
	public static void Create(LuaContext lc, String luaId)
	{
		Intent intent = new Intent(lc.GetContext(), LuaForm.class);
		intent.putExtra("LUA_ID_RUED", luaId);
		lc.GetContext().startActivity(intent);
	}

	/**
	 * Creates LuaForm Object From Lua with ui.
	 * Form that created will be sent on FORM_EVENT_CREATE event.
	 * @param lc
	 * @param luaId
	 * @param ui
	 */
	@LuaFunction(manual = false, methodName = "CreateWithUI", self = LuaForm.class, arguments = { LuaContext.class, String.class, String.class })
	public static void CreateWithUI(LuaContext lc, String luaId, String ui)
	{
		Intent intent = new Intent(lc.GetContext(), LuaForm.class);
		intent.putExtra("LUA_ID_RUED", luaId);
		intent.putExtra("LUA_UI_RUED", ui);
		lc.GetContext().startActivity(intent);
	}

	//TODO:Documentation
	/**
	 * Creates LuaForm Object From Lua for tabs.
	 * @param lc
	 * @param luaId
	 * @return NativeObject
	 */
	@LuaFunction(manual = false, methodName = "CreateForTab", self = LuaForm.class, arguments = { LuaContext.class, String.class })
	public static Object CreateForTab(LuaContext lc, String luaId)
	{
		Intent intent = new Intent(lc.GetContext(), LuaForm.class);
		intent.putExtra("LUA_ID_RUED", luaId);
		return intent;
	}

	/**
	 * Gets Active LuaForm
	 * @return LuaForm
	 */
	@LuaFunction(manual = false, methodName = "GetActiveForm", self = LuaForm.class)
	public static LuaForm GetActiveForm()
	{
		return LuaForm.activeForm;
	}

	public static void SetActiveForm(LuaForm form)
	{
		LuaForm.activeForm = form;
	}

	/**
	 * Gets LuaContext value of form
	 * @return LuaContext
	 */
	@LuaFunction(manual = false, methodName = "GetContext")
	public LuaContext GetContext()
	{
		return luaContext;
	}

	/**
	 * Gets the view of fragment.
	 * @return LGView
	 */
	@LuaFunction(manual = false, methodName = "GetViewById", arguments = { String.class })
	public LGView GetViewById(String lId)
	{
		//return MainActivity.GeneralGetViewById(lId);
		return this.view.GetViewById(lId);
	}

	/**
	 * Gets the view.
	 * @return LGView
	 */
	@LuaFunction(manual = false, methodName = "GetView")
	public LGView GetView()
	{
		return view;
	}

	/**
	 * Sets the view to render.
	 * @param v
	 */
	@LuaFunction(manual = false, methodName = "SetView", arguments = { LGView.class })
	public void SetView(LGView v)
	{
		view = v;
	}

	/**
	 * Sets the xml file of the view to render.
	 * @param xml
	 */
	@LuaFunction(manual = false, methodName = "SetViewXML", arguments = { String.class })
	public void SetViewXML(String xml)
	{
		LuaViewInflator inflater = new LuaViewInflator(luaContext);
		view = inflater.ParseFile(xml, null);
		setContentView(view.view);
	}

	/**
	 * Sets the title of the screen.
	 * @param str
	 */
	@LuaFunction(manual = false, methodName = "SetTitle", arguments = { String.class })
	public void SetTitle(String str)
	{
		setTitle(str);
	}

	/**
	 * Closes the form
	 */
	@LuaFunction(manual = false, methodName = "Close")
	public void Close()
	{
		finishActivity(-1);
	}

	/**
	 * (Ignore)
	 */
	@SuppressLint("NewApi")
	public void onCreateOverload(Bundle savedInstanceState)
	{
		if(Defines.CheckPermission(this, android.Manifest.permission.NFC))
		{
			mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

			// Create a generic PendingIntent that will be deliver to this activity. The NFC stack
			// will fill in the intent with the details of the discovered tag before delivering to
			// this activity.
			mNfcPendingIntent = PendingIntent.getActivity(this, 0,
					new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		}

		Common.pack = getApplicationContext().getPackageName();
		Common.scale = getResources().getDisplayMetrics().density;

		super.onCreate(savedInstanceState);
	}

	/**
	 * (Ignore)
	 */
	@SuppressLint("NewApi")
	public void SetNfc(NfcAdapter adapter, PendingIntent pi)
	{
		if(Defines.CheckPermission(this, android.Manifest.permission.NFC))
		{
			if(mNfcAdapter != null)
				mNfcAdapter.disableForegroundDispatch(this);
			mNfcAdapter = adapter;
			mNfcPendingIntent = pi;
			if(mNfcAdapter != null)
				mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, null, null);
		}
	}

	/**
	 * (Ignore)
	 */
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		/*requestWindowFeature(Window.FEATURE_NO_TITLE);
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		if(Defines.CheckPermission(this, android.Manifest.permission.NFC))
		{
			mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

			// Create a generic PendingIntent that will be deliver to this activity. The NFC stack
			// will fill in the intent with the details of the discovered tag before delivering to
			// this activity.
			mNfcPendingIntent = PendingIntent.getActivity(this, 0,
					new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		}

		Common.pack = getApplicationContext().getPackageName();
		Common.scale = getResources().getDisplayMetrics().density;

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		final PackageManager pm = getPackageManager();

		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		List<ResolveInfo> appList = pm.queryIntentActivities(mainIntent, 0);
		Collections.sort(appList, new ResolveInfo.DisplayNameComparator(pm));

		for (ResolveInfo temp : appList)
		{
			Log.d("Lua Engine", "Launcher package and activity name = "
					+ temp.activityInfo.packageName + "    "
					+ temp.activityInfo.name);
			if(getClass().getSimpleName().compareTo(temp.activityInfo.name) == 0)
			{
				return;
			}
		}
		/*if(this.getClass() == MainActivity.class)
			return;*/
		LuaForm.activeForm = this;
		String luaId = "LuaForm";
		Bundle extras = null;
		if (savedInstanceState == null)
		{
			extras = getIntent().getExtras();
			if(extras == null)
			{
				luaId = "LuaForm";
				ui = "";
			}
			else
			{
				luaId= extras.getString("LUA_ID_RUED", "LuaForm");
				ui = extras.getString("LUA_UI_RUED", "");
			}
		}
		else
		{
			luaId= savedInstanceState.getString("LUA_ID_RUED", "LuaForm");
			ui = savedInstanceState.getString("LUA_UI_RUED", "");
		}

		this.luaId = luaId;
		luaContext = LuaContext.CreateLuaContext(this);
		if(ui == null || ui.compareTo("") == 0)
		{
			LuaForm.OnFormEvent(this, LuaForm.FORM_EVENT_CREATE, luaContext);
		}
		else
		{
			LuaViewInflator inflater = new LuaViewInflator(luaContext);
			this.view = inflater.ParseFile(ui, null);
			setContentView(view.view);
		}
	}

	/**
	 * (Ignore)
	 */
	@SuppressLint("NewApi")
	@Override
	protected void onResume()
	{
		super.onResume();
		LuaForm.activeForm = this;
		if(Defines.CheckPermission(this, android.Manifest.permission.NFC))
		{
			if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())
					|| NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())
					|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction()))
			{
				Parcelable[] arr = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
				for(Parcelable p : arr)
				{
					if(p instanceof NdefMessage)
					{
						NdefMessage nDefMsg = (NdefMessage)p;
						try
						{
							/*TODO: Burda hashmap uzerinden halledecez,
							 * mesela smartposterrecord icin hashmap<Integer, Hashmap<String, string>
							 * uzerinde ic hashmapte
							 * "type"->"spr" (SmartPosterRecord)
							 * "title"->"title degeri"
							 * "uri"->"uri degeri"
							 * gibi ayarlanıp gönderilecek,
							 * su an icin sadece urli text olarak parse icin gonderiyoruz.
							 */
							Message highLevel = new Message(nDefMsg);
							for(Record r : highLevel)
							{
								if(r instanceof UriRecord)
								{
									UriRecord rec = (UriRecord)r;
									LuaForm.OnFormEvent(this, LuaForm.FORM_EVENT_NFC, luaContext, rec.getUri().toString());
								}
							}
						}
						catch(FormatException e)
						{

						}
					}
				}
			}
		}
		LuaForm.OnFormEvent(this, LuaForm.FORM_EVENT_RESUME, luaContext);
		if(Defines.CheckPermission(this, android.Manifest.permission.NFC))
		{
			if(mNfcAdapter != null)
				mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, null, null);
		}
		if(view != null)
		{
			view.onResume();
		}
	}

	/**
	 * (Ignore)
	 */
	@SuppressLint("NewApi")
	@Override
	protected void onPause()
	{
		super.onPause();
		LuaForm.OnFormEvent(this, LuaForm.FORM_EVENT_PAUSE, luaContext);
		if(Defines.CheckPermission(this, android.Manifest.permission.NFC))
		{
			if(mNfcAdapter != null)
				mNfcAdapter.disableForegroundDispatch(this);
		}
		if(view != null)
		{
			view.onPause();
		}
	}

	/**
	 * (Ignore)
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		LuaForm.OnFormEvent(this, LuaForm.FORM_EVENT_DESTROY, luaContext);
		//Move destroy event to subviews
		if(view != null)
		{
			view.onDestroy();
		}
	}

	/**
	 * (Ignore)
	 */
	@SuppressLint("NewApi")
	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		if(Defines.CheckPermission(this, android.Manifest.permission.NFC))
		{
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			for(String tech : tag.getTechList())
			{
				if(tech.equals(Ndef.class.getName()))
				{
					Parcelable[] arr = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
					for(Parcelable p : arr)
					{
						if(p instanceof NdefMessage)
						{
							NdefMessage nDefMsg = (NdefMessage)p;
							try
							{
								/*TODO: Burda hashmap uzerinden halledecez,
								 * mesela smartposterrecord icin hashmap<Integer, Hashmap<String, string>
								 * uzerinde ic hashmapte
								 * "type"->"spr" (SmartPosterRecord)
								 * "title"->"title degeri"
								 * "uri"->"uri degeri"
								 * gibi ayarlanıp gönderilecek,
								 * su an icin sadece urli text olarak parse icin gonderiyoruz.
								 */
								int count = 0;
								HashMap<Integer, HashMap<String, String>> nfcData = new HashMap<Integer, HashMap<String,String>>();
								Message highLevel = new Message(nDefMsg);
								for(Record r : highLevel)
								{
									HashMap<String, String> record = new HashMap<String, String>();
									if(r instanceof UriRecord)
									{
										UriRecord rec = (UriRecord)r;
										record.put("type", "uri");
										record.put("uri", rec.getUri().toString());
										nfcData.put(count++, record);
									}
									if(r instanceof SmartPosterRecord)
									{
										SmartPosterRecord spr = (SmartPosterRecord)r;
										record.put("type", "spr");
										record.put("title", spr.getTitle().toString());
										record.put("uri", spr.getUri().toString());
										nfcData.put(count++, record);
									}
								}
								LuaForm.OnFormEvent(this, LuaForm.FORM_EVENT_NFC, luaContext, nfcData);
							}
							catch(FormatException e)
							{

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
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		luaId= savedInstanceState.getString("LUA_ID_RUED");
		ui = savedInstanceState.getString("LUA_UI_RUED");
	}

	/**
	 * (Ignore)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putString("LUA_ID_RUED", luaId);
		outState.putString("LUA_UI_RUED", ui);
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		if(luaId != null)
			return luaId;
		return "LuaForm";
	}
}
