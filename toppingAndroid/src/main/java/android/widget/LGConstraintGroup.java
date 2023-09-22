package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.Group;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintGroup")
public class LGConstraintGroup extends LGView implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintGroup(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintGroup(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintGroup(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGConstraintGroup(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "Group");
        if(view == null)
            view = new Group(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "Group", attrs);
        if(view == null)
            view = new Group(context, attrs);
    }
}

