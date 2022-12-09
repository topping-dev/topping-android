package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentContainerView;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * App Container View for navigation
 */
@LuaClass(className = "LGFragmentAppContainerView")
public class LGFragmentContainerView extends LGViewGroup implements LuaInterface {
    private FragmentContainerView fragmentContainerView;

    /**
     * (Ignore)
     */
    public LGFragmentContainerView(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGFragmentContainerView(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGFragmentContainerView(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGFragmentContainerView(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    @Override
    public void Setup(Context context) {
        super.Setup(context);
        view = new LinearLayout(context);
        fragmentContainerView = new FragmentContainerView(context);
    }

    /**
     * (Ignore)
     */
    @Override
    public void Setup(Context context, AttributeSet attrs) {
        super.Setup(context, attrs);
        view = new LinearLayout(context, attrs);
        fragmentContainerView = new FragmentContainerView(context, attrs);
    }

    /**
     * (Ignore)
     */
    @Override
    public void Setup(Context context, AttributeSet attrs, int defStyle) {
        super.Setup(context, attrs, defStyle);
        view = new LinearLayout(context, attrs, defStyle);
        fragmentContainerView = new FragmentContainerView(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    @Override
    public void AfterSetup(Context context) {
        super.AfterSetup(context);
        fragmentContainerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((LinearLayout)view).addView(fragmentContainerView);
    }
}
