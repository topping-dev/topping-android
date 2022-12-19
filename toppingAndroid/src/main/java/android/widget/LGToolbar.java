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
import androidx.core.content.res.ResourcesCompat;

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
    @LuaFunction(manual = false, methodName = "SetLogo", arguments = { LuaRef.class })
    public void SetLogo(LuaRef logo)
    {
        ((Toolbar)view).setLogo(ResourcesCompat.getDrawable(view.getContext().getResources(), logo.getRef(), view.getContext().getTheme()));
    }

    /**
     * Sets the toolbar navigation icon
     * @param logo
     */
    @LuaFunction(manual = false, methodName = "SetNavigationIcon", arguments = { LuaRef.class })
    public void SetNavigationIcon(LuaRef icon)
    {
        ((Toolbar)view).setNavigationIcon(ResourcesCompat.getDrawable(view.getContext().getResources(), icon.getRef(), view.getContext().getTheme()));
    }

    /**
     * Sets the toolbar overflow icon
     * @param logo
     */
    @LuaFunction(manual = false, methodName = "SetOverflowIcon", arguments = { LuaRef.class })
    public void SetOverflowIcon(LuaRef icon)
    {
        ((Toolbar)view).setOverflowIcon(ResourcesCompat.getDrawable(view.getContext().getResources(), icon.getRef(), view.getContext().getTheme()));
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
    public void SetTitleInternal(String text)
    {
        ((Toolbar)view).setTitle(text);
    }

    /**
     * Sets the toolbar title
     * @param ref
     */
    @LuaFunction(manual = false, methodName = "SetTitleRef", arguments = { LuaRef.class })
    public void SetTitle(LuaRef ref)
    {
        ((Toolbar)view).setTitle(ref.getRef());
    }

    /**
     * Sets the toolbar title text color
     * @param ref
     */
    @LuaFunction(manual = false, methodName = "SetTitleTextColor", arguments = { LuaRef.class })
    public void SetTitleTextColor(LuaRef ref)
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
    public void SetSubtitleInternal(String text)
    {
        ((Toolbar)view).setSubtitle(text);
    }

    @LuaFunction(manual = false, methodName = "SetSubtitleRef", arguments = { LuaRef.class })
    public void SetSubtitle(LuaRef ref)
    {
        ((Toolbar)view).setSubtitle(ref.getRef());
    }

    @LuaFunction(manual = false, methodName = "SetSubtitleTextColor", arguments = { LuaRef.class })
    public void SetSubtitleTextColor(LuaRef ref)
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
