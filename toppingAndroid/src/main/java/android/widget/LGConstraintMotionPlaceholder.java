package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.helper.widget.MotionPlaceholder;
import androidx.constraintlayout.utils.widget.MotionButton;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintMotionPlaceholder")
public class LGConstraintMotionPlaceholder extends LGView implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintMotionPlaceholder(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintMotionPlaceholder(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintMotionPlaceholder(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGConstraintMotionPlaceholder(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "MotionPlaceholder");
        if(view == null)
            view = new MotionPlaceholder(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "MotionPlaceholder", attrs);
        if(view == null)
            view = new MotionPlaceholder(context, attrs);
    }
}

