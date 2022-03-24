package android.widget;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import java.io.InputStream;
import java.lang.reflect.Constructor;

import androidx.annotation.XmlRes;
import androidx.appcompat.app.AppCompatViewInflater;
import androidx.appcompat.widget.Toolbar;
import dev.topping.android.LuaStream;
import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;
import dev.topping.android.osspecific.ClassCache;

@LuaClass(className = "LGToolbar")
public class LGToolbar extends LGView implements LuaInterface
{
    /**
     * (Ignore)
     */
    public LGToolbar(LuaContext context)
    {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGToolbar(LuaContext context, String luaId)
    {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGToolbar(LuaContext context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGToolbar(LuaContext context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    @Override
    public void Setup(Context context)
    {
        view = lc.GetLayoutInflater().createView(context, "Toolbar");
        if(view == null){
            if(attrs instanceof XmlResourceParser)
            {
                String name = ((XmlResourceParser) attrs).getName();
                if(name.equals("com.google.android.material.appbar.MaterialToolbar"))
                {
                    try {
                        Class clz = ClassCache.forName("com.google.android.material.appbar.MaterialToolbar");
                        Class[] types = {Context.class};
                        Constructor constructor = clz.getConstructor(types);

                        Object[] parameters = {context};
                        view = (View) constructor.newInstance(parameters);
                    } catch (Exception e) {
                        view = new Toolbar(context);
                    }
                }
                else
                {
                    view = new Toolbar(context, attrs);
                }
            }
        }
        else
            view = new Toolbar(context, attrs);
    }

    /**
     * (Ignore)
     */
    @Override
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.GetLayoutInflater().createView(context, "Toolbar", attrs);
        if(view == null){
            if(attrs instanceof XmlResourceParser)
            {
                String name = ((XmlResourceParser) attrs).getName();
                if(name.equals("com.google.android.material.appbar.MaterialToolbar"))
                {
                    try {
                        Class clz = ClassCache.forName("com.google.android.material.appbar.MaterialToolbar");
                        Class[] types = {Context.class, AttributeSet.class};
                        Constructor constructor = clz.getConstructor(types);

                        Object[] parameters = {context, attrs};
                        view = (View) constructor.newInstance(parameters);
                    } catch (Exception e) {
                        view = new Toolbar(context, attrs);
                    }
                }
                else
                {
                    view = new Toolbar(context, attrs);
                }
            }
        }
        else
            view = new Toolbar(context, attrs);
    }

    /**
     * (Ignore)
     */
    @Override
    public void Setup(Context context, AttributeSet attrs, int defStyle)
    {
        view = lc.GetLayoutInflater().createView(context, "Toolbar", attrs);
        if(view == null) {
            if(attrs instanceof XmlResourceParser)
            {
                String name = ((XmlResourceParser) attrs).getName();
                if(name.equals("com.google.android.material.appbar.MaterialToolbar"))
                {
                    try {
                        Class clz = ClassCache.forName("com.google.android.material.appbar.MaterialToolbar");
                        Class[] types = {Context.class, AttributeSet.class, Integer.TYPE};
                        Constructor constructor = clz.getConstructor(types);

                        Object[] parameters = {context, attrs, defStyle};
                        view = (View) constructor.newInstance(parameters);
                    } catch (Exception e) {
                        view = new Toolbar(context, attrs, defStyle);
                    }
                }
                else
                {
                    view = new Toolbar(context, attrs);
                }
            }
        }
        else
            view = new Toolbar(context, attrs);
    }

    /**
     * Sets the toolbar menu
     * @param ref
     */
    @LuaFunction(manual = false, methodName = "SetMenu", arguments = { LuaRef.class })
    public void SetMenu(LuaRef ref)
    {
        ((Toolbar)view).inflateMenu(ref.getRef());
    }

    /**
     * Sets the toolbar logo
     * @param logo
     */
    @LuaFunction(manual = false, methodName = "SetLogo", arguments = { LuaStream.class })
    public void SetLogo(LuaStream logo)
    {
        InputStream is = (InputStream)logo.GetStreamInternal();
        BitmapDrawable bd = new BitmapDrawable(is);
        ((Toolbar)view).setLogo(bd);
    }

    /**
     * Sets the toolbar navigation icon
     * @param logo
     */
    @LuaFunction(manual = false, methodName = "SetNavigationIcon", arguments = { LuaStream.class })
    public void SetNavigationIcon(LuaStream logo)
    {
        InputStream is = (InputStream)logo.GetStreamInternal();
        BitmapDrawable bd = new BitmapDrawable(is);
        ((Toolbar)view).setNavigationIcon(bd);
    }

    /**
     * Sets the toolbar overflow icon
     * @param logo
     */
    @LuaFunction(manual = false, methodName = "SetOverflowIcon", arguments = { LuaStream.class })
    public void SetOverflowIcon(LuaStream logo)
    {
        InputStream is = (InputStream)logo.GetStreamInternal();
        BitmapDrawable bd = new BitmapDrawable(is);
        ((Toolbar)view).setOverflowIcon(bd);
    }

    /**
     * Gets the toolbar title
     * @return String
     */
    @LuaFunction(manual = false, methodName = "GetTitle")
    public String GetTitle()
    {
        return ((Toolbar)view).getTitle().toString();
    }

    /**
     * Sets the toolbar title
     * @param text
     */
    @LuaFunction(manual = false, methodName = "SetTitle", arguments = { String.class })
    public void SetTitle(String text)
    {
        ((Toolbar)view).setTitle(text);
    }

    /**
     * Sets the toolbar title
     * @param ref
     */
    @LuaFunction(manual = false, methodName = "SetTitleRef", arguments = { LuaRef.class })
    public void SetTitleRef(LuaRef ref)
    {
        ((Toolbar)view).setTitle(ref.getRef());
    }

    /**
     * Sets the toolbar title text color
     * @param color
     */
    @LuaFunction(manual = false, methodName = "SetTitleTextColor", arguments = { String.class })
    public void SetTitleTextColor(String color)
    {
        int colorInt = Color.parseColor(color);
        ((Toolbar)view).setTitleTextColor(colorInt);
    }

    /**
     * Sets the toolbar title text color
     * @param ref
     */
    @LuaFunction(manual = false, methodName = "SetTitleTextColorRef", arguments = { LuaRef.class })
    public void SetTitleTextColorRef(LuaRef ref)
    {
        ((Toolbar)view).setTitleTextColor(view.getResources().getColor(ref.getRef()));
    }

    /**
     * Sets the toolbar title text appearance
     * @param ref
     */
    @LuaFunction(manual = false, methodName = "SetTitleTextAppearance", arguments = { LuaRef.class })
    public void SetTitleTextAppearance(LuaRef ref)
    {
        ((Toolbar)view).setTitleTextAppearance(lc.GetContext(), ref.getRef());
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

    @LuaFunction(manual = false, methodName = "SetSubtitleRef", arguments = { LuaRef.class })
    public void SetSubtitle(LuaRef ref)
    {
        ((Toolbar)view).setSubtitle(ref.getRef());
    }

    @LuaFunction(manual = false, methodName = "SetSubtitleTextColor", arguments = { String.class })
    public void SetSubtitleTextColor(String color)
    {
        int colorInt = Color.parseColor(color);
        ((Toolbar)view).setSubtitleTextColor(colorInt);
    }

    @LuaFunction(manual = false, methodName = "SetSubtitleTextColorRef", arguments = { LuaRef.class })
    public void SetSubtitleTextColorRef(LuaRef ref)
    {
        ((Toolbar)view).setSubtitleTextColor(view.getResources().getColor(ref.getRef()));
    }

    @LuaFunction(manual = false, methodName = "SetSubtitleTextApperance", arguments = { LuaRef.class })
    public void SetSubtitleTextApperance(LuaRef ref)
    {
        ((Toolbar)view).setSubtitleTextAppearance(lc.GetContext(), ref.getRef());
    }

    /**
     * Sets the toolbar navigation listener
     * @param lt
     */
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

    /**
     * Sets the toolbar menu item listener
     * @param lt
     */
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
