package dev.topping.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;
import android.widget.LGAbsListView;
import android.widget.LGAdapterView;
import android.widget.LGAutoCompleteTextView;
import android.widget.LGBottomNavigationView;
import android.widget.LGButton;
import android.widget.LGCheckBox;
import android.widget.LGComboBox;
import android.widget.LGCompoundButton;
import android.widget.LGConstraintLayout;
import android.widget.LGDatePicker;
import android.widget.LGEditText;
import androidx.fragment.app.LGFragmentContainerView;

import android.widget.LGFragmentStateAdapter;
import android.widget.LGFrameLayout;
import android.widget.LGImageView;
import android.widget.LGLinearLayout;
import android.widget.LGListView;
import android.widget.LGProgressBar;
import android.widget.LGRadioButton;
import android.widget.LGRadioGroup;
import android.widget.LGRecyclerView;
import android.widget.LGRecyclerViewAdapter;
import android.widget.LGScrollView;
import android.widget.LGTabLayout;
import android.widget.LGTextView;
import android.widget.LGToolbar;
import android.widget.LGView;
import android.widget.LGViewPager;
import android.widget.LGWebView;

import com.naef.jnlua.IDelegate;
import com.naef.jnlua.Lua;
import com.naef.jnlua.LuaState;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaObject;
import dev.topping.android.backend.LuaTasker;
import dev.topping.android.backend.Lunar;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;
import dev.topping.android.luagui.LuaViewInflator;
import dev.topping.android.osspecific.Defines;
import dev.topping.android.osspecific.UrlHttpClient;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KProperty;

public class ToppingEngine
{
	private Context context;
	private static ToppingEngine instance;
	private static List<Class<?>> plugins = new ArrayList<Class<?>>();
	private static ArrayList<Class<?>> viewPlugins = new ArrayList<Class<?>>();

	public static void AddLuaPlugin(Class<?> plugin)
	{
		plugins.add(plugin);
		if(plugin.isAssignableFrom(LGView.class))
		{
			viewPlugins.add(plugin);
		}
	}

	public static ArrayList<Class<?>> GetViewPlugins()
	{
		return viewPlugins;
	}

	public synchronized static ToppingEngine getInstance()
	{
		if (instance == null)
		{
			// it's ok, we can call this constructor
			instance = new ToppingEngine();
		}
		return instance;
	}

	public Object clone() throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException();
		// that'll teach 'em
	}

	//reg type defines
	public final int REGTYPE_GUI = (1 << 0);

	protected ToppingEngine()
	{
		super();
	}

	LuaState L;
	HashSet<Long> pendingThreads;

	String scriptsRoot = "";
	int primaryLoad = RESOURCE_DATA;
	double forceLoad = 0;
	String uiRoot;
	String mainUI;
	String mainForm;
	public static final int EXTERNAL_DATA = 1;
	public static final int INTERNAL_DATA = 2;
	public static final int RESOURCE_DATA = 3;
	public ProgressDialog loadDialog;

	/* Custom values that stored */
	HashMap<String, UrlHttpClient> httpClientMap = new HashMap<>();
	LuaTasker<Class<?>> tasker = new LuaTasker<Class<?>>()
	{
		@Override
		public void DoJob(Class<?> val)
		{
			LuaClass lc = val.getAnnotation(LuaClass.class);
			if(lc != null) {
				if(lc.isKotlin()) {
					KClass kclass = kotlin.jvm.JvmClassMappingKt.getKotlinClass(val);
					if (kclass != null) {
						Collection<KClass> nestedClasses = null;
						try {
							nestedClasses = kclass.getNestedClasses();
						} catch (Exception e) {}
						if (nestedClasses != null) {
							for (Iterator i = nestedClasses.iterator(); i.hasNext(); ) {
								KClass nestedKClass = (KClass) i.next();
								if (nestedKClass.isCompanion()) {
									Collection<KCallable> members = nestedKClass.getMembers();
									if (members != null) {
										ArrayList<KFunction> functions = new ArrayList<>();
										ArrayList<KProperty> properties = new ArrayList<>();
										for (Iterator j = members.iterator(); j.hasNext(); ) {
											KCallable kallable = (KCallable) j.next();
											if (kallable instanceof KFunction) {
												functions.add((KFunction) kallable);
											} else if (kallable instanceof KProperty) {
												properties.add((KProperty) kallable);
											}
										}
										Lunar.kotlinCompanionMap.put(val, new Lunar.KotlinCompanionObject(nestedKClass.getObjectInstance(), functions.toArray(new KFunction[0]), properties.toArray(new KProperty[0])));
									}
								}

								Log.d("asd", nestedKClass.toString());
							}
						}
					}
				}
				Lunar.methodMap.put(val, val.getMethods());
				Lunar.fieldMap.put(val, val.getDeclaredFields());
			}
		}
	};
	Thread taskerThread;

	public void Startup(Context context)
	{
		this.context = context;
		Lua.primaryLoad = primaryLoad;
		Lua.scriptsRoot = scriptsRoot;
		LuaState.setasset(context.getAssets());
		L = Lua.lua_open();
		pendingThreads = new HashSet<Long>();

		Lua.luaL_openlibs(L);
		/*try
		{*/
		RegisterCoreFunctions();
		RegisterGlobals();
		LuaRef.ResourceLoader(context);
		/*}
		catch (LuaException e)
		{
			e.printStackTrace();
		}*/
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try
		{
			InputStream is = context.getAssets().open("defines.lua");

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();
			is.close();
		}
		catch (Exception e)
		{
			Log.e("LuaEngine.java", e.getMessage());
		}
		if(Lua.luaL_loadstring(L, buffer.toString(), "defines.lua") != 0)
		//if(Lua.luaL_loadbuffer(L, buffer.toString(), s) != 0)
		//if(Lua.LloadFile(s) != 0)
		{
			Report(L);
		}
		else
		{
			if(Lua.lua_pcall(L, 0, 0, 0) != 0)
			{
				Report(L);
			}
			else
			{
				Log.i("LuaEngine", "Script defines.lua loaded.");
			}
		}

		try
		{
			buffer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}


		scriptsRoot = "";
		Lua.lua_getglobal(L, "ScriptsRoot");
		if(Lua.lua_isstring(L, -1) == 0)
			Log.e("LuaEngine.java", "ScriptsRoot must be string");
		else
			scriptsRoot = Lua.lua_tostring(L, -1).toString();
		Lua.lua_pop(L, 1);

		primaryLoad = 1;
		Lua.lua_getglobal(L, "PrimaryLoad");
		if(Lua.lua_isnumber(L, -1) == 0)
			Log.e("LuaEngine.java", "PrimaryLoad must be number");
		else
			primaryLoad = Lua.lua_tointeger(L, -1);
		Lua.lua_pop(L, 1);

		forceLoad = 0;
		Lua.lua_getglobal(L, "ForceLoad");
		if(Lua.lua_isnumber(L, -1) == 0)
			Log.e("LuaEngine.java", "ForceLoad must be number");
		else
			forceLoad = Lua.lua_tointeger(L, -1);
		Lua.lua_pop(L, 1);

		uiRoot = "ui";
		Lua.lua_getglobal(L, "UIRoot");
		if(Lua.lua_isstring(L, -1) == 0)
			Log.e("LuaEngine.java", "UIRoot must be string");
		else
			uiRoot = Lua.lua_tostring(L, -1).toString();
		Lua.lua_pop(L, 1);

		mainUI = "main.xml";
		Lua.lua_getglobal(L, "MainUI");
		if(Lua.lua_isstring(L, -1) == 0)
			Log.e("LuaEngine.java", "MainUI must be string");
		else
			mainUI = Lua.lua_tostring(L, -1).toString();
		Lua.lua_pop(L, 1);

		mainForm = "";
		Lua.lua_getglobal(L, "MainForm");
		if(Lua.lua_isstring(L, -1) == 0)
			Log.e("LuaEngine.java", "MainForm must be string");
		else
			mainForm = Lua.lua_tostring(L, -1).toString();
		Lua.lua_pop(L, 1);

		Lua.lua_getglobal(L, "LuaDebug");
		if(Lua.lua_isnumber(L, -1) == 1 && Lua.lua_tointeger(L, -1) == 1)
		{
			String scriptsRootStore = scriptsRoot;
			scriptsRoot = "";

			/*Lua.luaL_loadfile(L, context, "moddebug.lua");
			Lua.lua_pcall(L, 0, 0, 0);*/

			scriptsRoot = scriptsRootStore;

			/*Lua.lua_getglobal(L, "SocketBufferSize");
			if(Lua.lua_isnumber(L, -1) == 0)
				Log.i("LuaEngine.java", "SocketBufferSize not set using default");
			else
				Lua.STEPSIZE = Lua.lua_tointeger(L, -1);
			Lua.lua_pop(L, 1);

			Lua.lua_getglobal(L, "LuaBufferSize");
			if(Lua.lua_isnumber(L, -1) == 0)
				Log.i("LuaEngine.java", "LuaBufferSize not set using default");
			else
				Lua.LUAL_BUFFERSIZE = Lua.lua_tointeger(L, -1);
			Lua.lua_pop(L, 1);

			Lua.lua_getglobal(L, "PBufferSize");
			if(Lua.lua_isnumber(L, -1) == 0)
				Log.i("LuaEngine.java", "PBufferSize not set using default");
			else
				pBuffer.BUF_SIZE = Lua.lua_tointeger(L, -1);
			Lua.lua_pop(L, 1);*/
		}
		Lua.lua_pop(L, 1);

		StartupDefines();
	}

	public void DeleteFolder(File path)
	{
		File[] farr = path.listFiles();
		if(farr != null)
		{
			for(File f : farr)
			{
				if(f.isDirectory())
				{
					DeleteFolder(f);
				}
				else
					f.delete();
			}
		}
	}

	@SuppressWarnings("unused")
	public void StartupDefines()
	{
		AssetManager assetManager = context.getAssets();

		String[] rtn = null;
		try
		{
			rtn = assetManager.list(scriptsRoot);
		}
		catch (IOException e)
		{
			Log.e("LuaEngine", e.getMessage());
		}

		int cnt_uncomp = 0;

		switch(primaryLoad)
		{
		case EXTERNAL_DATA: //External SD
		{
			String scriptsDir = Defines.GetExternalPathForResource(context, scriptsRoot);
			File scripts = new File(scriptsDir);
			{
				if(!scripts.exists())
					scripts.mkdir();
				else
					DeleteFolder(scripts);
				for(String s : rtn)
				{
					try
					{
						InputStream is = assetManager.open(scriptsRoot + "/" + s);
						File scriptFile = new File(scriptsDir + "/" + s);
						if(!scriptFile.exists() || forceLoad > 0)
						{
							if(scriptFile.exists())
								scriptFile.delete();
							scriptFile.createNewFile();

							//Open the empty file as the output stream
							FileOutputStream myOutput = new FileOutputStream(scriptFile, false);

							//transfer bytes from the inputfile to the outputfile
							byte[] buffer = new byte[1024];
							int length;
							while ((length = is.read(buffer)) > 0)
							{
								myOutput.write(buffer, 0, length);
							}

							//Close the streams
							myOutput.flush();
							myOutput.close();
						}
						is.close();
					}
					catch (Exception e)
					{
						File scriptFile = new File(scriptsDir + "/" + s);
						if(scriptFile.exists())
							scriptFile.delete();
					}
				}
			}

			if(scripts == null)
			{
				Log.e("LuaEngine.java", "Cannot find sdcard to load binaries");
				return;
			}

			int count = 0;
			String[] files = scripts.list();
			for(String s : files)
			{
				try
				{
					FileInputStream is = new FileInputStream(scriptsDir + s);
					ByteArrayOutputStream buffer = new ByteArrayOutputStream();

					int nRead;
					byte[] data = new byte[16384];

					while ((nRead = is.read(data, 0, data.length)) != -1) {
					  buffer.write(data, 0, nRead);
					}

					buffer.flush();
					is.close();

					//if(Lua.luaL_loadstring(L, buffer.toString()) != 0)
					if(Lua.luaL_loadstring(L, buffer.toString(), s) != 0)
					{
						Report(L);
					}
					else
					{
						if(Lua.lua_pcall(L, 0, 0, 0) != 0)
						{
							Report(L);
						}
						else
						{
							Log.i("LuaEngine", "Script " + s + " loaded.");
							count++;
							final int countF = count;
							final String[] filesF = files;
							((Activity) context).runOnUiThread(new Runnable()
							{
								@Override
								public void run()
								{
									if(loadDialog != null)
										loadDialog.setProgress((loadDialog.getMax() * countF) / filesF.length);
								}
							});
						}
					}

					//buffer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				cnt_uncomp++;
			}
		}break;
		case RESOURCE_DATA: //Internal Data
		{
			int count = 0;
			for(String s : rtn)
			{
				try
				{
						/*InputStream is = assetManager.open(scriptsRoot + "/" + s);
						ByteArrayOutputStream buffer = new ByteArrayOutputStream();

						int nRead;
						byte[] data = new byte[16384];

						while ((nRead = is.read(data, 0, data.length)) != -1) {
						  buffer.write(data, 0, nRead);
						}

						buffer.flush();
						is.close();*/

					//if(Lua.luaL_loadstring(L, buffer.toString()) != 0)
					if(Lua.luaL_loadfile(L, context, s) != 0)
					{
						Report(L);
					}
					else
					{
						if(Lua.lua_pcall(L, 0, 0, 0) != 0)
						{
							Report(L);
						}
						else
						{
							Log.i("LuaEngine", "Script " + s + " loaded.");
							count++;
							final int countF = count;
							final String[] rtnF = rtn;
							((Activity) context).runOnUiThread(new Runnable()
							{
								@Override
								public void run()
								{
									if(loadDialog != null)
										loadDialog.setProgress((loadDialog.getMax() * countF) / rtnF.length);
								}
							});
						}
					}

					//buffer.close();
				}
				catch (Exception e)
				{
				}
				cnt_uncomp++;
			}
		}break;
		case INTERNAL_DATA: //Assets
		{
			String scriptsDir = context.getFilesDir().getAbsolutePath();
			scriptsDir = scriptsDir + "/" + scriptsRoot;
			File scripts = new File(scriptsDir);
			{
				if(!scripts.exists())
					scripts.mkdir();
				else
					DeleteFolder(scripts);
				for(String s : rtn)
				{
					try
					{
						InputStream is = assetManager.open(scriptsRoot + "/" + s);
						File scriptFile = new File(scriptsDir + "/" + s);
						if(!scriptFile.exists() || forceLoad > 0)
						{
							if(scriptFile.exists())
								scriptFile.delete();
							scriptFile.createNewFile();

							//Open the empty file as the output stream
							FileOutputStream myOutput = new FileOutputStream(scriptFile, false);

							//transfer bytes from the inputfile to the outputfile
							byte[] buffer = new byte[1024];
							int length;
							while ((length = is.read(buffer)) > 0)
							{
								myOutput.write(buffer, 0, length);
							}

							//Close the streams
							myOutput.flush();
							myOutput.close();
						}
						is.close();
					}
					catch (Exception e)
					{
						File scriptFile = new File(scriptsDir + "/" + s);
						if(scriptFile.exists())
							scriptFile.delete();
					}
				}
			}

			if(scripts == null)
			{
				Log.e("LuaEngine.java", "Cannot find internal data to load binaries");
				return;
			}

			int count = 0;
			String[] files = scripts.list();
			for(String s : files)
			{
				try
				{
						/*FileInputStream is = new FileInputStream(scripts.getAbsolutePath() + "/" + s);
						ByteArrayOutputStream buffer = new ByteArrayOutputStream();

						int nRead;
						byte[] data = new byte[16384];

						while ((nRead = is.read(data, 0, data.length)) != -1) {
						  buffer.write(data, 0, nRead);
						}

						buffer.flush();
						is.close();*/

					//if(Lua.luaL_loadstring(L, buffer.toString()) != 0)
					if(Lua.luaL_loadfile(L, context, s) != 0)
					{
						Report(L);
					}
					else
					{
						if(Lua.lua_pcall(L, 0, 0, 0) != 0)
						{
							Report(L);
						}
						else
						{
							Log.i("LuaEngine", "Script " + s + " loaded.");
							count++;
							final int countF = count;
							final String[] filesF = files;
							((Activity) context).runOnUiThread(new Runnable()
							{
								@Override
								public void run()
								{
									if(loadDialog != null)
										loadDialog.setProgress((loadDialog.getMax() * countF) / filesF.length);
								}
							});
						}
					}

					//buffer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				cnt_uncomp++;
			}
		}break;
		}

		Log.i("LuaEngine", "Loaded " + cnt_uncomp + " Lua scripts.");
	}

	public void Startup(String root)
	{
		L = Lua.lua_open();
		pendingThreads = new HashSet<>();

		LoadScripts(root);
	}

	public void Startup(Context context, AssetManager assetManager, String root)
	{
		L = Lua.lua_open();
		pendingThreads = new HashSet<>();

		LoadScriptsFromAsset(context, assetManager, root);
	}

	void ScriptLoadDir(String root, HashSet<String> rtn)
	{
		File dir = new File(root);
		if(dir.isDirectory())
		{
			for(File file : dir.listFiles())
				ScriptLoadDir(file.getPath(), rtn);
		}
		else
		{
			String filename = dir.getName();
			String[] arr = filename.split("\\.");
			if(arr[arr.length - 1].compareTo("lua") == 0)
			{
				rtn.add(dir.getAbsolutePath());
			}
		}
	}

	boolean SetScriptFromStream(Context context, String name, InputStream is)
	{
		try
		{
			File filesDir = context.getFilesDir();
			String fileDir = filesDir.getAbsolutePath() + "/scripts/" + name;
			File file = new File(fileDir);
			if(!file.exists())
				return false;

			file.delete();
			file.createNewFile();

			//Open the empty db as the output stream
			FileOutputStream myOutput = new FileOutputStream(file, false);


			//transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0)
			{
				myOutput.write(buffer, 0, length);
			}

			//Close the streams
			myOutput.flush();
			myOutput.close();
			is.close();
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}

	void LoadScriptsFromAsset(Context context, AssetManager assetManager, String root)
	{
		String[] rtn = null;
		try
		{
			rtn = assetManager.list(root);
		}
		catch (IOException e)
		{
			Log.e("LuaEngine", e.getMessage());
		}

		int cnt_uncomp=0;

		//File filesDir = context.getFilesDir();
		//String scriptsDir = filesDir.getAbsolutePath() + "/scripts";
		String scriptsDir = Defines.GetExternalPathForResource(context, root);
		File scripts = new File(scriptsDir);
		{
			if(!scripts.exists())
				scripts.mkdir();
			for(String s : rtn)
			{
				try
				{
					InputStream is = assetManager.open(root + "/" + s);
					File scriptFile = new File(scriptsDir + "/" + s);
					if(!scriptFile.exists())
					{
						scriptFile.createNewFile();

						//Open the empty db as the output stream
						FileOutputStream myOutput = new FileOutputStream(scriptFile, false);


						//transfer bytes from the inputfile to the outputfile
						byte[] buffer = new byte[1024];
						int length;
						while ((length = is.read(buffer)) > 0)
						{
							myOutput.write(buffer, 0, length);
						}

						//Close the streams
						myOutput.flush();
						myOutput.close();
					}
					is.close();
				}
				catch (Exception e)
				{
				}
			}
		}
		for(String s : scripts.list())
		{
			try
			{
				//InputStream is = assetManager.open(root + "/" + s);
				FileInputStream is = new FileInputStream(scripts.getAbsolutePath() + "/" + s);
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				int nRead;
				byte[] data = new byte[16384];

				while ((nRead = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}

				buffer.flush();
				is.close();

				if(Lua.luaL_loadstring(L, buffer.toString(), s) != 0)
				//if(Lua.luaL_loadbuffer(L, buffer.toString(), s) != 0)
				//if(Lua.LloadFile(s) != 0)
				{
					Report(L);
				}
				else
				{
					if(Lua.lua_pcall(L, 0, 0, 0) != 0)
					{
						Report(L);
					}
					else
					{
						Log.i("LuaEngine", "Script " + s + " loaded.");
					}
				}

				buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			cnt_uncomp++;
		}
		Log.i("LuaEngine", "Loaded " + cnt_uncomp + " Lua scripts.");
	}

	void LoadScripts(String root)
	{
		HashSet<String> rtn = new HashSet<String>();
		ScriptLoadDir(root, rtn);

		int cnt_uncomp=0;

		Lua.luaL_openlibs(L);

		RegisterCoreFunctions();

		for(String s : rtn)
		{
			if(Lua.luaL_loadfile(L, context, s) != 0)
			{
				Report(L);
			}
			else
			{
				if(Lua.lua_pcall(L, 0, 0, 0) != 0)
				{
					Report(L);
				}
				else
				{
					Log.i("LuaEngine", "Script " + s + " loaded.");
				}
			}
			cnt_uncomp++;
		}
		Log.i("LuaEngine", "Loaded " + cnt_uncomp + " Lua scripts.");
	}

	public void Report(LuaState L)
	{
		int count = 20;
		String msgP = Lua.lua_tostring(L, -1);
		if(msgP == null)
			return;

		String msg= msgP.toString();
		while(msg != null && count > 0)
		{
			Lua.lua_pop(L, -1);
			Log.e("LuaEngine", msg);
			msgP = Lua.lua_tostring(L, -1);
			if(msgP == null)
				return;
			msg = msgP.toString();
			count--;
		}
	}

	public void FillVariable(Object val)
	{
		if(val == null)
		{
			PushNIL();
			return;
		}
		Class name = val.getClass();
		if(name == java.lang.Boolean.class)
			PushBool((Boolean)val);
		else if(name == java.lang.Byte.class
				|| name == java.lang.Short.class
				|| name == java.lang.Integer.class)
			PushInt(Integer.parseInt(val.toString()));
		else if(name == java.lang.Long.class)
			PushLong(((Long)val).longValue());
		else if(name == java.lang.Float.class)
			PushFloat((Float)val);
		else if(name == java.lang.Double.class)
			PushDouble((Double)val);
		else if(name == java.lang.Character.class
				|| name == java.lang.String.class)
			PushString(val.toString());
		else if(name == java.lang.Void.class)
			PushInt(0);
		else if(name == java.util.HashMap.class)
			PushTable((HashMap<Object, Object>) val);
		else
			Lunar.push(L, val, true, true);
	}

	public Object OnGuiEventResult()
	{
		Object retVal = null;
		if(Lua.lua_isnoneornil(L, -1))
			retVal = null;
		else if(Lua.lua_isboolean(L, -1))
			retVal = Lua.lua_toboolean(L, -1) == 1;
		else if(Lua.lua_isnumber(L, -1) > 0)
			retVal = Double.valueOf((Lua.lua_tonumber(L, -1)));
		else if(Lua.lua_isstring(L, -1) > 0)
			retVal = Lua.lua_tostring(L, -1).toString();
		else
		{
			//argList.add(Lua.luaL_checkudata(L, count, c.getName()));
			Object o = Lua.lua_touserdata(L, -1);
			if(o != null)
			{
				if(o.getClass() == dev.topping.android.backend.LuaObject.class)
				{
					Object obj = ((LuaObject<?>)o).obj;
					if(obj == null)
					{
						Log.e("Lunar Push", "Cannot get lua object property static thunk");
						retVal = null;
					}
					retVal = obj;
				}
				else
				{
					retVal = o;
				}
			}
			else
			{
				o = Lua.lua_topointer(L, -1);
				if(o == null)
					return o;
				long ot = (long)o; //table
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				int size = Lua.sizenode(L, ot);
				for(int i = 0; i < size; i++)
				{
					long node = Lua.gnode(L, ot, i);
					long key = Lua.key2tval(L, node);
					Object keyObject = null;
					switch (Lua.ttype(L, key))
					{
					case Lua.LUA_TNIL:
						break;
					case Lua.LUA_TSTRING:
					{
						keyObject = Lua.svalue(L, key);
					}break;
					case Lua.LUA_TNUMBER:
						keyObject = Lua.nvalue(L, key);
						break;
					}

					long val = Lua.luaH_get(L, ot, key);
					Object valObject = null;
					switch (Lua.ttype(L, val))
					{
					case Lua.LUA_TNIL:
						break;
					case Lua.LUA_TSTRING:
					{
						valObject = Lua.svalue(L, val);
					}break;
					case Lua.LUA_TNUMBER:
						valObject = Lua.nvalue(L, val);
						break;
					case Lua.LUA_TTABLE:
						valObject = Lunar.ParseTable(val);
						break;
					case Lua.LUA_TUSERDATA:
					{
											/*valObject = (Lua.rawuvalue(val).user_data);
											if(valObject != null)
											{
												if(valObject.getClass().getName().startsWith("dev.topping.android.LuaObject"))
												{
													Object objA = ((LuaObject<?>)valObject).obj;
													if(objA == null)
													{
														Log.e("Lunar Push", "Cannot get lua object property static thunk");
														return 0;
													}

													valObject = objA;
												}
											}*/
					}break;
					case Lua.LUA_TLIGHTUSERDATA:
					{
						valObject = Lua.pvalue(L, val);
						if(valObject != null)
						{
							if(valObject.getClass().getName().startsWith("dev.topping.android.LuaObject"))
							{
								Object objA = ((LuaObject<?>)valObject).obj;
								if(objA == null)
								{
									Log.e("Lunar Push", "Cannot get lua object property static thunk");
									return 0;
								}

								valObject = objA;
							}
						}
					}break;
					}

					if(valObject != null)
						map.put(keyObject, valObject);
				}
				retVal = map;
			}
		}

		return retVal;
	}

	public Object OnGuiEvent(Object gui, Object ... arguments)
	{
		Lunar.push(L, gui, false, true);

		int j = 0;
		for(Object type : arguments)
		{
			FillVariable(type);
			j++;
		}

		int r = Lua.lua_pcall(L, j + 1, Lua.LUA_MULTRET, 0);
		if(r != 0)
		{
			Report(L);
			return null;
		}

		return OnGuiEventResult();
	}

	public Object OnNativeEvent(Object gui, Integer ref, Object ... arguments)
	{
		Lua.lua_rawgeti(L, Lua.LUA_REGISTRYINDEX, ref);

		return OnGuiEvent(gui, arguments);
	}

	public Object OnGuiEvent(Object gui, String FunctionName, Object ... arguments)
	{
		if(FunctionName == null || FunctionName.compareTo("") == 0)
			return null;

		Lua.lua_getglobal(L, FunctionName);
		if(Lua.lua_isnil(L, -1))
		{
			Log.e("LuaEngine", "Tried to call invalid LUA function '" + FunctionName + "'!\n");
			return null;
		}

		return OnGuiEvent(gui, arguments);
	}

	void RegisterCoreFunctions()
	{
		Lua.lua_register(L, "Log", new IDelegate() {

			@Override
			public Object invoke() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object invoke(Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object invoke(Object arg) {
				int logType = Lua.luaL_checkinteger(L,1);
				String where = Lua.luaL_checkstring(L, 2).toString();
				String msg = Lua.luaL_checkstring(L, 3).toString();

				switch(logType)
				{
				case Log.VERBOSE: //2
				{
					Log.v(where, msg);
				}break;
				case Log.DEBUG: //3
				{
					Log.d(where, msg);
				}break;
				case Log.INFO: //4
				{
					Log.i(where, msg);
				}break;
				case Log.WARN: //5
				{
					Log.w(where, msg);
				}break;
				case Log.ERROR: //6
				{
					Log.e(where, msg);
				}break;
				case Log.ASSERT: //7
				{
					//Log.(where, msg);
				}break;
				default:
					break;
				};

				return 0;
			}

			@Override
			public Object invoke(Object[] args) {
				return null;
			}
		});

		Class<?>[] clsArr = {
			LuaTranslator.class,
			LuaContext.class,
			LuaGraphics.class,
			LuaViewInflator.class,
			LGAbsListView.class,
			LGAdapterView.class,
			LGAutoCompleteTextView.class,
			LGBottomNavigationView.class,
			LGButton.class,
			LGCheckBox.class,
			LGComboBox.class,
			LGCompoundButton.class,
			LGConstraintLayout.class,
			LGDatePicker.class,
			LGEditText.class,
			LGFragmentContainerView.class,
			LGFragmentStateAdapter.class,
			LGFrameLayout.class,
			LGImageView.class,
			LGLinearLayout.class,
			LGListView.class,
			LGProgressBar.class,
			LGRadioButton.class,
			LGRadioGroup.class,
			//LGRelativeLayout.class,
			LGScrollView.class,
			LGTabLayout.class,
			LGTextView.class,
			LGView.class,
			//LGViewGroup.class,
			LGRecyclerView.class,
			LGRecyclerViewAdapter.class,
			LGToolbar.class,
			LGViewPager.class,
			LGWebView.class,

			LuaDefines.class,
			LuaNativeObject.class,
			LuaObjectStore.class,

			LuaBuffer.class,
			LuaBundle.class,
			LuaColor.class,
			LuaDatabase.class,
			LuaDate.class,
			LuaDialog.class,
			LuaEvent.class,
			LuaForm.class,
			LuaFragment.class,
			LuaHttpClient.class,
			LuaJavaFunction.class,
			LuaJSONArray.class,
			LuaJSONObject.class,
			LuaLog.class,
			LuaMenu.class,
			LuaNativeCall.class,
			LuaPoint.class,
			LuaRect.class,
			LuaRef.class,
			LuaResource.class,
			LuaStore.class,
			LuaStream.class,
			LuaTab.class,
			LuaThread.class,
			LuaToast.class,

			LuaNFC.class,

			LuaAppBarConfiguration.class,
			LuaCoroutineScope.class,
			LuaDispatchers.class,
			LuaFragmentManager.class,
			LuaLifecycle.class,
			LuaLifecycleObserver.class,
			LuaLifecycleOwner.class,
			LuaLiveData.class,
			LuaMutableLiveData.class,
			LuaNavController.class,
			LuaNavigationUI.class,
			LuaNavOptions.class,
			LuaNavHostFragment.class,
			LuaViewModel.class,
			LuaViewModelProvider.class
		};

		for(Class<?> cls : clsArr) {
			tasker.AddToQueue(cls);
		}

		for(Class<?> cls : plugins)
			tasker.AddToQueue(cls);

		if(taskerThread == null && Lunar.methodMap.size() == 0)
		{
			taskerThread = new Thread(tasker);
			taskerThread.start();

			while(tasker.HasJob())
			{
				try
				{
					Thread.sleep(100);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			tasker.Exit();
			try
			{
				taskerThread.join();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			taskerThread = null;
		}
		/*luaGlobalFunctions::Register(lu);*/

		for(Class<?> cls : clsArr) {
			Lunar.Register(L, cls);
		}

		for(Class<?> cls : plugins)
			Lunar.Register(L, cls);

		//set the suspendluathread a coroutine function
		/*lua_getglobal(lu,"coroutine");
		if(lua_istable(lu,-1) )
		{
			lua_pushcfunction(lu,SuspendLuaThread);
			lua_setfield(lu,-2,"wait");
			lua_pushcfunction(lu,SuspendLuaThread);
			lua_setfield(lu,-2,"WAIT");
		}
		lua_pop(lu,1);*/
	}

	void RegisterGlobals()
	{
		Lua.lua_pushstring(L, "Android");
		Lua.lua_setglobal(L, "OS_TYPE");

		Lua.lua_pushstring(L, String.valueOf(Build.VERSION.SDK_INT));
		Lua.lua_setglobal(L, "OS_VERSION");

		String s = GetContext().getResources().getString(R.string.deviceType);
		dev.topping.android.luagui.DisplayMetrics.isTablet = (s.compareTo("Tablet") == 0);
		Lua.lua_pushboolean(L, dev.topping.android.luagui.DisplayMetrics.isTablet);
		Lua.lua_setglobal(L, "IS_TABLET");
	}

	public void Unload()
	{
		Lua.lua_close(L);
	}

	public void Restart(String root)
	{
		Unload();
		Startup(root);
	}

	public void Restart(Context context, AssetManager assetManager, String root)
	{
		Unload();
		Startup(context, assetManager, root);
	}

	public LuaState GetLuaState() { return L; }

	public void PushBool(boolean value) { Lua.lua_pushboolean(L, value); }
	public void PushNIL() { Lua.lua_pushnil(L); }
	public void PushInt(int val) { Lua.lua_pushinteger(L, val); }
	void PushLong(long val) { Lua.lua_pushnumber(L, (double)val); }
	public void PushFloat(float val) { Lua.lua_pushnumber(L, val); }
	public void PushDouble(double val) { Lua.lua_pushnumber(L, val); }
	public void PushString(String val) { Lua.lua_pushstring(L, val); }

	public void PushTable(HashMap<Object, Object> retVal)
	{
		Lua.lua_lock(L);
		//Lua.lua_createtable(L, 0, retVal.size());
		Lua.lua_newtable(L);
		for(Map.Entry<Object, Object> entry : retVal.entrySet())
		{
			Lua.lua_pushstring(L, String.valueOf(entry.getKey()));
			Object retval = entry.getValue();
			String retName = retval.getClass().getName();
			if(retName.compareTo("java.lang.Boolean") == 0
				|| retval.getClass() == boolean.class)
				((ToppingEngine) ToppingEngine.getInstance()).PushBool((Boolean)retval);
			else if(retName.compareTo("java.lang.Byte") == 0
					|| retval.getClass() == byte.class
					|| retName.compareTo("java.lang.Short") == 0
					|| retval.getClass() == short.class
					|| retName.compareTo("java.lang.Integer") == 0
					|| retval.getClass() == int.class
					|| retName.compareTo("java.lang.Long") == 0
					|| retval.getClass() == long.class)
				((ToppingEngine) ToppingEngine.getInstance()).PushInt((Integer)retval);
			else if(retName.compareTo("java.lang.Float") == 0
					|| retval.getClass() == float.class)
				((ToppingEngine) ToppingEngine.getInstance()).PushFloat((Float)retval);
			else if(retName.compareTo("java.lang.Double") == 0
					|| retval.getClass() == double.class)
				((ToppingEngine) ToppingEngine.getInstance()).PushDouble((Double)retval);
			else if(retName.compareTo("java.lang.Char") == 0
					|| retval.getClass() == char.class
					|| retName.compareTo("java.lang.String") == 0)
				((ToppingEngine) ToppingEngine.getInstance()).PushString((String)retval);
			else if(retName.compareTo("java.lang.Void") == 0
					|| retval.getClass() == void.class)
				((ToppingEngine) ToppingEngine.getInstance()).PushInt(0);
			else if(retName.compareTo("java.util.HashMap") == 0)
			{
				((ToppingEngine) ToppingEngine.getInstance()).PushTable((HashMap<Object, Object>)retval);
			}
			else if(retName.compareTo("java.util.ArrayList") == 0)
			{
				HashMap<Object, Object> map = new HashMap<>();
				ArrayList<Object> lst = (ArrayList<Object>) retval;
				for(int i = 0; i < lst.size(); i++)
				{
					map.put(i, lst.get(i));
				}
				((ToppingEngine) ToppingEngine.getInstance()).PushTable(map);
			}
			else
			{
				ToppingEngine l = (ToppingEngine) ToppingEngine.getInstance();
				Lunar.push(l.GetLuaState(), retval, false, true);
			}

			//Lua.lua_setfield(L, -2, String.valueOf(entry.getKey()));
			//Lua.lua_pushstring(L, String.valueOf(entry.getKey()));
			/*
			 * To put values into the table, we first push the index, then the
			 * value, and then call lua_rawset() with the index of the table in the
			 * stack. Let's see why it's -3: In Lua, the value -1 always refers to
			 * the top of the stack. When you create the table with lua_newtable(),
			 * the table gets pushed into the top of the stack. When you push the
			 * index and then the cell value, the stack looks like:
			 *
			 * <- [stack bottom] -- table, index, value [top]
			 *
			 * So the -1 will refer to the cell value, thus -3 is used to refer to
			 * the table itself. Note that lua_rawset() pops the two last elements
			 * of the stack, so that after it has been called, the table is at the
			 * top of the stack.
			 */
			Lua.lua_settable(L, -3);
			//Lua.lua_rawset(L, -3);
		}
		//Lua.sethvalue(L, obj, x)
		//Lua.luaH_new(L, narray, nhash)
		//Lua.setbvalue(L.top, (b != 0) ? 1 : 0); // ensure that true is 1
		//Lua.api_incr_top(L);
		Lua.lua_unlock(L);

	}

	/*Custom variables*/
	public UrlHttpClient GetHttpClient(String id)
	{
		if(httpClientMap.containsKey(id))
			return httpClientMap.get(id);
		else
		{
			UrlHttpClient client = new UrlHttpClient();
			httpClientMap.put(id, client);
			return client;
		}
	}

	public void DestroyHttpClient(Integer id)
	{
		if(httpClientMap.containsKey(id))
		{
			@SuppressWarnings("unused")
			UrlHttpClient client = httpClientMap.get(id);
			httpClientMap.remove(id);
			client = null;
		}
	}

	public Context GetContext()
	{
		return context;
	}

	public void SetContext(Context context)
	{
		this.context = context;
	}

	public String GetScriptsRoot()
	{
		return scriptsRoot;
	}

	public int GetPrimaryLoad()
	{
		return primaryLoad;
	}

	public String GetUIRoot()
	{
		return uiRoot;
	}

	public String GetMainUI()
	{
		return mainUI;
	}

	public String GetMainForm()
	{
		return mainForm;
	}

	public boolean CatchExceptions()
	{
		return true;
	}

	public boolean ThrowExceptions()
	{
		return true;
	}
}
