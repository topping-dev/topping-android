package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.utils.widget.MotionButton;
import androidx.constraintlayout.widget.Placeholder;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintPlaceholder")
public class LGConstraintPlaceholder extends LGView implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintPlaceholder(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintPlaceholder(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintPlaceholder(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGConstraintPlaceholder(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "Placeholder");
        if(view == null)
            view = new Placeholder(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "Placeholder", attrs);
        if(view == null)
            view = new Placeholder(context, attrs);
    }
}

