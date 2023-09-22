package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.Barrier;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintFlow")
public class LGConstraintFlow extends LGView implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintFlow(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintFlow(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintFlow(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGConstraintFlow(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "Flow");
        if(view == null)
            view = new Flow(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "Flow", attrs);
        if(view == null)
            view = new Flow(context, attrs);
    }
}

