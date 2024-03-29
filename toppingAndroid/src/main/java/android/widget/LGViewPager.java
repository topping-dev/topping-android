package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import dev.topping.android.LuaTab;
import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.luagui.LuaContext;

/**
 * ViewPager
 */
@LuaClass(className = "LGViewPager")
public class LGViewPager extends LGViewGroup {
    /**
     * (Ignore)
     */
    public LGViewPager(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGViewPager(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGViewPager(LuaContext context, AttributeSet attrs, int defStyle) {
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
        if(attrs == null) {
            view = lc.getLayoutInflater().createView(context, "ViewPager");
            if (view == null)
                view = new ViewPager2(context);
        }
        else {
            view = lc.getLayoutInflater().createView(context, "ViewPager", attrs);
            if (view == null)
                view = new ViewPager2(context, attrs);
        }
    }

    /**
     * Set pager adapter
     * @param adapter
     */
    @LuaFunction(manual = false, methodName = "setAdapter", arguments = { LGFragmentStateAdapter.class })
    public void setAdapter(LGFragmentStateAdapter adapter) {
        ((ViewPager2)view).setAdapter(adapter);
    }

    /**
     * Set tab layout
     * @param tabLayout
     * @param ltTab +fun(viewPager: LGViewPager, position: number):LuaTab
     */
    @LuaFunction(manual = false, methodName = "setTabLayout", arguments = { LGTabLayout.class, LuaTranslator.class })
    public void setTabLayout(LGTabLayout tabLayout, LuaTranslator ltTab) {
        new TabLayoutMediator((TabLayout) tabLayout.view, (ViewPager2) view, (tab, position) -> {
            LuaTab luaTab = (LuaTab) ltTab.callIn(position);
            luaTab.createTab(tab);
        }).attach();
    }
}
