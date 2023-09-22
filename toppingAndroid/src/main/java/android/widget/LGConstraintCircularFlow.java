package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.helper.widget.CircularFlow;
import androidx.constraintlayout.widget.Barrier;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintCircularFlow")
public class LGConstraintCircularFlow extends LGView implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintCircularFlow(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintCircularFlow(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintCircularFlow(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGConstraintCircularFlow(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "CircularFlow");
        if(view == null)
            view = new CircularFlow(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "CircularFlow", attrs);
        if(view == null)
            view = new CircularFlow(context, attrs);
    }
}

