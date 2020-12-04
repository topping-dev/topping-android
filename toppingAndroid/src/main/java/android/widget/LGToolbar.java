package android.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import java.io.InputStream;

import androidx.appcompat.widget.Toolbar;
import dev.topping.android.LuaStream;
import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaRef;

@LuaClass(className = "LGToolbar")
public class LGToolbar extends LGView implements LuaInterface
{
    public LGToolbar(Context context)
    {
        super(context);
    }

    public LGToolbar(Context context, String luaId)
    {
        super(context, luaId);
    }

    public LGToolbar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public LGToolbar(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public void Setup(Context context)
    {
        view = new Toolbar(context);
    }

    @Override
    public void Setup(Context context, AttributeSet attrs)
    {
        view = new Toolbar(context, attrs);
    }

    @Override
    public void Setup(Context context, AttributeSet attrs, int defStyle)
    {
        view = new Toolbar(context, attrs, defStyle);
    }

    @LuaFunction(manual = false, methodName = "SetMenu", arguments = { LuaRef.class })
    public void SetMenu(LuaRef ref)
    {
        ((Toolbar)view).inflateMenu(ref.getRef());
    }

    @LuaFunction(manual = false, methodName = "SetLogo", arguments = { LuaStream.class })
    public void SetLogo(LuaStream logo)
    {
        InputStream is = (InputStream)logo.GetStreamInternal();
        BitmapDrawable bd = new BitmapDrawable(is);
        ((Toolbar)view).setLogo(bd);
    }

    @LuaFunction(manual = false, methodName = "SetNavigationIcon", arguments = { LuaStream.class })
    public void SetNavigationIcon(LuaStream logo)
    {
        InputStream is = (InputStream)logo.GetStreamInternal();
        BitmapDrawable bd = new BitmapDrawable(is);
        ((Toolbar)view).setNavigationIcon(bd);
    }

    @LuaFunction(manual = false, methodName = "SetOverflowIcon", arguments = { LuaStream.class })
    public void SetOverflowIcon(LuaStream logo)
    {
        InputStream is = (InputStream)logo.GetStreamInternal();
        BitmapDrawable bd = new BitmapDrawable(is);
        ((Toolbar)view).setOverflowIcon(bd);
    }

    @LuaFunction(manual = false, methodName = "GetTitle")
    public String GetTitle()
    {
        return ((Toolbar)view).getTitle().toString();
    }

    @LuaFunction(manual = false, methodName = "SetTitle", arguments = { String.class })
    public void SetTitle(String text)
    {
        ((Toolbar)view).setTitle(text);
    }

    @LuaFunction(manual = false, methodName = "SetTitleTextColor", arguments = { String.class })
    public void SetTitleTextColor(String color)
    {
        int colorInt = Color.parseColor(color);
        ((Toolbar)view).setTitleTextColor(colorInt);
    }

    @LuaFunction(manual = false, methodName = "SetTitleTextApperance", arguments = { Integer.class })
    public void SetTitleTextApperance(int ref)
    {
        ((Toolbar)view).setTitleTextAppearance(lc.GetContext(), ref);
    }

    @LuaFunction(manual = false, methodName = "GetSubtitle")
    public String GetSubtitle()
    {
        return ((Toolbar)view).getSubtitle().toString();
    }

    @LuaFunction(manual = false, methodName = "SetSubtitle", arguments = { String.class })
    public void SetSubtitle(String text)
    {
        ((Toolbar)view).setSubtitle(text);
    }

    @LuaFunction(manual = false, methodName = "SetSubtitleTextColor", arguments = { String.class })
    public void SetSubtitleTextColor(String color)
    {
        int colorInt = Color.parseColor(color);
        ((Toolbar)view).setSubtitleTextColor(colorInt);
    }

    @LuaFunction(manual = false, methodName = "SetSubtitleTextApperance", arguments = { LuaRef.class })
    public void SetSubtitleTextApperance(LuaRef ref)
    {
        ((Toolbar)view).setSubtitleTextAppearance(lc.GetContext(), ref.getRef());
    }

    @LuaFunction(manual = false, methodName = "SetNavigationOnClickListener", arguments = { LuaTranslator.class })
    public void SetNavigationOnClickListener(LuaTranslator lt)
    {
        if(lt == null)
        {
            ((Toolbar)view).setNavigationOnClickListener(null);
            return;
        }
        ((Toolbar)view).setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                lt.CallIn();
            }
        });
    }

    @LuaFunction(manual = false, methodName = "SetMenuItemClickListener", arguments = { LuaTranslator.class })
    public void SetMenuItemClickListener(LuaTranslator lt)
    {
        if(lt == null)
        {
            ((Toolbar)view).setOnMenuItemClickListener(null);
            return;
        }
        ((Toolbar)view).setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                lt.CallIn(item.getItemId());
                return true;
            }
        });
    }
}
