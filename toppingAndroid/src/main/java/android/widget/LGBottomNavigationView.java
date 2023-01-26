package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGBottomNavigationView")
public class LGBottomNavigationView extends LGFrameLayout implements LuaInterface {

    private LuaTranslator ltTabSelectedListener;
    private LuaTranslator ltCanSelectTab;

    /**
     * (Ignore)
     */
    public LGBottomNavigationView(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGBottomNavigationView(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGBottomNavigationView(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGBottomNavigationView(LuaContext context, AttributeSet attrs, int defStyle) {
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
            view = lc.getLayoutInflater().createView(context, "BottomNavigationView", null);
            if (view == null)
                view = new BottomNavigationView(context);
        } else {
            view = lc.getLayoutInflater().createView(context, "BottomNavigationView", attrs);
            if (view == null)
                view = new BottomNavigationView(context, attrs);
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

    @LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGBottomNavigationView.class)
    public static LGBottomNavigationView create(LuaContext context) {
        return new LGBottomNavigationView(context);
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
     * Set can tab select listener
     * @param ltCanSelectTab
     */
    @LuaFunction(manual = false, methodName = "setCanSelectTab", arguments = { LuaTranslator.class })
    public void setCanSelectTab(LuaTranslator ltCanSelectTab) {
        this.ltCanSelectTab = ltCanSelectTab;
    }

    /**
     * (Ignore)
     */
    @Override
    public String GetId() {
        return "LGBottomNavigationView";
    }
}
