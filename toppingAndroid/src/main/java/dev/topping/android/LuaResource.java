package dev.topping.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaRef;
import dev.topping.android.osspecific.Defines;

/**
 * Lua resource class.
 * This class is used to fetch resources from lua.
 */
@LuaClass(className = "LuaResource")
public class LuaResource implements LuaInterface
{
	/**
	 * This function gets resource from package, if can not it gets from other data location.
	 * @param path root path to search.
	 * @param resName resource name to search
	 * @return LuaStream of resource
	 */
	@LuaFunction(manual = false, methodName = "GetResourceAssetSd", self = LuaResource.class, arguments = { String.class, String.class })
	public static LuaStream GetResourceAssetSd(String path, String resName)
	{
		LuaStream ls = new LuaStream();
		ls.SetStream(Defines.GetResourceAssetSd(path, resName));
		return ls;
	}
	
	/**
	 * This function gets resource from other data location, if can not it gets from package.
	 * @param path root path to search.
	 * @param resName resource name to search
	 * @return LuaStream of resource
	 */
	@LuaFunction(manual = false, methodName = "GetResourceSdAsset", self = LuaResource.class, arguments = { String.class, String.class })
	public static LuaStream GetResourceSdAsset(String path, String resName)
	{
		LuaStream ls = new LuaStream();
		ls.SetStream(Defines.GetResourceSdAsset(path, resName));
		return ls;
	}
	
	/**
	 * This function gets resource from package.
	 * @param path root path to search.
	 * @param resName resource name to search
	 * @return LuaStream of resource
	 */
	@LuaFunction(manual = false, methodName = "GetResourceAsset", self = LuaResource.class, arguments = { String.class, String.class })
	public static LuaStream GetResourceAsset(String path, String resName)
	{
		LuaStream ls = new LuaStream();
		ls.SetStream(Defines.GetResourceAsset(path, resName));
		return ls;
	}
	
	/**
	 * This function gets resource from other data location.
	 * @param path root path to search.
	 * @param resName resource name to search
	 * @return LuaStream of resource
	 */
	@LuaFunction(manual = false, methodName = "GetResourceSd", self = LuaResource.class, arguments = { String.class, String.class })
	public static LuaStream GetResourceSd(String path, String resName)
	{
		LuaStream ls = new LuaStream();
		ls.SetStream(Defines.GetResourceSd(path, resName));
		return ls;
	}
	
	/**
	 * This function gets resource based on defines.lua config
	 * @param path root path to search.
	 * @param resName resource name to search
	 * @return LuaStream of resource
	 */
	public static LuaStream GetResource(String path, String resName)
	{
		//String scriptsRoot = LuaEngine.getInstance().GetScriptsRoot();
		int primaryLoad = ToppingEngine.getInstance().GetPrimaryLoad();
		switch(primaryLoad)
		{
			case ToppingEngine.EXTERNAL_DATA:
			{
				LuaStream ls = new LuaStream();
				ls.SetStream(Defines.GetResourceSdAsset(path + "/", resName.toString()));
				return ls;
			}
			case ToppingEngine.INTERNAL_DATA:
			{
				LuaStream ls = new LuaStream();
				ls.SetStream(Defines.GetResourceInternalAsset(path + "/", resName.toString()));
				return ls;
			}
			case ToppingEngine.RESOURCE_DATA:
			{
				LuaStream ls = new LuaStream();
				ls.SetStream(Defines.GetResourceAsset(path + "/", resName.toString()));
				return ls;
			}
			default:
			{
				LuaStream ls = new LuaStream();
				ls.SetStream(Defines.GetResourceAsset(path + "/", resName.toString()));
				return ls;
			}
		}
	}

	/**
	 * This function gets resource based on defines.lua config
	 * @param ref LuaRef resource reference
	 * @return LuaStream of resource
	 */
	@LuaFunction(manual = false, methodName = "GetResourceRef", self = LuaResource.class, arguments = { LuaRef.class })
	public static LuaStream GetResourceRef(LuaRef ref)
	{
		LuaStream ls = new LuaStream();
		Context context = ToppingEngine.getInstance().GetContext();
		String resourceTypeName = context.getResources().getResourceTypeName(ref.getRef());
		if(resourceTypeName.equals("drawable"))
		{
			Drawable value = ContextCompat.getDrawable(context, ref.getRef());
			if(value instanceof BitmapDrawable)
			{
				BitmapDrawable bitDw = ((BitmapDrawable) value);
				Bitmap bitmap = bitDw.getBitmap();
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
				byte[] imageInByte = stream.toByteArray();
				System.out.println("........length......" + imageInByte);
				ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
				ls.SetStream(bis);
			}
		}
		return ls;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId() 
	{
		return "LuaResource";
	}
}
