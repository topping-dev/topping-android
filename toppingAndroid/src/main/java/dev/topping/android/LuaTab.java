package dev.topping.android;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LGTabLayout;
import android.widget.LGView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.InputStream;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;
import dev.topping.android.luagui.LuaViewInflator;

@LuaClass(className = "LuaTab")
public class LuaTab extends LGView implements LuaInterface {

    private LuaRef textRef;
    private String text;
    private Drawable icon;
    private LuaRef iconRef;
    private LGView customView;

    /**
     * (Ignore)
     */
    public LuaTab(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LuaTab(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LuaTab(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LuaTab(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    @Override
    public void Setup(Context context) {
        Setup(context, null, -1);
    }

    /**
     * (Ignore)
     */
    @Override
    public void Setup(Context context, AttributeSet attrs) {
        Setup(context, attrs, -1);
    }

    /**
     * (Ignore)
     */
    @Override
    public void Setup(Context context, AttributeSet attrs, int defStyle) {
        super.Setup(context, attrs, defStyle);

        if(parent instanceof LGTabLayout) {
            TabLayout tabLayout = (TabLayout) parent.view;

            TabItem ti = new TabItem(context, attrs);

            text = (String) ti.text;
            icon = ti.icon;
            if(ti.customLayout != View.NO_ID)
                customView = LuaViewInflator.create(lc).inflate(LuaRef.withValue(ti.customLayout), null);

            TabLayout.Tab tab = new TabLayout.Tab();
            tabLayout.addTab(createTab(tab));
        }
    }

    /**
     * Create LuaTab
     * @return LuaTab
     */
    @LuaFunction(manual = false, methodName = "create", self = LuaTab.class)
    public static LuaTab create() {
        return new LuaTab(new LuaContext());
    }

    /**
     * Set tab title
     * @param text
     */
    @LuaFunction(manual = false, methodName = "setText", arguments = { String.class })
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Set tab title
     * @param text
     */
    @LuaFunction(manual = false, methodName = "setTextRef", arguments = { LuaRef.class })
    public void setTextRef(LuaRef text) {
        textRef = text;
    }

    /**
     * Set tab icon
     * @param icon
     */
    @LuaFunction(manual = false, methodName = "setIcon", arguments = { LuaRef.class })
    public void setIcon(LuaRef icon) {
        iconRef = icon;
    }

    /**
     * Set tab icon
     * @param icon
     */
    @LuaFunction(manual = false, methodName = "setIconStream", arguments = { LuaStream.class })
    public void setIconStream(LuaStream icon) {
        InputStream is = (InputStream)icon.getStreamInternal();
        this.icon = new BitmapDrawable(is);
    }

    /**
     * Set tab custom view
     * @param view
     */
    @LuaFunction(manual = false, methodName = "setCustomView", arguments = { LGView.class })
    public void setCustomView(LGView view) {
        this.customView = view;
    }

    /**
     * (Ignore)
     */
    public TabLayout.Tab createTab(TabLayout.Tab tab) {
        if(text != null) {
            tab.setText(text);
        }
        if(textRef != null) {
            tab.setText(textRef.getRef());
        }
        if(icon != null) {
            tab.setIcon(icon);
        }
        if(iconRef != null) {
            tab.setIcon(iconRef.getRef());
        }
        if(customView != null) {
            tab.setTag(customView);
            tab.setCustomView(customView.view);
        }
        return tab;
    }

    /**
     * (Ignore)
     */
    @Override
    public String GetId() {
        return "LuaTab";
    }
}
