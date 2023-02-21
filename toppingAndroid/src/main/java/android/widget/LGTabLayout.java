package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.tabs.TabLayout;

import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * Tab layout
 */
@LuaClass(className = "LGTabLayout")
public class LGTabLayout extends LGScrollView implements LuaInterface {

    private LuaTranslator ltTabSelectedListener;

    /**
     * (Ignore)
     */
    public LGTabLayout(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGTabLayout(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGTabLayout(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGTabLayout(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    @Override
    public void Setup(Context context) {
        Setup(null, attrs, -1);
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
        if(attrs == null) {
            view = lc.getLayoutInflater().createView(context, "TabLayout", null);
            if (view == null)
                view = new TabLayout(context);
        } else {
            view = lc.getLayoutInflater().createView(context, "TabLayout", attrs);
            if (view == null)
                view = new TabLayout(context, attrs);
        }

        ((TabLayout)view).addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(ltTabSelectedListener != null) {
                    ltTabSelectedListener.callIn(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Set tab selected listener
     * @param ltTabSelectedListener
     */
    @LuaFunction(manual = false, methodName = "setTabSelectedListener", arguments = { LuaTranslator.class })
    public void setTabSelectedListener(LuaTranslator ltTabSelectedListener) {
        this.ltTabSelectedListener = ltTabSelectedListener;
    }

    /**
     * (Ignore)
     */
    @Override
    public String GetId() {
        return "LGTabLayout";
    }
}
