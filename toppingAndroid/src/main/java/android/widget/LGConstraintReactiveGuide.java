package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.constraintlayout.widget.ReactiveGuide;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintReactiveGuide")
public class LGConstraintReactiveGuide extends LGView implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintReactiveGuide(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintReactiveGuide(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintReactiveGuide(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGConstraintReactiveGuide(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "ReactiveGuide");
        if(view == null)
            view = new ReactiveGuide(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "ReactiveGuide", attrs);
        if(view == null)
            view = new ReactiveGuide(context, attrs);
    }
}

