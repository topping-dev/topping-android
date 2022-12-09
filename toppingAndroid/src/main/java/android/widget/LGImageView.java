package android.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import java.io.InputStream;

import dev.topping.android.LuaStream;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;

/**
 * ImageView
 */
@LuaClass(className = "LGImageView")
public class LGImageView extends LGView implements LuaInterface
{
	/**
	 * Creates LGImageView Object From Lua.
	 * @param lc LuaContext
	 * @param luaId String
	 * @return LGImageView
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class, String.class }, self = LGImageView.class)
	public static LGImageView Create(LuaContext lc, String luaId)
	{
		LGImageView iv = new LGImageView(lc);
		iv.luaId = luaId;
		iv.view.setTag(iv);
		return iv;
	}

	/**
	 * (Ignore)
	 */
	public LGImageView(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGImageView(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGImageView(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGImageView(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.GetLayoutInflater().createView(context, "ImageView");
		if(view == null)
			view = new ImageView(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.GetLayoutInflater().createView(context, "ImageView", attrs);
		if(view == null)
			view = new ImageView(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.GetLayoutInflater().createView(context, "ImageView", attrs);
		if(view == null)
			view = new ImageView(context, attrs, defStyle);
	}

	/**
	 * Sets the image view from LuaStream stream
	 * @param stream
	 */
	@LuaFunction(manual = false, methodName = "SetImage", arguments = { LuaStream.class })
	public void SetImage(LuaStream stream)
	{
		InputStream is = (InputStream)stream.GetStreamInternal();
		BitmapDrawable bd = new BitmapDrawable(is);
		((ImageView)view).setImageDrawable(bd);
	}

	/**
	 * Sets the image view from ref
	 * @param ref
	 */
	@LuaFunction(manual = false, methodName = "SetImageRef", arguments = { LuaRef.class })
	public void SetImageRef(LuaRef ref)
	{
		((ImageView)view).setImageResource(ref.getRef());
	}
}
