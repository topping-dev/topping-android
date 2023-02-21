package android.widget;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Constructor;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;
import dev.topping.android.osspecific.ClassCache;

/**
 * Toolbar
 */
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
        Setup(context, null, -1);
    }

    /**
     * (Ignore)
     */
    @Override
    public void Setup(Context context, AttributeSet attrs)
    {
        Setup(context, attrs, -1);
    }

    /**
     * (Ignore)
     */
    @Override
    public void Setup(Context context, AttributeSet attrs, int defStyle)
    {
        if(attrs != null)
            view = lc.getLayoutInflater().createView(context, "Toolbar", attrs);
        else
            view = lc.getLayoutInflater().createView(context, "Toolbar");
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
    @LuaFunction(manual = false, methodName = "setMenu", arguments = { LuaRef.class })
    public void setMenu(LuaRef ref)
    {
        ((Toolbar)view).inflateMenu(ref.getRef());
    }

    /**
     * Sets the toolbar logo
     * @param logo
     */
    @LuaFunction(manual = false, methodName = "setLogo", arguments = { LuaRef.class })
    public void setLogo(LuaRef logo)
    {
        ((Toolbar)view).setLogo(ResourcesCompat.getDrawable(view.getContext().getResources(), logo.getRef(), view.getContext().getTheme()));
    }

    /**
     * Sets the toolbar navigation icon
     * @param icon
     */
    @LuaFunction(manual = false, methodName = "setNavigationIcon", arguments = { LuaRef.class })
    public void setNavigationIcon(LuaRef icon)
    {
        ((Toolbar)view).setNavigationIcon(ResourcesCompat.getDrawable(view.getContext().getResources(), icon.getRef(), view.getContext().getTheme()));
    }

    /**
     * Sets the toolbar overflow icon
     * @param icon
     */
    @LuaFunction(manual = false, methodName = "setOverflowIcon", arguments = { LuaRef.class })
    public void setOverflowIcon(LuaRef icon)
    {
        ((Toolbar)view).setOverflowIcon(ResourcesCompat.getDrawable(view.getContext().getResources(), icon.getRef(), view.getContext().getTheme()));
    }

    /**
     * Gets the toolbar title
     * @return String
     */
    @LuaFunction(manual = false, methodName = "getTitle")
    public String getTitle()
    {
        return ((Toolbar)view).getTitle().toString();
    }

    /**
     * Sets the toolbar title
     * @param text
     */
    @LuaFunction(manual = false, methodName = "setTitle", arguments = { String.class })
    public void setTitleInternal(String text)
    {
        ((Toolbar)view).setTitle(text);
    }

    /**
     * Sets the toolbar title
     * @param ref
     */
    @LuaFunction(manual = false, methodName = "setTitleRef", arguments = { LuaRef.class })
    public void setTitle(LuaRef ref)
    {
        ((Toolbar)view).setTitle(ref.getRef());
    }

    /**
     * Sets the toolbar title text color
     * @param ref
     */
    @LuaFunction(manual = false, methodName = "setTitleTextColor", arguments = { LuaRef.class })
    public void setTitleTextColor(LuaRef ref)
    {
        ((Toolbar)view).setTitleTextColor(view.getResources().getColor(ref.getRef()));
    }

    /**
     * Sets the toolbar title text appearance
     * @param ref
     */
    @LuaFunction(manual = false, methodName = "setTitleTextAppearance", arguments = { LuaRef.class })
    public void setTitleTextAppearance(LuaRef ref)
    {
        ((Toolbar)view).setTitleTextAppearance(lc.getContext(), ref.getRef());
    }

    /**
     * Get subtitle
     * @return subtitle
     */
    @LuaFunction(manual = false, methodName = "getSubtitle")
    public String getSubtitle()
    {
        return ((Toolbar)view).getSubtitle().toString();
    }

    /**
     * Set subtitle
     * @param text
     */
    @LuaFunction(manual = false, methodName = "setSubtitle", arguments = { String.class })
    public void setSubtitleInternal(String text)
    {
        ((Toolbar)view).setSubtitle(text);
    }

    /**
     * Set subtitle
     * @param LuaRef
     */
    @LuaFunction(manual = false, methodName = "setSubtitleRef", arguments = { LuaRef.class })
    public void setSubtitle(LuaRef ref)
    {
        ((Toolbar)view).setSubtitle(ref.getRef());
    }

    /**
     * Set subtitle text color
     * @param LuaRef
     */
    @LuaFunction(manual = false, methodName = "setSubtitleTextColor", arguments = { LuaRef.class })
    public void setSubtitleTextColor(LuaRef ref)
    {
        ((Toolbar)view).setSubtitleTextColor(view.getResources().getColor(ref.getRef()));
    }

    /**
     * Set subtitle text appearance
     * @param LuaRef
     */
    @LuaFunction(manual = false, methodName = "setSubtitleTextApperance", arguments = { LuaRef.class })
    public void setSubtitleTextApperance(LuaRef ref)
    {
        ((Toolbar)view).setSubtitleTextAppearance(lc.getContext(), ref.getRef());
    }

    /**
     * Sets the toolbar navigation listener
     * @param lt +fun(toolbar: LGToolbar):void
     */
    @LuaFunction(manual = false, methodName = "setNavigationOnClickListener", arguments = { LuaTranslator.class })
    public void setNavigationOnClickListener(LuaTranslator lt)
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
                lt.callIn();
            }
        });
    }

    /**
     * Sets the toolbar menu item listener
     * @param lt +fun(toolbar: LGToolbar):void
     */
    @LuaFunction(manual = false, methodName = "setMenuItemClickListener", arguments = { LuaTranslator.class })
    public void setMenuItemClickListener(LuaTranslator lt)
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
                lt.callIn(item.getItemId());
                return true;
            }
        });
    }
}
